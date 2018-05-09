package mydomain.org.productlist.presenter;


import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import mydomain.org.productlist.database.AppDatabase;
import mydomain.org.productlist.model.Product;
import mydomain.org.productlist.view.ListView;
import mydomain.org.productlist.view.adapter.ProductAdapter;

public class ProductPresenterImpl implements ProductPresenter {
    private ListView view;
    private AppDatabase database;
    private List<Product> productFind;
    private boolean searching = false;

    public ProductPresenterImpl(ListView view) {
        this.view = view;
        database = AppDatabase.getAppDatabase(view.getContext());
        productFind = new ArrayList<>();
    }

    @Override
    public int getItemCount() {
        return searching ? productFind.size() : database.getProductDao().getTotalCount();
    }


    @Override
    public void deleteElementAtPosition(int position) {
        database.getProductDao().deleteByPosition(position);
    }


    @Override
    public void addElement() {
        database.getProductDao().insert(Product.createDefault());
    }

    @Override
    public void search(String str) {
        if (str.isEmpty()) {
            searching = false;
            return;
        }
        List<Product> products = database.getProductDao().getAllProduct();
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
        } else product = database.getProductDao().getProductByPosition(position);
        holder.nameField.setText(product.getName());
        holder.countField.setText(product.getCount() + "");
        holder.priceField.setText(String.format("%.2f" + product.getCurrency().getSymbol(), product.getPrice()));
        if (product.getIconPath() != null)
            Picasso.get().load(product.getIconPath()).into(holder.iconField);
    }

    @Override
    public void onItemClick(int position) {
        view.openInfoDialog(position);
    }

    @Override
    public void onItemLongClick(int position) {

    }
}
