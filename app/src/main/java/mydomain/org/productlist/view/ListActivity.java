package mydomain.org.productlist.view;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.View;
import android.widget.SearchView;

import mydomain.org.productlist.R;
import mydomain.org.productlist.presenter.ListPresenter;
import mydomain.org.productlist.presenter.ListPresenterImpl;
import mydomain.org.productlist.view.adapter.ProductAdapter;

public class ListActivity extends AppCompatActivity implements ListView {
    private ListPresenter presenter;
    private ProductAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        recyclerView = findViewById(R.id.products_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        presenter = new ListPresenterImpl(this);
        FloatingActionButton fab = findViewById(R.id.add_btn);
        fab.setOnClickListener((view) -> openCreateDialog());

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(ListActivity.this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                openInfoDialog(position);
            }

            @Override
            public void onLongItemClick(View view, int position) {
                showDescription(position);
            }
        }));

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0 || dy < 0 && fab.isShown())
                    fab.hide();
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    fab.show();
                }
                super.onScrollStateChanged(recyclerView, newState);
            }
        });

        adapter = new ProductAdapter(presenter);
        recyclerView.setAdapter(adapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
    }

    public void openInfoDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ListActivity.this);
        builder
                .setMessage(getString(R.string.select_action))
                .setNegativeButton(getText(R.string.delete), (dialog, which) -> {
                    dialog.dismiss();
                    deleteElement(position);
                })
                .setPositiveButton(R.string.edit, (dialog, which) -> {
                    dialog.dismiss();
                    openEditActivity(position);
                })
                .setNeutralButton(R.string.cancel, (dialog, which) -> dialog.dismiss())
                .create().show();
    }

    public void openCreateDialog() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        CreateProductDialogFragment dialog = new CreateProductDialogFragment();
        dialog.init(presenter, this);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.add(android.R.id.content, dialog).addToBackStack(null).commitAllowingStateLoss();
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    public void openEditActivity(int position) {
        Intent intent = new Intent(ListActivity.this, EditActivity.class);
        intent.putExtra("position", position);
        startActivity(intent);
    }

    public void deleteElement(int position) {
        adapter.removeAt(position, recyclerView);
    }

    @Override
    public void addProductToView() {
        adapter.notifyDataSetChanged();
        recyclerView.scrollToPosition(adapter.getItemCount() - 1);
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

        SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.filter(newText);
                return true;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.filter(query);
                return true;
            }
        };
        searchView.setOnQueryTextListener(queryTextListener);

        return true;
    }

    public void showDescription(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ListActivity.this);
        builder.setTitle(R.string.description)
                .setMessage(presenter.getDescription(position))
                .setPositiveButton(android.R.string.ok, (d, w) -> d.dismiss())
                .create().show();
    }

}
