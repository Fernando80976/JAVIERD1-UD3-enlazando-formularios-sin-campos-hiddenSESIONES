package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
@Controller
public class Controlador {
	static String usuarioOk="fer",contraseñaOk="12345";

    // 1
    @GetMapping("/")
    public String inicio(HttpSession session) {
        if (session.getAttribute("usuario") != null) {
            return "pagina1";
        }
        return "login";
    }

    // 2
    @PostMapping("/login")
    String formLogin(
            @RequestParam(name = "usuario", required = true) String usuario,
            @RequestParam(name = "contraseña", required = true) String contraseña,
            Model model,
            HttpSession session) {


        if (usuario.equals(usuarioOk) && contraseña.equals(contraseñaOk)) {
            session.setAttribute("usuario", usuario);
            return "pagina1";
        } else {
            model.addAttribute("error", "Usuario o contraseña incorrectos");
            return "login"; 
        }
    }
    // 3
    @GetMapping("/pagina1")
    public String pagina1(HttpSession session) {
        if (session.getAttribute("usuario") == null) {
            return "login";
        }
        return "pagina1";
    }

    // 4
    @GetMapping("/pagina2")
    public String pagina2(HttpSession session) {
        if (session.getAttribute("usuario") == null) {
            return "login";
        }
        return "pagina2";
    }
}
