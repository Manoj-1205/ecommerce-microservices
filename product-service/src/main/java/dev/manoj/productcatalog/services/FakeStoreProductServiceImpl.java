package dev.manoj.productcatalog.services;


import dev.manoj.productcatalog.clients.fakeStoreApi.FakeStoreClient;
import dev.manoj.productcatalog.clients.fakeStoreApi.FakeStoreProductDto;
import dev.manoj.productcatalog.dtos.ProductDto;
import dev.manoj.productcatalog.dtos.ProductNamePriceDto;
import dev.manoj.productcatalog.dtos.UserDTO;
import dev.manoj.productcatalog.exceptions.NotFoundException;
import dev.manoj.productcatalog.models.Category;
import dev.manoj.productcatalog.models.Product;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;


@Service
@AllArgsConstructor
@Qualifier("FakeStoreProductServiceImpl")
public class FakeStoreProductServiceImpl implements ProductService {

    //Rest Template Builder is used to create instance of rest template
    //and using rest template we can do a http call to 3rd party apis

    private FakeStoreClient fakeStoreClient;
    private RedisTemplate<Long, Object> redisTemplate;


    public Product convertFakeStoreProductDtoToProduct(FakeStoreProductDto fakeStoreProductDto){
        Category category = new Category();
        category.setName(fakeStoreProductDto.getCategory());
        return Product.builder()
                .id(fakeStoreProductDto.getId())
                .title(fakeStoreProductDto.getTitle())
                .price(fakeStoreProductDto.getPrice())
                .imageUrl(fakeStoreProductDto.getImage())
                .category(category)
                .build();
    }

    public FakeStoreProductDto convertProductToFakeStoreDto(Product product){
        return FakeStoreProductDto.builder()
                .id(product.getId())
                .title(product.getTitle())
                .description(product.getDescription())
                .price(product.getPrice())
                .image(product.getImageUrl())
                .category(product.getCategory().getName())
                .build();
    }

    @Override
    public Page<Product> getProducts(String query,int offset, int noOfResults) {
        return null;
    }

    @Override
    public  List<Product> getAllProducts() {

        List<FakeStoreProductDto> fakeStoreProductList = fakeStoreClient.getAllProducts(); //Client Call
        List<Product> productList=new ArrayList<>();
        for(FakeStoreProductDto fakeStoreProductDto : fakeStoreProductList){
            Product product = Product.builder()
                    .title(fakeStoreProductDto.getTitle())
                    .description(fakeStoreProductDto.getDescription())
                    .price(fakeStoreProductDto.getPrice())
                    .imageUrl(fakeStoreProductDto.getImage())
                    .rating(fakeStoreProductDto.getRating().toRating())
                    .build();
            productList.add(product);

        }
        return productList;
    }


        @Override public ResponseEntity<Product> getSingleProduct(Long productId) {

//            FakeStoreProductDto fakeStoreProductDto = (FakeStoreProductDto) redisTemplate.opsForHash().get(productId, "PRODUCTS");
//            //It will return the response entity of the product DTO from the API call
//            if(fakeStoreProductDto!=null)
//                return new ResponseEntity<>(convertFakeStoreProductDtoToProduct(fakeStoreProductDto), HttpStatus.OK);
            ResponseEntity<FakeStoreProductDto> response = fakeStoreClient.getSingleProduct(productId);
            if(response.getBody()==null){
                throw new NotFoundException("Product not found with id : "+productId);
            }
            Product product = convertFakeStoreProductDtoToProduct(response.getBody());
//            redisTemplate.opsForHash().put(productId,"PRODUCTS",response.getBody());
            return new ResponseEntity<>(product,HttpStatus.OK);
        }

    @Override
    public Product addNewProduct(ProductDto productDto) {
            FakeStoreProductDto fakeStoreProductDto = FakeStoreProductDto.builder()
                    .id(productDto.getId())
                    .title(productDto.getTitle())
                    .description(productDto.getDescription())
                    .image(productDto.getImage())
                    .build();
            ResponseEntity<FakeStoreProductDto> fakeStoreProductDtoResponseEntity = fakeStoreClient.addNewProduct(fakeStoreProductDto);
            Product product = convertFakeStoreProductDtoToProduct(fakeStoreProductDtoResponseEntity.getBody());
            return product;
    }

    @Override
    //Put request
    public Product replaceProduct(Long productId, Product product) {
        FakeStoreProductDto fakeStoreProductDto = fakeStoreClient.replaceProduct(productId, convertProductToFakeStoreDto(product)).getBody();
        return convertFakeStoreProductDtoToProduct(fakeStoreProductDto);
    }

    @Override
    //PATCH Request
    public Product updateProduct(Long productId, Product product) {
       FakeStoreProductDto fakeStoreProductDto = fakeStoreClient.updateProduct(productId, convertProductToFakeStoreDto(product)).getBody();
        return convertFakeStoreProductDtoToProduct(fakeStoreProductDto);
    }

    @Override

    public Product deleteProduct(Long productId) {
        Product product = getSingleProduct(productId).getBody();
        product.setIsDeleted(true);
        return product;
    }

    @Override
    public List<Product> getProductsInCategory(String categoryType) {
        return null;
    }

    @Override
    public UserDTO getUserDetails() {
        return null;
    }

    @Override
    public List<ProductNamePriceDto> requestFromOrderService(List<Long> productIds) {

        return null;
    }



    //RequestForEntity methods

}

