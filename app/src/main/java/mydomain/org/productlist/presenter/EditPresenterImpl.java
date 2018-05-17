package mydomain.org.productlist.presenter;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.io.File;

import mydomain.org.productlist.R;
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

        String name = product.getName();
        String description = product.getDescription();
        String price = product.getPrice() + "";
        String count = product.getCount() + "";
        view.setValues(product.getIconPath(), name, description, price, count, product.getCurrency());
    }

    @Override
    public void changeImage(Uri iconUri, ImageView imageView) {
        String image = getRealPathFromURI(iconUri);
        Picasso.get().load(new File(image)).error(R.mipmap.noimage).transform(new CropSquareTransformation()).into(imageView);
        imageView.setTag(image);
    }

    private String getRealPathFromURI(Uri contentURI) {
        try {
            String[] projection = {MediaStore.Images.Media.DATA};
            Cursor cursor = view.getContext().getContentResolver().query(contentURI, projection, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String res = cursor.getString(column_index);
            cursor.close();
            return res;
        } catch (NullPointerException e) {
            return "";
        }
    }

    private static class CropSquareTransformation implements Transformation {

        private int mWidth;
        private int mHeight;

        @Override
        public Bitmap transform(Bitmap source) {
            int size = Math.min(source.getWidth(), source.getHeight());

            mWidth = (source.getWidth() - size) / 2;
            mHeight = (source.getHeight() - size) / 2;

            Bitmap bitmap = Bitmap.createBitmap(source, mWidth, mHeight, size, size);
            if (bitmap != source) {
                source.recycle();
            }

            return bitmap;
        }

        @Override
        public String key() {
            return "CropSquareTransformation(width=" + mWidth + ", height=" + mHeight + ")";
        }
    }
}
