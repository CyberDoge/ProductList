package mydomain.org.productlist.view;

import android.content.Context;

import mydomain.org.productlist.model.Currency;

public interface EditView {
    void setValues(String iconPath, String name, String description, String price, String count, Currency currency);
    void close();
    void showErrorMessage();
    Context getContext();
}
