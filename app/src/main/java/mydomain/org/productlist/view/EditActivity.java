package mydomain.org.productlist.view;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import com.squareup.picasso.Picasso;

import mydomain.org.productlist.R;
import mydomain.org.productlist.model.Currency;
import mydomain.org.productlist.presenter.EditPresenter;
import mydomain.org.productlist.presenter.EditPresenterImpl;

public class EditActivity extends AppCompatActivity implements EditView {

    private TextInputEditText name;
    private TextInputEditText description;
    private TextInputEditText price;
    private TextInputEditText count;
    private ImageView icon;
    private Spinner currency;
    private EditPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        icon = findViewById(R.id.product_icon);
        name = findViewById(R.id.product_name);
        description = findViewById(R.id.product_description);
        price = findViewById(R.id.product_price);
        count = findViewById(R.id.product_count);
        currency = findViewById(R.id.currency);
        FloatingActionButton changeIcon = findViewById(R.id.change_photo);
        changeIcon.setOnClickListener((v) -> selectImage());
        presenter = new EditPresenterImpl(this, getIntent().getIntExtra("position", -1));
        presenter.setValues();
    }


    @Override
    public void setValues(String icon, String name, String description, String price, String count, Currency currency) {
        this.name.setText(name);
        this.description.setText(description);
        this.price.setText(price);
        this.count.setText(count);
        ArrayAdapter adapter = new ArrayAdapter(EditActivity.this, android.R.layout.simple_spinner_item, 0, Currency.getAllSymbols());
        this.currency.setAdapter(adapter);
        this.currency.setSelection(currency.ordinal());
        presenter.changeImage(icon, this.icon);
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
                presenter.update(icon.getTag().toString(), name.getText().toString(), description.getText().toString(), price.getText().toString(),
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
        builder.setPositiveButton("close", (dialog, id) -> close());
        builder.create().show();
    }

    private void selectImage() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, 1234);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1234:
                if (resultCode == RESULT_OK) {
                    presenter.changeImage(data.getDataString(), icon);
                    //presenter.changeImage(data.getData(), getContentResolver(), icon);
                }

        }
    }

    @Override
    public Context getContext() {
        return EditActivity.this;
    }
}
