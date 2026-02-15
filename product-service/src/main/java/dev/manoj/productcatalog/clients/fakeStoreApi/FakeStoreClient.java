package dev.manoj.productcatalog.clients.fakeStoreApi;

import dev.manoj.productcatalog.exceptions.NotFoundException;
//import dev.manoj.productcatalog.models.FakeStoreProductDto;
import dev.manoj.productcatalog.models.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
//import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Component
@Getter
@Setter
@AllArgsConstructor
public class FakeStoreClient {
//    private RestTemplateBuilder restTemplateBuilder;
    public  List<FakeStoreProductDto> getAllProducts(){
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<FakeStoreProductDto[]> FakeStoreProductsArray = restTemplate.getForEntity(
                "https://fakestoreapi.com/products",
                FakeStoreProductDto[].class
        );
        return Arrays.asList(FakeStoreProductsArray.getBody());
    };

    public ResponseEntity<FakeStoreProductDto> getSingleProduct(Long productId) throws NotFoundException{
        RestTemplate restTemplate = new RestTemplate();
        //It will return the response entity of the product DTO from the API call
        return restTemplate.getForEntity("https://fakestoreapi.com/products/{id}", FakeStoreProductDto.class, productId);
    }

    public ResponseEntity<FakeStoreProductDto>  addNewProduct(FakeStoreProductDto fakeStoreProductDto){
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<FakeStoreProductDto> fakeStoreProductDtoResponseEntity = restTemplate.postForEntity("https://fakestoreapi.com/products",
                fakeStoreProductDto, //Request Body
                FakeStoreProductDto.class //Return type class
        );
        return fakeStoreProductDtoResponseEntity;
    }

    /*
    FakeStoreProductDto object has only those fields filled which need to be updated.
    Everything else is null
     */
    public ResponseEntity<FakeStoreProductDto> replaceProduct(Long productId, FakeStoreProductDto fakeStoreProductDto){
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<FakeStoreProductDto> fakeStoreProductDtoResponseEntity = requestForEntity(
                HttpMethod.PUT,
                "https://fakestoreapi.com/products/{id}",
                fakeStoreProductDto,
                FakeStoreProductDto.class,
                productId
        );
        return fakeStoreProductDtoResponseEntity;
    }
    public ResponseEntity<FakeStoreProductDto> updateProduct(Long productId, FakeStoreProductDto fakeStoreProductDto){
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<FakeStoreProductDto> fakeStoreProductDtoResponseEntity = requestForEntity(
                HttpMethod.PATCH,
                "https://fakestoreapi.com/products/{id}",
                fakeStoreProductDto,
                FakeStoreProductDto.class,
                productId
        );
        return  fakeStoreProductDtoResponseEntity;
    }


    FakeStoreProductDto deleteProduct(Long productId){
        return null;
    }

    private <T> ResponseEntity<T> requestForEntity(HttpMethod httpMethod, String url, @Nullable Object request, Class<T> responseType, Object... uriVariables) throws RestClientException {
        RestTemplate restTemplate = new RestTemplate();
        RequestCallback requestCallback = restTemplate.httpEntityCallback(request, responseType);
        ResponseExtractor<ResponseEntity<T>> responseExtractor = restTemplate.responseEntityExtractor(responseType);
        return (ResponseEntity)restTemplate.execute(url, httpMethod, requestCallback, responseExtractor, uriVariables);
    }

    public List<String> getAllCategories(){
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String[]> AllCategoryEntities = restTemplate.getForEntity("https://fakestoreapi.com/products/categories",
                String[].class);
        return Arrays.asList(AllCategoryEntities.getBody());
    };

    public ResponseEntity<FakeStoreProductDto[]> getProductsInCategory(String categoryType){
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<FakeStoreProductDto[]> productFromCategory = restTemplate.getForEntity("https://fakestoreapi.com/products/category/{type}",
                FakeStoreProductDto[].class,
                categoryType);
        return productFromCategory;
    };
}
