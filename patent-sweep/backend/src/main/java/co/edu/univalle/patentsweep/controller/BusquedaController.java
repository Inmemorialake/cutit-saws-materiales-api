package co.edu.univalle.patentsweep.controller;

import co.edu.univalle.patentsweep.config.OpenApiConfig;
import co.edu.univalle.patentsweep.controller.dto.BusquedaRequestDTO;
import co.edu.univalle.patentsweep.controller.dto.BusquedaResponseDTO;
import co.edu.univalle.patentsweep.controller.mapper.ConflictoMapper;
import co.edu.univalle.patentsweep.service.BusquedaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Búsqueda manual de patentes en conflicto con una fórmula (PB HU-02). */
@RestController
@RequestMapping("/api/v1/patentes")
@Tag(name = "Búsqueda de patentes", description = "Búsqueda manual de patentes para una fórmula")
@SecurityRequirement(name = OpenApiConfig.ESQUEMA_SEGURIDAD)
public class BusquedaController {

    private final BusquedaService busquedaService;
    private final ConflictoMapper conflictoMapper;

    public BusquedaController(BusquedaService busquedaService, ConflictoMapper conflictoMapper) {
        this.busquedaService = busquedaService;
        this.conflictoMapper = conflictoMapper;
    }

    @PostMapping("/busqueda")
    @Operation(summary = "Buscar patentes en conflicto con una fórmula",
            description = "Consulta la fórmula en Materiales/Fórmulas, busca patentes con las clasificaciones "
                    + "y el rango de fechas vigentes y registra los conflictos nuevos en estado DETECTADO. "
                    + "El usuario responsable se toma del token de la sesión.")
    @ApiResponse(responseCode = "200", description = "Búsqueda realizada; la lista de conflictos puede ser vacía")
    @ApiResponse(responseCode = "400", description = "El formulaId está vacío o no tiene el formato form-NNN")
    @ApiResponse(responseCode = "401", description = "No autenticado")
    @ApiResponse(responseCode = "404", description = "La fórmula no existe en Materiales/Fórmulas")
    @ApiResponse(responseCode = "409", description = "La fórmula está DESCONTINUADA")
    @ApiResponse(responseCode = "503", description = "Materiales/Fórmulas o la fuente de patentes no respondieron")
    public ResponseEntity<BusquedaResponseDTO> buscar(@Valid @RequestBody BusquedaRequestDTO solicitud,
                                                      Authentication autenticacion) {
        var conflictos = busquedaService.buscar(solicitud.formulaId(), autenticacion.getName());
        return ResponseEntity.ok(conflictoMapper.aRespuestaDeBusqueda(solicitud.formulaId(), conflictos));
    }
}
