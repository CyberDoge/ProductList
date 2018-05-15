package mydomain.org.productlist.view;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SearchView;

import mydomain.org.productlist.R;
import mydomain.org.productlist.presenter.ListPresenter;
import mydomain.org.productlist.presenter.ListPresenterImpl;
import mydomain.org.productlist.view.adapter.ProductAdapter;


public class ListFragment extends Fragment implements ListView {
    private RecyclerView recyclerView;

    private OnFragmentInteractionListener mListener;

    private ListPresenter presenter;
    private ProductAdapter adapter;

    public ListFragment() {
    }

    public static ListFragment newInstance() {
        ListFragment fragment = new ListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        this.presenter = new ListPresenterImpl(this);
        recyclerView = view.findViewById(R.id.products_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        FloatingActionButton fab = view.findViewById(R.id.add_btn);
        fab.setOnClickListener((v) -> mListener.openCreateProductFragment());
        adapter = new ProductAdapter(presenter);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(view.getContext(), LinearLayout.HORIZONTAL));
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
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

        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        super.getView().setFocusableInTouchMode(true);
        super.getView().requestFocus();
        super.getView().setOnKeyListener((v, keyCode, event)-> {
            if (event.getAction() == KeyEvent.ACTION_DOWN) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    getActivity().finish();
                    return true;
                }
            }
            return false;
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void addProductToView() {
        adapter.notifyDataSetChanged();
        recyclerView.scrollToPosition(adapter.getItemCount() - 1);
    }

    public void openInfoDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder
                .setMessage(getString(R.string.select_action))
                .setNegativeButton(getText(R.string.delete), (dialog, which) -> {
                    dialog.dismiss();
                    adapter.removeAt(position, recyclerView);
                })
                .setPositiveButton(R.string.edit, (dialog, which) -> {
                    dialog.dismiss();
                    openEditActivity(position);
                })
                .setNeutralButton(R.string.cancel, (dialog, which) -> dialog.dismiss())
                .create().show();
    }


    public void showDescription(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(R.string.description)
                .setMessage(presenter.getDescription(position))
                .setPositiveButton(android.R.string.ok, (d, w) -> d.dismiss())
                .create().show();
    }

    public void openEditActivity(int position) {
        Intent intent = new Intent(getContext(), EditActivity.class);
        intent.putExtra("position", position);
        startActivity(intent);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_list, menu);
        SearchManager searchManager = (SearchManager) getContext().getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setOnCloseListener(()->{
            searchView.clearFocus();
            return false;
        });
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
        super.onCreateOptionsMenu(menu, inflater);
    }


    public interface OnFragmentInteractionListener {
        void openCreateProductFragment();
    }
}
