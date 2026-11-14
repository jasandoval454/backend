package co.edu.usbcali.jasan.java.domain.repository;

import co.edu.usbcali.jasan.java.domain.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ComentarioRepository extends JpaRepository<Comentario, Integer> {

    List<Comentario> findByPublicacion_IdAndDeletedAtIsNullOrderByCreatedAtAsc(Integer publicacionId);

    Optional<Comentario> findByIdAndPublicacion_IdAndDeletedAtIsNull(
            Integer id,
            Integer publicacionId
    );
}
