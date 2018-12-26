package com.wakabatimes.simplewiki.view.login;

import com.wakabatimes.simplewiki.app.domain.model.system.System;
import com.wakabatimes.simplewiki.app.domain.service.system.SystemService;
import com.wakabatimes.simplewiki.app.interfaces.system.dto.SystemResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    @Autowired
    private SystemService systemService;

    @GetMapping("/login")
    public String login(Model model){
        System system = systemService.get();
        SystemResponseDto systemResponseDto = new SystemResponseDto(system);
        model.addAttribute("system",systemResponseDto);
        return "login/login";
    }
}
