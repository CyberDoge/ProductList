package mydomain.org.productlist.presenter;

import mydomain.org.productlist.model.Product;
import mydomain.org.productlist.repository.ProductRepository;
import mydomain.org.productlist.repository.ProductRepositoryImplBuff;
import mydomain.org.productlist.view.ListView;

public class ProductPresenterImpl implements ProductPresenter {
    private ListView view;
    private ProductRepository repository;

    public ProductPresenterImpl(ListView view) {
        this.view = view;
        repository = new ProductRepositoryImplBuff();
    }

    @Override
    public int getItemCount(){
        return repository.getTotalCounts();
    }

    @Override
    public String getProductName(int position) {
        return repository.getProductByPosition(position).getName();
    }

    @Override
    public float getProductPrice(int position) {
        return repository.getProductByPosition(position).getPrice();
    }

    @Override
    public int getProductCount(int position) {
        return repository.getProductByPosition(position).getCount();
    }

    @Override
    public void deleteElement(int position) {
        repository.deleteElement(position);
    }

    @Override
    public void onItemClick(int position) {
        view.openInfoDialog(position);
    }

    @Override
    public void onItemLongClick(int position) {

    }
}
