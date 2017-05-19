package hu.ait.karen.bkktix.data;


import java.util.Date;

import hu.ait.karen.bkktix.TicketType;

public class Ticket {

    private TicketType ticketType;
    private Date datePurchased;
    private Date dateValidated;

    public Ticket(Date datePurchased, TicketType ticketType){
        this.datePurchased = datePurchased;
        this.ticketType = ticketType;
    }

    public TicketType getTicketType() {
        return ticketType;
    }

    public void setTicketType(TicketType ticketType) {
        this.ticketType = ticketType;
    }

    public Date getDatePurchased() {
        return datePurchased;
    }

    public Date getDateValidated() {
        return dateValidated;
    }

    public void setDateValidated(Date dateValidated) {
        this.dateValidated = dateValidated;
    }
}
