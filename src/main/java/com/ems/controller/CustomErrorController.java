package com.ems.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.context.request.WebRequest;

import com.ems.util.CustomeErrorResponse;

import jakarta.servlet.http.HttpSession;

@ControllerAdvice
public class CustomErrorController {

    @GetMapping("/error")
    public String handleError(HttpSession session) {

        if (session.getAttribute("userId") == null) {
            return "redirect:/login";
        }

        return "error/404";
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomeErrorResponse> handleExcception(Exception ex,WebRequest request){
    	return ResponseEntity.status(404).body(
    			new CustomeErrorResponse("This is not working",request.getDescription(false),404)
    			);
    			
    }
}
