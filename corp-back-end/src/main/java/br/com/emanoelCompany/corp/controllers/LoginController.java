package br.com.emanoelCompany.corp.controllers;


import br.com.emanoelCompany.corp.model.Administrador;
import br.com.emanoelCompany.corp.services.AdministratorService;
import br.com.emanoelCompany.corp.services.CookieService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.UnsupportedEncodingException;


@Controller
@RequestMapping()
public class LoginController {

    @Autowired
    private AdministratorService administratorService;

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(Administrador adm, String usuario,
                        String senha, Model model, HttpServletResponse response) throws UnsupportedEncodingException {
        if (usuario != null && !usuario.isEmpty() && senha != null && !senha.isEmpty()) {

            if (administratorService.administradorExiste(usuario, senha)) {
                CookieService.setCookie(response, "usuarioId", String.valueOf(adm.getId()), 1200);
                return "redirect:/emanoelcompany";

            } else {
                model.addAttribute("error", "Usuário ou senha inválidos");
                return "login";
            }

        } else {
            model.addAttribute("error", "Por favor, preencha todos os campos");
            return "login";
        }

    }

    @GetMapping("/logout")
    public String logout(HttpServletResponse response) {
        CookieService.setCookie(response, "usuarioId", "", 0);
        return "redirect:/login";
    }
}

