//package com.sparta.springlevel3.controller;
//
//import com.sparta.springlevel3.error.CustomException;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//
//@ControllerAdvice
//public class GlobalExceptionHandler {
//
//    @ExceptionHandler(CustomException.class)
//    public ResponseEntity<String> handleUnauthorizedException(CustomException ex) {
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                .body("{\"status\": 400, \"error\": \"" + ex.getMessage() + "\"}");
//    }
//}
