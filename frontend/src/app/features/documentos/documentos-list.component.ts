import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { CardModule } from 'primeng/card';
import { ButtonModule } from 'primeng/button';
import { ProgressSpinnerModule } from 'primeng/progressspinner';
import { DocumentoService } from '../../core/services/documento.service';
import { DocumentoDto } from '../../core/models/documento.model';

@Component({
    selector: 'app-documentos-list',
    standalone: true,
    imports: [CommonModule, RouterModule, CardModule, ButtonModule, ProgressSpinnerModule],
    templateUrl: './documentos-list.component.html',
    styleUrl: './documentos-list.component.scss'
})
export class DocumentosListComponent implements OnInit {
    private documentoService = inject(DocumentoService);
    documentos: DocumentoDto[] = [];
    loading = false;

    ngOnInit(): void {
        this.carregarDocumentos();
    }

    carregarDocumentos(): void {
        this.loading = true;
        this.documentoService.listarTodos().subscribe({
            next: (documentos) => {
                this.documentos = documentos;
                this.loading = false;
            },
            error: () => {
                this.loading = false;
            }
        });
    }
}
