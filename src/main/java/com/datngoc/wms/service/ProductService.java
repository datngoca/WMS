package com.datngoc.wms.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.datngoc.wms.entity.Product;
import com.datngoc.wms.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service // Nhãn dán để Spring biết đây là tầng Service
@RequiredArgsConstructor // Lombok sẽ tự tạo Contructor để inject Repository vào
public class ProductService {

    private final ProductRepository productRepository;

    // 1. Get all products
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // 2. Find product by SKU
    public Product getProductBySku(String sku) {
        return productRepository.findBySku(sku)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm với SKU: " + sku));
    }

    // 3. Create product (check SKU exists yet)
    public Product createProduct(Product product) {
        Optional<Product> isPresent = productRepository.findBySku(product.getSku());
        if (isPresent != null) {
            throw new RuntimeException("Sản phẩm đã tồn tại");
        }
        return productRepository.save(product);
    }

    // 4. Update product
    public Product updateProduct(Long id, Product productDetails) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sản phẩm không tồn tại"));
        if (productDetails.getName() != null)
            product.setName(productDetails.getName());
        if (productDetails.getCategory() != null)
            product.setCategory(productDetails.getCategory());
        if (productDetails.getBasePrice() != null)
            product.setBasePrice(productDetails.getBasePrice());

        return productRepository.save(product);
    }

    // 5. Delete product
    public void deleteProduct(Long id) {
        productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sản phẩm không tồn tại"));
        productRepository.deleteById(id);
    }
}
