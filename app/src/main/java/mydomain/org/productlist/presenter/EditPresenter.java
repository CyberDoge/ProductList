package mydomain.org.productlist.presenter;

import android.widget.ImageView;

public interface EditPresenter {
    void update(String iconPath, String name, String description, String price, String count, char currency);
    void cancel();
    void setValues();
    void changeImage(String iconPath, ImageView imageView);
}
