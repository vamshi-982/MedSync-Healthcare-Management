package com.medsync;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MedSyncApplication {
    public static void main(String[] args) {
        SpringApplication.run(MedSyncApplication.class, args);
        System.out.println("✅ MedSync Healthcare System is running!");
        System.out.println("📖 Swagger UI: http://localhost:8080/swagger-ui.html");
    }
}
