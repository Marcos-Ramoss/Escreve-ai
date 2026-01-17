package com.docpronto.domain.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Document(collection = "geracoes_documentos")
public class GeracaoDocumentoEntity {

    @Id
    private UUID id;
    private UUID documentoId;
    private Map<String, Object> dadosJson;
    private boolean pago;
    private LocalDateTime createdAt;

    public GeracaoDocumentoEntity() {
        this.id = UUID.randomUUID();
        this.pago = false;
        this.createdAt = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getDocumentoId() {
        return documentoId;
    }

    public void setDocumentoId(UUID documentoId) {
        this.documentoId = documentoId;
    }

    public Map<String, Object> getDadosJson() {
        return dadosJson;
    }

    public void setDadosJson(Map<String, Object> dadosJson) {
        this.dadosJson = dadosJson;
    }

    public boolean isPago() {
        return pago;
    }

    public void setPago(boolean pago) {
        this.pago = pago;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
