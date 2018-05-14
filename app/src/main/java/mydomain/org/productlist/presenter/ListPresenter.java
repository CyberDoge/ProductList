package mydomain.org.productlist.presenter;

import mydomain.org.productlist.view.adapter.ProductAdapter;

public interface ListPresenter {
    int getItemCount();

    void deleteElementAtPosition(int position);

    void search(String str);

    void setValues(ProductAdapter.ViewHolder holder, int position);

    String getDescription(int position);
}
