package com.datngoc.wms.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.datngoc.wms.dto.request.OrderItemRequestDTO;
import com.datngoc.wms.dto.request.OrderRequestDTO;
import com.datngoc.wms.dto.response.OrderResponseDTO;
import com.datngoc.wms.entity.Order;
import com.datngoc.wms.entity.OrderItem;
import com.datngoc.wms.entity.OrderStatus;
import com.datngoc.wms.entity.Product;
import com.datngoc.wms.exception.BusinessException;
import com.datngoc.wms.exception.ErrorCode;
import com.datngoc.wms.mapper.OrderMapper;
import com.datngoc.wms.repository.OrderRepository;
import com.datngoc.wms.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final InventoryService inventoryService;
    private final OrderMapper orderMapper;

    // Sinh mã đơn hàng tự động định dạng: ORD-yyyyMMdd-XXXXX
    public String generateOrderCode() {
        String datePart = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String randomPart = UUID.randomUUID().toString().substring(0, 5).toUpperCase();
        return "ORD-" + datePart + "-" + randomPart;
    }

    // 1. Tạo đơn hàng (lưu lịch sử bán & trừ tồn kho)
    @Transactional
    public Order createOrder(OrderRequestDTO request) {
        String orderCode = generateOrderCode();
        while (orderRepository.findByOrderCode(orderCode).isPresent()) {
            orderCode = generateOrderCode();
        }

        Order order = new Order();
        order.setOrderCode(orderCode);
        order.setCustomerName(request.getCustomerName());
        order.setCustomerPhone(request.getCustomerPhone());
        order.setNote(request.getNote());
        order.setStatus(OrderStatus.CONFIRMED);

        BigDecimal totalAmount = BigDecimal.ZERO;
        List<OrderItem> items = new ArrayList<>();

        for (OrderItemRequestDTO itemDto : request.getItems()) {
            Product product = productRepository.findById(itemDto.getProductId())
                    .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND));

            // Trừ tồn kho và ghi log StockMovement (OUTBOUND)
            inventoryService.removeStock(product.getId(), itemDto.getQuantity(),
                    "Xuất kho đơn hàng " + orderCode);

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(itemDto.getQuantity());
            orderItem.setUnitPrice(itemDto.getUnitPrice());

            BigDecimal subtotal = itemDto.getUnitPrice().multiply(BigDecimal.valueOf(itemDto.getQuantity()));
            orderItem.setSubtotal(subtotal);

            totalAmount = totalAmount.add(subtotal);
            items.add(orderItem);
        }

        order.setTotalAmount(totalAmount);
        order.setItems(items);

        return orderRepository.save(order);
    }

    // 2. Lấy danh sách đơn hàng (phân trang)
    @Transactional(readOnly = true)
    public Page<OrderResponseDTO> getAllOrders(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return orderRepository.findAll(pageable).map(orderMapper::toDto);
    }

    // 3. Lấy chi tiết đơn hàng theo ID
    @Transactional(readOnly = true)
    public OrderResponseDTO getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.ORDER_NOT_FOUND));
        return orderMapper.toDto(order);
    }

    // 4. Hủy đơn hàng và hoàn lại tồn kho
    @Transactional
    public Order cancelOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.ORDER_NOT_FOUND));

        if (order.getStatus() == OrderStatus.CANCELLED) {
            throw new BusinessException(ErrorCode.ORDER_CANNOT_CANCEL);
        }

        // Hoàn lại tồn kho cho từng sản phẩm
        for (OrderItem item : order.getItems()) {
            inventoryService.addStock(item.getProduct().getId(), item.getQuantity(),
                    "Hoàn kho do hủy đơn hàng " + order.getOrderCode());
        }

        order.setStatus(OrderStatus.CANCELLED);
        return orderRepository.save(order);
    }
}
