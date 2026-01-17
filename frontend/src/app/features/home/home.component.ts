import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { CardModule } from 'primeng/card';
import { ButtonModule } from 'primeng/button';
import { DocumentoService } from '../../core/services/documento.service';
import { DocumentoDto } from '../../core/models/documento.model';

@Component({
    selector: 'app-home',
    standalone: true,
    imports: [CommonModule, RouterModule, CardModule, ButtonModule],
    templateUrl: './home.component.html',
    styleUrl: './home.component.scss'
})
export class HomeComponent implements OnInit {
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

    irParaGeracao(documentoId: string): void {
        // Navegação feita via routerLink no template
    }
}
