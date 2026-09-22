package com.datngoc.wms;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.datngoc.wms.repository.ProductRepository;
import com.datngoc.wms.service.ProductService;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void generateSku_WhenNoExistingProducts_ShouldReturnFirstSequence() {
        when(productRepository.findSkusByPrefix("SP-BANH-QUY-BO-"))
                .thenReturn(List.of());

        String sku = productService.generateSku("Bánh quy bơ");
        assertEquals("SP-BANH-QUY-BO-00001", sku);
    }

    @Test
    void generateSku_WhenExistingProductsExist_ShouldIncrementSequence() {
        when(productRepository.findSkusByPrefix("SP-BANH-QUY-BO-"))
                .thenReturn(List.of("SP-BANH-QUY-BO-00001", "SP-BANH-QUY-BO-00002"));

        String sku = productService.generateSku("Bánh quy bơ");
        assertEquals("SP-BANH-QUY-BO-00003", sku);
    }

    @Test
    void generateSku_WithVietnameseAndSpecialCharacters() {
        when(productRepository.findSkusByPrefix("SP-SUA-TUOI-TIET-TRUNG-VINAMILK-100-"))
                .thenReturn(List.of());

        String sku = productService.generateSku("Sữa tươi tiệt trùng Vinamilk 100%");
        assertEquals("SP-SUA-TUOI-TIET-TRUNG-VINAMILK-100-00001", sku);
    }
}
