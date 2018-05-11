package mydomain.org.productlist.view.adapter;

import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import mydomain.org.productlist.R;
import mydomain.org.productlist.presenter.ListPresenter;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private ListPresenter presenter;

    public ProductAdapter(ListPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_line, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        presenter.setValues(holder, position);
    }

    public void filter(String str) {
        presenter.search(str);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return presenter.getItemCount();
    }

    public void removeAt(int position, final RecyclerView recyclerView) {
        Snackbar snackbar = Snackbar
                .make(recyclerView, R.string.item_removed, Snackbar.LENGTH_LONG)
                .setAction(R.string.undo, (view) -> {
                    ((ViewHolder)recyclerView.findViewHolderForAdapterPosition(position)).setVisible(true);
                });
        snackbar.addCallback(new BaseTransientBottomBar.BaseCallback<Snackbar>() {
            @Override
            public void onDismissed(Snackbar transientBottomBar, int event) {
                if(event == BaseTransientBottomBar.BaseCallback.DISMISS_EVENT_TIMEOUT) {
                    presenter.deleteElementAtPosition(position);
                    notifyItemRemoved(position);
                }
            }
        });
        ((ViewHolder)recyclerView.findViewHolderForAdapterPosition(position)).setVisible(false);
        snackbar.show();

    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public final ImageView iconField;
        public final TextView nameField;
        public final TextView priceField;
        public final TextView countField;
        public final View v;

        public ViewHolder(View v) {
            super(v);
            this.v = v;
            iconField = v.findViewById(R.id.product_icon);
            nameField = v.findViewById(R.id.product_name);
            priceField = v.findViewById(R.id.product_price);
            countField = v.findViewById(R.id.product_count);
        }

        public void setVisible(boolean b){
            if (b) {
                v.setVisibility(View.VISIBLE);
            } else {
                v.setVisibility(View.GONE);
            }
        }
    }
}