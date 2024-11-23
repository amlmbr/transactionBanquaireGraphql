package com.example.banque_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "com.example.banque_service.entities")
@EnableJpaRepositories(basePackages = "com.example.banque_service.repositories")
public class BanqueServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(BanqueServiceApplication.class, args);
    }
}