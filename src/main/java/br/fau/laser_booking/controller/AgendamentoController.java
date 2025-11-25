package br.fau.laser_booking.controller;

import br.fau.laser_booking.dto.AgendamentoRequest;
import br.fau.laser_booking.model.Aluno;
import br.fau.laser_booking.model.Reserva;
import br.fau.laser_booking.repository.AlunoRepository;
import br.fau.laser_booking.service.AgendamentoService;
import br.fau.laser_booking.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/agendamentos")
public class AgendamentoController {

    private final AgendamentoService agendamentoService;
    private final AlunoRepository alunoRepository;
    private final AuthService authService;

    public AgendamentoController(AgendamentoService agendamentoService,
                                 AlunoRepository alunoRepository,
                                 AuthService authService) {
        this.agendamentoService = agendamentoService;
        this.alunoRepository = alunoRepository;
        this.authService = authService;
    }

    
    @PostMapping
    public ResponseEntity<?> criarAgendamento(@RequestBody AgendamentoRequest req) {
        try {
            
            Aluno aluno = authService.getAlunoLogado();

            Reserva reserva = agendamentoService.agendarHorario(
                    aluno, req.getInicio(), req.getFim(), req.getEquipamento()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(reserva);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao agendar: " + e.getMessage());
        }
    }

    
   
    @GetMapping("/meus/{ignored}")
    public ResponseEntity<?> listarReservasDoAluno(@PathVariable Long ignored) {
        try {
            Aluno aluno = authService.getAlunoLogado();
            List<Reserva> reservas = agendamentoService.listarReservasDoAluno(aluno);
            return ResponseEntity.ok(reservas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao listar reservas: " + e.getMessage());
        }
    }

    
    @PostMapping("/{reservaId}/cancelar")
    public ResponseEntity<?> cancelarReserva(@PathVariable Long reservaId,
                                             
                                             @RequestParam(required = false) Long alunoId) {
        try {
            Aluno aluno = authService.getAlunoLogado();
            agendamentoService.cancelarReserva(aluno, reservaId);
            return ResponseEntity.ok("Reserva cancelada com sucesso.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao cancelar reserva: " + e.getMessage());
        }
    }

    
@PostMapping("/{reservaId}/suplente")
public ResponseEntity<?> incluirSuplente(@PathVariable Long reservaId,
                                         @RequestParam String suplenteEmail) {
    try {
        
        Aluno titular = authService.getAlunoLogado();

        
        Aluno suplente = alunoRepository.findByEmail(suplenteEmail)
                .orElseThrow(() -> new IllegalArgumentException("Suplente não encontrado."));

        agendamentoService.incluirSuplente(titular, reservaId, suplente);

        String msg = "Suplente " + suplente.getNome()
                + " incluído na reserva " + reservaId + " com sucesso.";
        return ResponseEntity.ok(msg);

    } catch (IllegalArgumentException e) {
        
        return ResponseEntity.badRequest().body(e.getMessage());
    } catch (IllegalStateException e) {
        
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao incluir suplente: " + e.getMessage());
    }
}


    
    @PostMapping("/{reservaId}/editar")
    public ResponseEntity<?> editarReserva(@PathVariable Long reservaId,
                                           
                                           @RequestParam(required = false) Long alunoId,
                                           @RequestParam String inicio,
                                           @RequestParam String fim,
                                           @RequestParam String equipamento) {
        try {
            Aluno aluno = authService.getAlunoLogado();

            LocalDateTime ni = LocalDateTime.parse(inicio);
            LocalDateTime nf = LocalDateTime.parse(fim);

            Reserva editada = agendamentoService.editarHorario(aluno, reservaId, ni, nf, equipamento);
            return ResponseEntity.ok(editada);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao editar reserva: " + e.getMessage());
        }
    }
}
