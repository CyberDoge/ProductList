package mydomain.org.productlist.presenter;

public interface ProductPresenter {
    void onItemClick(int position);
    void onItemLongClick(int position);
    int getItemCount();
    String getProductName(int position);
    float getProductPrice(int position);
    int getProductCount(int position);
    void deleteElement(int position);
}
