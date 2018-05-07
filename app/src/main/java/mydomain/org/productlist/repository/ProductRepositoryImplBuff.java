package mydomain.org.productlist.repository;

import java.util.ArrayList;
import java.util.List;

import mydomain.org.productlist.model.Currency;
import mydomain.org.productlist.model.Product;
import mydomain.org.productlist.model.ProductImpl;

public class ProductRepositoryImplBuff implements ProductRepository {
    //todo make mysql database and add dagger
    private final List<Product> productDB = new ArrayList<>(31);
    private static final ProductRepository instance = new ProductRepositoryImplBuff();
    public static ProductRepository getInstance(){
        return instance;
    }
    private ProductRepositoryImplBuff() {
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

    @Override
    public void deleteElement(int id) {
        productDB.remove(id);
    }

    @Override
    public void save(String name, String description, float price, int count, char currency) {
        productDB.add(new ProductImpl(name, description, price, count, Currency.getCurrency(currency)));
    }
}
