package mydomain.org.productlist.view;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
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
        presenter = new EditPresenterImpl(this);
    }


    @Override
    public void setValues(String name, String description, float price, float count, Currency currency) {
        if (name == null) name = "";
        this.name.setText(name);
        if (description == null) description = "";
        this.description.setText(description);
        this.price.setText(price + "");
        this.count.setText(count + "");
        ArrayAdapter adapter = new ArrayAdapter(EditActivity.this, android.R.layout.simple_spinner_item, 0, Currency.getAllSymbols());
        this.currency.setAdapter(adapter);
    }

    @Override
    public void close() {
        onDestroy();
    }
}
