package net.casinovoyage.server.slots.results;

import net.casinovoyage.server.slots.Symbols;

public class NormalSpinResult extends SpinResult {
    private final double winnings;
    private final double betSize;
    private final boolean freeSpin;

    private final double freeSpinPayoutMultiplier;

    private final FreeSpinResult[] freeSpinResults;

    public NormalSpinResult(Symbols[][] symbols, double winnings, double betSize, boolean freeSpin, double freeSpinPayoutMultiplier, FreeSpinResult[] freeSpinResults) {
        super(symbols);
        this.winnings = winnings;
        this.betSize = betSize;
        this.freeSpin = freeSpin;
        this.freeSpinPayoutMultiplier = freeSpinPayoutMultiplier;
        this.freeSpinResults = freeSpinResults;
    }

    public double getWinnings() {
        return winnings;
    }

    public double getBetSize() {
        return betSize;
    }

    public boolean isFreeSpin() {
        return freeSpin;
    }

    public double getFreeSpinPayoutMultiplier() {
        return freeSpinPayoutMultiplier;
    }

    public FreeSpinResult[] getFreeSpinResults() {
        return freeSpinResults;
    }
}
