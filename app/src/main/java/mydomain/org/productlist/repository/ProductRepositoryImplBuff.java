package mydomain.org.productlist.repository;

import java.util.ArrayList;
import java.util.List;

import mydomain.org.productlist.model.Product;
import mydomain.org.productlist.model.ProductImpl;

public class ProductRepositoryImplBuff implements ProductRepository {
    private final List<Product> productDB = new ArrayList<>(31);

    public ProductRepositoryImplBuff() {
        for (int i = 1; i < 31; i++)
            productDB.add(new ProductImpl("name#" + i, i * 1000 - (30 % i) * (-i % 2), (i * i + i * i) / i));
    }

    @Override
    public List<Product> getProducts() {
        return productDB;
    }

    @Override
    public int getTotalCounts() {
        return productDB.size();
    }

    @Override
    public Product getProductByPosition(int position) {
        return productDB.get(position);
    }
}
