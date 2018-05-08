package mydomain.org.productlist.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import mydomain.org.productlist.R;
import mydomain.org.productlist.adapter.ProductAdapter;
import mydomain.org.productlist.presenter.ProductPresenter;
import mydomain.org.productlist.presenter.ProductPresenterImpl;

public class ListActivity extends AppCompatActivity implements ListView {
    private RecyclerView recyclerView;
    private ProductPresenter presenter;
    private ProductAdapter adapter;

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
        adapter = new ProductAdapter(presenter);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void openInfoDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ListActivity.this);

        LayoutInflater inflater = ListActivity.this.getLayoutInflater();
        View view = inflater.inflate(R.layout.choose_dialog, null);
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getWindow().setLayout((int) getResources().getDimension(R.dimen.dialog_width), (int) getResources().getDimension(R.dimen.dialog_height));

        TextView deleteBtn = view.findViewById(R.id.delete_btn);
        TextView editBtn = view.findViewById(R.id.edit_btn);

        deleteBtn.setOnClickListener((v) -> {
            deleteElement(position);
            dialog.dismiss();
        });

        editBtn.setOnClickListener((v) -> {
            dialog.dismiss();
            openEditActivity(position);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void openEditActivity(int position) {
        Intent intent = new Intent(ListActivity.this, EditActivity.class);
        intent.putExtra("position", position);
        startActivity(intent);
    }

    @Override
    public void deleteElement(int position) {
        adapter.removeAt(position);
    }

}
