package com.datngoc.wms.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.datngoc.wms.entity.Inventory;
import com.datngoc.wms.entity.MovementType;
import com.datngoc.wms.entity.Product;
import com.datngoc.wms.entity.StockMovement;
import com.datngoc.wms.entity.Warehouse;
import com.datngoc.wms.repository.InventoryRepository;
import com.datngoc.wms.repository.ProductRepository;
import com.datngoc.wms.repository.StockMovementRepository;
import com.datngoc.wms.repository.WarehouseRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final StockMovementRepository stockMovementRepository;
    private final ProductRepository productRepository;
    private final WarehouseRepository warehouseRepository;

    @Transactional
    public void addStock(Long productId, Long warehouseId, Integer quantity, String reason) {
        // B1: Kiểm tra Product và Warehouse có tồn tại không
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Sản phẩm không tồn tại"));

        Warehouse warehouse = warehouseRepository.findById(warehouseId)
                .orElseThrow(() -> new RuntimeException("Nhà kho không tồn tại"));

        // B2: Kiểm tra xem sản phẩm này đã có bản ghi trong kho này chưa
        Inventory inventory = inventoryRepository.findByProductIdAndWarehouseId(productId, warehouseId)
                .orElseGet(() -> {
                    Inventory newInv = new Inventory();
                    newInv.setProduct(product);
                    newInv.setWarehouse(warehouse);
                    newInv.setQuantity(0);
                    return newInv;
                });

        // B3: Cộng dồn số lượng
        inventory.setQuantity(quantity + inventory.getQuantity());

        // B4: Lưu Inventory
        inventoryRepository.save(inventory);

        // B5 : Tạo StockMovement
        StockMovement movement = new StockMovement();
        movement.setType(MovementType.INBOUND);
        movement.setQuantity(quantity);
        movement.setProduct(product);
        stockMovementRepository.save(movement);
    }
}
