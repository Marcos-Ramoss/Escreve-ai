package com.docpronto.domain.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record DocumentoDto(
        UUID id,
        String nome,
        String descricao,
        LocalDateTime createdAt
) {
}
