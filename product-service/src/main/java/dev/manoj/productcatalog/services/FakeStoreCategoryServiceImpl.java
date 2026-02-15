package dev.manoj.productcatalog.services;

import dev.manoj.productcatalog.clients.fakeStoreApi.FakeStoreClient;
import dev.manoj.productcatalog.clients.fakeStoreApi.FakeStoreProductDto;
import dev.manoj.productcatalog.models.Category;
import dev.manoj.productcatalog.models.Product;
import lombok.AllArgsConstructor;
//import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor

public class FakeStoreCategoryServiceImpl implements CategoryService {
    private FakeStoreClient fakeStoreClient;
//    private RestTemplate restTemplate;

    @Override
    public List<Category>  getAllCategories() {
        List<String> allCategories = fakeStoreClient.getAllCategories();
        List<Category> categoryList = new ArrayList<>();
        for(String categoryName : allCategories){
            categoryList.add(
                    Category.builder()
                            .name(categoryName)
                            .build()
            );
        }
        return categoryList;

    }

    @Override
    public Category saveCategory(Category category) {
        return null;
    }



//    @Override
//    public List<Product> getProductsInCategory(String categoryType) {
//        ResponseEntity<FakeStoreProductDto[]> fakeStoreProductDtoResponseEntity = fakeStoreClient.getProductsInCategory(categoryType);
//        return getProductsFromProductList(fakeStoreProductDtoResponseEntity);
//    }


    private List<Product> getProductsFromProductList(ResponseEntity<FakeStoreProductDto[]> productFromCategory) {
        List<Product> productList=new ArrayList<>();
        for(FakeStoreProductDto fakeStoreProductDto : productFromCategory.getBody() ){
            Product product = Product.builder()
                    .id(fakeStoreProductDto.getId())
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
}
