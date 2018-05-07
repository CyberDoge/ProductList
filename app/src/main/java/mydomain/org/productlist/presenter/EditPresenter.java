package mydomain.org.productlist.presenter;

public interface EditPresenter {
    void save(String name, String description, float price, int count, char currency);
    void cancel();
}
