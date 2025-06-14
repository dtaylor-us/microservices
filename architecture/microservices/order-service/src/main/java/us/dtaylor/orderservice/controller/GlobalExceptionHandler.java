package us.dtaylor.orderservice.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import us.dtaylor.orderservice.exception.OrderValidationException;

import java.time.ZonedDateTime;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(OrderValidationException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(OrderValidationException ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of(
                        "status", 400,
                        "error", "Bad Request",
                        "message", ex.getMessage(),
                        "path", request.getRequestURI(),
                        "timestamp", ZonedDateTime.now().toString()
                ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneric(Exception ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.ofEntries(
                        Map.entry("status", 500),
                        Map.entry("error", "Internal Server Error"),
                        Map.entry("message", ex.getMessage() != null ? ex.getMessage() : "Unexpected error"),
                        Map.entry("path", request.getRequestURI()),
                        Map.entry("timestamp", ZonedDateTime.now().toString())
                ));
    }

}
