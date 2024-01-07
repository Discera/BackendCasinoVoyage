package net.casinovoyage.server.slots.results;

import net.casinovoyage.server.slots.Symbols;

abstract class SpinResult {
    private final Symbols[][] symbols;

    SpinResult(Symbols[][] symbols) {
        this.symbols = symbols;
    }

    public Symbols[][] getSymbols() {
        return symbols;
    }
}
