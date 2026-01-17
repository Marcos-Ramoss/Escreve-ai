package com.docpronto.domain.dto;

import java.util.UUID;

public record PreviewDocumentoDto(
        UUID id,
        String html,
        boolean pago
) {
}
