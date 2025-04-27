package com.sm.app.admin.web.main.controller;

import com.sm.app.admin.config.security.bean.LoginInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class MainController {

    @GetMapping({"/", "/main"})
    public String main(@AuthenticationPrincipal LoginInfo loginInfo) {
        log.info("loginInfo: {}", loginInfo);
        return "pages/main/main";
    }
}
