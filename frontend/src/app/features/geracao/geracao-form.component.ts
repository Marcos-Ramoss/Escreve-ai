import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { InputTextModule } from 'primeng/inputtext';
import { TextareaModule } from 'primeng/textarea';
import { ButtonModule } from 'primeng/button';
import { CardModule } from 'primeng/card';
import { ProgressSpinnerModule } from 'primeng/progressspinner';
import { MessageModule } from 'primeng/message';
import { ToastModule } from 'primeng/toast';
import { DocumentoService } from '../../core/services/documento.service';
import { GeracaoService } from '../../core/services/geracao.service';
import { TemplateFieldExtractor, CampoFormulario } from '../../shared/utils/template-field-extractor.util';
import { MaskUtil } from '../../shared/utils/mask.util';
import { DocumentoDto } from '../../core/models/documento.model';
import { CriarGeracaoDto } from '../../core/models/geracao.model';

@Component({
    selector: 'app-geracao-form',
    standalone: true,
    imports: [
        CommonModule,
        ReactiveFormsModule,
        InputTextModule,
        TextareaModule,
        ButtonModule,
        CardModule,
        ProgressSpinnerModule,
        MessageModule,
        ToastModule
    ],
    templateUrl: './geracao-form.component.html',
    styleUrl: './geracao-form.component.scss'
})
export class GeracaoFormComponent implements OnInit {
    private route = inject(ActivatedRoute);
    private router = inject(Router);
    private fb = inject(FormBuilder);
    private documentoService = inject(DocumentoService);
    private geracaoService = inject(GeracaoService);

    documento: DocumentoDto | null = null;
    campos: CampoFormulario[] = [];
    formulario!: FormGroup;
    loading = false;
    salvando = false;
    erro: string = '';

    ngOnInit(): void {
        const documentoId = this.route.snapshot.paramMap.get('documentoId');
        if (documentoId) {
            this.carregarDocumento(documentoId);
        }
    }

    carregarDocumento(id: string): void {
        this.loading = true;
        this.documentoService.buscarPorId(id).subscribe({
            next: (documento) => {
                this.documento = documento;
                this.extrairCampos(documento);
                this.criarFormulario();
                this.loading = false;
            },
            error: () => {
                this.loading = false;
            }
        });
    }

    extrairCampos(documento: DocumentoDto): void {
        if (!documento.templateHtml) {
            this.erro = 'Template HTML não encontrado';
            return;
        }
        this.campos = TemplateFieldExtractor.extrairCampos(documento.templateHtml);
    }

    criarFormulario(): void {
        const controles: Record<string, any> = {};
        this.campos.forEach(campo => {
            const validators = campo.obrigatorio ? [Validators.required] : [];
            
            if (campo.tipo === 'cpf') {
                validators.push(this.validarCPF);
            }
            
            controles[campo.nome] = ['', validators];
        });
        this.formulario = this.fb.group(controles);
    }

    validarCPF(control: any): { [key: string]: any } | null {
        if (!control.value) return null;
        const cpf = control.value.replace(/\D/g, '');
        if (cpf.length !== 11) return { cpfInvalido: true };
        return MaskUtil.validarCPF(control.value) ? null : { cpfInvalido: true };
    }

    aplicarMascara(event: any, campo: CampoFormulario): void {
        const input = event.target;
        const valor = input.value;
        
        if (campo.tipo === 'cpf' || campo.tipo === 'cep' || campo.tipo === 'phone') {
            const valorMascarado = MaskUtil.aplicarMascara(valor, campo.tipo);
            this.formulario.get(campo.nome)?.setValue(valorMascarado, { emitEvent: false });
        }
    }

    onSubmit(): void {
        if (this.formulario.invalid) {
            this.formulario.markAllAsTouched();
            return;
        }

        this.salvando = true;
        const dados: Record<string, any> = {};
        this.campos.forEach(campo => {
            dados[campo.nome] = this.formulario.get(campo.nome)?.value || '';
        });

        const dto: CriarGeracaoDto = {
            documentoId: this.documento!.id,
            dados
        };

        this.geracaoService.criarGeracao(dto).subscribe({
            next: (geracaoId) => {
                this.router.navigate(['/preview', geracaoId]);
            },
            error: () => {
                this.salvando = false;
            }
        });
    }

    getMensagemErro(campoNome: string): string {
        const control = this.formulario.get(campoNome);
        if (control?.errors?.['required']) {
            return 'Este campo é obrigatório';
        }
        if (control?.errors?.['cpfInvalido']) {
            return 'CPF inválido';
        }
        return 'Campo inválido';
    }

    cancelar(): void {
        this.router.navigate(['/']);
    }
}
