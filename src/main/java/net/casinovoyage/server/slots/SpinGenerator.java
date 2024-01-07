package net.casinovoyage.server.slots;

import net.casinovoyage.server.slots.results.FreeSpinResult;
import net.casinovoyage.server.slots.results.NormalSpinResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

public class SpinGenerator {

    public static NormalSpinResult generateSpinResult(double betSize) {
        Symbols[][] symbols = new Symbols[3][5];
        for (int i = 0; i < 3; i++) {
            Symbols[] row = symbols[i];
            generateRow(row, false);
        }

        boolean freeSpin = getLionCount(symbols) >= 3;
        double spinPayoutMultiplier = identifyResults(symbols);

        if(!freeSpin){
            return new NormalSpinResult(symbols, (betSize * spinPayoutMultiplier), betSize, false, 0, null);
        }

        ArrayList<FreeSpinResult> freeSpinResults = new ArrayList<>();
        int winningWildCount = 1;
        int freeSpinCount = 5;
        while (freeSpinCount > 0) {
            Symbols[][] freeSpinSymbols = new Symbols[3][5];
            for (int i = 0; i < 3; i++) {
                Symbols[] row = freeSpinSymbols[i];
                generateRow(row, true);
            }
            freeSpinCount += getLionCount(freeSpinSymbols);
            spinPayoutMultiplier += identifyResults(freeSpinSymbols);
            int newWinningWildCount = getWinningWildCount(freeSpinSymbols);
            winningWildCount += newWinningWildCount;
            freeSpinCount--;
            freeSpinResults.add(new FreeSpinResult(freeSpinSymbols, newWinningWildCount));
        }

        return new NormalSpinResult(symbols, (betSize * spinPayoutMultiplier * winningWildCount), betSize, true, spinPayoutMultiplier, freeSpinResults.toArray(new FreeSpinResult[0]));
    }

    private static Symbols[] generateRow(Symbols[] row, boolean freeSpin) {
        for (int i = 0; i < 5; i++) {
            Symbols currentSymbol = generateNormalSymbol(freeSpin, row);
            row[i] = currentSymbol;
        }
        return row;
    }

    private static Symbols generateNormalSymbol(boolean freeSpin, Symbols[] currentRow) {
        double symbolWeight = 0;
        ArrayList<Symbols> symbols = new ArrayList<>();
        symbols.add(Symbols.DOG);
        symbols.add(Symbols.DIAMOND);
        symbols.add(Symbols.WHEEL);
        symbols.add(Symbols.KOALA);
        symbols.add(Symbols.JETON);
        symbols.add(Symbols.BALL);
        symbols.add(Symbols.LION_GOLD);
        if(freeSpin){
            symbols.add(Symbols.WINNING_WILD);
            symbols.add(Symbols.LOOSING_WILD_ONE);
            symbols.add(Symbols.LOOSING_WILD_TWO);
            symbols.add(Symbols.LOOSING_WILD_THREE);
        }
        //Generate weight for each symbol (based on chance and magnetic)
        for (int i = 0; i < symbols.size(); i++) {
            Symbols currentSymbol = symbols.get(i);
            symbolWeight += calculateSymbolWeight(currentSymbol, currentRow);
        }
        double chosenWeight = Math.random() * symbolWeight;

        //Choose symbol based on weight
        double weight = 0;
        for (int i = 0; i < symbols.size(); i++) {
            Symbols currentSymbol = symbols.get(i);
            weight += calculateSymbolWeight(currentSymbol, currentRow);
            if (weight >= chosenWeight) {
                return symbols.get(i);
            }
        }
        return null;
    }

    private static double calculateSymbolWeight(Symbols symbol, Symbols[] currentRow){
        double symbolWeight = 0;
        symbolWeight += symbol.chance();
        if(!symbol.magnetic()) return symbolWeight;

        int symbolInRow = 0;
        for(Symbols currentSymbol : currentRow){
            if(currentSymbol == symbol){
                symbolInRow++;
            }
        }
        if(symbolInRow == 3){
            symbolWeight += symbol.chance()*2;
        }
        if(symbolInRow == 4){
            symbolWeight += symbol.chance()*3;
        }

        return symbolWeight;
    }

    private static int getLionCount(Symbols[][] symbols){
        int lionGoldCount = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 5; j++) {
                Symbols currentSymbol = symbols[i][j];
                if(currentSymbol == Symbols.LION_GOLD){
                    lionGoldCount++;
                }
            }
        }
        return lionGoldCount;
    }

    private static int getWinningWildCount(Symbols[][] symbols){
        int winningWildCount = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 5; j++) {
                Symbols currentSymbol = symbols[i][j];
                if(currentSymbol == Symbols.WINNING_WILD){
                    winningWildCount++;
                }
            }
        }
        return winningWildCount;
    }

    private static double identifyResults(Symbols[][] symbols){
        double payoutMultiplier = 0;
        HashMap<Symbols, Integer> symbolCount = new HashMap<>();
        for(Symbols[] row : symbols){
            // Count symbols
            for(Symbols symbol : row){
                if(!symbolCount.containsKey(symbol)){
                    symbolCount.put(symbol, 1);
                    continue;
                }
                symbolCount.put(symbol, symbolCount.get(symbol)+1);
            }

            // Identify jokers
            int jokerSymbolCount = 0;
            if(symbolCount.containsKey(Symbols.WINNING_WILD)){
                jokerSymbolCount += symbolCount.get(Symbols.WINNING_WILD);
            }
            if(symbolCount.containsKey(Symbols.LOOSING_WILD_ONE)){
                jokerSymbolCount += symbolCount.get(Symbols.LOOSING_WILD_ONE);
            }
            if(symbolCount.containsKey(Symbols.LOOSING_WILD_TWO)){
                jokerSymbolCount += symbolCount.get(Symbols.LOOSING_WILD_TWO);
            }
            if(symbolCount.containsKey(Symbols.LOOSING_WILD_THREE)){
                jokerSymbolCount += symbolCount.get(Symbols.LOOSING_WILD_THREE);
            }
            for (Symbols symbol : symbolCount.keySet()) {
                if(symbol.special()) continue;
                if(symbolCount.get(symbol) + jokerSymbolCount == 4){
                    payoutMultiplier += symbol.payoutMultiplierByFour();
                    continue;
                }
                if(symbolCount.get(symbol) + jokerSymbolCount >= 5){
                    payoutMultiplier += symbol.payoutMultiplierByFive();
                }
            }

            //Clear symbol count
            symbolCount.clear();
        }
        return payoutMultiplier;
    }
}
