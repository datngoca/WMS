package com.datngoc.wms.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.datngoc.wms.dto.request.WarehouseRequestDTO;
import com.datngoc.wms.entity.Warehouse;
import com.datngoc.wms.exception.ResourceNotFoundException;
import com.datngoc.wms.mapper.WarehouseMapper;
import com.datngoc.wms.repository.WarehouseRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WarehouseService {

    private final WarehouseRepository warehouseRepository;

    private final WarehouseMapper warehouseMapper;

    public Warehouse createWarehouse(WarehouseRequestDTO warehouseRequestDTO) {
        Warehouse warehouse = warehouseMapper.toEntity(warehouseRequestDTO);
        return warehouseRepository.save(warehouse);
    }

    public List<Warehouse> getAllWarehouse() {
        List<Warehouse> warehouse = warehouseRepository.findAll();
        return warehouse;
    }

    public Warehouse getWarehouseById(Long id) {
        Warehouse warehouse = warehouseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy kho với id: " + id));
        return warehouse;
    }

    public Warehouse updateWarehouse(Long id, WarehouseRequestDTO warehouseRequestDTO) {
        Warehouse warehouse = warehouseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy kho với id: " + id));
        warehouseMapper.updateEntityFromDTO(warehouseRequestDTO, warehouse);
        return warehouseRepository.save(warehouse);
    }

    public void deleteWarehouse(Long id) {
        warehouseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy kho với id: " + id));
        warehouseRepository.deleteById(id);
    }
}
