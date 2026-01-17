import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { CriarPagamentoDto, UrlPagamentoDto, WebhookPagamentoDto } from '../models/pagamento.model';

@Injectable({
    providedIn: 'root'
})
export class PagamentoService {
    private http = inject(HttpClient);
    private apiUrl = environment.apiUrl;

    criarPagamento(dto: CriarPagamentoDto): Observable<UrlPagamentoDto> {
        return this.http.post<UrlPagamentoDto>(`${this.apiUrl}/pagamentos`, dto);
    }

    processarWebhook(dto: WebhookPagamentoDto): Observable<void> {
        return this.http.post<void>(`${this.apiUrl}/pagamentos/webhook`, dto);
    }
}
