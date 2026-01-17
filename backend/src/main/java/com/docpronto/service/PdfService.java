package com.docpronto.service;

import com.docpronto.util.PdfUtil;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class PdfService {

    private final PdfUtil pdfUtil;

    public PdfService(PdfUtil pdfUtil) {
        this.pdfUtil = pdfUtil;
    }

    public byte[] gerarPdf(String html, boolean pago) {
        try {
            return pdfUtil.gerarPdf(html, pago);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar PDF: " + e.getMessage(), e);
        }
    }
}
