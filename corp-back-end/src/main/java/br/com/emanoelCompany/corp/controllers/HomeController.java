package br.com.emanoelCompany.corp.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/emanoelcompany")
public class HomeController {
    @GetMapping
    public String home() {
        return "home";
    }

    @PostMapping
    public String processFormData() {
        return "redirect:/emanoelcompany";
    }
}
