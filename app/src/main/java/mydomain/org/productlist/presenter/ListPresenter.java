package mydomain.org.productlist.presenter;

import android.widget.EditText;

import mydomain.org.productlist.view.adapter.ProductAdapter;

public interface ListPresenter {
    int getItemCount();
    void deleteElementAtPosition(int position);
    boolean addElement(EditText name, EditText price, EditText count, char currency);
    void search(String str);
    void setValues(ProductAdapter.ViewHolder holder, int position);
}
