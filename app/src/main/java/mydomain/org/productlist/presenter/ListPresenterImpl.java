package mydomain.org.productlist.presenter;


import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.os.AsyncTask;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import mydomain.org.productlist.R;
import mydomain.org.productlist.database.AppDatabase;
import mydomain.org.productlist.model.Currency;
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
    public void addElement(String name, String price, String count, char currency) {
        Product product = new Product();
        product.setName(name);
        product.setPrice(Float.parseFloat(price));
        product.setCount(Integer.parseInt(count));
        product.setCurrency(Currency.getCurrency(currency));
        database.getProductDao().insert(product);
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
        if (product.getIconPath() != null && new File(product.getIconPath()).exists())
            Picasso.get().load(product.getIconPath()).
                    transform(new CircularTransformation()).
                    into(holder.iconField);
        else  Picasso.get().load(R.mipmap.ic_launcher_round).
                transform(new CircularTransformation()).
                into(holder.iconField);
    }

    @Override
    public void onItemClick(int position) {
        view.openInfoDialog(position);
    }

    @Override
    public void onItemLongClick(int position) {

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
        public Bitmap transform(final Bitmap source) {
            final Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setShader(new BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));

            final Bitmap output = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
            final Canvas canvas = new Canvas(output);
            canvas.drawCircle(source.getWidth() / 2, source.getHeight() / 2, source.getWidth() / 2, paint);
            if (source != output)
                source.recycle();

            return output;
        }

        @Override
        public String key() {
            return "circle";
        }
    }
}
