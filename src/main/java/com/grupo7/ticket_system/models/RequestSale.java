package com.grupo7.ticket_system.models;

import java.util.List;

public class RequestSale {
    
    Sale sell;
    List<Ticket> tickets;

    public Sale getSell() {
        return sell;
    }
    public void setSell(Sale sell) {
        this.sell = sell;
    }
    public List<Ticket> getTickets() {
        return tickets;
    }
    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }
    @Override
    public String toString() {
        return "RequestSell [sell=" + sell + ", tickets=" + tickets + "]";
    }
}
