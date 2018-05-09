package mydomain.org.productlist.presenter;

import mydomain.org.productlist.database.AppDatabase;
import mydomain.org.productlist.model.Currency;
import mydomain.org.productlist.model.Product;
import mydomain.org.productlist.view.EditView;

public class EditPresenterImpl implements EditPresenter {
    private final int pid;
    private EditView view;
    private AppDatabase database;

    public EditPresenterImpl(EditView view, int pid) {
        this.view = view;
        database = AppDatabase.getAppDatabase(view.getContext());
        this.pid = pid;
    }

    @Override
    public void update(String name, String description, String price, String count, char currency) {
        if (name.isEmpty() || price.isEmpty() || price.length() > 12 || count.isEmpty() || count.length() > 9)
            return;
        Product product = new Product(pid, name, description, Float.parseFloat(price), Integer.parseInt(count), Currency.getCurrency(currency));
        database.getProductDao().update(product);
    }

    @Override
    public void cancel() {
        view.close();
    }

    @Override
    public void setValues() {
        if (pid == -1) {
            view.showErrorMessage();
            return;
        }
        Product product = database.getProductDao().getProductByPosition(pid);
        if (product == null) {
            view.showErrorMessage();
            return;
        }
        view.setValues(product.getName(), product.getDescription(), product.getPrice(), product.getCount(), product.getCurrency());
    }
}
