package com.docpronto.controller;

import com.docpronto.domain.dto.DocumentoDto;
import com.docpronto.service.DocumentoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/documentos")
public class DocumentoController {

    private final DocumentoService documentoService;

    public DocumentoController(DocumentoService documentoService) {
        this.documentoService = documentoService;
    }

    @GetMapping
    public ResponseEntity<List<DocumentoDto>> listarTodos() {
        List<DocumentoDto> documentos = documentoService.listarTodos();
        return ResponseEntity.ok(documentos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DocumentoDto> buscarPorId(@PathVariable UUID id) {
        DocumentoDto documento = documentoService.buscarPorId(id);
        return ResponseEntity.ok(documento);
    }
}
