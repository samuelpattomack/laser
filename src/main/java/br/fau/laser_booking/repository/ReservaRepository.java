package br.fau.laser_booking.repository;

import br.fau.laser_booking.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    
    boolean existsByEquipamentoAndStatusInAndInicioBeforeAndFimAfter(
            String equipamento,
            Collection<Reserva.Status> status,
            LocalDateTime fimNovo,
            LocalDateTime inicioNovo
    );

    
    boolean existsByEquipamentoAndStatusInAndInicioBeforeAndFimAfterAndIdNot(
            String equipamento,
            Collection<Reserva.Status> status,
            LocalDateTime fimNovo,
            LocalDateTime inicioNovo,
            Long idToIgnore
    );

    
    List<Reserva> findAllByTitularIdOrderByInicioDesc(Long titularId);
}
