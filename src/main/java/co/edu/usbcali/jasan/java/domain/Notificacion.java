package co.edu.usbcali.jasan.java.domain;

import co.edu.usbcali.jasan.java.domain.enums.TipoNotificacion;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "notificaciones")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Notificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "destinatario_id", nullable = false)
    private Usuario destinatario;

    @ManyToOne
    @JoinColumn(name = "origen_id")
    private Usuario origen;

    @Column(name = "tipo", nullable = false)
    private TipoNotificacion tipo;

    @Column(name = "leida", nullable = false)
    private Boolean leida;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        if (leida == null) {
            leida = false;
        }
    }
}
