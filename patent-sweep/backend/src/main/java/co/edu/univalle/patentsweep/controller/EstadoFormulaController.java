package co.edu.univalle.patentsweep.controller;

import co.edu.univalle.patentsweep.config.OpenApiConfig;
import co.edu.univalle.patentsweep.controller.dto.EstadoFormulaResponseDTO;
import co.edu.univalle.patentsweep.service.EstadoFormulaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/** Consulta del estado precalculado de una fórmula para Fabricación (PB HU-04). */
@RestController
@RequestMapping("/api/v1/patentes")
@Tag(name = "Estado de Fórmulas", description = "Consulta del estado operativo de fórmulas para Fabricación")
@SecurityRequirement(name = OpenApiConfig.ESQUEMA_SEGURIDAD)
@Validated
public class EstadoFormulaController {

    private final EstadoFormulaService estadoFormulaService;

    public EstadoFormulaController(EstadoFormulaService estadoFormulaService) {
        this.estadoFormulaService = estadoFormulaService;
    }


    @GetMapping("/estado")
    @Operation(summary = "Consultar el estado de una fórmula frente al barrido de patentes",
            description = "Permite a Fabricación verificar el estado precalculado de la fórmula "
                    + "para autorizar su producción en menos de 1 segundo (HU-04, modelo P16 / DA-08). "
                    + "El usuario responsable se toma del token de la sesión.")
    @ApiResponse(responseCode = "200", description = "Estado consultado exitosamente")
    @ApiResponse(responseCode = "400", description = "El formulaId está vacío o no tiene el formato form-NNN")
    @ApiResponse(responseCode = "401", description = "No autenticado")
    @ApiResponse(responseCode = "404", description = "La fórmula no existe")
    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    public ResponseEntity<EstadoFormulaResponseDTO> consultarEstado(
            @RequestParam
            @NotBlank(message = "El código de la fórmula es obligatorio y debe tener el formato form-NNN")
            @Pattern(regexp = "^form-\\d{3}$", message = "El código de la fórmula es obligatorio y debe tener el formato form-NNN")
            String formulaId) {

        var formula = estadoFormulaService.consultarEstado(formulaId);
        return ResponseEntity.ok(new EstadoFormulaResponseDTO(
                formula.getFormulaId(),
                formula.getEstado(),
                formula.getFechaUltimaRevision(),
                "La fórmula se encuentra en estado " + formula.getEstado() + " para su uso en fabricación."
        ));
    }
}
