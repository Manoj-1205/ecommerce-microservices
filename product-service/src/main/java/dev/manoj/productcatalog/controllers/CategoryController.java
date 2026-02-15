package dev.manoj.productcatalog.controllers;


import dev.manoj.productcatalog.dtos.CategoryDto;
import dev.manoj.productcatalog.dtos.ProductDto;
import dev.manoj.productcatalog.models.Category;
import dev.manoj.productcatalog.models.Product;
import dev.manoj.productcatalog.services.CategoryService;
import dev.manoj.productcatalog.services.ProductService;
import dev.manoj.productcatalog.services.SelfCategoryService;
import dev.manoj.productcatalog.services.SelfProductService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/products/category")
@AllArgsConstructor
public class CategoryController {

//    @Autowired
    private CategoryService categoryService;
//    @Autowired
    private ProductService productService;




    @GetMapping()
    public List<CategoryDto>  getAllCategories() {
        List<Category> categoryList = categoryService.getAllCategories();
        List<CategoryDto> categoryDtoList = new ArrayList<>();
        for(Category category : categoryList){
            categoryDtoList.add(
                    CategoryDto.builder()
                            .name(category.getName())
                            .build()
            );
        }
        return categoryDtoList;
    }

    @GetMapping("/{categoryType}")
    public List<ProductDto> getProductsInCategory(@PathVariable String categoryType) {
        List<ProductDto> productDtoList = new ArrayList<>();
        for(Product product : productService.getProductsInCategory(categoryType)){
           ProductDto productDto = ProductDto.builder()
                   .id(product.getId())
                   .title(product.getTitle())
                   .description(product.getDescription())
                   .price(product.getPrice())
                   .image(product.getImageUrl())
                   .build();

            productDtoList.add(productDto);

        }
        return productDtoList;
    }
}
