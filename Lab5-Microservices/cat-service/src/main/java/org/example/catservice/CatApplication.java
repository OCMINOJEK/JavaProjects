package org.example.catservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

//@EnableJpaRepositories({"org.example.repository.api"})
//@SpringBootApplication(scanBasePackages = {"org.example.repository",
// "org.example.service",
// "org.example.controller"})
//exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class},
@EnableJpaRepositories({"org.example.catservice.repositories"})
@SpringBootApplication(scanBasePackages = {
        "org.example.catservice",
        "org.example.common"
})
@EntityScan(
        basePackages = {"org.example.common.model"}
)

public class CatApplication {
    public CatApplication() {
    }

    public static void main(String[] args) {
        SpringApplication.run(CatApplication.class, args);
    }
}