package com.sm.app.admin.web.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    @GetMapping("/auth/login-form")
    public String loginForm() {
        return "pages/auth/loginForm";
    }
}
