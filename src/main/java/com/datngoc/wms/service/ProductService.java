package com.datngoc.wms.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.datngoc.wms.dto.request.ProductRequestDTO;
import com.datngoc.wms.entity.Product;
import com.datngoc.wms.exception.ResourceNotFoundException;
import com.datngoc.wms.mapper.ProductMapper;
import com.datngoc.wms.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service // Nhãn dán để Spring biết đây là tầng Service
@RequiredArgsConstructor // Lombok sẽ tự tạo Contructor để inject Repository vào
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    // 1. Get all products
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // 2. Find product by SKU
    public Product getProductBySku(String sku) {
        return productRepository.findBySku(sku)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy sản phẩm với SKU: " + sku));
    }

    // 3. Find product by ID
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy sản phẩm với ID: " + id));
    }

    // 4. Create product (check SKU exists yet)
    public Product createProduct(ProductRequestDTO requestDTO) {
        Product product = productMapper.toEntity(requestDTO);
        boolean isPresent = productRepository.findBySku(product.getSku()).isPresent();
        if (isPresent) {
            throw new RuntimeException("Sản phẩm đã tồn tại");
        }

        return productRepository.save(product);
    }

    // 5. Update product
    public Product updateProduct(Long id, ProductRequestDTO productDetails) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sản phẩm không tồn tại"));
        productMapper.updateEntityFromDTO(productDetails, product);

        return productRepository.save(product);
    }

    // 6. Delete product
    public void deleteProduct(Long id) {
        productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sản phẩm không tồn tại"));
        productRepository.deleteById(id);
    }
}
