import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { DocumentoDto } from '../models/documento.model';

@Injectable({
    providedIn: 'root'
})
export class DocumentoService {
    private http = inject(HttpClient);
    private apiUrl = environment.apiUrl;

    listarTodos(): Observable<DocumentoDto[]> {
        return this.http.get<DocumentoDto[]>(`${this.apiUrl}/documentos`);
    }

    buscarPorId(id: string): Observable<DocumentoDto> {
        return this.http.get<DocumentoDto>(`${this.apiUrl}/documentos/${id}`);
    }
}
