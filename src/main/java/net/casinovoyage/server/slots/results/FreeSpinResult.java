package net.casinovoyage.server.slots.results;

import net.casinovoyage.server.slots.Symbols;

public class FreeSpinResult extends SpinResult {

    private final int freeSpinSymbolCount;
    public FreeSpinResult(Symbols[][] symbols, int freeSpinSymbolCount) {
        super(symbols);
        this.freeSpinSymbolCount = freeSpinSymbolCount;
    }

    public int getFreeSpinSymbolCount() {
        return freeSpinSymbolCount;
    }
}
