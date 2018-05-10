package mydomain.org.productlist.presenter;

import mydomain.org.productlist.view.adapter.ProductAdapter;

public interface ListPresenter {
    void onItemClick(int position);
    void onItemLongClick(int position);
    int getItemCount();
    void deleteElementAtPosition(int position);
    void addElement();
    void search(String str);
    void setValues(ProductAdapter.ViewHolder holder, int position);
}
