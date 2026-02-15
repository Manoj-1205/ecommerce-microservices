package dev.manoj.productcatalog.services;

import dev.manoj.productcatalog.exceptions.NotFoundException;
import dev.manoj.productcatalog.models.Category;
import dev.manoj.productcatalog.models.Product;
import dev.manoj.productcatalog.repositories.CategoryRepository;
import dev.manoj.productcatalog.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
@AllArgsConstructor
public class SelfCategoryService implements CategoryService{

    private CategoryRepository categoryRepository;
//    private SelfProductService productService;
    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }


//    @Override
//    public List<Product> getProductsInCategory(String categoryType) {
//        return productService.findAllByCategory_Name(categoryType);
//    }

    public Category saveCategory(Category category){
        return  categoryRepository.save(category);
    }
    public Category     getCategoryById(Long categoryId){
        Category category = categoryRepository.findCategoryById(categoryId);
        if(category==null) throw new NotFoundException("Category not found.");
        return category;
    }
}
