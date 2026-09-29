package co.edu.usbcali.jasan.java.domain.repository;

import co.edu.usbcali.jasan.java.domain.Conversacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConversacionRepository extends JpaRepository<Conversacion, Integer> {

    @Query("""
            SELECT c
            FROM Conversacion c
            WHERE (c.usuario1.id = :usuario1Id AND c.usuario2.id = :usuario2Id)
               OR (c.usuario1.id = :usuario2Id AND c.usuario2.id = :usuario1Id)
            """)
    Optional<Conversacion> findEntreUsuarios(
            @Param("usuario1Id") Integer usuario1Id,
            @Param("usuario2Id") Integer usuario2Id
    );

    @Query("""
            SELECT c
            FROM Conversacion c
            WHERE c.usuario1.id = :usuarioId OR c.usuario2.id = :usuarioId
            ORDER BY c.updatedAt DESC
            """)
    List<Conversacion> findByUsuarioId(@Param("usuarioId") Integer usuarioId);
}
