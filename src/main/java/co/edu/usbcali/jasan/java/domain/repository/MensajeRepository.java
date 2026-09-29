package co.edu.usbcali.jasan.java.domain.repository;

import co.edu.usbcali.jasan.java.domain.Mensaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MensajeRepository extends JpaRepository<Mensaje, Integer> {

    List<Mensaje> findByConversacionIdOrderByCreatedAtAsc(Integer conversacionId);

    List<Mensaje> findByDestinatarioIdAndLeidoFalseOrderByCreatedAtAsc(Integer destinatarioId);
}
