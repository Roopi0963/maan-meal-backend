package org.techtricks.maanmeal.services;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.techtricks.maanmeal.exceptions.ProductNotFoundException;
import org.techtricks.maanmeal.models.Product;

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
