package hu.ait.karen.bkktix.dialog;

import hu.ait.karen.bkktix.data.TicketType;

public interface VerifyPurchaseMessageFragmentAnswer {
        public void onPurchaseSelected(TicketType ticketType, int numberOfTickets);

        public void onPurchaseCancelledSelected();
    }
