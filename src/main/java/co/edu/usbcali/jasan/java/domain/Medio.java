package co.edu.usbcali.jasan.java.domain;

import co.edu.usbcali.jasan.java.domain.enums.TipoMedio;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "medios")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Medio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "publicacion_id", nullable = false)
    private Publicacion publicacion;

    @Column(name = "tipo", nullable = false)
    private TipoMedio tipo;

    @Column(name = "url", nullable = false, length = 500)
    private String url;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }
}
