export interface CriarPagamentoDto {
    geracaoDocumentoId: string;
}

export interface UrlPagamentoDto {
    urlPagamento: string;
}

export interface WebhookPagamentoDto {
    action: string;
    type: string;
    data: {
        id: string;
    };
}
