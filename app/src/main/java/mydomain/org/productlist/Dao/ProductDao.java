package mydomain.org.productlist.Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.TypeConverters;
import android.arch.persistence.room.Update;

import java.util.List;

import mydomain.org.productlist.model.Product;

@Dao
@TypeConverters({TypeTransmogrifier.class})
public interface ProductDao {
    @Insert
    void insertAll(Product... products);

    @Delete
    void delete(Product product);

    @Query("SELECT * FROM products")
    List<Product> getAllProduct();

    @Query("SELECT COUNT(*) FROM products")
    int getTotalCount();

    @Query("SELECT * FROM products LIMIT 1 OFFSET :position")
    Product getProductByPosition(int position);

    @Query("DELETE FROM products WHERE name in (SELECT name FROM products LIMIT 1 OFFSET :id)")
    void deleteElement(int id);

    @Insert
    void insert(Product product);

    @Update
    int updateProduct(Product product);
}
