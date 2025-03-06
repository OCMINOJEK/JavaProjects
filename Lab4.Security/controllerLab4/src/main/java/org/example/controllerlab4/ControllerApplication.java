//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.example.controllerlab4;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories({"org.example.repositorylab4.api"})
@SpringBootApplication(
        scanBasePackages = {"org.example.repositorylab4", "org.example.servicelab4", "org.example.controllerlab4", "org.example.controllerlab4.SecurityConfig"}
)
@EntityScan(
        basePackages = {"org.example.repositorylab4.model"}
)
public class ControllerApplication {
    public ControllerApplication() {
    }

    public static void main(String[] args) {
        SpringApplication.run(ControllerApplication.class, args);
    }
}
