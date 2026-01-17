package com.docpronto.domain.dto;

public record WebhookPagamentoDto(
        String action,
        String type,
        WebhookDataDto data
) {
    public record WebhookDataDto(String id) {
    }
}
