package org.example.externalinterfaceservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

//@EnableJpaRepositories({"org.example.repository.api"})
//@SpringBootApplication(scanBasePackages = {"org.example.repository",
// "org.example.service",
// "org.example.controller"})
//exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class},
@EnableJpaRepositories({"org.example.externalinterfaceservice.repositories"})
@SpringBootApplication(scanBasePackages = {
        "org.example.externalinterfaceservice",
        "org.example.common"
})
@EntityScan(
        basePackages = {"org.example.common.model"}
)

public class ExternalApplication {
    public ExternalApplication() {
    }

    public static void main(String[] args) {
        SpringApplication.run(ExternalApplication.class, args);
    }
}