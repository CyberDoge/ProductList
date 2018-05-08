package mydomain.org.productlist.presenter;

import mydomain.org.productlist.model.Product;
import mydomain.org.productlist.repository.ProductRepository;
import mydomain.org.productlist.repository.ProductRepositoryImplBuff;
import mydomain.org.productlist.view.EditView;

public class EditPresenterImpl implements EditPresenter {
    private EditView view;
    private ProductRepository repository;
    private final int position;

    public EditPresenterImpl(EditView view, int pos) {
        this.view = view;
        repository = ProductRepositoryImplBuff.getInstance();
        position = pos;
    }

    @Override
    public void save(String name, String description, String price, String count, char currency) {
        //todo check if name, description etc is empty
        if(!(name.isEmpty() && description.isEmpty() && price.isEmpty() && count.isEmpty()))
        repository.save(name, description, Float.parseFloat(price), Integer.parseInt(count), currency);
    }

    @Override
    public void update(String name, String description, String price, String count, char currency) {
        if(!(name.isEmpty() && description.isEmpty() && price.isEmpty() && count.isEmpty()))
            repository.update(position, name, description, Float.parseFloat(price), Integer.parseInt(count), currency);
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
        Product product = repository.getProductByPosition(position);
        if (product == null) {
            view.showErrorMessage();
            return;
        }
        view.setValues(product.getName(), product.getDescription(), product.getPrice(), product.getCount(), product.getCurrency());
    }
}
