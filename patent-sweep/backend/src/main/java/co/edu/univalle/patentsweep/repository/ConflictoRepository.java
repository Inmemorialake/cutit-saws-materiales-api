package co.edu.univalle.patentsweep.repository;

import co.edu.univalle.patentsweep.model.Conflicto;
import co.edu.univalle.patentsweep.model.enums.EstadoConflicto;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** Acceso a datos de conflictos (P04 Repository). */
@Repository
public interface ConflictoRepository extends JpaRepository<Conflicto, Long> {

    Optional<Conflicto> findByFormula_FormulaIdAndPatente_PatenteId(String formulaId, Long patenteId);

    long countByFormula_FormulaIdAndEstado(String formulaId, EstadoConflicto estado);
}
