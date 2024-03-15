package br.com.emanoelCompany.corp.controllers;

import org.springframework.web.bind.annotation.GetMapping;

public class LoginController {
    @GetMapping("/")
    String home() {
        return "home";
    }
    @GetMapping("/login")
    String login() {
        return "login";
    }
    
}
