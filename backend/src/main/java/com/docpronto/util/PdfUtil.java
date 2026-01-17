package com.docpronto.util;

import org.springframework.stereotype.Component;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Component
public class PdfUtil {

    private static final String MARCA_DAGUA = "VERSÃO PREVIEW";

    public byte[] gerarPdf(String html, boolean pago) throws IOException {
        String htmlCompleto = prepararHtml(html, pago);
        
        try {
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(htmlCompleto);
            renderer.layout();
            
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            renderer.createPDF(outputStream);
            
            return outputStream.toByteArray();
        } catch (Exception e) {
            throw new IOException("Erro ao gerar PDF com Flying Saucer: " + e.getMessage(), e);
        }
    }

    private String prepararHtml(String html, boolean pago) {
        if (!html.contains("<html>")) {
            html = "<html><head><meta charset=\"UTF-8\" /></head><body>" + html + "</body></html>";
        }
        
        html = normalizarHtml(html);
        
        // Comentado para testes locais - descomentar em produção
        // if (!pago) {
        //     String marcaDaguaStyle = "position:fixed;top:50%;left:50%;transform:translate(-50%,-50%) rotate(-45deg);" +
        //             "font-size:48px;font-weight:bold;color:#cccccc;opacity:0.3;pointer-events:none;z-index:1000;" +
        //             "font-family:Arial,sans-serif;white-space:nowrap;";
        //     
        //     html = html.replace("</body>", 
        //         "<div style=\"" + marcaDaguaStyle + "\">" + MARCA_DAGUA + "</div></body>");
        // }
        
        return html;
    }

    private String normalizarHtml(String html) {
        html = html.replace("'", "\"");
        html = html.replaceAll("<meta([^>]*?)>", "<meta$1 />");
        html = html.replaceAll("<style>([^<]*)</style>", "<style type=\"text/css\">$1</style>");
        html = html.replaceAll("class='([^']*)'", "class=\"$1\"");
        html = html.replaceAll("<br>", "<br />");
        html = html.replaceAll("<br/>", "<br />");
        return html;
    }
}
