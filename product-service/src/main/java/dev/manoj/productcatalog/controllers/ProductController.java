package dev.manoj.productcatalog.controllers;


import dev.manoj.productcatalog.clients.authenticationClient.AuthenticationClient;
import dev.manoj.productcatalog.clients.authenticationClient.dtos.Role;
import dev.manoj.productcatalog.clients.authenticationClient.dtos.SessionStatus;
import dev.manoj.productcatalog.clients.authenticationClient.dtos.ValidateTokenRequestDTO;
import dev.manoj.productcatalog.clients.authenticationClient.dtos.ValidateTokenResponseDTO;
import dev.manoj.productcatalog.clients.fakeStoreApi.FakeStoreProductDto;
import dev.manoj.productcatalog.dtos.GetProductRequestDto;
import dev.manoj.productcatalog.dtos.ProductDto;
import dev.manoj.productcatalog.dtos.ProductNamePriceDto;
import dev.manoj.productcatalog.dtos.UserDTO;
import dev.manoj.productcatalog.exceptions.NotFoundException;
import dev.manoj.productcatalog.models.Category;
import dev.manoj.productcatalog.models.Product;
import dev.manoj.productcatalog.services.ProductService;
import dev.manoj.productcatalog.services.SelfProductService;
import lombok.AllArgsConstructor;
import org.apache.naming.EjbRef;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.actuate.autoconfigure.observation.ObservationProperties;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")

//@NoArgsConstructor

public class ProductController {
    private ProductService productService;
    private AuthenticationClient authenticationClient;
    ProductController(@Qualifier("SelfProductService") ProductService productService, AuthenticationClient authenticationClient){
        this.productService=productService;
        this.authenticationClient=authenticationClient;
    }

//    public ProductController(ProductService productService) {
//        this.productService = productService;
//    }

    @GetMapping("/paginated")
    public Page<Product> getProducts(@RequestBody GetProductRequestDto requestDto){
        return productService.getProducts(
                requestDto.getQuery(),
                requestDto.getOffset(),
                requestDto.getNoOfResults()
        );
    }



    //Only admins should access this API. If not return status code 403: Not authorized
    @GetMapping()
    public ResponseEntity<List<Product>> getAllProducts(@Nullable @RequestHeader("AUTH_TOKEN") String token,
                                                        @Nullable @RequestHeader("USER_ID") Long userId) {


        List<Product> productList = productService.getAllProducts();
        return new ResponseEntity<>(
                productList,
                HttpStatus.OK
        );
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Product> getSingleProduct(@PathVariable Long productId) {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("auth-token", "permission-granted");
        headers.add("auth-token", "Temporary access");

        Product product;
        try {
            product = productService.getSingleProduct(productId).getBody();
        } catch (NotFoundException notFoundException) {
            throw new NotFoundException(notFoundException.getMessage());
        }

        ResponseEntity<Product> responseEntity = new ResponseEntity<>(product, headers, HttpStatus.OK);

        return responseEntity;
//        return productService.getSingleProduct(productId).getBody();
    }


    @PostMapping()
    public ResponseEntity<Product> addNewProduct(@RequestBody ProductDto productDtoObj) {
        Product product = productService.addNewProduct(productDtoObj);
        return new ResponseEntity<>(product, HttpStatus.OK);

    }

    @PutMapping("/{productId}")
    public ProductDto replaceProduct(@PathVariable("productId") Long productId, @RequestBody ProductDto productDto) {
        Product product = convertProductDtoToProduct(productDto);
        return convertProductToProductDto(productService.replaceProduct(productId, product));

    }
    @PatchMapping("/{productId}")
    public ProductDto updateProduct(@PathVariable("productId") Long productId, @RequestBody ProductDto productDto) {

        Product product = productService.updateProduct(productId, convertProductDtoToProduct(productDto));
        System.out.println("Product Image "+product.getImageUrl());
        return convertProductToProductDto(product);

    }

    @DeleteMapping("/{productId}")
    public ProductDto deleteProduct(@PathVariable("productId") Long productId) {
        Product product=productService.deleteProduct(productId);
        ProductDto responseProductDto = convertProductToProductDto(product);
        responseProductDto.setIsDeleted(product.getIsDeleted());
        return responseProductDto;
    }


    public Product convertProductDtoToProduct(ProductDto productDto){
        Category category=new Category();

        return Product.builder()
                .title(productDto.getTitle())
                .price(productDto.getPrice())
                .description(productDto.getDescription())
                .imageUrl(productDto.getImage())
                .category(category)
                .build();
    }
    public ProductDto convertProductToProductDto(Product product){
        return ProductDto.builder()
                .id(product.getId())
                .title(product.getTitle())
                .description(product.getDescription())
                .price(product.getPrice())
                .image(product.getImageUrl())

                .build();
    }

    @GetMapping("/user")
    public UserDTO getUserDetails(){
        return productService.getUserDetails();
    }
    @PostMapping("/details")
    public List<ProductNamePriceDto> requestFromOrderService(@RequestBody List<Long> productIds){
        return productService.requestFromOrderService(productIds);
    }


}
