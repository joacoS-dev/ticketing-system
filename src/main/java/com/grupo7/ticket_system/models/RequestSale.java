package com.grupo7.ticket_system.models;
import java.util.List;

public class RequestSale {
    
    Sale sale;
    List<Ticket> tickets;

    public Sale getSale() {
        return sale;
    }
    public void setSale(Sale sell) {
        this.sale = sell;
    }
    public List<Ticket> getTickets() {
        return tickets;
    }
    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }
    @Override
    public String toString() {
        return "RequestSell [sell=" + sale + ", tickets=" + tickets + "]";
    }
}
