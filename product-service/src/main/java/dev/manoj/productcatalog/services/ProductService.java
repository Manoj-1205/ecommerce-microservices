package dev.manoj.productcatalog.services;


import dev.manoj.productcatalog.clients.fakeStoreApi.FakeStoreProductDto;
import dev.manoj.productcatalog.dtos.GetProductRequestDto;
import dev.manoj.productcatalog.dtos.ProductDto;
import dev.manoj.productcatalog.dtos.ProductNamePriceDto;
import dev.manoj.productcatalog.dtos.UserDTO;
import dev.manoj.productcatalog.exceptions.NotFoundException;
import dev.manoj.productcatalog.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductService {

    Page<Product> getProducts(String query, int offset, int noOfResults);

    List<Product> getAllProducts();

    ResponseEntity<Product> getSingleProduct(Long productId) throws NotFoundException;

    Product addNewProduct(ProductDto product);

    /*
    Product object has only those fields filled which need to be updated.
    Everything else is null
     */
    Product replaceProduct(Long productId, Product product);
    Product updateProduct(Long productId, Product product);
    // if (product.getImageUrl() != null) {
    //
    // }

    Product deleteProduct(Long productId) throws NotFoundException;

    List<Product> getProductsInCategory(String categoryType);
     UserDTO getUserDetails();

    List<ProductNamePriceDto> requestFromOrderService(List<Long> productIds);
}

// update product with id 123
// {
//   name: iPhone 15
// }
