import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { CardModule } from 'primeng/card';
import { ButtonModule } from 'primeng/button';
import { MessageModule } from 'primeng/message';
import { GeracaoService } from '../../core/services/geracao.service';

@Component({
    selector: 'app-pagamento-sucesso',
    standalone: true,
    imports: [CommonModule, CardModule, ButtonModule, MessageModule],
    templateUrl: './pagamento-sucesso.component.html',
    styleUrl: './pagamento-sucesso.component.scss'
})
export class PagamentoSucessoComponent implements OnInit {
    private route = inject(ActivatedRoute);
    private router = inject(Router);
    private geracaoService = inject(GeracaoService);

    geracaoId: string = '';
    baixando = false;
    erro: string = '';

    ngOnInit(): void {
        this.geracaoId = this.route.snapshot.queryParams['geracaoId'] || '';
        if (!this.geracaoId) {
            this.erro = 'ID de geração não encontrado';
        }
    }

    baixarPdf(): void {
        if (!this.geracaoId) {
            this.erro = 'ID de geração não encontrado';
            return;
        }

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
                this.erro = 'Erro ao baixar PDF. Tente novamente.';
                this.baixando = false;
            }
        });
    }

    voltar(): void {
        this.router.navigate(['/']);
    }
}
