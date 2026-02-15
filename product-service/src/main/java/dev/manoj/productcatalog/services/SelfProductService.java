package dev.manoj.productcatalog.services;

import dev.manoj.productcatalog.dtos.ProductNamePriceDto;
import dev.manoj.productcatalog.dtos.UserDTO;
import dev.manoj.productcatalog.dtos.ProductDto;
import dev.manoj.productcatalog.exceptions.NotFoundException;
import dev.manoj.productcatalog.models.Product;
import dev.manoj.productcatalog.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Primary
@Qualifier("SelfProductService")
public class SelfProductService implements ProductService{

    private ProductRepository productRepository;
    private SelfCategoryService categoryService;
    private RestTemplate restTemplate;

    @Override
    public Page<Product> getProducts(String query, int offset, int noOfResults) {
        //Sorting happens on Database and then we fetch the data
        return productRepository.findProductByTitleContaining(
                query,
                PageRequest.of(offset/noOfResults,
                        noOfResults,
                        Sort.by("title")

                )
        );
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public ResponseEntity<Product> getSingleProduct(Long productId) throws NotFoundException {
        Optional<Product> optionalProduct =  productRepository.findById(productId);

        System.out.println("Optional product -> "+optionalProduct);
        if(optionalProduct.isPresent()) {

            return new ResponseEntity<>(optionalProduct.get(), HttpStatus.OK);
        }
        throw new NotFoundException("Product not found!!");
    }

    @Override
    public Product addNewProduct(ProductDto productDto) {
        Product product = Product.builder()
                .title(productDto.getTitle())
                .price(productDto.getPrice())
                .description(productDto.getDescription())
                .imageUrl(productDto.getImage())
                .isDeleted(productDto.getIsDeleted())
                .build();
        Product savedProduct = productRepository.save(product);
//        Category category = categoryService.getCategoryById(productDto.getCategoryId());


        return productRepository.save(savedProduct);
    }

    @Override
    public Product replaceProduct(Long productId, Product product) {
        Product requiredProduct = productRepository.findProductById(productId);
        if(requiredProduct==null)
            throw new NotFoundException("Product not found");

        requiredProduct.setTitle(product.getTitle());
        requiredProduct.setDescription(product.getDescription());
        requiredProduct.setImageUrl(product.getImageUrl());
        requiredProduct.setPrice(product.getPrice());
        requiredProduct.setIsDeleted(product.getIsDeleted());
        requiredProduct.setCategory(product.getCategory());

        return productRepository.save(requiredProduct);
    }

    @Override
    public Product updateProduct(Long productId, Product product) {
        Product requiredProduct = productRepository.findProductById(productId);
        if(requiredProduct==null)
            throw new NotFoundException("Product not found");
        if(product.getTitle()!=null)
            requiredProduct.setTitle(product.getTitle());
        if(product.getDescription()!=null)
            requiredProduct.setDescription(product.getDescription());
        if(product.getImageUrl()!=null)
            requiredProduct.setImageUrl(product.getImageUrl());
        if(product.getPrice()!=null)
            requiredProduct.setPrice(product.getPrice());
        requiredProduct.setIsDeleted(product.getIsDeleted());
        if(product.getCategory()!=null)
            requiredProduct.setCategory(product.getCategory());

        return productRepository.save(requiredProduct);
    }

    @Override
    public Product deleteProduct(Long productId) throws NotFoundException {
        return productRepository.deleteProductById(productId);
    }

    @Override
    public List<Product> getProductsInCategory(String categoryType) {
        return productRepository.findByCategory_Name(categoryType);
    }

    public UserDTO getUserDetails(){

        ResponseEntity<UserDTO> userDetails = restTemplate.getForEntity(
                "http://userservice/users/1",
                UserDTO.class
        );
        return userDetails.getBody();
    }

    public List<ProductNamePriceDto> requestFromOrderService(List<Long> productIds){

        List<ProductNamePriceDto> productNamePriceDtoList=new ArrayList<>();
        for(Long id : productIds){
            Product product = productRepository.findProductById(id);
            if(product==null){
                throw new NotFoundException("Product Not Found");
            }
            productNamePriceDtoList.add(
                    new ProductNamePriceDto(
                            product.getTitle(),
                            product.getPrice()
                    )
            );
        }
        return productNamePriceDtoList;

    }

}
