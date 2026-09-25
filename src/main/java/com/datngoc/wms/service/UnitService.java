package com.datngoc.wms.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.datngoc.wms.dto.request.UnitRequestDTO;
import com.datngoc.wms.entity.Unit;
import com.datngoc.wms.exception.BusinessException;
import com.datngoc.wms.exception.ErrorCode;
import com.datngoc.wms.mapper.UnitMapper;
import com.datngoc.wms.repository.UnitRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UnitService {

    private final UnitMapper unitMapper;
    private final UnitRepository unitRepository;

    @Transactional
    public Unit createUnit(UnitRequestDTO unitRequest) {
        Unit unit = unitMapper.toEntity(unitRequest);
        if (unitRepository.existsByName(unitRequest.getName())) {
            throw new BusinessException(ErrorCode.UNIT_ALREADY_EXISTS, unitRequest.getName());
        }
        return unitRepository.save(unit);
    }

    public Page<Unit> getAllUnits(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return unitRepository.findAll(pageable);
    }

    public Unit getUnitById(Long id) {
        return unitRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.UNIT_NOT_FOUND));
    }

    @Transactional
    public Unit updateUnit(Long id, UnitRequestDTO unitRequest) {
        Unit unit = unitRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.UNIT_NOT_FOUND));

        if (!unit.getName().equals(unitRequest.getName())
                && unitRepository.existsByName(unitRequest.getName())) {
            throw new BusinessException(ErrorCode.UNIT_ALREADY_EXISTS, unitRequest.getName());
        }

        unitMapper.updateEntity(unitRequest, unit);
        return unitRepository.save(unit);
    }

    @Transactional
    public void deleteUnit(Long id) {
        Unit unit = unitRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.UNIT_NOT_FOUND));
        unitRepository.delete(unit);
    }
}
