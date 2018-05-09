package mydomain.org.productlist.view;

import android.content.Context;

public interface ListView {
    void openInfoDialog(final int position);
    void openEditActivity(int position);
    void deleteElement(int position);
    void addProduct();
    Context getContext();
}
