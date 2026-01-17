package com.docpronto.controller;

import com.docpronto.domain.dto.CriarGeracaoDto;
import com.docpronto.domain.dto.PreviewDocumentoDto;
import com.docpronto.service.GeracaoDocumentoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/geracoes")
public class GeracaoController {

    private final GeracaoDocumentoService geracaoService;

    public GeracaoController(GeracaoDocumentoService geracaoService) {
        this.geracaoService = geracaoService;
    }

    @PostMapping
    public ResponseEntity<UUID> criarGeracao(@Valid @RequestBody CriarGeracaoDto dto) {
        UUID geracaoId = geracaoService.criarGeracao(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(geracaoId);
    }

    @GetMapping("/{id}/preview")
    public ResponseEntity<PreviewDocumentoDto> obterPreview(@PathVariable UUID id) {
        PreviewDocumentoDto preview = geracaoService.obterPreview(id);
        return ResponseEntity.ok(preview);
    }

    @GetMapping("/{id}/pdf")
    public ResponseEntity<byte[]> gerarPdf(@PathVariable UUID id) {
        byte[] pdf = geracaoService.gerarPdf(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "documento.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .body(pdf);
    }
}
