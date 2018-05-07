package mydomain.org.productlist.view;

import mydomain.org.productlist.model.Currency;

public interface EditView {
    void setValues(String name, String description, float price, float count, Currency currency);
    void close();
}
