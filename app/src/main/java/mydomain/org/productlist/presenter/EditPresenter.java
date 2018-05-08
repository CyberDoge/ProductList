package mydomain.org.productlist.presenter;

public interface EditPresenter {
    void update(String name, String description, String price, String count, char currency);
    void cancel();
    void setValues();
}
