package mydomain.org.productlist.view;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import mydomain.org.productlist.R;
import mydomain.org.productlist.view.adapter.ProductAdapter;
import mydomain.org.productlist.presenter.ProductPresenter;
import mydomain.org.productlist.presenter.ProductPresenterImpl;

public class ListActivity extends AppCompatActivity implements ListView {
    private ProductPresenter presenter;
    private ProductAdapter adapter;
    private RecyclerView recyclerView;

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
                int key = adapter.getPrimaryKey(recyclerView.findViewHolderForAdapterPosition(position));
                presenter.onItemClick(position, key);
            }

            @Override
            public void onLongItemClick(View view, int position) {
                presenter.onItemLongClick(position);
            }
        }));
        adapter = new ProductAdapter(presenter);
        recyclerView.setAdapter(adapter);
        FloatingActionButton button = findViewById(R.id.add_btn);
        button.setOnClickListener((view) -> addProduct());

        LinearLayout sortByNameLayout = findViewById(R.id.sorting_name_layout);
        LinearLayout sortByPriceLayout = findViewById(R.id.sorting_price_layout);
        LinearLayout sortByCountLayout = findViewById(R.id.sorting_count_layout);
        sortByNameLayout.setOnClickListener((v)->presenter.sortByName());
        sortByPriceLayout.setOnClickListener((v)->presenter.sortByPrice());
        sortByCountLayout.setOnClickListener((v)->presenter.sortByCount());
    }

    @Override
    public void openInfoDialog(final int position, final int key) {
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
            dialog.dismiss();
            deleteElement(position, key);

        });

        editBtn.setOnClickListener((v) -> {
            dialog.dismiss();
            openEditActivity(key);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void openEditActivity(int key) {
        Intent intent = new Intent(ListActivity.this, EditActivity.class);
        intent.putExtra("key", key);
        startActivity(intent);
    }

    @Override
    public void deleteElement(int position, int key) {
        adapter.removeAt(position, key);
    }

    @Override
    public void addProduct() {
        presenter.addElement();
        adapter.notifyDataSetChanged();
        recyclerView.scrollToPosition(adapter.getItemCount()-1);
    }

    @Override
    public Context getContext() {
        return ListActivity.this;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextChange(String newText)
            {
                adapter.filter(newText);
                return true;
            }
            @Override
            public boolean onQueryTextSubmit(String query)
            {
                adapter.filter(query);
                return true;
            }
        };
        searchView.setOnQueryTextListener(queryTextListener);
        return true;
    }
}
