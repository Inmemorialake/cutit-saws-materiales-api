package co.edu.univalle.patentsweep.model;

import co.edu.univalle.patentsweep.model.enums.EstadoFormula;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Referencia local de una fórmula vigilada (ER: Formula). Nombre y descripción se copian de
 * Materiales/Fórmulas, dueña de esos datos; el estado y el último dictamen son propios de Patent Sweep.
 */
@Entity
@Table(name = "formula")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Formula {

    @Id
    @Column(name = "formula_id", length = 20)
    private String formulaId;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Column(nullable = false)
    private boolean activo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private EstadoFormula estado;

    @Column(name = "ultimo_dictamen", length = 30)
    private String ultimoDictamen;

    @Column(name = "fecha_ultima_revision")
    private LocalDateTime fechaUltimaRevision;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    void alCrear() {
        LocalDateTime ahora = LocalDateTime.now();
        createdAt = ahora;
        updatedAt = ahora;
    }

    @PreUpdate
    void alActualizar() {
        updatedAt = LocalDateTime.now();
    }
}
