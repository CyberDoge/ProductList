package mydomain.org.productlist.presenter;

import android.content.ContentResolver;
import android.net.Uri;
import android.widget.ImageView;

public interface EditPresenter {
    void update(String iconPath, String name, String description, String price, String count, char currency);
    void cancel();
    void setValues();
    void changeImage(String iconPath, ImageView imageView);
    void changeImage(Uri iconUri, ContentResolver contentResolver, ImageView imageView);
}
