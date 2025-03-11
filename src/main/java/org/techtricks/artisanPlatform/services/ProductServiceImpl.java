package org.techtricks.artisanPlatform.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.techtricks.artisanPlatform.exceptions.ProductAlreadyExistsException;
import org.techtricks.artisanPlatform.exceptions.ProductNotFoundException;
import org.techtricks.artisanPlatform.models.Product;
import org.techtricks.artisanPlatform.repositories.ProductRepository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> getAllProducts() {

        try {
            return productRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Product getProductById(Long id) throws ProductNotFoundException {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if(optionalProduct.isPresent()) {
            return optionalProduct.get();
        }
        throw new ProductNotFoundException("(Product not found : "+id+"please try with another");
    }

    @Override
    public Product updateProduct(Long id, Product product, MultipartFile imageFile) throws IOException {
        product.setImageName(imageFile.getOriginalFilename());
        product.setImageType(imageFile.getContentType());
        product.setImageData(imageFile.getBytes());

        return productRepository.save(product);
    }


    @Override
    public Product addProduct(Product product, MultipartFile imageFile) throws IOException {

            product.setImageName(imageFile.getOriginalFilename());
            product.setImageType(imageFile.getContentType());
            product.setImageData(imageFile.getBytes());
            return productRepository.save(product);

    }

    @Override
    public void deleteProduct(Long id) throws ProductNotFoundException {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if(optionalProduct.isPresent()) {
            productRepository.delete(optionalProduct.get());
        }else {
            throw new ProductNotFoundException("Product not found : "+id+"please try with another");
        }
    }

    @Override
    public List<Product> searchProduct(String keyword) throws ProductNotFoundException {
        List<Product> optionalProduct = productRepository.searchProducts(keyword);
        if(optionalProduct.isEmpty()) {
            throw new ProductNotFoundException("Product not Available");
        }
        return productRepository.searchProducts(keyword);
    }
}
