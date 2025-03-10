package org.techtricks.artisanPlatform.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.techtricks.artisanPlatform.exceptions.ProductNotFoundException;
import org.techtricks.artisanPlatform.models.Product;
import org.techtricks.artisanPlatform.services.ProductService;

import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("api/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> productList = productService.getAllProducts();
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Long id) throws ProductNotFoundException {
        Product product = productService.getProductById(id);
        if(product == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PostMapping("/addProduct")
    public ResponseEntity<Product> addProduct(@RequestPart("product") String productJson,
                                              @RequestPart("file") MultipartFile imageFile) throws IOException {
        Product product = new ObjectMapper().readValue(productJson, Product.class);

        return ResponseEntity.ok(productService.addProduct(product, imageFile));
    }

    @GetMapping("/{id}/image")
    public ResponseEntity<byte[]> getProductImage(@PathVariable Long id) throws ProductNotFoundException {
        Product product = productService.getProductById(id);
        byte[] imageFile  = product.getImageData();

        return ResponseEntity.ok().contentType(MediaType.valueOf(product.getImageType())).body(imageFile);
    }



    @PutMapping("/updateProduct/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable Long id ,@RequestPart Product product, @RequestPart MultipartFile file) throws ProductNotFoundException, IOException {
        Product updateProduct = null;

        try{
         updateProduct = productService.updateProduct(id,product,file);
        }catch (IOException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if(updateProduct != null) {
            return new ResponseEntity<>("Product added successfully",HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Product not found or failed to update",HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/deleteProduct/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) throws ProductNotFoundException {
        Product product = productService.getProductById(id);
        if(product != null) {
            productService.deleteProduct(id);
            return new ResponseEntity<>("Product deleted successfully",HttpStatus.OK);
        }
        return new ResponseEntity<>("Product not found or failed to delete",HttpStatus.NOT_FOUND);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam String keyword) throws ProductNotFoundException {
        List<Product> product = productService.searchProduct(keyword);
        System.out.println("searching with keyword: " + keyword);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }
}


