package com.datngoc.wms;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.datngoc.wms.entity.Inventory;
import com.datngoc.wms.entity.Product;
import com.datngoc.wms.entity.Warehouse;
import com.datngoc.wms.exception.BusinessException;
import com.datngoc.wms.repository.InventoryRepository;
import com.datngoc.wms.repository.ProductRepository;
import com.datngoc.wms.repository.StockMovementRepository;
import com.datngoc.wms.repository.WarehouseRepository;
import com.datngoc.wms.service.InventoryService;

@ExtendWith(MockitoExtension.class)
public class InventoryServiceTest {

    @Mock
    private InventoryRepository inventoryRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private WarehouseRepository warehouseRepository;

    @Mock
    private StockMovementRepository stockMovementRepository;

    @InjectMocks
    private InventoryService inventoryService;

    @Test
    void addStock_Success_WhenInventoryExists() {
        // 1. GIVEN (giả sử dữ liệu đầu vào)
        Long productId = 1L;
        Long warehouseId = 1L;
        Integer quantityToAdd = 10;

        Product mockProduct = new Product();
        Warehouse mockWarehouse = new Warehouse();
        Inventory mockInventory = new Inventory();

        mockInventory.setQuantity(20);

        // Giả lập Repository trả về dữ liệu
        when(productRepository.findById(productId)).thenReturn(Optional.of(mockProduct));
        when(warehouseRepository.findById(warehouseId)).thenReturn(Optional.of(mockWarehouse));
        when(inventoryRepository.findByProductIdAndWarehouseId(productId, warehouseId))
                .thenReturn(Optional.of(mockInventory));

        // 2. WHEN (Thực thi hàm cần test)
        inventoryService.addStock(productId, warehouseId, quantityToAdd, "Test nhập hàng");

        // 3. THEN (Kiểm tra kết quả)
        // Kiểm tra số lượng có thành 30 (20 + 10) không
        assertEquals(30, mockInventory.getQuantity());

        // Kiểm tra xem hàm save() có được gọi không
        verify(inventoryRepository, times(1)).save(any(Inventory.class));
    }

    @Test
    void removeStock_ShouldThrowException_WhenQuanityInsuffcient() {

        // 1. Chuẩn bị dữ liệu giả (Product, Warehouse, Inventory có quantity = 5)
        Long productId = 1L;
        Long warehouseId = 1L;
        Integer quantityToRemove = 5;

        Product mockProduct = new Product();
        Warehouse mockWarehouse = new Warehouse();
        Inventory mockInventory = new Inventory();
        mockInventory.setQuantity(0);

        // 2. Mock các Repository trả về dữ liệu giả này
        when(productRepository.findById(productId)).thenReturn(Optional.of(mockProduct));
        when(warehouseRepository.findById(warehouseId)).thenReturn(Optional.of(mockWarehouse));
        when(inventoryRepository.findByProductIdAndWarehouseId(productId, warehouseId))
                .thenReturn(Optional.of(mockInventory));

        // 3. Sử dụng assertThrows để kiểm tra lỗi
        assertThrows(BusinessException.class, () -> {
            inventoryService.removeStock(productId, warehouseId, quantityToRemove, "Xuất quá số lượng");
        });
    }
}
