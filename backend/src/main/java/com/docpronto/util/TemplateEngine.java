package com.docpronto.util;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.regex.Pattern;

@Component
public class TemplateEngine {

    private static final Pattern PLACEHOLDER_PATTERN = Pattern.compile("\\{\\{([^}]+)\\}\\}");

    public String processarTemplate(String template, Map<String, Object> dados) {
        if (template == null || template.isEmpty()) {
            return template;
        }

        String resultado = template;
        var matcher = PLACEHOLDER_PATTERN.matcher(template);

        while (matcher.find()) {
            String placeholder = matcher.group(0);
            String variavel = matcher.group(1).trim();
            Object valor = dados.get(variavel);
            String substituicao = valor != null ? valor.toString() : "";
            resultado = resultado.replace(placeholder, substituicao);
        }

        return resultado;
    }
}
