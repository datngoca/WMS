package com.datngoc.wms.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SuccessCode {
    AUTH_SUCCESS("AUTH_SUCCESS", HttpStatus.OK),
    REGISTER_SUCCESS("REGISTER_SUCCESS", HttpStatus.OK),
    // Chung
    GET_SUCCESS("GET_SUCCESS", HttpStatus.OK),
    CREATE_SUCCESS("CREATE_SUCCESS", HttpStatus.CREATED),
    UPDATE_SUCCESS("UPDATE_SUCCESS", HttpStatus.OK),
    DELETE_SUCCESS("DELETE_SUCCESS", HttpStatus.OK),

    // Nghiệp vụ WMS đặc thù
    IMPORT_SUCCESS("IMPORT_SUCCESS", HttpStatus.OK),
    EXPORT_SUCCESS("EXPORT_SUCCESS", HttpStatus.OK),
    STOCK_TAKE_COMPLETED("STOCK_TAKE_COMPLETED", HttpStatus.OK);

    private final String messageKey;
    private final HttpStatus httpStatus;
}
