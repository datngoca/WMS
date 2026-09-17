package com.datngoc.wms.dto.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL) // Ẩn các trường null khi trả ề
public class ApiResponseDTO<T> {
    private String code;
    private String message;
    private T data;
    private Meta meta;
    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Meta {
        private int page;
        private int size;
        private long total;
    }
}
