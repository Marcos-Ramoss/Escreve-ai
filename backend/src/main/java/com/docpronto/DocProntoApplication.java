package com.docpronto;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class DocProntoApplication {

    @Value("${server.port:8081}")
    private String port;

    @Value("${server.servlet.context-path:/api}")
    private String contextPath;

    public static void main(String[] args) {
        SpringApplication.run(DocProntoApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void mostrarUrlSwagger() {
        String baseUrl = "http://localhost:" + port + contextPath;
        System.out.println("\n" + "=".repeat(60));
        System.out.println("DocPronto API iniciada com sucesso!");
        System.out.println("=".repeat(60));
        System.out.println("Swagger UI: " + baseUrl + "/swagger-ui.html");
        System.out.println("API Docs:  " + baseUrl + "/api-docs");
        System.out.println("=".repeat(60) + "\n");
    }
}
