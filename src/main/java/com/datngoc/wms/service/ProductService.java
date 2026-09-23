package com.datngoc.wms.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.datngoc.wms.dto.request.ProductRequestDTO;
import com.datngoc.wms.dto.request.ProductUnitRequestDTO;
import com.datngoc.wms.dto.response.ProductResponseDTO;
import com.datngoc.wms.entity.Category;
import com.datngoc.wms.entity.Product;
import com.datngoc.wms.entity.ProductUnit;
import com.datngoc.wms.entity.Unit;
import com.datngoc.wms.exception.BusinessException;
import com.datngoc.wms.exception.ErrorCode;
import com.datngoc.wms.mapper.ProductMapper;
import com.datngoc.wms.mapper.ProductUnitMapper;
import com.datngoc.wms.repository.CategoryRepository;
import com.datngoc.wms.repository.ProductRepository;
import com.datngoc.wms.repository.UnitRepository;
import com.datngoc.wms.utils.StringUtils;

import lombok.RequiredArgsConstructor;

import java.util.Set;
import java.util.stream.Collectors;

@Service // Nhãn dán để Spring biết đây là tầng Service
@RequiredArgsConstructor // Lombok sẽ tự tạo Contructor để inject Repository vào
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;
    private final UnitRepository unitRepository;
    private final ProductUnitMapper productUnitMapper;

    // 1. Get all products
    @Transactional(readOnly = true)
    public Page<ProductResponseDTO> getAllProducts(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return productRepository.findAll(pageable).map(productMapper::toDto);
    }

    // 2. Find product by SKU
    @Transactional(readOnly = true)
    public ProductResponseDTO getProductBySku(String sku) {
        Product product = productRepository.findBySku(sku)
                .orElseThrow(() -> new BusinessException(ErrorCode.SKU_NOT_FOUND, sku));
        return productMapper.toDto(product);
    }

    // 3. Find product by ID
    @Transactional(readOnly = true)
    public ProductResponseDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND));
        return productMapper.toDto(product);
    }

    // Tự động sinh mã SKU theo quy tắc SP-(slug)-00001
    public String generateSku(String productName) {
        String slug = StringUtils.toUpperSlug(productName);
        String prefix = "SP-" + slug + "-";

        List<String> existingSkus = productRepository.findSkusByPrefix(prefix);
        int maxSeq = 0;
        for (String sku : existingSkus) {
            if (sku != null && sku.startsWith(prefix)) {
                String suffix = sku.substring(prefix.length());
                try {
                    int seq = Integer.parseInt(suffix);
                    if (seq > maxSeq) {
                        maxSeq = seq;
                    }
                } catch (NumberFormatException ignored) {
                }
            }
        }
        return prefix + String.format("%05d", maxSeq + 1);
    }

    // 4. Create product (check SKU exists yet or auto-generate)
    @Transactional
    public Product createProduct(ProductRequestDTO requestDTO) {
        Product product = productMapper.toEntity(requestDTO);

        // Tự động sinh SKU nếu để trống hoặc null
        if (product.getSku() == null || product.getSku().trim().isEmpty()) {
            product.setSku(generateSku(product.getName()));
        } else {
            product.setSku(product.getSku().trim());
            boolean isPresent = productRepository.findBySku(product.getSku()).isPresent();
            if (isPresent) {
                throw new BusinessException(ErrorCode.SKU_ALREADY_EXISTS, product.getSku());
            }
        }

        if (requestDTO.getCategories() != null && !requestDTO.getCategories().isEmpty()) {
            Set<Category> managedCategories = requestDTO.getCategories().stream()
                    .map(categoryId -> categoryRepository.findById(categoryId)
                            .orElseThrow(() -> new BusinessException(ErrorCode.CATEGORY_NOT_FOUND)))
                    .collect(Collectors.toSet());
            product.setCategories(managedCategories);
        }

        if (requestDTO.getProductUnits() != null && !requestDTO.getProductUnits().isEmpty()) {
            Set<Long> seenUnitIds = new HashSet<>();
            for (ProductUnitRequestDTO unitDto : requestDTO.getProductUnits()) {
                if (!seenUnitIds.add(unitDto.getUnitId())) {
                    throw new BusinessException(ErrorCode.DUPLICATE_PRODUCT_UNIT);
                }
            }

            List<ProductUnit> unitsToSave = new ArrayList<>();
            int unitIndex = 1;
            for (ProductUnitRequestDTO unitDto : requestDTO.getProductUnits()) {
                Unit unit = unitRepository.findById(unitDto.getUnitId())
                        .orElseThrow(() -> new BusinessException(ErrorCode.UNIT_NOT_FOUND));
                ProductUnit productUnit = productUnitMapper.toEntity(unitDto);
                productUnit.setUnit(unit);
                productUnit.setProduct(product);

                // Tự sinh SKU cho đơn vị quy đổi nếu để trống: {productSku}-{unitCode}
                if (productUnit.getSku() == null || productUnit.getSku().trim().isEmpty()) {
                    String unitSuffix = (unit.getCode() != null && !unit.getCode().trim().isEmpty())
                            ? unit.getCode().trim().toUpperCase()
                            : String.format("U%02d", unitIndex);
                    productUnit.setSku(product.getSku() + "-" + unitSuffix);
                } else {
                    productUnit.setSku(productUnit.getSku().trim());
                }

                unitsToSave.add(productUnit);
                unitIndex++;
            }
            product.setProductUnits(unitsToSave);
        }

        return productRepository.save(product);
    }

    // 5. Update product
    @Transactional
    public Product updateProduct(Long id, ProductRequestDTO productDetails) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_EXISTS));
        productMapper.updateEntityFromDTO(productDetails, product);

        if (productDetails.getCategories() != null && !productDetails.getCategories().isEmpty()) {
            Set<Category> managedCategories = productDetails.getCategories().stream()
                    .map(categoryId -> categoryRepository.findById(categoryId)
                            .orElseThrow(() -> new BusinessException(ErrorCode.CATEGORY_NOT_FOUND)))
                    .collect(Collectors.toSet());
            product.setCategories(managedCategories);
        } else if (productDetails.getCategories() != null && productDetails.getCategories().isEmpty()) {
            product.getCategories().clear();
        }

        if (productDetails.getProductUnits() != null) {
            Set<Long> seenUnitIds = new HashSet<>();
            for (ProductUnitRequestDTO unitDto : productDetails.getProductUnits()) {
                if (!seenUnitIds.add(unitDto.getUnitId())) {
                    throw new BusinessException(ErrorCode.DUPLICATE_PRODUCT_UNIT);
                }
            }

            List<ProductUnit> currentUnits = product.getProductUnits();
            if (currentUnits == null) {
                currentUnits = new java.util.ArrayList<>();
                product.setProductUnits(currentUnits);
            }

            // Tạo map từ request để tra cứu nhanh bằng unitId
            java.util.Map<Long, ProductUnitRequestDTO> requestedUnitMap = productDetails.getProductUnits().stream()
                    .collect(java.util.stream.Collectors.toMap(ProductUnitRequestDTO::getUnitId, dto -> dto));

            // Duyệt danh sách hiện tại: Xóa cái không có trong request, cập nhật cái trùng
            // khớp
            java.util.Iterator<ProductUnit> iterator = currentUnits.iterator();
            while (iterator.hasNext()) {
                ProductUnit existingUnit = iterator.next();
                if (existingUnit.getUnit() == null) {
                    iterator.remove();
                    continue;
                }
                ProductUnitRequestDTO matchingDto = requestedUnitMap.get(existingUnit.getUnit().getId());

                if (matchingDto != null) {
                    // Update dữ liệu unit đã có bằng MapStruct
                    productUnitMapper.updateEntityFromDTO(matchingDto, existingUnit);

                    // Loại khỏi map để chừa lại các unit cần Thêm Mới
                    requestedUnitMap.remove(existingUnit.getUnit().getId());
                } else {
                    // Xóa unit không có trong request truyền lên
                    iterator.remove();
                }
            }

            // Phần còn lại trong map là các Unit mới tinh chưa từng có -> Thêm mới
            for (ProductUnitRequestDTO newUnitDto : requestedUnitMap.values()) {
                Unit unit = unitRepository.findById(newUnitDto.getUnitId())
                        .orElseThrow(() -> new BusinessException(ErrorCode.UNIT_NOT_FOUND));

                ProductUnit newProductUnit = productUnitMapper.toEntity(newUnitDto);
                newProductUnit.setUnit(unit);
                newProductUnit.setProduct(product);

                currentUnits.add(newProductUnit);
            }
        }
        return productRepository.save(product);
    }

    // 6. Delete product
    @Transactional
    public void deleteProduct(Long id) {
        productRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_EXISTS));
        productRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<ProductResponseDTO> getProductsBySlug(String slug) {
        Category category = categoryRepository.findBySlug(slug)
                .orElseThrow(() -> new BusinessException(ErrorCode.CATEGORY_NOT_FOUND));
        return productRepository.findByCategories_Id(category.getId()).stream().map(productMapper::toDto).toList();
    }
}
