package com.dallinjohnson.financeManagerAPI.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class ApiError {
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
    private List<ErrorItem> details;

    @Data
    @AllArgsConstructor
    public static class ErrorItem {
        private String field;
        private String error;
    }
}
