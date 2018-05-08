package mydomain.org.productlist.view;

public interface ListView {
    void openInfoDialog(final int position);
    void openEditActivity(int position);
    void deleteElement(int position);
    void addProduct();
}
