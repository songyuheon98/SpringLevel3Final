//package com.sparta.springlevel3.error;

//import jakarta.servlet.http.HttpServletResponse;
//
//import java.io.IOException;
//import java.io.PrintWriter;
//
//public class CustomException extends RuntimeException{
//    HttpServletResponse res;
//    PrintWriter writer;
//    public CustomException() throws IOException {
//        res.setStatus(400);
//        writer= res.getWriter();
//
//        writer.println("{\n   \"status\":\"400\", ");
//        writer.println("   \"message\":\"토큰이 유효하지 않습니다 >.< !!! \"\n}");
//        writer.flush();
//        return;
//    }
//}
//public class CustomException extends RuntimeException {
//    public CustomException(String message) {
//        super(message);
//    }
//}

