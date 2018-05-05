package mydomain.org.productlist.repository;

import java.util.List;

import mydomain.org.productlist.model.Product;

public interface ProductRepository {
    List<Product> getProducts();
    int getTotalCounts();
    Product getProductByPosition(int position);
}
