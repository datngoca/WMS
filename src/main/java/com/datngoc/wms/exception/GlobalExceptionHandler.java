package com.datngoc.wms.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.datngoc.wms.dto.response.ApiResponseDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class GlobalExceptionHandler {
    private final MessageSource messageSource;

    // 1. Bắt lỗi Validation (DTO check)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponseDTO<Map<String, String>>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        // Logic: Duyệt qua danh sách lỗi (ex.getBindingResult().getFieldErrors())
        // Đẩy vào Map: fieldName -> errorMessage
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            String fieldName = error.getField();
            String errorMessage = messageSource.getMessage(error, LocaleContextHolder.getLocale());

            errors.put(fieldName, errorMessage);
        });

        String msg = messageSource.getMessage(ErrorCode.VALIDATION_FAILED.getMessageKey(), null,
                LocaleContextHolder.getLocale());
        ApiResponseDTO<Map<String, String>> response = ApiResponseDTO.<Map<String, String>>builder()
                .code(ErrorCode.VALIDATION_FAILED.name())
                .message(msg)
                .data(errors)
                .build();

        return ResponseEntity.status(ErrorCode.VALIDATION_FAILED.getHttpStatus()).body(response);
    }

    // 2. Bắt lỗi RuntimeException (Lỗi hệ thống không mong muốn)
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponseDTO<String>> handleRuntimeException(RuntimeException ex) {
        log.error("Unhandled Exception: ", ex);
        String msg = messageSource.getMessage(ErrorCode.INTERNAL_SERVER_ERROR.getMessageKey(), null,
                LocaleContextHolder.getLocale());
        ApiResponseDTO<String> response = ApiResponseDTO.<String>builder()
                .code(ErrorCode.INTERNAL_SERVER_ERROR.name())
                .message(msg)
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(ErrorCode.INTERNAL_SERVER_ERROR.getHttpStatus()).body(response);
    }

    // 4. Bắt lỗi BusinessException
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponseDTO<Void>> BusinessException(BusinessException ex) {
        String msg = messageSource.getMessage(ex.getErrorCode().getMessageKey(), ex.getArgs(),
                LocaleContextHolder.getLocale());

        ApiResponseDTO<Void> response = ApiResponseDTO.<Void>builder()
                .code(ex.getErrorCode().name())
                .message(msg)
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(ex.getErrorCode().getHttpStatus()).body(response);
    }

}
