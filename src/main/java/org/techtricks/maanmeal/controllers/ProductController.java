package org.techtricks.maanmeal.controllers;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.techtricks.maanmeal.exceptions.ProductNotFoundException;
import org.techtricks.maanmeal.models.Product;
import org.techtricks.maanmeal.repositories.ProductRepository;
import org.techtricks.maanmeal.services.ProductService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {

    private final ProductService productService;
    private final ProductRepository productRepository;

    public ProductController(ProductService productService, ProductRepository productRepository) {
        this.productService = productService;
        this.productRepository = productRepository;
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> productList = productService.getAllProducts();
        return ResponseEntity.ok(productList);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Long id) throws ProductNotFoundException {
        Product product = productService.getProductById(id);
        if(product == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PostMapping("/product")
    public ResponseEntity<?> addProduct(@RequestPart Product product, @RequestPart MultipartFile imageFile) {
        try {
            Product product1 = productService.addProduct(product, imageFile);
            return new ResponseEntity<>(product1, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/product/{productId}/image")
    public ResponseEntity<byte[]> getProductImage(@PathVariable Long productId ) throws ProductNotFoundException {
        Product product = productService.getProductById(productId);
        byte[] imageFile  = product.getImageData();

        return ResponseEntity.ok().contentType(MediaType.valueOf(product.getImageType())).body(imageFile);
    }


    @PutMapping("/product/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable Long id, @RequestPart Product product, @RequestPart MultipartFile imageFile) {

        Product product1 = null;
        try {
            product1 = productService.updateProduct(id, product, imageFile);
        } catch (IOException e) {
            return new ResponseEntity<>("Failed to update", HttpStatus.BAD_REQUEST);
        } catch (ProductNotFoundException e) {
            throw new RuntimeException(e);
        }
        if (product1 != null) {
            return new ResponseEntity<>("updated", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to update", HttpStatus.BAD_REQUEST);
        }


    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        try {
            productService.deleteProduct(id);
            return ResponseEntity.ok("Product deleted successfully");
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }


    @GetMapping("/products/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam String keyword) throws ProductNotFoundException {
        List<Product> product = productService.searchProduct(keyword);
        System.out.println("searching with keyword: " + keyword);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }
}


