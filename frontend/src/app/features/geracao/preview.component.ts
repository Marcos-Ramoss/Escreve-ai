import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DomSanitizer } from '@angular/platform-browser';
import { ActivatedRoute, Router } from '@angular/router';
import { CardModule } from 'primeng/card';
import { ButtonModule } from 'primeng/button';
import { ProgressSpinnerModule } from 'primeng/progressspinner';
import { GeracaoService } from '../../core/services/geracao.service';
import { PagamentoService } from '../../core/services/pagamento.service';
import { PreviewDocumentoDto } from '../../core/models/geracao.model';

@Component({
    selector: 'app-preview',
    standalone: true,
    imports: [CommonModule, CardModule, ButtonModule, ProgressSpinnerModule],
    templateUrl: './preview.component.html',
    styleUrl: './preview.component.scss'
})
export class PreviewComponent implements OnInit {
    private route = inject(ActivatedRoute);
    private router = inject(Router);
    private geracaoService = inject(GeracaoService);
    private pagamentoService = inject(PagamentoService);
    private sanitizer = inject(DomSanitizer);

    preview: PreviewDocumentoDto | null = null;
    htmlSanitizado: any;
    loading = false;
    baixando = false;
    processandoPagamento = false;
    geracaoId: string = '';

    ngOnInit(): void {
        const id = this.route.snapshot.paramMap.get('geracaoId');
        if (id) {
            this.geracaoId = id;
            this.carregarPreview(id);
        }
    }

    carregarPreview(id: string): void {
        this.loading = true;
        this.geracaoService.obterPreview(id).subscribe({
            next: (preview) => {
                this.preview = preview;
                this.htmlSanitizado = this.sanitizer.sanitize(1, preview.html);
                this.loading = false;
            },
            error: () => {
                this.loading = false;
            }
        });
    }

    iniciarPagamento(): void {
        this.processandoPagamento = true;
        this.pagamentoService.criarPagamento({ geracaoDocumentoId: this.geracaoId }).subscribe({
            next: (response) => {
                window.location.href = response.urlPagamento;
            },
            error: () => {
                this.processandoPagamento = false;
            }
        });
    }

    baixarPdf(): void {
        this.baixando = true;
        this.geracaoService.gerarPdf(this.geracaoId).subscribe({
            next: (blob) => {
                const url = window.URL.createObjectURL(blob);
                const link = document.createElement('a');
                link.href = url;
                link.download = 'documento.pdf';
                link.click();
                window.URL.revokeObjectURL(url);
                this.baixando = false;
            },
            error: () => {
                this.baixando = false;
            }
        });
    }

    voltar(): void {
        this.router.navigate(['/']);
    }
}
