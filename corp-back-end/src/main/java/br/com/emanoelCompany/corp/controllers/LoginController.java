package br.com.emanoelCompany.corp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/")
    public String dashboard() {
        return "home";
    }

    @GetMapping("/entrar")
    public String login() {
        return "login";
    }
}
