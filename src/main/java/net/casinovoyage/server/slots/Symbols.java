package net.casinovoyage.server.slots;

public enum Symbols {
    DOG(0.2,1.8,2.6 , true, false),
    DIAMOND(0.2,1.8,2.6 , true, false),
    WHEEL(0.2,1.8,2.6 , true, false),
    KOALA(0.2,1.8,2.6 , true, false),
    JETON(0.09,2,3, true, false),
    BALL(0.09,2,3, true, false),
    LION_GOLD(0.06,0,0, false, true),
    WINNING_WILD(0.04,0,0, false, true),
    LOOSING_WILD_ONE(0.04,0,0, false, true),
    LOOSING_WILD_TWO(0.04,0,0, false, true),
    LOOSING_WILD_THREE(0.04,0,0, false, true);

    private final double chance;
    private final double payoutMultiplierByFour;
    private final double payoutMultiplierByFive;
    private final boolean magnetic;

    private final boolean special;

    /**
     *
     * @param chance - chance of getting this symbol
     * @param payoutMultiplierByFour - payout multiplier if 4 of this symbol are in a row
     * @param payoutMultiplierByFive - payout multiplier if 5 of this symbol are in a row
     * @param magnetic - if this symbol is magnetic (if it is, if there are 3 of this symbol in a row, the 4th and 5th symbol will have a higher chance of being this symbol)
     * @param special - if this symbol is a special symbol
     */
    Symbols(double chance, double payoutMultiplierByFour,double payoutMultiplierByFive,boolean magnetic, boolean special) {
        this.chance = chance;
        this.payoutMultiplierByFour = payoutMultiplierByFour;
        this.payoutMultiplierByFive = payoutMultiplierByFive;
        this.magnetic = magnetic;
        this.special = special;
    }

    public double chance() {
        return chance;
    }

    public double payoutMultiplierByFour() {
        return payoutMultiplierByFour;
    }

    public double payoutMultiplierByFive() {
        return payoutMultiplierByFive;
    }

    public boolean magnetic() {
        return magnetic;
    }

    public boolean special() {
        return special;
    }

}
