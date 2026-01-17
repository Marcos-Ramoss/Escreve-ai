package com.docpronto.domain.dto;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record CriarPagamentoDto(
        @NotNull(message = "Geração documento ID é obrigatório")
        UUID geracaoDocumentoId
) {
}
