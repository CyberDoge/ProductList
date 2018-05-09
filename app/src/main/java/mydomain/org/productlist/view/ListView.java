package mydomain.org.productlist.view;

import android.content.Context;

public interface ListView {
    void openInfoDialog(final int position, final int key);
    void openEditActivity(int position);
    void deleteElement(int position, int key);
    void addProduct();
    Context getContext();
}
