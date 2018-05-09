package mydomain.org.productlist.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.net.Uri;
import android.support.annotation.NonNull;

import mydomain.org.productlist.Dao.TypeTransmogrifier;

@Entity(tableName = "products")
public class Product {
    private static int onlyForDebug = 0;//todo
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int pid;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "description")
    private String description;
    @ColumnInfo(name = "price")
    private float price;
    @ColumnInfo(name = "count")
    private int count;
    @ColumnInfo(name = "iconPath")
    private Uri iconPath;
    @ColumnInfo(name = "currency")
    @TypeConverters(TypeTransmogrifier.class)
    private Currency currency;


    public Product() {
    }

    @Ignore
    public Product(String name, float price, int count) {
        this.name = name;
        this.price = price;
        this.count = count;
        currency = Currency.RUB;
        description = "";
    }

    public static Product createDefault() {
        return new Product("new product" + onlyForDebug++, 0, 0);
    }

    public Uri getIconPath() {
        return iconPath;
    }

    public void setIconPath(Uri iconPath) {
        this.iconPath = iconPath;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public void changeDescription(String description) {
        this.description = description;
    }

    public void changeName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}

