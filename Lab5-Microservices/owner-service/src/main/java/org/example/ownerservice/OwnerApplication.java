package org.example.ownerservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

//@EnableJpaRepositories({"org.example.repository.api"})
//@SpringBootApplication(scanBasePackages = {"org.example.repository",
// "org.example.service",
// "org.example.controller"})
//exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class},
@EnableJpaRepositories({"org.example.ownerservice.repositories"})
@SpringBootApplication(scanBasePackages = {
        "org.example.ownerservice",
        "org.example.common"
})
@EntityScan(
        basePackages = {"org.example.common.model"}
)

public class OwnerApplication {
    public OwnerApplication() {
    }

    public static void main(String[] args) {
        SpringApplication.run(OwnerApplication.class, args);
    }
}