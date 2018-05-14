package mydomain.org.productlist.presenter;

import mydomain.org.productlist.model.Currency;

public interface CreatePresenter {
    boolean createProduct(String name, String price, String count, char currency);
}
