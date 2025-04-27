package com.sm.app.admin.web.error.controller;

import com.sm.app.common.exception.ExceptionResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ErrorController {

    @GetMapping("/error/error")
    public String error(@RequestParam(required = false) String code,
                        @RequestParam String message,
                        @RequestParam(required = false) String redirectUrl,
                        Model model) {

        model.addAttribute("exceptionResponse", ExceptionResponse.builder(message)
                .code(code)
                .redirectUrl(redirectUrl)
                .build());

        return "pages/error/error";
    }

    @GetMapping("/error/alert")
    public String errorAlert(@RequestParam(required = false) String code,
                             @RequestParam String message,
                             @RequestParam(required = false) String redirectUrl,
                             Model model) {

        model.addAttribute("exceptionResponse", ExceptionResponse.builder(message)
                .code(code)
                .redirectUrl(redirectUrl)
                .build());

        return "pages/error/alert";
    }
}
