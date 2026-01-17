export interface CampoFormulario {
    nome: string;
    label: string;
    tipo: 'text' | 'textarea' | 'date' | 'currency' | 'cpf' | 'cep' | 'phone';
    obrigatorio: boolean;
    placeholder?: string;
}

export class TemplateFieldExtractor {
    static extrairCampos(templateHtml: string): CampoFormulario[] {
        const campos: CampoFormulario[] = [];
        const regex = /\{\{(\w+)\}\}/g;
        const camposEncontrados = new Set<string>();
        let match;

        while ((match = regex.exec(templateHtml)) !== null) {
            const nomeCampo = match[1];
            if (!camposEncontrados.has(nomeCampo)) {
                camposEncontrados.add(nomeCampo);
                campos.push(this.mapearCampo(nomeCampo));
            }
        }

        return campos;
    }

    private static mapearCampo(nome: string): CampoFormulario {
        const label = this.formatarLabel(nome);
        let tipo: CampoFormulario['tipo'] = 'text';
        let placeholder = '';

        if (nome.includes('cpf')) {
            tipo = 'cpf';
            placeholder = '000.000.000-00';
        } else if (nome.includes('cep')) {
            tipo = 'cep';
            placeholder = '00000-000';
        } else if (nome.includes('telefone') || nome.includes('contato')) {
            tipo = 'phone';
            placeholder = '(00) 00000-0000';
        } else if (nome.includes('data')) {
            tipo = 'date';
            placeholder = 'DD/MM/AAAA';
        } else if (nome.includes('renda') || nome.includes('valor')) {
            tipo = 'currency';
            placeholder = '0,00';
        } else if (nome.includes('detalhes') || nome.includes('declaracao') || nome.includes('compromissos') || nome.includes('justificativa') || nome.includes('poderes')) {
            tipo = 'textarea';
        }

        return {
            nome,
            label,
            tipo,
            obrigatorio: true,
            placeholder
        };
    }

    private static formatarLabel(nome: string): string {
        return nome
            .split('_')
            .map(palavra => palavra.charAt(0).toUpperCase() + palavra.slice(1))
            .join(' ');
    }
}
