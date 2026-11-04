package co.edu.univalle.patentsweep.repository;

import co.edu.univalle.patentsweep.model.RevisionFormula;
import org.springframework.data.repository.Repository;

/**
 * Historial de revisiones solo de inserción (P18, DA-10): únicamente expone guardar;
 * no hay operaciones de actualización ni de borrado.
 */
@org.springframework.stereotype.Repository
public interface RevisionFormulaRepository extends Repository<RevisionFormula, Long> {

    RevisionFormula save(RevisionFormula revision);
}
