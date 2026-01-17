import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { CardModule } from 'primeng/card';
import { ProgressSpinnerModule } from 'primeng/progressspinner';
import { PagamentoService } from '../../core/services/pagamento.service';

@Component({
    selector: 'app-pagamento',
    standalone: true,
    imports: [CommonModule, CardModule, ProgressSpinnerModule],
    templateUrl: './pagamento.component.html',
    styleUrl: './pagamento.component.scss'
})
export class PagamentoComponent implements OnInit {
    private route = inject(ActivatedRoute);
    private pagamentoService = inject(PagamentoService);

    ngOnInit(): void {
        const geracaoId = this.route.snapshot.paramMap.get('geracaoId');
        if (geracaoId) {
            this.iniciarPagamento(geracaoId);
        }
    }

    iniciarPagamento(geracaoId: string): void {
        this.pagamentoService.criarPagamento({ geracaoDocumentoId: geracaoId }).subscribe({
            next: (response) => {
                window.location.href = response.urlPagamento;
            },
            error: () => {
                // Erro será tratado pelo interceptor
            }
        });
    }
}
