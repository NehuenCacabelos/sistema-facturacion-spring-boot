package com.facturacion.api.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LocaleController {

    @GetMapping("/locale")
    public String locale(HttpServletRequest request) {
        String ultimoUrl = request.getHeader("referer");
        if (ultimoUrl == null || ultimoUrl.isEmpty()) {
            return "redirect:/listar";
        }
        return "redirect:".concat(ultimoUrl);
    }
}
