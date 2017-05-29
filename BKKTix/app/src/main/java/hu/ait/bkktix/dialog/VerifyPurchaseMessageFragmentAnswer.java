package hu.ait.karen.bkktix.dialog;

import hu.ait.karen.bkktix.data.TicketType;

public interface VerifyPurchaseMessageFragmentAnswer {
    void onPurchaseSelected(TicketType ticketType, int numberOfTickets);

    void onPurchaseCancelledSelected();
}
