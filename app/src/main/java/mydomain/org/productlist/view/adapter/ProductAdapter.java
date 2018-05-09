package mydomain.org.productlist.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;


import mydomain.org.productlist.R;
import mydomain.org.productlist.presenter.ProductPresenter;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder>{
    private ProductPresenter presenter;
    public ProductAdapter(ProductPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_line, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
       presenter.setValues(holder, position);
    }

    public void filter(String str){
        presenter.search(str);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return presenter.getItemCount();
    }

    public void removeAt(int position) {
        presenter.deleteElement(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, presenter.getItemCount());
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView nameField;
        public final TextView priceField;
        public final TextView countField;

        public ViewHolder(View v) {
            super(v);
            nameField = v.findViewById(R.id.product_name);
            priceField = v.findViewById(R.id.product_price);
            countField = v.findViewById(R.id.product_price);
        }
    }
}