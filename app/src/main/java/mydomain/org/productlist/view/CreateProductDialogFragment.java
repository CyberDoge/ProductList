package mydomain.org.productlist.view;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import mydomain.org.productlist.R;
import mydomain.org.productlist.model.Currency;
import mydomain.org.productlist.presenter.ListPresenter;

public class CreateProductDialogFragment extends DialogFragment {
    private ListPresenter presenter;
    private EditText name;
    private EditText price;
    private EditText count;
    private TextView errors;
    private Spinner currency;
    private AppCompatActivity activity;

    public void init(final ListPresenter presenter, final AppCompatActivity activity) {
        this.presenter = presenter;
        this.activity = activity;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.create_dialog, container, false);
       name = rootView.findViewById(R.id.create_name);
        price = rootView.findViewById(R.id.create_price);
        count = rootView.findViewById(R.id.create_count);
        currency = rootView.findViewById(R.id.create_currency);
        errors = rootView.findViewById(R.id.errors_message);
        ArrayAdapter adapter = new ArrayAdapter(rootView.getContext(), android.R.layout.simple_spinner_item, 0, Currency.getAllSymbols());
        currency.setAdapter(adapter);
        currency.setSelection(0);
        setHasOptionsMenu(true);
        rootView.setFocusableInTouchMode(true);
        rootView.requestFocus();
        return rootView;
    }


   @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        getActivity().getMenuInflater().inflate(R.menu.menu_create, menu);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setHomeButtonEnabled(true);
    }

   @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.button_action_save: {
                if (presenter.addElement(name, price, count, (Character) currency.getSelectedItem()))
                        dismiss();
                else errors.setText(R.string.error);
                return true;
            }
            case android.R.id.home: {
                dismiss();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStop() {
        super.onStop();
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        activity.getSupportActionBar().setHomeButtonEnabled(false);
    }
}
