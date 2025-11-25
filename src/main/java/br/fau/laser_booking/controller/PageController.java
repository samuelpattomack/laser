package br.fau.laser_booking.controller;

import br.fau.laser_booking.model.Aluno;
import br.fau.laser_booking.service.AuthService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    private final AuthService authService;

    public PageController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/")
    public String root() {
        return "redirect:/app";
    }

    @GetMapping("/login")
    public String login() {
        return "login";          
    }

    @GetMapping("/app")
    public String app() {
        return "app";            
    }

   
    @GetMapping({"/uc01", "/agendar"})
    public String uc01(Model model) {
        Aluno aluno = authService.getAlunoLogado();

        model.addAttribute("alunoNome", aluno.getNome());
        model.addAttribute("alunoSemestre", aluno.getSemestre());
        model.addAttribute("alunoTipo", aluno.getTipoTrabalho());
        model.addAttribute("alunoId", aluno.getId());

        return "agendar";        
    }

    
    @GetMapping({"/uc02", "/cancelar"})
    public String uc02(Model model) {
        Aluno aluno = authService.getAlunoLogado();

        model.addAttribute("alunoNome", aluno.getNome());
        model.addAttribute("alunoSemestre", aluno.getSemestre());
        model.addAttribute("alunoTipo", aluno.getTipoTrabalho());
        model.addAttribute("alunoId", aluno.getId());

        return "cancelar";       
    }

    
    @GetMapping({"/uc03", "/suplente"})
    public String uc03(Model model) {
        Aluno aluno = authService.getAlunoLogado();

        model.addAttribute("alunoNome", aluno.getNome());
        model.addAttribute("alunoSemestre", aluno.getSemestre());
        model.addAttribute("alunoTipo", aluno.getTipoTrabalho());
        model.addAttribute("alunoId", aluno.getId());

        return "suplente";       
    }
}
