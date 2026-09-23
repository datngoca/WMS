package com.datngoc.wms.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.datngoc.wms.entity.OrderStatus;

import lombok.Data;

@Data
public class OrderResponseDTO {
    private Long id;
    private String orderCode;
    private String customerName;
    private String customerPhone;
    private String note;
    private OrderStatus status;
    private BigDecimal totalAmount;
    private List<OrderItemResponseDTO> items;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
