package co.edu.univalle.patentsweep.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import co.edu.univalle.patentsweep.config.JacksonConfig;
import co.edu.univalle.patentsweep.integration.adapter.FuentePatentesDatasetAdapter;
import co.edu.univalle.patentsweep.integration.dto.PatenteExternaDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

class FuentePatentesDatasetAdapterTest {

    private static final String DATASET = "classpath:data/patentes_dataset.json";

    private ObjectMapper objectMapper;
    private FuentePatentesDatasetAdapter adaptador;

    @BeforeEach
    void preparar() {
        Jackson2ObjectMapperBuilder constructor = Jackson2ObjectMapperBuilder.json();
        new JacksonConfig().formatoFechas().customize(constructor);
        objectMapper = constructor.build();
        adaptador = new FuentePatentesDatasetAdapter(objectMapper, new DefaultResourceLoader(), DATASET);
    }

    @Test
    @DisplayName("Sin filtros devuelve todo el dataset, con las fechas dd/mm/aaaa interpretadas")
    void deberiaDevolverTodoElDatasetSinFiltros() {
        // Act
        List<PatenteExternaDTO> resultado = adaptador.buscarPatentes(null, null, null);

        // Assert
        assertThat(resultado).extracting(PatenteExternaDTO::patenteId)
                .containsExactly("PAT-998822", "PAT-554433", "PAT-110022", "PAT-770011");
        assertThat(resultado.get(0).fechaSolicitud()).isEqualTo(LocalDate.of(2025, 3, 15));
    }

    @Test
    @DisplayName("Filtra por clasificación IPC/CPC")
    void deberiaFiltrarPorClasificacion() {
        // Act
        List<PatenteExternaDTO> resultado = adaptador.buscarPatentes(List.of(), List.of("B24D 3/00"), null);

        // Assert
        assertThat(resultado).extracting(PatenteExternaDTO::patenteId).containsExactly("PAT-554433", "PAT-770011");
    }

    @Test
    @DisplayName("Excluye las patentes solicitadas antes de la fecha mínima")
    void deberiaFiltrarPorFechaDeSolicitud() {
        // Act
        List<PatenteExternaDTO> resultado = adaptador.buscarPatentes(List.of(), List.of(), LocalDate.of(2025, 1, 1));

        // Assert
        assertThat(resultado).extracting(PatenteExternaDTO::patenteId).containsExactly("PAT-998822", "PAT-554433");
    }

    @Test
    @DisplayName("Filtra por términos sin distinguir mayúsculas")
    void deberiaFiltrarPorTerminos() {
        // Act
        List<PatenteExternaDTO> resultado = adaptador.buscarPatentes(List.of("COBALTO"), List.of(), null);

        // Assert
        assertThat(resultado).extracting(PatenteExternaDTO::patenteId).containsExactly("PAT-998822", "PAT-110022");
    }

    @Test
    @DisplayName("Un recurso inexistente falla al arrancar")
    void deberiaFallarSiElDatasetNoExiste() {
        assertThatThrownBy(() -> new FuentePatentesDatasetAdapter(objectMapper, new DefaultResourceLoader(),
                "classpath:data/no-existe.json"))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("No se pudo cargar el dataset de patentes");
    }
}
