package br.fau.laser_booking.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/")
    public String root() {
        return "redirect:/app";
    }

    @GetMapping("/login")
    public String login() {
        return "login";          // templates/login.html
    }

    @GetMapping("/app")
    public String app() {
        return "app";            // templates/app.html
    }

    // ---- UC pages (2 aliases para cada) ----
    @GetMapping({"/uc01", "/agendar"})
    public String uc01() {
        return "agendar";        // templates/agendar.html
    }

    @GetMapping({"/uc02", "/cancelar"})
    public String uc02() {
        return "cancelar";       // templates/cancelar.html
    }

    @GetMapping({"/uc03", "/suplente"})
    public String uc03() {
        return "suplente";       // templates/suplente.html
    }
}
