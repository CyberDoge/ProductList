package mydomain.org.productlist.model;

public enum Currency {
    RUB('\u20BD'), USD('$'), EUR('€'), GBR('£');

    private static final Character[] allSymbols = new Character[Currency.values().length];

    static {
        for (int i = 0; i < allSymbols.length; i++) {
            allSymbols[i] = Currency.values()[i].symbol;

        }
    }

    private char symbol;

    Currency(char c) {
        this.symbol = c;
    }

    public static Character[] getAllSymbols() {
        return allSymbols;
    }

    public static Currency getCurrency(char c) {
        for (Currency cur : values()) {
            if (cur.symbol == c) return cur;
        }
        return RUB;
    }

    public char getSymbol() {
        return symbol;
    }

    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }
}
