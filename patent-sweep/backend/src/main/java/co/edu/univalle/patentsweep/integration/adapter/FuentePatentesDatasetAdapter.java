package co.edu.univalle.patentsweep.integration.adapter;

import co.edu.univalle.patentsweep.integration.dto.PatenteExternaDTO;
import co.edu.univalle.patentsweep.integration.port.FuentePatentesPort;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

/**
 * Adaptador de la fuente de patentes sobre un dataset local en JSON (DAS S-04). Se reemplaza por
 * una fuente real (p. ej., EPO OPS) implementando {@link FuentePatentesPort}, sin tocar la lógica.
 */
@Component
public class FuentePatentesDatasetAdapter implements FuentePatentesPort {

    private final List<PatenteExternaDTO> dataset;

    public FuentePatentesDatasetAdapter(ObjectMapper objectMapper, ResourceLoader cargadorRecursos,
                                        @Value("${patentsweep.fuente-patentes.dataset}") String ubicacion) {
        this.dataset = cargarDataset(objectMapper, cargadorRecursos.getResource(ubicacion));
    }

    @Override
    @Retry(name = "fuentePatentes")
    @CircuitBreaker(name = "fuentePatentes")
    public List<PatenteExternaDTO> buscarPatentes(List<String> terminos, List<String> clasificaciones,
                                                  LocalDate fechaSolicitudDesde) {
        return dataset.stream()
                .filter(patente -> coincideClasificacion(patente, clasificaciones))
                .filter(patente -> cumpleRangoFecha(patente, fechaSolicitudDesde))
                .filter(patente -> mencionaAlgunTermino(patente, terminos))
                .toList();
    }

    private static boolean coincideClasificacion(PatenteExternaDTO patente, List<String> clasificaciones) {
        if (clasificaciones == null || clasificaciones.isEmpty()) {
            return true;
        }
        return patente.clasificaciones() != null
                && patente.clasificaciones().stream().anyMatch(clasificaciones::contains);
    }

    private static boolean cumpleRangoFecha(PatenteExternaDTO patente, LocalDate desde) {
        if (desde == null) {
            return true;
        }
        return patente.fechaSolicitud() != null && !patente.fechaSolicitud().isBefore(desde);
    }

    private static boolean mencionaAlgunTermino(PatenteExternaDTO patente, List<String> terminos) {
        if (terminos == null || terminos.isEmpty()) {
            return true;
        }
        String reivindicaciones = patente.reivindicaciones() == null
                ? "" : String.join(" ", patente.reivindicaciones());
        String texto = (Objects.toString(patente.tituloPatente(), "") + " "
                + Objects.toString(patente.resumen(), "") + " " + reivindicaciones).toLowerCase(Locale.ROOT);
        return terminos.stream().anyMatch(termino -> texto.contains(termino.toLowerCase(Locale.ROOT)));
    }

    private static List<PatenteExternaDTO> cargarDataset(ObjectMapper objectMapper, Resource recurso) {
        try (InputStream entrada = recurso.getInputStream()) {
            return objectMapper.readValue(entrada, new TypeReference<List<PatenteExternaDTO>>() { });
        } catch (IOException e) {
            throw new IllegalStateException("No se pudo cargar el dataset de patentes: " + recurso, e);
        }
    }
}
