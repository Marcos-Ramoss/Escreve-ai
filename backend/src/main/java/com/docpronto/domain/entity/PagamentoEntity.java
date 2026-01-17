package com.docpronto.domain.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Document(collection = "pagamentos")
public class PagamentoEntity {

    @Id
    private UUID id;
    private UUID geracaoDocumentoId;
    private StatusPagamento status;
    private BigDecimal valor;
    private String gatewayId;
    private LocalDateTime createdAt;

    public PagamentoEntity() {
        this.id = UUID.randomUUID();
        this.status = StatusPagamento.PENDENTE;
        this.createdAt = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getGeracaoDocumentoId() {
        return geracaoDocumentoId;
    }

    public void setGeracaoDocumentoId(UUID geracaoDocumentoId) {
        this.geracaoDocumentoId = geracaoDocumentoId;
    }

    public StatusPagamento getStatus() {
        return status;
    }

    public void setStatus(StatusPagamento status) {
        this.status = status;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getGatewayId() {
        return gatewayId;
    }

    public void setGatewayId(String gatewayId) {
        this.gatewayId = gatewayId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public enum StatusPagamento {
        PENDENTE,
        APROVADO,
        RECUSADO
    }
}
