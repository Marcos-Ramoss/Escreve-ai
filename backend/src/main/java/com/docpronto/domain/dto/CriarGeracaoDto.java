package com.docpronto.domain.dto;

import jakarta.validation.constraints.NotNull;
import java.util.Map;
import java.util.UUID;

public record CriarGeracaoDto(
        @NotNull(message = "Documento ID é obrigatório")
        UUID documentoId,
        
        @NotNull(message = "Dados são obrigatórios")
        Map<String, Object> dados
) {
}
