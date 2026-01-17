import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { environment } from '../../../environments/environment';
import { CriarGeracaoDto, PreviewDocumentoDto } from '../models/geracao.model';

@Injectable({
    providedIn: 'root'
})
export class GeracaoService {
    private http = inject(HttpClient);
    private apiUrl = environment.apiUrl;

    criarGeracao(dto: CriarGeracaoDto): Observable<string> {
        return this.http.post<string>(`${this.apiUrl}/geracoes`, dto).pipe(
            map(response => typeof response === 'string' ? response : String(response))
        );
    }

    obterPreview(id: string): Observable<PreviewDocumentoDto> {
        return this.http.get<PreviewDocumentoDto>(`${this.apiUrl}/geracoes/${id}/preview`);
    }

    gerarPdf(id: string): Observable<Blob> {
        return this.http.get(`${this.apiUrl}/geracoes/${id}/pdf`, {
            responseType: 'blob'
        });
    }
}
