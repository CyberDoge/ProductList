package mydomain.org.productlist.model;

public class ProductImpl implements Product {
    private int id;
    private String name;
    private String description;
    private float price;
    private int count;
    private int iconId;

    public ProductImpl(String name, float price, int count) {
        this.name = name;
        this.price = price;
        this.count = count;
    }

    @Override
    public void changeDescription(String description) {
        this.description = description;
    }

    @Override
    public void changeName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public float getPrice() {
        return price;
    }
}
