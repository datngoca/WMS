package com.datngoc.wms.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

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
    public List<ProductResponseDTO> getAllProducts() {
        return productRepository.findAll().stream().map(productMapper::toDto).toList();
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

        if (requestDTO.getCategories() != null && !requestDTO.getCategories().isEmpty()) {
            Set<Category> managedCategories = requestDTO.getCategories().stream()
                    .map(c -> categoryRepository.findById(c.getId())
                            .orElseThrow(() -> new BusinessException(ErrorCode.CATEGORY_NOT_FOUND)))
                    .collect(Collectors.toSet());
            product.setCategories(managedCategories);
        }

        if (requestDTO.getProductUnits() != null && !requestDTO.getProductUnits().isEmpty()) {
            List<ProductUnit> unitsToSave = new ArrayList<>();
            for (ProductUnitRequestDTO unitDto : requestDTO.getProductUnits()) {
                Unit unit = unitRepository.findById(unitDto.getUnitId())
                        .orElseThrow(() -> new BusinessException(ErrorCode.UNIT_NOT_FOUND));
                ProductUnit productUnit = productUnitMapper.toEntity(unitDto);
                productUnit.setUnit(unit);
                productUnit.setProduct(product);

                unitsToSave.add(productUnit);
            }
            product.setProductUnits(unitsToSave);
        }

        return productRepository.save(product);
    }

    // 5. Update product
    public Product updateProduct(Long id, ProductRequestDTO productDetails) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_EXISTS));
        productMapper.updateEntityFromDTO(productDetails, product);

        if (productDetails.getCategories() != null && !productDetails.getCategories().isEmpty()) {
            Set<Category> managedCategories = productDetails.getCategories().stream()
                    .map(c -> categoryRepository.findById(c.getId())
                            .orElseThrow(() -> new BusinessException(ErrorCode.CATEGORY_NOT_FOUND)))
                    .collect(Collectors.toSet());
            product.setCategories(managedCategories);
        } else if (productDetails.getCategories() != null && productDetails.getCategories().isEmpty()) {
            product.getCategories().clear();
        }

        if (productDetails.getProductUnits() != null) {
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
    public void deleteProduct(Long id) {
        productRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_EXISTS));
        productRepository.deleteById(id);
    }

    public List<ProductResponseDTO> getProductsBySlug(String slug) {
        Category category = categoryRepository.findBySlug(slug)
                .orElseThrow(() -> new BusinessException(ErrorCode.CATEGORY_NOT_FOUND));
        return productRepository.findByCategories_Id(category.getId()).stream().map(productMapper::toDto).toList();
    }
}
