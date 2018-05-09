package mydomain.org.productlist.dao;

import android.arch.persistence.room.TypeConverter;

import mydomain.org.productlist.model.Currency;

public class TypeTransmogrifier {

    @TypeConverter
    public static char fromCurrency(Currency currency){
        return currency.getSymbol();
    }
    @TypeConverter
    public static Currency toCurrency(char c){
        return Currency.getCurrency(c);
    }
}
