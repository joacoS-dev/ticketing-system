const API_BASE = "";
let qrInterval = null;
let qrRegenerating = false;

const $ = (selector) => document.querySelector(selector);
const $$ = (selector) => document.querySelectorAll(selector);

function getToken() {
  return localStorage.getItem("token");
}

function getRole() {
  return localStorage.getItem("role");
}

function setToken(token, role) {
  localStorage.setItem("token", token);
  localStorage.setItem("role", role);
  actualizarEstadoSesion();
  actualizarPermisos();
  cargarOpcionesCompra();
  cargarOpcionesTransferencia();
  cargarOpcionesAdmin();
  cargarOpcionesFuncionario();
}

function clearToken() {
  detenerQrAutomatico();
  localStorage.removeItem("token");
  localStorage.removeItem("role");
  actualizarEstadoSesion();
  actualizarPermisos();
  cargarOpcionesCompra();
  cargarOpcionesTransferencia();
  cargarOpcionesAdmin();
  cargarOpcionesFuncionario();
}

function actualizarEstadoSesion() {
  const role = getRole();
  $("#estadoSesion").textContent = getToken() ? "Sesión iniciada (" + role + ")" : "Sin sesión";
}

function mostrarMensaje(texto, esError = false) {
  const div = $("#mensaje");
  div.textContent = texto;
  div.className = esError ? "error" : "ok";
}

function mostrarSalida(data, salidaId) {
  const salida = $("#" + salidaId);
  if (!salida) {
    return;
  }
  salida.textContent = typeof data === "string" ? data : JSON.stringify(data, null, 2);
}

function mostrarError(error, salidaId) {
  const mensaje = error.message || String(error);
  mostrarMensaje(mensaje, true);
  mostrarSalida("Error: " + mensaje, salidaId);
}

function datosFormulario(form) {
  return Object.fromEntries(new FormData(form).entries());
}

function numero(valor) {
  return Number(valor);
}

function tienePermiso(elemento) {
  if (elemento.dataset.auth === "true" && !getToken()) {
    return false;
  }

  if (!elemento.dataset.roles) {
    return true;
  }

  const role = getRole();
  return Boolean(getToken() && role && elemento.dataset.roles.split(",").includes(role));
}

function actualizarPermisos() {
  $$("[data-auth], [data-roles]").forEach((elemento) => {
    const permitido = tienePermiso(elemento);

    if (elemento.tagName === "FORM") {
      elemento.querySelectorAll("input, select, button").forEach((control) => {
        control.disabled = !permitido;
      });
    } else {
      elemento.disabled = !permitido;
    }
  });
}

function limpiarSelect(select, texto) {
  select.innerHTML = "";
  select.append(new Option(texto, ""));
}

function actualizarEquiposDisponibles() {
  const localTeamSelect = $("#localTeamSelect");
  const visitorTeamSelect = $("#visitorTeamSelect");
  const localTeamId = localTeamSelect.value;
  const visitorTeamId = visitorTeamSelect.value;

  Array.from(visitorTeamSelect.options).forEach((option) => {
    option.disabled = Boolean(option.value && option.value === localTeamId);
  });

  Array.from(localTeamSelect.options).forEach((option) => {
    option.disabled = Boolean(option.value && option.value === visitorTeamId);
  });
}

async function llamarApi(ruta, opciones = {}) {
  const headers = opciones.headers || {};

  if (opciones.body) {
    headers["Content-Type"] = "application/json";
  }

  const token = getToken();
  if (token) {
    headers["Authorization"] = "Bearer " + token;
  }

  const respuesta = await fetch(API_BASE + ruta, {
    ...opciones,
    headers
  });

  const texto = await respuesta.text();
  let data = texto;

  try {
    data = texto ? JSON.parse(texto) : null;
  } catch (e) {
    data = texto;
  }

  if (!respuesta.ok) {
    const mensaje = data && typeof data === "object" && data.message
      ? data.message
      : texto || "Error HTTP " + respuesta.status;
    throw new Error(mensaje);
  }

  return data;
}

$("#logoutBtn").addEventListener("click", () => {
  clearToken();
  mostrarMensaje("Sesión cerrada.");
});

async function cargarCodigosPostales() {
  const select = $("#postalCodeSelect");

  try {
    const codigosPostales = await llamarApi("/users/postal-codes");

    select.innerHTML = "";

    if (!codigosPostales || codigosPostales.length === 0) {
      select.append(new Option("No hay códigos postales cargados", ""));
      select.disabled = true;
      mostrarMensaje("No hay códigos postales cargados en la base de datos.", true);
      mostrarSalida("No hay códigos postales cargados en la base de datos.", "salidaRegistro");
      return;
    }

    select.append(new Option("Elegí un código postal", ""));
    codigosPostales.forEach((codigoPostal) => {
      select.append(new Option(codigoPostal, codigoPostal));
    });
    select.disabled = false;
  } catch (error) {
    select.innerHTML = "";
    select.append(new Option("No se pudieron cargar", ""));
    select.disabled = true;
    mostrarError(new Error("Error al cargar códigos postales: " + error.message), "salidaRegistro");
  }
}

$("#loginForm").addEventListener("submit", async (e) => {
  e.preventDefault();
  const body = datosFormulario(e.target);

  try {
    const data = await llamarApi("/users/loginUser", {
      method: "POST",
      body: JSON.stringify(body)
    });

    setToken(data.token, data.role);
    mostrarMensaje("Login correcto. Token guardado.");
    mostrarSalida(data, "salidaLogin");
  } catch (error) {
    mostrarError(new Error("Error al iniciar sesión: " + error.message), "salidaLogin");
  }
});

$("#registerForm").addEventListener("submit", async (e) => {
  e.preventDefault();
  const body = datosFormulario(e.target);

  body.phones = body.phones
    ? body.phones.split(",").map(t => t.trim()).filter(t => t.length > 0)
    : [];

  try {
    const data = await llamarApi("/users/registerUser", {
      method: "POST",
      body: JSON.stringify(body)
    });

    mostrarMensaje("Usuario registrado correctamente. Ahora podés iniciar sesión.");
    mostrarSalida(data, "salidaRegistro");
  } catch (error) {
    mostrarError(new Error("Error al registrar: " + error.message), "salidaRegistro");
  }
});

$$("button[data-get]").forEach((boton) => {
  boton.addEventListener("click", async () => {
    if (boton.disabled) {
      return;
    }

    try {
      const data = await llamarApi(boton.dataset.get);
      mostrarMensaje("Consulta realizada correctamente.");
      mostrarSalida(data, "salidaConsultas");
    } catch (error) {
      mostrarError(new Error("Error en consulta: " + error.message), "salidaConsultas");
    }
  });
});

async function cargarOpcionesCompra() {
  const eventSelect = $("#saleEventSelect");
  const sectionSelect = $("#saleSectionSelect");

  if (getRole() !== "USER") {
    eventSelect.innerHTML = "";
    eventSelect.append(new Option("Iniciá sesión como USER para cargar eventos", ""));
    sectionSelect.innerHTML = "";
    sectionSelect.append(new Option("Elegí un evento", ""));
    return;
  }

  try {
    const eventos = await llamarApi("/sales/events");

    eventSelect.innerHTML = "";
    eventSelect.append(new Option("Elegí un evento", ""));

    eventos.forEach((evento) => {
      const label = "#" + evento.id_evento + " - " + evento.nombre_estadio + " - " + evento.fecha_evento;
      const option = new Option(label, evento.id_evento);
      option.dataset.stadiumId = evento.id_estadio;
      eventSelect.append(option);
    });
  } catch (error) {
    eventSelect.innerHTML = "";
    eventSelect.append(new Option("No se pudieron cargar eventos", ""));
    mostrarError(new Error("Error al cargar eventos para comprar: " + error.message), "salidaCompra");
  }
}

async function cargarSectoresCompra(stadiumId) {
  const sectionSelect = $("#saleSectionSelect");

  sectionSelect.innerHTML = "";

  if (!stadiumId) {
    sectionSelect.append(new Option("Elegí un evento", ""));
    return;
  }

  try {
    const sectores = await llamarApi("/sales/stadiums/" + stadiumId + "/sections");

    if (!sectores || sectores.length === 0) {
      sectionSelect.append(new Option("No hay sectores cargados", ""));
      return;
    }

    sectionSelect.append(new Option("Elegí un sector", ""));
    sectores.forEach((sector) => {
      const label = "#" + sector.id_sector + " - Sector " + sector.letra_sector + " - $" + sector.costo;
      sectionSelect.append(new Option(label, sector.id_sector));
    });
  } catch (error) {
    sectionSelect.append(new Option("No se pudieron cargar sectores", ""));
    mostrarError(new Error("Error al cargar sectores: " + error.message), "salidaCompra");
  }
}

$("#saleEventSelect").addEventListener("change", async (e) => {
  const option = e.target.selectedOptions[0];
  const stadiumId = option ? option.dataset.stadiumId : "";

  await cargarSectoresCompra(stadiumId);
});

$("#saleForm").addEventListener("submit", async (e) => {
  e.preventDefault();
  const data = datosFormulario(e.target);
  const cantidad = numero(data.quantity);

  if (cantidad < 1 || cantidad > 5) {
    mostrarError(new Error("La cantidad debe estar entre 1 y 5."), "salidaCompra");
    return;
  }

  const tickets = [];
  for (let i = 0; i < cantidad; i++) {
    tickets.push({
      sectionId: data.sectionId
    });
  }

  const body = {
    tickets
  };

  try {
    const respuesta = await llamarApi(`/sales/${data.eventId}/createSale`, {
      method: "POST",
      body: JSON.stringify(body)
    });

    mostrarMensaje("Venta creada correctamente.");
    mostrarSalida(respuesta, "salidaCompra");
    await cargarOpcionesTransferencia();
  } catch (error) {
    mostrarError(new Error("Error al crear venta: " + error.message), "salidaCompra");
  }
});

function etiquetaTicketUsuario(ticket) {
  const ticketId = ticket.ticketId ?? ticket.id_entrada;
  const eventId = ticket.eventId ?? ticket.id_evento;
  const stadiumName = ticket.stadiumName ?? ticket.nombre_estadio;
  const sectionId = ticket.sectionId ?? ticket.id_sector;
  const sectionLetter = ticket.sectionLetter ?? ticket.letra_sector;
  const deviceId = ticket.deviceId ?? ticket.id_dispositivo;
  const sector = sectionLetter ? " - Sector " + sectionLetter + " (#" + sectionId + ")" : "";
  const validada = deviceId ? " - Ya validada" : "";
  return "Entrada #" + ticketId + " - Evento #" + eventId + " - " + stadiumName + sector + validada;
}

async function cargarOpcionesTransferencia() {
  const transferTicketSelect = $("#transferTicketSelect");

  if (getRole() !== "USER") {
    limpiarSelect(transferTicketSelect, "Iniciá sesión como USER para cargar tus entradas");
    return;
  }

  try {
    const tickets = await llamarApi("/users/me/tickets");
    const transferibles = (tickets || []).filter((ticket) => !(ticket.deviceId ?? ticket.id_dispositivo));

    limpiarSelect(transferTicketSelect, "Elegí una entrada");
    transferibles.forEach((ticket) => {
      const ticketId = ticket.ticketId ?? ticket.id_entrada;
      transferTicketSelect.append(new Option(etiquetaTicketUsuario(ticket), ticketId));
    });

    if (transferTicketSelect.options.length === 1) {
      limpiarSelect(transferTicketSelect, "No tenés entradas transferibles");
    }
  } catch (error) {
    limpiarSelect(transferTicketSelect, "No se pudieron cargar tus entradas");
    mostrarError(new Error("Error al cargar entradas para transferir: " + error.message), "salidaTransferencia");
  }
}

$("#transferForm").addEventListener("submit", async (e) => {
  e.preventDefault();
  const data = datosFormulario(e.target);

  const body = {
    newOwnerUsername: data.newOwnerUsername,
    newOwnerEmail: data.newOwnerEmail
  };

  try {
    await llamarApi(`/transfers/${data.ticketId}/transferTicket`, {
      method: "POST",
      body: JSON.stringify(body)
    });

    mostrarMensaje("Entrada transferida correctamente.");
    mostrarSalida("Transferencia OK", "salidaTransferencia");
    await cargarOpcionesTransferencia();
  } catch (error) {
    mostrarError(new Error("Error al transferir: " + error.message), "salidaTransferencia");
  }
});

async function cargarOpcionesAdmin() {
  const eventStadiumSelect = $("#eventStadiumSelect");
  const eventAdminSelect = $("#eventAdminSelect");
  const sectionStadiumSelect = $("#sectionStadiumSelect");
  const querySectionStadiumSelect = $("#querySectionStadiumSelect");
  const localTeamSelect = $("#localTeamSelect");
  const visitorTeamSelect = $("#visitorTeamSelect");
  const sectionLetterSelect = $("#sectionLetterSelect");

  if (getRole() !== "ADMIN") {
    limpiarSelect(eventStadiumSelect, "Iniciá sesión como ADMIN para cargar estadios");
    limpiarSelect(eventAdminSelect, "Iniciá sesión como ADMIN para cargar admins");
    limpiarSelect(sectionStadiumSelect, "Iniciá sesión como ADMIN para cargar estadios");
    limpiarSelect(querySectionStadiumSelect, "Iniciá sesión como ADMIN para cargar estadios");
    limpiarSelect(localTeamSelect, "Iniciá sesión como ADMIN para cargar equipos");
    limpiarSelect(visitorTeamSelect, "Iniciá sesión como ADMIN para cargar equipos");
    limpiarSelect(sectionLetterSelect, "Elegí un estadio");
    return;
  }

  try {
    const estadios = await llamarApi("/infrastructures/getAllStadiums");

    limpiarSelect(eventStadiumSelect, "Elegí un estadio");
    limpiarSelect(sectionStadiumSelect, "Elegí un estadio");
    limpiarSelect(querySectionStadiumSelect, "Elegí un estadio");

    estadios.forEach((estadio) => {
      const label = "#" + estadio.stadiumId + " - " + estadio.stadiumName;
      eventStadiumSelect.append(new Option(label, estadio.stadiumId));
      sectionStadiumSelect.append(new Option(label, estadio.stadiumId));
      querySectionStadiumSelect.append(new Option(label, estadio.stadiumId));
    });
  } catch (error) {
    limpiarSelect(eventStadiumSelect, "No se pudieron cargar estadios");
    limpiarSelect(eventAdminSelect, "No se pudieron cargar admins");
    limpiarSelect(sectionStadiumSelect, "No se pudieron cargar estadios");
    limpiarSelect(querySectionStadiumSelect, "No se pudieron cargar estadios");
    mostrarError(new Error("Error al cargar estadios: " + error.message), "salidaEvento");
    mostrarSalida("Error: Error al cargar estadios: " + error.message, "salidaSector");
  }

  try {
    const admins = await llamarApi("/events/admins");

    limpiarSelect(eventAdminSelect, "Elegí un admin");
    admins.forEach((admin) => {
      const label = "#" + admin.id_usuario + " - " + admin.nombre_usuario + " - " + admin.mail;
      eventAdminSelect.append(new Option(label, admin.id_usuario));
    });

    if (eventAdminSelect.options.length === 1) {
      limpiarSelect(eventAdminSelect, "No hay admins cargados");
    }
  } catch (error) {
    limpiarSelect(eventAdminSelect, "No se pudieron cargar admins");
    mostrarError(new Error("Error al cargar admins: " + error.message), "salidaEvento");
  }

  try {
    const equipos = await llamarApi("/events/teams");

    limpiarSelect(localTeamSelect, "Elegí equipo local");
    limpiarSelect(visitorTeamSelect, "Elegí equipo visitante");

    equipos.forEach((equipo) => {
      const label = "#" + equipo.id_equipo + " - " + equipo.nombre_equipo;
      localTeamSelect.append(new Option(label, equipo.id_equipo));
      visitorTeamSelect.append(new Option(label, equipo.id_equipo));
    });
    actualizarEquiposDisponibles();
  } catch (error) {
    limpiarSelect(localTeamSelect, "No se pudieron cargar equipos");
    limpiarSelect(visitorTeamSelect, "No se pudieron cargar equipos");
    mostrarError(new Error("Error al cargar equipos: " + error.message), "salidaEvento");
  }
}

async function cargarLetrasSectorDisponibles(stadiumId) {
  const sectionLetterSelect = $("#sectionLetterSelect");
  limpiarSelect(sectionLetterSelect, "Elegí una letra");

  if (!stadiumId) {
    limpiarSelect(sectionLetterSelect, "Elegí un estadio");
    return;
  }

  try {
    const sectores = await llamarApi("/infrastructures/" + stadiumId + "/sections");
    const letrasUsadas = new Set((sectores || []).map((sector) => sector.letra_sector));
    const letras = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".split("");

    letras.forEach((letra) => {
      if (!letrasUsadas.has(letra)) {
        sectionLetterSelect.append(new Option(letra, letra));
      }
    });

    if (sectionLetterSelect.options.length === 1) {
      limpiarSelect(sectionLetterSelect, "No quedan letras disponibles");
    }
  } catch (error) {
    limpiarSelect(sectionLetterSelect, "No se pudieron cargar letras");
    mostrarError(new Error("Error al cargar sectores existentes: " + error.message), "salidaSector");
  }
}

$("#sectionStadiumSelect").addEventListener("change", async (e) => {
  await cargarLetrasSectorDisponibles(e.target.value);
});

$("#listSectionsBtn").addEventListener("click", async () => {
  const stadiumId = $("#querySectionStadiumSelect").value;

  if (!stadiumId) {
    mostrarError(new Error("Elegí un estadio para listar sus sectores."), "salidaConsultas");
    return;
  }

  try {
    const sectores = await llamarApi("/infrastructures/" + stadiumId + "/sections");
    mostrarMensaje("Sectores consultados correctamente.");
    mostrarSalida(sectores, "salidaConsultas");
  } catch (error) {
    mostrarError(new Error("Error al listar sectores: " + error.message), "salidaConsultas");
  }
});

$("#localTeamSelect").addEventListener("change", actualizarEquiposDisponibles);
$("#visitorTeamSelect").addEventListener("change", actualizarEquiposDisponibles);

$("#eventForm").addEventListener("submit", async (e) => {
  e.preventDefault();
  const data = datosFormulario(e.target);

  if (data.localTeamId === data.visitorTeamId) {
    mostrarError(new Error("El equipo local y visitante no pueden ser el mismo."), "salidaEvento");
    return;
  }

  const body = {
    eventDate: data.eventDate.replace("T", " "),
    stadiumId: numero(data.stadiumId),
    userId: numero(data.userId),
    localTeamId: numero(data.localTeamId),
    visitorTeamId: numero(data.visitorTeamId)
  };

  try {
    const respuesta = await llamarApi("/events/createEvent", {
      method: "POST",
      body: JSON.stringify(body)
    });

    const eventos = await llamarApi("/events/list");
    mostrarMensaje("Evento creado correctamente. Lista de eventos actualizada.");
    mostrarSalida(eventos, "salidaEvento");
  } catch (error) {
    mostrarError(new Error("Error al crear evento: " + error.message), "salidaEvento");
  }
});

$("#sectionForm").addEventListener("submit", async (e) => {
  e.preventDefault();
  const data = datosFormulario(e.target);

  const body = {
    sectionLetter: data.sectionLetter,
    maxCapacity: numero(data.maxCapacity),
    price: numero(data.price)
  };

  try {
    const respuesta = await llamarApi(`/infrastructures/${data.stadiumId}/createSection`, {
      method: "POST",
      body: JSON.stringify(body)
    });

    const sectores = await llamarApi("/infrastructures/" + data.stadiumId + "/sections");
    mostrarMensaje("Sector creado correctamente. Lista de sectores actualizada.");
    mostrarSalida(sectores, "salidaSector");
    await cargarLetrasSectorDisponibles(data.stadiumId);
  } catch (error) {
    mostrarError(new Error("Error al crear sector: " + error.message), "salidaSector");
  }
});

function etiquetaEntrada(ticket) {
  const sector = ticket.letra_sector ? " - Sector " + ticket.letra_sector : "";
  const dispositivo = ticket.id_dispositivo ? " - Disp. #" + ticket.id_dispositivo : "";
  return "#" + ticket.id_entrada + " - Evento #" + ticket.id_evento + " - " + ticket.nombre_estadio + sector + " - " + ticket.nombre_usuario + dispositivo;
}

async function cargarOpcionesFuncionario() {
  const validateDeviceSelect = $("#validateDeviceSelect");
  const validateTicketSelect = $("#validateTicketSelect");
  const qrTicketSelect = $("#qrTicketSelect");
  const assignDeviceSelect = $("#assignDeviceSelect");

  if (getRole() !== "FUNCIONARIO") {
    limpiarSelect(validateDeviceSelect, "Iniciá sesión como FUNCIONARIO para cargar dispositivos");
    limpiarSelect(validateTicketSelect, "Iniciá sesión como FUNCIONARIO para cargar entradas");
    limpiarSelect(qrTicketSelect, "Iniciá sesión como FUNCIONARIO para cargar entradas");
    limpiarSelect(assignDeviceSelect, "Iniciá sesión como FUNCIONARIO para cargar dispositivos");
    return;
  }

  try {
    const dispositivos = await llamarApi("/validates/devices");

    limpiarSelect(validateDeviceSelect, "Elegí un dispositivo");
    limpiarSelect(assignDeviceSelect, "Elegí un dispositivo");
    dispositivos.forEach((dispositivo) => {
      const optionText = "Dispositivo #" + dispositivo.id_dispositivo;
      validateDeviceSelect.append(new Option(optionText, dispositivo.id_dispositivo));
      assignDeviceSelect.append(new Option(optionText, dispositivo.id_dispositivo));
    });

    if (validateDeviceSelect.options.length === 1) {
      limpiarSelect(validateDeviceSelect, "No hay dispositivos cargados");
    }
    if (assignDeviceSelect.options.length === 1) {
      limpiarSelect(assignDeviceSelect, "No hay dispositivos cargados");
    }
  } catch (error) {
    limpiarSelect(validateDeviceSelect, "No se pudieron cargar dispositivos");
    limpiarSelect(assignDeviceSelect, "No se pudieron cargar dispositivos");
    mostrarError(new Error("Error al cargar dispositivos: " + error.message), "salidaValidacion");
  }

  try {
    const entradasPendientes = await llamarApi("/validates/tickets/pending");

    limpiarSelect(validateTicketSelect, "Elegí una entrada pendiente");
    entradasPendientes.forEach((ticket) => {
      validateTicketSelect.append(new Option(etiquetaEntrada(ticket), ticket.id_entrada));
    });

    if (validateTicketSelect.options.length === 1) {
      limpiarSelect(validateTicketSelect, "No hay entradas pendientes");
    }
  } catch (error) {
    limpiarSelect(validateTicketSelect, "No se pudieron cargar entradas");
    mostrarError(new Error("Error al cargar entradas pendientes: " + error.message), "salidaValidacion");
  }

  try {
    const entradas = await llamarApi("/validates/tickets");

    limpiarSelect(qrTicketSelect, "Elegí una entrada");
    entradas.forEach((ticket) => {
      qrTicketSelect.append(new Option(etiquetaEntrada(ticket), ticket.id_entrada));
    });

    if (qrTicketSelect.options.length === 1) {
      limpiarSelect(qrTicketSelect, "No hay entradas cargadas");
    }
  } catch (error) {
    limpiarSelect(qrTicketSelect, "No se pudieron cargar entradas");
    mostrarError(new Error("Error al cargar entradas para QR: " + error.message), "salidaQr");
  }
}

$("#validateForm").addEventListener("submit", async (e) => {
  e.preventDefault();
  const data = datosFormulario(e.target);

  try {
    await llamarApi(`/validates/${data.deviceId}/${data.ticketId}/validateTicket`, {
      method: "POST"
    });

    mostrarMensaje("Entrada validada correctamente.");
    mostrarSalida("Validación OK", "salidaValidacion");
    await cargarOpcionesFuncionario();
  } catch (error) {
    mostrarError(new Error("Error al validar entrada: " + error.message), "salidaValidacion");
  }
});

async function regenerarQr(refrescarOpciones = true) {
  const ticketId = $("#qrTicketSelect").value;

  if (!ticketId) {
    mostrarError(new Error("Elegí una entrada para regenerar el QR."), "salidaQr");
    return false;
  }

  if (qrRegenerating) {
    return false;
  }

  qrRegenerating = true;
  try {
    await llamarApi(`/validates/${ticketId}/regenerateQr`, {
      method: "POST"
    });

    const hora = new Date().toLocaleTimeString();
    mostrarMensaje("QR regenerado correctamente.");
    mostrarSalida("QR regenerado OK a las " + hora, "salidaQr");

    if (refrescarOpciones) {
      await cargarOpcionesFuncionario();
    }
    return true;
  } catch (error) {
    mostrarError(new Error("Error al regenerar QR: " + error.message), "salidaQr");
    return false;
  } finally {
    qrRegenerating = false;
  }
}

async function regenerarTodosLosQr() {
  if (qrRegenerating) {
    return false;
  }

  qrRegenerating = true;
  try {
    const resultado = await llamarApi("/validates/regenerateAllQr", {
      method: "POST"
    });

    const hora = new Date().toLocaleTimeString();
    const cantidad = resultado && resultado.ticketsUpdated != null ? resultado.ticketsUpdated : "todas";
    const omitidas = resultado && resultado.ticketsSkipped != null ? resultado.ticketsSkipped : 0;
    mostrarMensaje("QR de todas las entradas regenerados correctamente.");
    mostrarSalida("QR regenerados para " + cantidad + " entradas a las " + hora + ". Entradas omitidas: " + omitidas + ".", "salidaQr");
    return true;
  } catch (error) {
    mostrarError(new Error("Error al regenerar todos los QR: " + error.message), "salidaQr");
    return false;
  } finally {
    qrRegenerating = false;
  }
}

async function iniciarQrAutomatico() {
  if (qrInterval) {
    mostrarMensaje("QR automático ya está iniciado.");
    return;
  }

  const regenerado = await regenerarTodosLosQr();
  if (!regenerado) {
    return;
  }

  qrInterval = setInterval(() => {
    regenerarTodosLosQr();
  }, 30000);

  mostrarMensaje("QR dinámico iniciado. Se regenerarán todos los QR cada 30 segundos.");
  mostrarSalida("QR dinámico iniciado para todas las entradas. Intervalo: 30 segundos.", "salidaQr");
}

function detenerQrAutomatico() {
  if (!qrInterval) {
    return;
  }

  clearInterval(qrInterval);
  qrInterval = null;
  mostrarMensaje("QR dinámico detenido.");
  mostrarSalida("QR dinámico detenido.", "salidaQr");
}

$("#qrForm").addEventListener("submit", async (e) => {
  e.preventDefault();
  await regenerarQr();
});

$("#startQrAutoBtn").addEventListener("click", iniciarQrAutomatico);
$("#stopQrAutoBtn").addEventListener("click", detenerQrAutomatico);
document.addEventListener("visibilitychange", () => {
  if (document.hidden) {
    detenerQrAutomatico();
  }
});

$("#assignDeviceForm").addEventListener("submit", async (e) => {
  e.preventDefault();
  const data = datosFormulario(e.target);

  try {
    const resultado = await llamarApi(`/validates/devices/${data.deviceId}/assign-funcionario/${data.userId}`, {
      method: "POST"
    });

    mostrarMensaje("Dispositivo asignado correctamente.");
    mostrarSalida(resultado, "salidaAssignDevice");
    await cargarOpcionesFuncionario();
  } catch (error) {
    mostrarError(new Error("Error al asignar dispositivo: " + error.message), "salidaAssignDevice");
  }
});

actualizarEstadoSesion();
actualizarPermisos();
cargarCodigosPostales();
cargarOpcionesCompra();
cargarOpcionesTransferencia();
cargarOpcionesAdmin();
cargarOpcionesFuncionario();
