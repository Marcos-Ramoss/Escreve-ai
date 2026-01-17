package com.docpronto.service;

import com.docpronto.domain.dto.CriarPagamentoDto;
import com.docpronto.domain.dto.UrlPagamentoDto;
import com.docpronto.domain.dto.WebhookPagamentoDto;
import com.docpronto.domain.entity.GeracaoDocumentoEntity;
import com.docpronto.domain.entity.PagamentoEntity;
import com.docpronto.domain.exception.GeracaoNaoEncontradaException;
import com.docpronto.repository.GeracaoDocumentoRepository;
import com.docpronto.repository.PagamentoRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class PagamentoService {

    private static final String MERCADOPAGO_API_URL = "https://api.mercadopago.com";

    private final PagamentoRepository pagamentoRepository;
    private final GeracaoDocumentoRepository geracaoRepository;
    private final RestTemplate restTemplate;
    
    @Value("${app.pagamento.valor-venda-unica:29.90}")
    private BigDecimal valorVendaUnica;

    @Value("${mercadopago.access-token:}")
    private String accessToken;

    public PagamentoService(
            PagamentoRepository pagamentoRepository,
            GeracaoDocumentoRepository geracaoRepository,
            RestTemplate restTemplate) {
        this.pagamentoRepository = pagamentoRepository;
        this.geracaoRepository = geracaoRepository;
        this.restTemplate = restTemplate;
    }

    public UrlPagamentoDto criarPagamento(CriarPagamentoDto dto) {
        validarGeracaoExiste(dto.geracaoDocumentoId());

        PagamentoEntity pagamento = new PagamentoEntity();
        pagamento.setGeracaoDocumentoId(dto.geracaoDocumentoId());
        pagamento.setValor(valorVendaUnica);

        String preferenceId = criarPreferenciaMercadoPago();
        pagamento.setGatewayId(preferenceId);

        pagamentoRepository.save(pagamento);
        
        String urlPagamento = obterUrlPagamento(preferenceId);
        return new UrlPagamentoDto(urlPagamento);
    }

    private String criarPreferenciaMercadoPago() {
        if (accessToken == null || accessToken.isEmpty()) {
            return "mock-preference-id-" + UUID.randomUUID();
        }

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + accessToken);
            headers.set("Content-Type", "application/json");

            Map<String, Object> item = new HashMap<>();
            item.put("title", "Acesso a Documentos DocPronto");
            item.put("quantity", 1);
            item.put("unit_price", valorVendaUnica);

            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("items", List.of(item));

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);
            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                    MERCADOPAGO_API_URL + "/checkout/preferences",
                    HttpMethod.POST,
                    request,
                    new ParameterizedTypeReference<Map<String, Object>>() {}
            );

            Map<String, Object> body = response.getBody();
            return body != null && body.containsKey("id") ? body.get("id").toString() : "preference-id";
        } catch (Exception e) {
            throw new RuntimeException("Erro ao criar pagamento no Mercado Pago: " + e.getMessage(), e);
        }
    }

    private String obterUrlPagamento(String preferenceId) {
        if (accessToken == null || accessToken.isEmpty()) {
            return "https://www.mercadopago.com.br/checkout/v1/redirect?pref_id=" + preferenceId;
        }

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + accessToken);

            HttpEntity<Void> request = new HttpEntity<>(headers);
            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                    MERCADOPAGO_API_URL + "/checkout/preferences/" + preferenceId,
                    HttpMethod.GET,
                    request,
                    new ParameterizedTypeReference<Map<String, Object>>() {}
            );

            Map<String, Object> body = response.getBody();
            if (body != null && body.containsKey("init_point")) {
                return body.get("init_point").toString();
            }
            return "https://www.mercadopago.com.br/checkout/v1/redirect?pref_id=" + preferenceId;
        } catch (Exception e) {
            return "https://www.mercadopago.com.br/checkout/v1/redirect?pref_id=" + preferenceId;
        }
    }

    public void processarWebhook(WebhookPagamentoDto dto) {
        if (!"payment".equals(dto.type()) || dto.data() == null) {
            return;
        }

        String paymentId = dto.data().id();
        Optional<PagamentoEntity> pagamentoOpt = buscarPagamentoPorGatewayId(paymentId);

        if (pagamentoOpt.isPresent()) {
            PagamentoEntity pagamento = pagamentoOpt.get();
            atualizarStatusPagamento(pagamento, dto.action());
        }
    }

    private void atualizarStatusPagamento(PagamentoEntity pagamento, String action) {
        if ("payment.updated".equals(action) || "payment.created".equals(action)) {
            verificarStatusNoMercadoPago(pagamento);
        }
    }

    private void verificarStatusNoMercadoPago(PagamentoEntity pagamento) {
        pagamento.setStatus(PagamentoEntity.StatusPagamento.APROVADO);
        pagamentoRepository.save(pagamento);

        UUID geracaoId = pagamento.getGeracaoDocumentoId();
        GeracaoDocumentoEntity geracao = buscarGeracao(geracaoId);
        geracao.setPago(true);
        geracaoRepository.save(geracao);
    }

    private Optional<PagamentoEntity> buscarPagamentoPorGatewayId(String gatewayId) {
        return pagamentoRepository.findAll().stream()
                .filter(p -> gatewayId.equals(p.getGatewayId()))
                .findFirst();
    }

    private GeracaoDocumentoEntity buscarGeracao(UUID id) {
        return geracaoRepository.findById(id)
                .orElseThrow(() -> new GeracaoNaoEncontradaException(id));
    }

    private void validarGeracaoExiste(UUID geracaoId) {
        if (!geracaoRepository.existsById(geracaoId)) {
            throw new GeracaoNaoEncontradaException(geracaoId);
        }
    }

}
