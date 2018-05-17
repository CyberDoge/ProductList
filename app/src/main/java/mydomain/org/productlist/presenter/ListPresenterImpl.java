package mydomain.org.productlist.presenter;


import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.AsyncTask;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import mydomain.org.productlist.R;
import mydomain.org.productlist.database.AppDatabase;
import mydomain.org.productlist.model.Product;
import mydomain.org.productlist.view.ListView;
import mydomain.org.productlist.view.adapter.ProductAdapter;

public class ListPresenterImpl implements ListPresenter {
    private ListView view;
    private AppDatabase database;
    private List<Product> productFind;
    private boolean searching = false;
    private AgentAsyncTask asyncTask;

    public ListPresenterImpl(ListView view) {
        this.view = view;
        database = AppDatabase.getAppDatabase(view.getContext());
        productFind = new ArrayList<>();
        asyncTask = new AgentAsyncTask(database);
    }

    @Override
    public int getItemCount() {
        return searching ? productFind.size() : asyncTask.doInBackground();
    }


    @Override
    public void deleteElementAtPosition(int position) {
        database.getProductDao().deleteByPosition(position);
    }

    @Override
    public void search(String str) {
        if (str.isEmpty()) {
            searching = false;
            return;
        }
        List<Product> products = database.getProductDao().getAllProduct();
        productFind.clear();
        for (Product p : products) {
            if (p.getName().contains(str)) {
                productFind.add(p);
            }
        }
        searching = true;
    }

    @Override
    public void setValues(ProductAdapter.ViewHolder holder, int position) {
        Product product;
        if (searching) {
            if (position >= productFind.size()) {
                searching = false;
                return;
            }
            product = productFind.get(position);
        } else product = database.getProductDao().getProductByPosition(position);
        holder.nameField.setText(product.getName());
        holder.countField.setText(product.getCount() + "");
        holder.priceField.setText(String.format("%.2f" + product.getCurrency().getSymbol(), product.getPrice()));
        Picasso.get().load(new File(product.getIconPath())).placeholder(R.mipmap.ic_launcher_round).error(R.mipmap.ic_launcher_round).
                transform(new CircularTransformation()).into(holder.iconField);
    }

    @Override
    public String getDescription(int position) {
        return database.getProductDao().getProductByPosition(position).getDescription();
    }

    private static class AgentAsyncTask extends AsyncTask<Void, Void, Integer> {
        private AppDatabase database;

        public AgentAsyncTask(AppDatabase database) {
            this.database = database;
        }

        @Override
        protected Integer doInBackground(Void... databases) {
            return database.getProductDao().getTotalCount();
        }
    }

    private static class CircularTransformation implements Transformation {
        @Override
        public Bitmap transform(Bitmap source) {
            int size = Math.min(source.getWidth(), source.getHeight());

            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;

            Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size, size);
            if (squaredBitmap != source) {
                source.recycle();
            }

            Bitmap bitmap = Bitmap.createBitmap(size, size, source.getConfig());

            Canvas canvas = new Canvas(bitmap);
            Paint paint = new Paint();
            BitmapShader shader = new BitmapShader(squaredBitmap,
                    BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
            paint.setShader(shader);
            paint.setAntiAlias(true);

            float r = size / 2f;
            canvas.drawCircle(r, r, r, paint);

            squaredBitmap.recycle();
            return bitmap;
        }

        @Override
        public String key() {
            return "circle";
        }
    }
}
