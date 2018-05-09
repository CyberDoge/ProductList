package mydomain.org.productlist.presenter;

import mydomain.org.productlist.view.adapter.ProductAdapter;

public interface ProductPresenter {
    void onItemClick(int position, int key);
    void onItemLongClick(int position);
    int getItemCount();
    void deleteElement(int key);
    void addElement();
    void search(String str);
    void setValues(ProductAdapter.ViewHolder holder, int position);
    void sortByName();
    void sortByPrice();
    void sortByCount();
}
