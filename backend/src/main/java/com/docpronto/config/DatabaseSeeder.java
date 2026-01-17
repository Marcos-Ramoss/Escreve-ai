package com.docpronto.config;

import com.docpronto.domain.entity.DocumentoEntity;
import com.docpronto.repository.DocumentoRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class DatabaseSeeder {

    private final DocumentoRepository documentoRepository;

    public DatabaseSeeder(DocumentoRepository documentoRepository) {
        this.documentoRepository = documentoRepository;
    }

    @PostConstruct
    public void popularDocumentos() {
        if (documentoRepository.count() > 0) {
            return;
        }

        documentoRepository.save(criarDeclaracaoSimples());
        documentoRepository.save(criarDeclaracaoResidencia());
        documentoRepository.save(criarDeclaracaoRenda());
        documentoRepository.save(criarAutorizacaoTerceiros());
        documentoRepository.save(criarCartaSolicitacao());
        documentoRepository.save(criarCartaReclamacao());
        documentoRepository.save(criarCartaPedidoFormal());
        documentoRepository.save(criarTermoResponsabilidade());
        documentoRepository.save(criarContratoPrestacaoServico());
        documentoRepository.save(criarProcuracaoSimples());
    }

    private DocumentoEntity criarDeclaracaoSimples() {
        DocumentoEntity doc = new DocumentoEntity();
        doc.setNome("Declaração Simples");
        doc.setDescricao("Declaração genérica para uso pessoal ou institucional");
        doc.setTemplateHtml("<!DOCTYPE html><html><head><meta charset='UTF-8'><style>body{font-family:'Times New Roman',serif;font-size:12pt;line-height:1.6;margin:60px 80px;text-align:justify;color:#000}.titulo{text-align:center;font-size:14pt;font-weight:bold;margin-bottom:30px;text-transform:uppercase}.conteudo{margin:20px 0;text-indent:0}.dados-pessoais{margin:15px 0}.assinatura{margin-top:50px;text-align:right}.linha-assinatura{border-top:1px solid #000;width:300px;margin:40px auto 5px;padding-top:5px;text-align:center}</style></head><body><div class='titulo'>Declaração</div><div class='conteudo'><p>Eu, <strong>{{nome}}</strong>, portador(a) do CPF nº <strong>{{cpf}}</strong>, declaro para os devidos fins que {{declaracao}}.</p></div><div class='conteudo'><p>Local e data: {{cidade}}, {{data}}.</p></div><div class='assinatura'><div class='linha-assinatura'></div><p>{{nome}}</p><p>CPF: {{cpf}}</p></div></body></html>");
        return doc;
    }

    private DocumentoEntity criarDeclaracaoResidencia() {
        DocumentoEntity doc = new DocumentoEntity();
        doc.setNome("Declaração de Residência");
        doc.setDescricao("Declaração comprobatória de endereço residencial");
        doc.setTemplateHtml("<!DOCTYPE html><html><head><meta charset='UTF-8'><style>body{font-family:'Times New Roman',serif;font-size:12pt;line-height:1.6;margin:60px 80px;text-align:justify;color:#000}.titulo{text-align:center;font-size:14pt;font-weight:bold;margin-bottom:30px;text-transform:uppercase}.conteudo{margin:20px 0;text-indent:0}.endereco{margin:15px 0;padding:10px;background-color:#f9f9f9;border-left:3px solid #333}.assinatura{margin-top:50px;text-align:right}.linha-assinatura{border-top:1px solid #000;width:300px;margin:40px auto 5px;padding-top:5px;text-align:center}</style></head><body><div class='titulo'>Declaração de Residência</div><div class='conteudo'><p>Eu, <strong>{{nome}}</strong>, portador(a) do CPF nº <strong>{{cpf}}</strong>, declaro para os devidos fins que resido no endereço abaixo:</p></div><div class='endereco'><p><strong>Endereço:</strong> {{endereco}}</p><p><strong>Cidade:</strong> {{cidade}} - {{estado}}</p><p><strong>CEP:</strong> {{cep}}</p></div><div class='conteudo'><p>Declaro ainda que as informações acima são verdadeiras e assumo total responsabilidade pela veracidade dos dados apresentados.</p></div><div class='conteudo'><p>Local e data: {{cidade}}, {{data}}.</p></div><div class='assinatura'><div class='linha-assinatura'></div><p>{{nome}}</p><p>CPF: {{cpf}}</p></div></body></html>");
        return doc;
    }

    private DocumentoEntity criarDeclaracaoRenda() {
        DocumentoEntity doc = new DocumentoEntity();
        doc.setNome("Declaração de Renda");
        doc.setDescricao("Declaração comprobatória de renda mensal");
        doc.setTemplateHtml("<!DOCTYPE html><html><head><meta charset='UTF-8'><style>body{font-family:'Times New Roman',serif;font-size:12pt;line-height:1.6;margin:60px 80px;text-align:justify;color:#000}.titulo{text-align:center;font-size:14pt;font-weight:bold;margin-bottom:30px;text-transform:uppercase}.conteudo{margin:20px 0;text-indent:0}.dados-renda{margin:15px 0;padding:10px;background-color:#f9f9f9;border-left:3px solid #333}.valor{font-size:14pt;font-weight:bold;color:#0066cc}.assinatura{margin-top:50px;text-align:right}.linha-assinatura{border-top:1px solid #000;width:300px;margin:40px auto 5px;padding-top:5px;text-align:center}</style></head><body><div class='titulo'>Declaração de Renda</div><div class='conteudo'><p>Eu, <strong>{{nome}}</strong>, portador(a) do CPF nº <strong>{{cpf}}</strong>, declaro para os devidos fins que minha situação financeira é a seguinte:</p></div><div class='dados-renda'><p><strong>Renda Mensal:</strong> <span class='valor'>R$ {{renda}}</span></p><p><strong>Fonte da Renda:</strong> {{fonte_renda}}</p></div><div class='conteudo'><p>Declaro ainda que as informações acima são verdadeiras e assumo total responsabilidade pela veracidade dos dados apresentados.</p></div><div class='conteudo'><p>Local e data: {{cidade}}, {{data}}.</p></div><div class='assinatura'><div class='linha-assinatura'></div><p>{{nome}}</p><p>CPF: {{cpf}}</p></div></body></html>");
        return doc;
    }

    private DocumentoEntity criarAutorizacaoTerceiros() {
        DocumentoEntity doc = new DocumentoEntity();
        doc.setNome("Autorização para Terceiros");
        doc.setDescricao("Autorização para que terceiros realizem ações em seu nome");
        doc.setTemplateHtml("<!DOCTYPE html><html><head><meta charset='UTF-8'><style>body{font-family:'Times New Roman',serif;font-size:12pt;line-height:1.6;margin:60px 80px;text-align:justify;color:#000}.titulo{text-align:center;font-size:14pt;font-weight:bold;margin-bottom:30px;text-transform:uppercase}.conteudo{margin:20px 0;text-indent:0}.partes{margin:20px 0;padding:15px;background-color:#f0f0f0;border:1px solid #ccc}.parte{margin:10px 0}.assinatura{margin-top:50px;text-align:right}.linha-assinatura{border-top:1px solid #000;width:300px;margin:40px auto 5px;padding-top:5px;text-align:center}</style></head><body><div class='titulo'>Autorização</div><div class='conteudo'><p>Eu, <strong>{{nome_autorizante}}</strong>, portador(a) do CPF nº <strong>{{cpf_autorizante}}</strong>, autorizo expressamente:</p></div><div class='partes'><div class='parte'><p><strong>Autorizado:</strong> {{nome_autorizado}}</p><p><strong>CPF:</strong> {{cpf_autorizado}}</p></div></div><div class='conteudo'><p>A realizar a seguinte ação em meu nome: <strong>{{acao_autorizada}}</strong>.</p></div><div class='conteudo'><p>Esta autorização é válida para os fins acima especificados e permanece em vigor até sua revogação expressa.</p></div><div class='conteudo'><p>Local e data: {{cidade}}, {{data}}.</p></div><div class='assinatura'><div class='linha-assinatura'></div><p>{{nome_autorizante}}</p><p>CPF: {{cpf_autorizante}}</p></div></body></html>");
        return doc;
    }

    private DocumentoEntity criarCartaSolicitacao() {
        DocumentoEntity doc = new DocumentoEntity();
        doc.setNome("Carta de Solicitação");
        doc.setDescricao("Carta formal para solicitar serviços, documentos ou informações");
        doc.setTemplateHtml("<!DOCTYPE html><html><head><meta charset='UTF-8'><style>body{font-family:'Times New Roman',serif;font-size:12pt;line-height:1.8;margin:60px 80px;color:#000}.cabecalho{text-align:right;margin-bottom:30px}.destinatario{margin:20px 0}.saudacao{margin:20px 0}.corpo{margin:20px 0;text-align:justify;text-indent:1.5cm}.fechamento{margin-top:30px}.assinatura{margin-top:50px;text-align:right}.linha-assinatura{border-top:1px solid #000;width:300px;margin:40px auto 5px;padding-top:5px;text-align:center}</style></head><body><div class='cabecalho'><p>{{cidade}}, {{data}}.</p></div><div class='destinatario'><p>Ao(à) {{destinatario}}</p><p>{{cargo_destinatario}}</p><p>{{empresa_instituicao}}</p></div><div class='saudacao'><p>Prezado(a) Senhor(a),</p></div><div class='corpo'><p>Venho por meio desta solicitar {{objeto_solicitacao}}.</p><p>{{detalhes_solicitacao}}</p></div><div class='fechamento'><p>Atenciosamente,</p></div><div class='assinatura'><div class='linha-assinatura'></div><p>{{nome_remetente}}</p><p>CPF: {{cpf_remetente}}</p></div></body></html>");
        return doc;
    }

    private DocumentoEntity criarCartaReclamacao() {
        DocumentoEntity doc = new DocumentoEntity();
        doc.setNome("Carta de Reclamação");
        doc.setDescricao("Carta formal para registrar reclamação sobre produtos ou serviços");
        doc.setTemplateHtml("<!DOCTYPE html><html><head><meta charset='UTF-8'><style>body{font-family:'Times New Roman',serif;font-size:12pt;line-height:1.8;margin:60px 80px;color:#000}.cabecalho{text-align:right;margin-bottom:30px}.destinatario{margin:20px 0}.saudacao{margin:20px 0}.corpo{margin:20px 0;text-align:justify;text-indent:1.5cm}.reclamacao{padding:15px;background-color:#fff3cd;border-left:4px solid #ffc107;margin:20px 0}.fechamento{margin-top:30px}.assinatura{margin-top:50px;text-align:right}.linha-assinatura{border-top:1px solid #000;width:300px;margin:40px auto 5px;padding-top:5px;text-align:center}</style></head><body><div class='cabecalho'><p>{{cidade}}, {{data}}.</p></div><div class='destinatario'><p>Ao(à) {{destinatario}}</p><p>{{empresa_instituicao}}</p></div><div class='saudacao'><p>Prezado(a) Senhor(a),</p></div><div class='corpo'><p>Venho por meio desta apresentar reclamação sobre:</p></div><div class='reclamacao'><p><strong>Objeto da Reclamação:</strong> {{objeto_reclamacao}}</p><p><strong>Detalhes:</strong> {{detalhes_reclamacao}}</p></div><div class='corpo'><p>Solicito que sejam tomadas as devidas providências para resolver a situação apresentada.</p></div><div class='fechamento'><p>Atenciosamente,</p></div><div class='assinatura'><div class='linha-assinatura'></div><p>{{nome_remetente}}</p><p>CPF: {{cpf_remetente}}</p><p>Contato: {{contato}}</p></div></body></html>");
        return doc;
    }

    private DocumentoEntity criarCartaPedidoFormal() {
        DocumentoEntity doc = new DocumentoEntity();
        doc.setNome("Carta de Pedido Formal");
        doc.setDescricao("Carta formal para fazer pedidos ou requerimentos");
        doc.setTemplateHtml("<!DOCTYPE html><html><head><meta charset='UTF-8'><style>body{font-family:'Times New Roman',serif;font-size:12pt;line-height:1.8;margin:60px 80px;color:#000}.cabecalho{text-align:right;margin-bottom:30px}.destinatario{margin:20px 0}.saudacao{margin:20px 0}.corpo{margin:20px 0;text-align:justify;text-indent:1.5cm}.pedido{padding:15px;background-color:#e7f3ff;border-left:4px solid #0066cc;margin:20px 0}.fechamento{margin-top:30px}.assinatura{margin-top:50px;text-align:right}.linha-assinatura{border-top:1px solid #000;width:300px;margin:40px auto 5px;padding-top:5px;text-align:center}</style></head><body><div class='cabecalho'><p>{{cidade}}, {{data}}.</p></div><div class='destinatario'><p>Ao(à) {{destinatario}}</p><p>{{cargo_destinatario}}</p><p>{{empresa_instituicao}}</p></div><div class='saudacao'><p>Prezado(a) Senhor(a),</p></div><div class='corpo'><p>Venho por meio desta formalizar pedido de:</p></div><div class='pedido'><p><strong>Objeto do Pedido:</strong> {{objeto_pedido}}</p><p><strong>Justificativa:</strong> {{justificativa_pedido}}</p></div><div class='corpo'><p>Desde já, agradeço a atenção dispensada.</p></div><div class='fechamento'><p>Atenciosamente,</p></div><div class='assinatura'><div class='linha-assinatura'></div><p>{{nome_remetente}}</p><p>CPF: {{cpf_remetente}}</p></div></body></html>");
        return doc;
    }

    private DocumentoEntity criarTermoResponsabilidade() {
        DocumentoEntity doc = new DocumentoEntity();
        doc.setNome("Termo de Responsabilidade");
        doc.setDescricao("Termo para assumir responsabilidade sobre bens, serviços ou situações");
        doc.setTemplateHtml("<!DOCTYPE html><html><head><meta charset='UTF-8'><style>body{font-family:'Times New Roman',serif;font-size:12pt;line-height:1.6;margin:60px 80px;text-align:justify;color:#000}.titulo{text-align:center;font-size:16pt;font-weight:bold;margin-bottom:40px;text-transform:uppercase;letter-spacing:2px;border-bottom:2px solid #000;padding-bottom:10px}.conteudo{margin:20px 0;text-indent:0}.objeto{padding:15px;background-color:#fff3cd;border:2px solid #ffc107;margin:20px 0;border-radius:5px}.compromissos{margin:20px 0;padding-left:20px}.assinatura{margin-top:60px;text-align:right}.linha-assinatura{border-top:1px solid #000;width:300px;margin:40px auto 5px;padding-top:5px;text-align:center}</style></head><body><div class='titulo'>Termo de Responsabilidade</div><div class='conteudo'><p>Eu, <strong>{{nome_responsavel}}</strong>, portador(a) do CPF nº <strong>{{cpf_responsavel}}</strong>, declaro assumir total responsabilidade sobre:</p></div><div class='objeto'><p><strong>{{objeto_responsabilidade}}</strong></p></div><div class='conteudo'><p>Comprometo-me a:</p><div class='compromissos'><p>{{compromissos}}</p></div></div><div class='conteudo'><p>Estou ciente de que serei responsabilizado(a) civil e criminalmente por qualquer dano ou prejuízo decorrente do descumprimento deste termo.</p></div><div class='conteudo'><p>Local e data: {{cidade}}, {{data}}.</p></div><div class='assinatura'><div class='linha-assinatura'></div><p>{{nome_responsavel}}</p><p>CPF: {{cpf_responsavel}}</p></div></body></html>");
        return doc;
    }

    private DocumentoEntity criarContratoPrestacaoServico() {
        DocumentoEntity doc = new DocumentoEntity();
        doc.setNome("Contrato de Prestação de Serviço");
        doc.setDescricao("Contrato simples para prestação de serviços autônomos");
        doc.setTemplateHtml("<!DOCTYPE html><html><head><meta charset='UTF-8'><style>body{font-family:'Times New Roman',serif;font-size:12pt;line-height:1.6;margin:60px 80px;text-align:justify;color:#000}.titulo{text-align:center;font-size:16pt;font-weight:bold;margin-bottom:40px;text-transform:uppercase;letter-spacing:2px;border-bottom:2px solid #000;padding-bottom:10px}.clausula{margin:20px 0;text-indent:0}.clausula-titulo{font-weight:bold;margin-bottom:5px}.partes{margin:25px 0;padding:20px;background-color:#f9f9f9;border:1px solid #ddd}.parte{margin:15px 0;padding:10px;background-color:#fff;border-left:3px solid #0066cc}.valor{font-size:14pt;font-weight:bold;color:#0066cc}.assinaturas{margin-top:60px;display:flex;justify-content:space-around}.assinatura-box{text-align:center;width:300px}.linha-assinatura{border-top:1px solid #000;width:250px;margin:40px auto 5px;padding-top:5px}</style></head><body><div class='titulo'>Contrato de Prestação de Serviços</div><div class='partes'><div class='parte'><div class='clausula-titulo'>CONTRATANTE:</div><p><strong>{{nome_contratante}}</strong>, portador(a) do CPF nº <strong>{{cpf_contratante}}</strong>.</p></div><div class='parte'><div class='clausula-titulo'>CONTRATADO:</div><p><strong>{{nome_contratado}}</strong>, portador(a) do CPF nº <strong>{{cpf_contratado}}</strong>.</p></div></div><div class='clausula'><div class='clausula-titulo'>CLÁUSULA PRIMEIRA - DO OBJETO:</div><p>O presente contrato tem por objeto a prestação de serviços de <strong>{{tipo_servico}}</strong>.</p></div><div class='clausula'><div class='clausula-titulo'>CLÁUSULA SEGUNDA - DO VALOR:</div><p>O valor total dos serviços é de <span class='valor'>R$ {{valor_total}}</span>.</p></div><div class='clausula'><div class='clausula-titulo'>CLÁUSULA TERCEIRA - DO PRAZO:</div><p>O prazo para execução dos serviços é de <strong>{{prazo_execucao}}</strong>.</p></div><div class='clausula'><p>Local e data: {{cidade}}, {{data}}.</p></div><div class='assinaturas'><div class='assinatura-box'><div class='linha-assinatura'></div><p>Contratante</p><p>{{nome_contratante}}</p><p>CPF: {{cpf_contratante}}</p></div><div class='assinatura-box'><div class='linha-assinatura'></div><p>Contratado</p><p>{{nome_contratado}}</p><p>CPF: {{cpf_contratado}}</p></div></div></body></html>");
        return doc;
    }

    private DocumentoEntity criarProcuracaoSimples() {
        DocumentoEntity doc = new DocumentoEntity();
        doc.setNome("Procuração Simples");
        doc.setDescricao("Procuração para representação em atos específicos");
        doc.setTemplateHtml("<!DOCTYPE html><html><head><meta charset='UTF-8'><style>body{font-family:'Times New Roman',serif;font-size:12pt;line-height:1.6;margin:60px 80px;text-align:justify;color:#000}.titulo{text-align:center;font-size:16pt;font-weight:bold;margin-bottom:40px;text-transform:uppercase;letter-spacing:2px;border-bottom:2px solid #000;padding-bottom:10px}.conteudo{margin:20px 0;text-indent:0}.partes{margin:25px 0;padding:20px;background-color:#f9f9f9;border:1px solid #ddd}.parte{margin:15px 0;padding:10px;background-color:#fff;border-left:3px solid #0066cc}.poderes{padding:15px;background-color:#e7f3ff;border-left:4px solid #0066cc;margin:20px 0}.assinatura{margin-top:60px;text-align:right}.linha-assinatura{border-top:1px solid #000;width:300px;margin:40px auto 5px;padding-top:5px;text-align:center}</style></head><body><div class='titulo'>Procuração</div><div class='conteudo'><p>Eu, <strong>{{nome_outorgante}}</strong>, portador(a) do CPF nº <strong>{{cpf_outorgante}}</strong>, nomeio e constituo como meu( minha) procurador(a):</p></div><div class='partes'><div class='parte'><p><strong>Procurador(a):</strong> {{nome_procurador}}</p><p><strong>CPF:</strong> {{cpf_procurador}}</p></div></div><div class='conteudo'><p>Com poderes para:</p></div><div class='poderes'><p><strong>{{poderes_procuracao}}</strong></p></div><div class='conteudo'><p>Esta procuração é válida para os fins acima especificados e permanece em vigor até sua revogação expressa.</p></div><div class='conteudo'><p>Local e data: {{cidade}}, {{data}}.</p></div><div class='assinatura'><div class='linha-assinatura'></div><p>{{nome_outorgante}}</p><p>CPF: {{cpf_outorgante}}</p></div></body></html>");
        return doc;
    }
}
