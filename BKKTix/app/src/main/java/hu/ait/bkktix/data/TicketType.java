package hu.ait.karen.bkktix.data;

public enum TicketType {

    _20_MINUTES,
    _60_MINUTES,
    _120_MINUTES;

    public static TicketType fromOrdinal(int n) {
        return values()[n];
    }

}
