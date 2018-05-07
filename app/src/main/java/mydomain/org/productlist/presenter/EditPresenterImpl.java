package mydomain.org.productlist.presenter;

import mydomain.org.productlist.repository.ProductRepository;
import mydomain.org.productlist.repository.ProductRepositoryImplBuff;
import mydomain.org.productlist.view.EditView;

public class EditPresenterImpl implements EditPresenter {
    private EditView  view;
    private ProductRepository repository;

    public EditPresenterImpl(EditView view) {
        this.view = view;
        repository = ProductRepositoryImplBuff.getInstance();
    }

    @Override
    public void save(String name, String description, float price, int count, char currency) {
        //todo check if name, description etc is empty
        repository.save(name, description, price, count, currency);
    }

    @Override
    public void cancel() {
        view.close();
    }
}
