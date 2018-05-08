package mydomain.org.productlist.model;

public class Product {
    private int id;
    private String name;
    private String description;
    private float price;
    private int count;
    private int iconId;
    private Currency currency;
    public Product(String name, String description, float price, int count, Currency currency) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.count = count;
        this.currency = currency;
    }
    public Product(String name, float price, int count) {
        this.name = name;
        this.price = price;
        this.count = count;
        currency = Currency.RUB;
    }

    public String getDescription() {
        return description;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void changeDescription(String description) {
        this.description = description;
    }

    public void changeName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getCount() {
        return count;
    }

    public float getPrice() {
        return price;
    }
}

