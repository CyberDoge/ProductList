package mydomain.org.productlist.model;

public interface Product {
    void changeDescription(String description);
    void changeName(String name);
    String getName();
    int getCount();
    float getPrice();
}
