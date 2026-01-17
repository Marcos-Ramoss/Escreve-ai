package com.docpronto.domain.exception;

public class DocumentoNaoPagoException extends RuntimeException {

    public DocumentoNaoPagoException() {
        super("Documento não foi pago. É necessário realizar o pagamento para baixar o PDF.");
    }
}
