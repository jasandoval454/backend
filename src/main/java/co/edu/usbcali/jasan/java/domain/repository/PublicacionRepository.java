package co.edu.usbcali.jasan.java.domain.repository;

import co.edu.usbcali.jasan.java.domain.Publicacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PublicacionRepository extends JpaRepository<Publicacion, Integer> {

    List<Publicacion> findAllByDeletedAtIsNullOrderByCreatedAtDesc();

    Optional<Publicacion> findByIdAndDeletedAtIsNull(Integer id);
}
