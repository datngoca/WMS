package com.datngoc.wms.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.datngoc.wms.dto.request.ProductRequestDTO;
import com.datngoc.wms.entity.Product;
import com.datngoc.wms.exception.BusinessException;
import com.datngoc.wms.exception.ErrorCode;
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
                .orElseThrow(() -> new BusinessException(ErrorCode.SKU_NOT_FOUND, sku));
    }

    // 3. Find product by ID
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND));
    }

    // 4. Create product (check SKU exists yet)
    public Product createProduct(ProductRequestDTO requestDTO) {
        Product product = productMapper.toEntity(requestDTO);
        boolean isPresent = productRepository.findBySku(product.getSku()).isPresent();
        if (isPresent) {
            throw new BusinessException(ErrorCode.SKU_ALREADY_EXISTS, requestDTO.getSku());
        }

        return productRepository.save(product);
    }

    // 5. Update product
    public Product updateProduct(Long id, ProductRequestDTO productDetails) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_EXISTS));
        productMapper.updateEntityFromDTO(productDetails, product);

        return productRepository.save(product);
    }

    // 6. Delete product
    public void deleteProduct(Long id) {
        productRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_EXISTS));
        productRepository.deleteById(id);
    }
}
