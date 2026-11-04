package co.edu.univalle.patentsweep.model;

import co.edu.univalle.patentsweep.model.enums.EstadoVigenciaPatente;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Patente candidata detectada en la fuente externa (ER: Patente). */
@Entity
@Table(name = "patente")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Patente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "patente_id")
    private Long patenteId;

    /** Código de negocio con formato PAT-NNNNNN. */
    @Column(name = "codigo_patente", nullable = false, unique = true, length = 100)
    private String codigoPatente;

    @Column(nullable = false, length = 255)
    private String titulo;

    @Column(name = "tipo_producto", length = 60)
    private String tipoProducto;

    @Column(columnDefinition = "TEXT")
    private String resumen;

    /** Reivindicaciones separadas por salto de línea. */
    @Column(columnDefinition = "TEXT")
    private String reivindicaciones;

    @Column(name = "fecha_solicitud")
    private LocalDate fechaSolicitud;

    @Column(name = "fecha_concesion")
    private LocalDate fechaConcesion;

    @Column(name = "fecha_vencimiento")
    private LocalDate fechaVencimiento;

    @Column(length = 50)
    private String jurisdiccion;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_vigencia", length = 30)
    private EstadoVigenciaPatente estadoVigencia;

    @Column(length = 100)
    private String fuente;

    @Column(name = "url_fuente", columnDefinition = "TEXT")
    private String urlFuente;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    void alCrear() {
        createdAt = LocalDateTime.now();
    }
}
