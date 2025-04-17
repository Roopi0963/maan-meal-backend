package org.techtricks.maanmeal.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.techtricks.maanmeal.exceptions.ProductNotFoundException;
import org.techtricks.maanmeal.models.Product;
import org.techtricks.maanmeal.repositories.ProductRepository;

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
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            try {
                productRepository.delete(product); // Try delete
            } catch (DataIntegrityViolationException ex) {
                // Foreign key constraint violated
                product.setProductAvailable(false);
                product.setStockQuantity(0);
                productRepository.save(product); // Mark as out of stock
                throw new ProductNotFoundException("Product is linked to existing orders. Marked as Out of Stock instead.");
            }
        } else {
            throw new ProductNotFoundException("Product not found with id: " + id);
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
