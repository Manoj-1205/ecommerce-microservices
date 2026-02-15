package dev.manoj.productcatalog.repositories;

import dev.manoj.productcatalog.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAll();

    Product findProductById(Long productId);

    Page<Product> findProductByTitleContaining(String query,Pageable pageable);

    //Find Single product
    @Query(value = "select * from product where id = :productId", nativeQuery = true)
    Optional<Product> findById(Long productId);

    @Query(value = "select title from product where id = :productId", nativeQuery = true)
    String fetchOnlyProductName(Long productId);

    Product save(Product product);

    List<Product> findByPriceLessThan(Double amount);

    List<Product> findByCategory_Name(String categoryName);

//    List<Product> findByCategory_NameAndOrderByPriceAsc(String CategoryName);
    List<Product> findByCategory_NameOrderByPrice(String categoryName);

    Product deleteProductById(Long ProductId);


}
