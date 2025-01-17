package com.wisenut.spring;

import com.wisenut.spring.controller.BrokerController;
import com.wisenut.spring.controller.SearchController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice(basePackageClasses = {SearchController.class, BrokerController.class})
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {

        Map<String, String> data = new HashMap<>();

        for (ObjectError oe : e.getBindingResult()
                               .getAllErrors()) {
            FieldError fieldError = (FieldError) oe;
            String fieldName = fieldError.getField();
            String errorMessage = fieldError.getDefaultMessage();

            if (data.containsKey(fieldName))
                data.put(fieldName, data.get(fieldName) + " " + errorMessage);
            else
                data.put(fieldName, errorMessage);
        }
        log.warn("Request body 에러: {}", data);

        return ResponseEntity
                .badRequest()
                .contentType(MediaType.APPLICATION_JSON)
                .body(data);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<?> handleNullPointerException(NullPointerException e) {
        Map<String, String> data = new HashMap<>();
        data.put("message", "에러가 발생했습니다. 관리자에게 문의하세요.");

        log.warn("NullPointerExcpetion...");
        return ResponseEntity.internalServerError()
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(data);
    }
}

