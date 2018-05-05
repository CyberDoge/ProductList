package mydomain.org.productlist.view;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import mydomain.org.productlist.R;
import mydomain.org.productlist.adapter.ProductAdapter;
import mydomain.org.productlist.presenter.ProductPresenter;
import mydomain.org.productlist.presenter.ProductPresenterImpl;

public class ListActivity extends AppCompatActivity implements ListView {
    private RecyclerView recyclerView;
    private ProductPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        recyclerView = findViewById(R.id.products_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        presenter = new ProductPresenterImpl(this);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(ListActivity.this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                presenter.onItemClick(position);
            }

            @Override
            public void onLongItemClick(View view, int position) {
                presenter.onItemLongClick(position);
            }
        }));

        recyclerView.setAdapter(new ProductAdapter(presenter));
    }

    @Override
    public void openInfoDialog() {
        final Dialog dialog = new Dialog(ListActivity.this, android.R.style.Theme_Translucent_NoTitleBar);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.choose_dialog);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));


        dialog.getWindow().setAttributes(lp);

        TextView deleteBtn = dialog.findViewById(R.id.delete_btn);
        TextView editBtn = dialog.findViewById(R.id.edit_btn);


        deleteBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        editBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public void openEditActivity() {

    }
}
