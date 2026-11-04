package co.edu.univalle.patentsweep.repository;

import co.edu.univalle.patentsweep.model.Formula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** Acceso a datos de fórmulas (P04 Repository). */
@Repository
public interface FormulaRepository extends JpaRepository<Formula, String> {
}
