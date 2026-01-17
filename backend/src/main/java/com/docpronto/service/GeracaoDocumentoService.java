package com.docpronto.service;

import com.docpronto.domain.dto.CriarGeracaoDto;
import com.docpronto.domain.dto.PreviewDocumentoDto;
import com.docpronto.domain.entity.DocumentoEntity;
import com.docpronto.domain.entity.GeracaoDocumentoEntity;
import com.docpronto.domain.exception.DocumentoNaoEncontradoException;
import com.docpronto.domain.exception.DocumentoNaoPagoException;
import com.docpronto.domain.exception.GeracaoNaoEncontradaException;
import com.docpronto.repository.DocumentoRepository;
import com.docpronto.repository.GeracaoDocumentoRepository;
import com.docpronto.util.TemplateEngine;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GeracaoDocumentoService {

    private final GeracaoDocumentoRepository geracaoRepository;
    private final DocumentoRepository documentoRepository;
    private final TemplateEngine templateEngine;
    private final PdfService pdfService;

    public GeracaoDocumentoService(
            GeracaoDocumentoRepository geracaoRepository,
            DocumentoRepository documentoRepository,
            TemplateEngine templateEngine,
            PdfService pdfService) {
        this.geracaoRepository = geracaoRepository;
        this.documentoRepository = documentoRepository;
        this.templateEngine = templateEngine;
        this.pdfService = pdfService;
    }

    public UUID criarGeracao(CriarGeracaoDto dto) {
        validarDocumentoExiste(dto.documentoId());

        GeracaoDocumentoEntity entity = new GeracaoDocumentoEntity();
        entity.setDocumentoId(dto.documentoId());
        entity.setDadosJson(dto.dados());

        GeracaoDocumentoEntity saved = geracaoRepository.save(entity);
        return saved.getId();
    }

    public PreviewDocumentoDto obterPreview(UUID geracaoId) {
        GeracaoDocumentoEntity geracao = buscarGeracao(geracaoId);
        DocumentoEntity documento = buscarDocumento(geracao.getDocumentoId());

        String htmlProcessado = templateEngine.processarTemplate(
                documento.getTemplateHtml(),
                geracao.getDadosJson()
        );

        return new PreviewDocumentoDto(geracaoId, htmlProcessado, geracao.isPago());
    }

    public byte[] gerarPdf(UUID geracaoId) {
        GeracaoDocumentoEntity geracao = buscarGeracao(geracaoId);

        // if (!geracao.isPago()) {
        //     throw new DocumentoNaoPagoException();
        // }

        PreviewDocumentoDto preview = obterPreview(geracaoId);
        return pdfService.gerarPdf(preview.html(), true);
    }

    private GeracaoDocumentoEntity buscarGeracao(UUID id) {
        return geracaoRepository.findById(id)
                .orElseThrow(() -> new GeracaoNaoEncontradaException(id));
    }

    private DocumentoEntity buscarDocumento(UUID id) {
        return documentoRepository.findById(id)
                .orElseThrow(() -> new DocumentoNaoEncontradoException(id));
    }

    private void validarDocumentoExiste(UUID documentoId) {
        if (!documentoRepository.existsById(documentoId)) {
            throw new DocumentoNaoEncontradoException(documentoId);
        }
    }
}
