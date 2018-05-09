package mydomain.org.productlist.presenter;

import java.util.ArrayList;
import java.util.List;

import mydomain.org.productlist.model.Product;
import mydomain.org.productlist.repository.ProductRepository;
import mydomain.org.productlist.repository.ProductRepositoryImplBuff;
import mydomain.org.productlist.view.ListView;
import mydomain.org.productlist.view.adapter.ProductAdapter;

public class ProductPresenterImpl implements ProductPresenter {
    private ListView view;
    private ProductRepository repository;
    private List<Product> productFind;
    private boolean searching = false;

    public ProductPresenterImpl(ListView view) {
        this.view = view;
        repository = ProductRepositoryImplBuff.getInstance();
        productFind = new ArrayList<>();
    }

    @Override
    public int getItemCount() {
        return searching ? productFind.size() :
                repository.getTotalCounts();
    }


    @Override
    public void deleteElement(int position) {
        repository.deleteElement(position);
    }


    @Override
    public void addElement() {
        repository.createDefault();
    }

    @Override
    public void search(String str) {
        if (str.isEmpty()) {
            searching = false;
            return;
        }
        List<Product> products = repository.getProducts();
        productFind.clear();
        for (Product p : products) {
            if (p.getName().contains(str)) {
                productFind.add(p);
            }
        }
        searching = true;

    }

    @Override
    public void setValues(ProductAdapter.ViewHolder holder, int position) {
        Product product;
        if (searching) {
            if (position >= productFind.size()) {
                searching = false;
                return;
            }
            product = productFind.get(position);
        } else product = repository.getProductByPosition(position);
        holder.nameField.setText(product.getName());
        holder.countField.setText(product.getCount() + "");
        holder.priceField.setText(String.format("%.2f" + product.getCurrency().getSymbol(), product.getPrice()));
    }

    @Override
    public void onItemClick(int position) {
        view.openInfoDialog(position);
    }

    @Override
    public void onItemLongClick(int position) {

    }
}
