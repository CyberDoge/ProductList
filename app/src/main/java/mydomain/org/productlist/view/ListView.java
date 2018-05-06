package mydomain.org.productlist.view;

public interface ListView {
    void openInfoDialog(final int position);
    void openEditActivity();
    void deleteElement(int position);
}
