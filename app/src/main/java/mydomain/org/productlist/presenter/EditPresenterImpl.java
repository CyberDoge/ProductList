package mydomain.org.productlist.presenter;

import android.graphics.Bitmap;
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
        view.setValues(product.getIconPath(), product.getName(), product.getDescription(), product.getPrice(), product.getCount(), product.getCurrency());
    }

    @Override
    public void changeImage(String image, ImageView imageView) {
        Picasso.get().load(image).transform(new CropSquareTransformation()).into(imageView);
        imageView.setTag(image);
    }

    private static class CropSquareTransformation implements Transformation {
        @Override
        public Bitmap transform(Bitmap source) {
            int size = Math.min(source.getWidth(), source.getHeight());
            int mWidth = (source.getWidth() - size) / 2;
            int mHeight = (source.getHeight() - size) / 2;
            size = Math.max(mHeight, mWidth);
            Bitmap bitmap = Bitmap.createBitmap(source, mWidth, mHeight, size, size);
            if (bitmap != source) {
                source.recycle();
            }

            return bitmap;
        }

        @Override
        public String key() {
            return "CropSquareTransformation";
        }
    }
}
