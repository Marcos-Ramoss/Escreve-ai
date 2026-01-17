import { Routes } from '@angular/router';

export const appRoutes: Routes = [
    {
        path: '',
        loadComponent: () => import('./app/features/home/home.component').then(m => m.HomeComponent)
    },
    {
        path: 'documentos',
        loadComponent: () => import('./app/features/documentos/documentos-list.component').then(m => m.DocumentosListComponent)
    },
    {
        path: 'geracao/:documentoId',
        loadComponent: () => import('./app/features/geracao/geracao-form.component').then(m => m.GeracaoFormComponent)
    },
    {
        path: 'preview/:geracaoId',
        loadComponent: () => import('./app/features/geracao/preview.component').then(m => m.PreviewComponent)
    },
    {
        path: 'pagamento/:geracaoId',
        loadComponent: () => import('./app/features/pagamento/pagamento.component').then(m => m.PagamentoComponent)
    },
    {
        path: 'pagamento/sucesso',
        loadComponent: () => import('./app/features/pagamento/pagamento-sucesso.component').then(m => m.PagamentoSucessoComponent)
    },
    {
        path: '**',
        redirectTo: ''
    }
];
