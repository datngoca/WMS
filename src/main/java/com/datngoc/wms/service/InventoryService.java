package com.datngoc.wms.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.datngoc.wms.entity.Inventory;
import com.datngoc.wms.entity.MovementType;
import com.datngoc.wms.entity.Product;
import com.datngoc.wms.entity.StockMovement;
import com.datngoc.wms.exception.BusinessException;
import com.datngoc.wms.exception.ErrorCode;
import com.datngoc.wms.repository.InventoryRepository;
import com.datngoc.wms.repository.ProductRepository;
import com.datngoc.wms.repository.StockMovementRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InventoryService {

        private final InventoryRepository inventoryRepository;
        private final StockMovementRepository stockMovementRepository;
        private final ProductRepository productRepository;

        // Logic nhập kho
        @Transactional
        public void addStock(Long productId, Integer quantity, String reason) {
                // B1: Kiểm tra Product và Warehouse có tồn tại không
                Product product = productRepository.findById(productId)
                                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND));

                // B2: Kiểm tra xem sản phẩm này đã có bản ghi trong kho này chưa
                Inventory inventory = inventoryRepository.findByProductId(productId)
                                .orElseGet(() -> {
                                        Inventory newInv = new Inventory();
                                        newInv.setProduct(product);
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
                movement.setReason(reason);
                stockMovementRepository.save(movement);
        }

        // Logic xuất kho
        public void removeStock(Long productId, Integer quantity, String reason) {
                // B1: Kiểm tra Product có tồn tại không
                Product product = productRepository.findById(productId)
                                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND));

                // B2: Tìm Inventory tương ứng
                Inventory inventory = inventoryRepository.findByProductId(productId)
                                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_IN_WAREHOUSE,
                                                product.getName()));

                // B3: Kiểm tra số lượng tồn
                if (inventory.getQuantity() < quantity) {
                        throw new BusinessException(ErrorCode.STOCK_SHORTAGE, product.getName(), quantity,
                                        inventory.getQuantity());
                }

                // B4: Trừ số lượng
                inventory.setQuantity(inventory.getQuantity() - quantity);
                inventoryRepository.save(inventory);

                // B5: Tạo bản ghi StockMovement
                StockMovement stockMovement = new StockMovement();
                stockMovement.setType(MovementType.OUTBOUND);
                stockMovement.setProduct(product);
                stockMovement.setQuantity(quantity);
                stockMovement.setReason(reason);
                stockMovementRepository.save(stockMovement);
        }
}
