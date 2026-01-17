package com.docpronto.controller;

import com.docpronto.domain.dto.CriarPagamentoDto;
import com.docpronto.domain.dto.UrlPagamentoDto;
import com.docpronto.domain.dto.WebhookPagamentoDto;
import com.docpronto.service.PagamentoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {

    private final PagamentoService pagamentoService;

    public PagamentoController(PagamentoService pagamentoService) {
        this.pagamentoService = pagamentoService;
    }

    @PostMapping
    public ResponseEntity<UrlPagamentoDto> criarPagamento(@Valid @RequestBody CriarPagamentoDto dto) {
        UrlPagamentoDto urlPagamento = pagamentoService.criarPagamento(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(urlPagamento);
    }

    @PostMapping("/webhook")
    public ResponseEntity<Void> processarWebhook(@RequestBody WebhookPagamentoDto dto) {
        pagamentoService.processarWebhook(dto);
        return ResponseEntity.ok().build();
    }
}
