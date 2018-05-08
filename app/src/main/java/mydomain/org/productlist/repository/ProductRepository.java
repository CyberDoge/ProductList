package mydomain.org.productlist.repository;

import java.util.List;

import mydomain.org.productlist.model.Product;

public interface ProductRepository {
    List<Product> getProducts();
    int getTotalCounts();
    Product getProductByPosition(int position);
    void deleteElement(int id);
    void save(String name, String description, float price, int count, char currency);
    void update(int position, String name, String description, float price, int count, char currency);
}
