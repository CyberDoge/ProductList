package mydomain.org.productlist.presenter;

import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import mydomain.org.productlist.database.AppDatabase;
import mydomain.org.productlist.model.Currency;
import mydomain.org.productlist.model.Product;
import mydomain.org.productlist.view.EditView;

public class EditPresenterImpl implements EditPresenter {
    private final int position;
    private EditView view;
    private AppDatabase database;
    private Product product;

    public EditPresenterImpl(EditView view, int position) {
        this.view = view;
        database = AppDatabase.getAppDatabase(view.getContext());
        this.position = position;
    }

    @Override
    public void update(String path, String name, String description, String price, String count, char currency) {
        if (name.isEmpty() || price.isEmpty() || price.length() > 12 || count.isEmpty() || count.length() > 9)
            return;
        product.setName(name);
        product.setDescription(description);
        product.setPrice(Float.parseFloat(price));
        product.setCount(Integer.parseInt(count));
        product.setCurrency(Currency.getCurrency(currency));
        product.setIconPath(path);
        database.getProductDao().update(product);
    }

    @Override
    public void cancel() {
        view.close();
    }

    @Override
    public void setValues() {
        if (position == -1) {
            view.showErrorMessage();
            return;
        }
        product = database.getProductDao().getProductByPosition(position);
        if (product == null) {
            view.showErrorMessage();
            return;
        }

        String name = product.getName() == null ? "" : product.getName();
        String description = product.getDescription() == null ? "" : product.getDescription();
        String price = product.getPrice() + "";
        String count = product.getCount() + "";
        view.setValues(product.getIconPath(), name, description, price, count, product.getCurrency());
    }

    @Override
    public void changeImage(String image, ImageView imageView) {
        Picasso.get().load(image)./*transform(new CropSquareTransformation(imageView)).*/into(imageView);
        imageView.setTag(image);
    }
    //content://media/external/images/media/37749
    //content://media/external/images/media/37749
    @Override
    public void changeImage(Uri iconUri, ContentResolver contentResolver, ImageView imageView) {
        changeImage(getRealPathFromURI(iconUri, contentResolver), imageView);
    }

    private String getRealPathFromURI(Uri contentURI, ContentResolver contentResolver) {
        String result;
        Cursor cursor = contentResolver.query(contentURI, null, null, null, null);
        if (cursor == null) {
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    private static class CropSquareTransformation implements Transformation {
        private final ImageView view;

        public CropSquareTransformation(ImageView view) {
            this.view = view;
        }

        @Override
        public Bitmap transform(Bitmap source) {
            int targetWidth = view.getWidth();

            double aspectRatio = (double) source.getHeight() / (double) source.getWidth();
            int targetHeight = (int) (targetWidth * aspectRatio);
            Bitmap result = Bitmap.createScaledBitmap(source, targetWidth, targetHeight, false);
            if (result != source) {
                source.recycle();
            }
            return result;
        }

        @Override
        public String key() {
            return "cropSquareTransformation";
        }
    }
}
