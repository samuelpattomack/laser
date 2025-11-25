package br.fau.laser_booking.config;

import br.fau.laser_booking.model.Aluno;
import br.fau.laser_booking.repository.AlunoRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AlunoUserDetailsService implements UserDetailsService {

    private final AlunoRepository alunoRepository;

    public AlunoUserDetailsService(AlunoRepository alunoRepository) {
        this.alunoRepository = alunoRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        Aluno aluno = alunoRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Aluno não encontrado: " + username));

        return new AlunoUserDetails(aluno);
    }
}
