package mydomain.org.productlist.presenter;

import android.support.design.widget.FloatingActionButton;

import mydomain.org.productlist.database.AppDatabase;
import mydomain.org.productlist.model.Currency;
import mydomain.org.productlist.model.Product;
import mydomain.org.productlist.view.EditView;

public class EditPresenterImpl implements EditPresenter {
    private final int position;
    private EditView view;
    private AppDatabase database;
    private Product product;
    public EditPresenterImpl(EditView view, int position) {
        this.view = view;
        database = AppDatabase.getAppDatabase(view.getContext());
        this.position = position;
    }

    @Override
    public void update(String name, String description, String price, String count, char currency) {
        if (name.isEmpty() || price.isEmpty() || price.length() > 12 || count.isEmpty() || count.length() > 9)
            return;
        product.setName(name);
        product.setDescription(description);
        product.setPrice(Float.parseFloat(price));
        product.setCount(Integer.parseInt(count));
        product.setCurrency(Currency.getCurrency(currency));
        database.getProductDao().update(product);
    }

    @Override
    public void cancel() {
        view.close();
    }

    @Override
    public void setValues() {
        if (position == -1) {
            view.showErrorMessage();
            return;
        }
        product = database.getProductDao().getProductByPosition(position);
        if (product == null) {
            view.showErrorMessage();
            return;
        }
        view.setValues(product.getName(), product.getDescription(), product.getPrice(), product.getCount(), product.getCurrency());
    }
}
