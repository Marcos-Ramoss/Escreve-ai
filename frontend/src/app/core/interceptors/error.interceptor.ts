import { HttpInterceptorFn, HttpErrorResponse } from '@angular/common/http';
import { inject } from '@angular/core';
import { catchError, throwError } from 'rxjs';
import { MessageService } from 'primeng/api';

export const errorInterceptor: HttpInterceptorFn = (req, next) => {
    const messageService = inject(MessageService);

    return next(req).pipe(
        catchError((error: HttpErrorResponse) => {
            let errorMessage = 'Erro ao processar solicitação';

            if (error.error instanceof ErrorEvent) {
                errorMessage = `Erro: ${error.error.message}`;
            } else {
                switch (error.status) {
                    case 400:
                        errorMessage = error.error?.erro || 'Dados inválidos';
                        break;
                    case 404:
                        errorMessage = error.error?.erro || 'Recurso não encontrado';
                        break;
                    case 500:
                        errorMessage = error.error?.erro || 'Erro interno do servidor';
                        break;
                    default:
                        errorMessage = error.error?.erro || `Erro ${error.status}: ${error.statusText}`;
                }
            }

            messageService.add({
                severity: 'error',
                summary: 'Erro',
                detail: errorMessage,
                life: 5000
            });

            return throwError(() => error);
        })
    );
};
