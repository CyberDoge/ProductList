package mydomain.org.productlist.presenter;

import mydomain.org.productlist.view.adapter.ProductAdapter;

public interface ProductPresenter {
    void onItemClick(int position);
    void onItemLongClick(int position);
    int getItemCount();
    void deleteElement(int position);
    void addElement();
    void search(String str);
    void setValues(ProductAdapter.ViewHolder holder, int position);
}
