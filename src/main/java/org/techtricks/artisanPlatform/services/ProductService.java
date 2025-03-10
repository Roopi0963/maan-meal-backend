package org.techtricks.artisanPlatform.services;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.techtricks.artisanPlatform.exceptions.ProductAlreadyExistsException;
import org.techtricks.artisanPlatform.exceptions.ProductNotFoundException;
import org.techtricks.artisanPlatform.models.Product;

import java.io.IOException;
import java.util.List;

@Service
public interface ProductService {

    public Product addProduct(Product product, MultipartFile imageFile) throws  IOException;

    List<Product> getAllProducts();

    public Product getProductById(Long id) throws ProductNotFoundException;

    public Product updateProduct(Long id , Product product , MultipartFile imageFile) throws IOException, ProductNotFoundException;

    public void deleteProduct(Long id) throws ProductNotFoundException;

    public List<Product> searchProduct(String keyword) throws ProductNotFoundException;
}
