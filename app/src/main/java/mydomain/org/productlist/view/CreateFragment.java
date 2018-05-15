package mydomain.org.productlist.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import mydomain.org.productlist.R;
import mydomain.org.productlist.model.Currency;
import mydomain.org.productlist.presenter.CreatePresenter;
import mydomain.org.productlist.presenter.CreatePresenterImpl;

public class CreateFragment extends Fragment implements CreateView {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private CreatePresenter presenter;
    private EditText name;
    private EditText price;
    private EditText count;
    private TextView errors;
    private Spinner currency;

    public CreateFragment() {
        presenter = new CreatePresenterImpl(this);
    }

    public static CreateFragment newInstance() {
        CreateFragment fragment = new CreateFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create, container, false);
        name = view.findViewById(R.id.create_name);
        price = view.findViewById(R.id.create_price);
        count = view.findViewById(R.id.create_count);
        currency = view.findViewById(R.id.create_currency);
        errors = view.findViewById(R.id.errors_message);
        ArrayAdapter adapter = new ArrayAdapter(view.getContext(), android.R.layout.simple_spinner_item, 0, Currency.getAllSymbols());
        currency.setAdapter(adapter);
        currency.setSelection(0);
        setHasOptionsMenu(true);
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener((v, keyCode, event) -> {
            if (event.getAction() == KeyEvent.ACTION_DOWN) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    ListFragment fragmentFirst = new ListFragment();
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frag_content, fragmentFirst);
                    fragmentTransaction.commit();
                    return true;
                }
            }
            return false;
        });
    }

    public void onSaveButtonPressed() {
        if (presenter.createProduct(name.getText().toString(), price.getText().toString(), count.getText().toString(), (Character) currency.getSelectedItem())) {
            if (mListener != null) {
            }
            close();
        } else errors.setText(R.string.error);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_create, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.button_action_save: {
                onSaveButtonPressed();
                close();
                return true;
            }
            case android.R.id.home: {
                close();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
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

    private void close() {
        getActivity().getFragmentManager().popBackStack();
    }

    public interface OnFragmentInteractionListener {

    }
}
