package br.com.emanoelCompany.corp.controllers;


import br.com.emanoelCompany.corp.services.AdministratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/login")
public class LoginController {

    String logado = "false";

    @Autowired
    private AdministratorService administratorService;

    @GetMapping()
    public String showLoginPage() {
        return "login";
    }

    @PostMapping()
    public String login(String usuario, String senha, Model model) {
        if (usuario != null && !usuario.isEmpty() && senha != null && !senha.isEmpty()) {
            if (administratorService.administradorExiste(usuario, senha)) {
                    model.addAttribute("true", logado);
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
}

