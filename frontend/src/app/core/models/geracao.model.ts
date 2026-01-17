export interface CriarGeracaoDto {
    documentoId: string;
    dados: Record<string, any>;
}

export interface PreviewDocumentoDto {
    id: string;
    html: string;
    pago: boolean;
}
