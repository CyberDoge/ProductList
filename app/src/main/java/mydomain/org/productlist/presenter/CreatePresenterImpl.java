package mydomain.org.productlist.presenter;

import mydomain.org.productlist.database.AppDatabase;
import mydomain.org.productlist.model.Currency;
import mydomain.org.productlist.model.Product;
import mydomain.org.productlist.view.CreateView;

public class CreatePresenterImpl implements CreatePresenter {
    private AppDatabase database;
    private CreateView view;

    public CreatePresenterImpl(CreateView view) {
        this.view = view;
        database = AppDatabase.getAppDatabase(view.getContext());
    }

    @Override
    public boolean createProduct(String name, String price, String count, char currency) {
        if (name.length() * price.length() * count.length() == 0)
            return false;
        float fPrice = Float.parseFloat(price);
        int iCount = Integer.parseInt(count);
        Currency cCurrency = Currency.getCurrency(currency);
        Product product = new Product();
        product.setName(name);
        product.setPrice(fPrice);
        product.setCount(iCount);
        product.setCurrency(cCurrency);
        database.getProductDao().insert(product);
        return true;
    }
}
