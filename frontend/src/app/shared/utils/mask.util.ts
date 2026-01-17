export class MaskUtil {
    static aplicarMascara(valor: string, tipo: 'cpf' | 'cep' | 'phone' | 'currency'): string {
        if (!valor) return '';

        const apenasNumeros = valor.replace(/\D/g, '');

        switch (tipo) {
            case 'cpf':
                return apenasNumeros
                    .replace(/(\d{3})(\d)/, '$1.$2')
                    .replace(/(\d{3})(\d)/, '$1.$2')
                    .replace(/(\d{3})(\d{1,2})$/, '$1-$2')
                    .substring(0, 14);
            case 'cep':
                return apenasNumeros
                    .replace(/(\d{5})(\d)/, '$1-$2')
                    .substring(0, 9);
            case 'phone':
                if (apenasNumeros.length <= 10) {
                    return apenasNumeros
                        .replace(/(\d{2})(\d)/, '($1) $2')
                        .replace(/(\d{4})(\d)/, '$1-$2')
                        .substring(0, 14);
                } else {
                    return apenasNumeros
                        .replace(/(\d{2})(\d)/, '($1) $2')
                        .replace(/(\d{5})(\d)/, '$1-$2')
                        .substring(0, 15);
                }
            case 'currency':
                const numero = parseFloat(apenasNumeros) / 100;
                return numero.toLocaleString('pt-BR', {
                    minimumFractionDigits: 2,
                    maximumFractionDigits: 2
                });
            default:
                return valor;
        }
    }

    static validarCPF(cpf: string): boolean {
        const apenasNumeros = cpf.replace(/\D/g, '');
        if (apenasNumeros.length !== 11) return false;
        if (/^(\d)\1{10}$/.test(apenasNumeros)) return false;

        let soma = 0;
        for (let i = 0; i < 9; i++) {
            soma += parseInt(apenasNumeros.charAt(i)) * (10 - i);
        }
        let digito = 11 - (soma % 11);
        if (digito >= 10) digito = 0;
        if (digito !== parseInt(apenasNumeros.charAt(9))) return false;

        soma = 0;
        for (let i = 0; i < 10; i++) {
            soma += parseInt(apenasNumeros.charAt(i)) * (11 - i);
        }
        digito = 11 - (soma % 11);
        if (digito >= 10) digito = 0;
        return digito === parseInt(apenasNumeros.charAt(10));
    }
}
