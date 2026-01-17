package com.docpronto.domain.exception;

import java.util.UUID;

public class GeracaoNaoEncontradaException extends RuntimeException {

    public GeracaoNaoEncontradaException(UUID id) {
        super("Geração de documento não encontrada com ID: " + id);
    }
}
