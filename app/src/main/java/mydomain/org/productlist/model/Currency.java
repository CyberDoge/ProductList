package mydomain.org.productlist.model;

public enum Currency {
    RUB('\u20BD'), USD('$'), EUR('€'), GBR('£');
    private char c;
    private static final Character[] allSymbols = new Character[Currency.values().length];
    static {
        for (int i = 0; i < allSymbols.length; i++) {
            allSymbols[i] = Currency.values()[i].c;

        }
    }
    Currency(char c) {
        this.c = c;
    }
    public char getSymbol(){
        return c;
    }
    public static Character[] getAllSymbols(){
        return allSymbols;
    }
    public static Currency getCurrency(char c){
        for (Currency cur : values()) {
            if(cur.c == c) return cur;
        }
        return RUB;
    }
}
