package net.casinovoyage.server.slots;

public enum BetSizes {
    TINY(0.05),
    SMALL(0.1),
    MEDIUM(0.25),
    LARGE(0.5),
    HUGE(1.0),
    EXTREME(2.0),
    INSANE(5.0),
    MAX(10.0);

    private final double bet;
    BetSizes(double bet) {
        this.bet = bet;
    }

    public double bet() {
        return bet;
    }
}
