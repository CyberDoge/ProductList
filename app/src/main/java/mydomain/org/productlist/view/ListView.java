package mydomain.org.productlist.view;

import android.content.Context;

public interface ListView {
    void openInfoDialog(final int position);
    void openCreateDialog();
    void openEditActivity(int position);
    void deleteElement(int position);
    Context getContext();
}
