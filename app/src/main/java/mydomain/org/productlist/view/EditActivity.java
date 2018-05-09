package mydomain.org.productlist.view;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import mydomain.org.productlist.R;
import mydomain.org.productlist.model.Currency;
import mydomain.org.productlist.presenter.EditPresenter;
import mydomain.org.productlist.presenter.EditPresenterImpl;

public class EditActivity extends AppCompatActivity implements EditView {

    private TextInputEditText name;
    private TextInputEditText description;
    private TextInputEditText price;
    private TextInputEditText count;
    private Spinner currency;
    private EditPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        name = findViewById(R.id.product_name);
        description = findViewById(R.id.product_description);
        price = findViewById(R.id.product_price);
        count = findViewById(R.id.product_count);
        currency = findViewById(R.id.currency);
        presenter = new EditPresenterImpl(this, getIntent().getIntExtra("position", -1));
        presenter.setValues();
    }


    @Override
    public void setValues(String name, String description, float price, int count, Currency currency) {
        if (name == null) name = "";
        this.name.setText(name);
        if (description == null) description = "";
        this.description.setText(description);
        this.price.setText(price + "");
        this.count.setText(count + "");
        ArrayAdapter adapter = new ArrayAdapter(EditActivity.this, android.R.layout.simple_spinner_item, 0, Currency.getAllSymbols());
        this.currency.setAdapter(adapter);
        this.currency.setSelection(currency.ordinal());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_edit, menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_btn:
                presenter.update(name.getText().toString(), description.getText().toString(), price.getText().toString(),
                        count.getText().toString(), (Character) currency.getSelectedItem());
                close();
                return true;
            case android.R.id.home:
                close();
                return true;
            default:
                return true;
        }
    }

    @Override
    public void close() {
        finish();
    }

    @Override
    public void showErrorMessage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(EditActivity.this);
        builder.setMessage("Invalid product!");
        builder.setCancelable(false);
        builder.setIcon(R.drawable.ic_error_red_24dp);
        builder.setPositiveButton(
                "close", (dialog, id) -> close());
        builder.create().show();
    }

    @Override
    public Context getContext() {
        return EditActivity.this;
    }
}
