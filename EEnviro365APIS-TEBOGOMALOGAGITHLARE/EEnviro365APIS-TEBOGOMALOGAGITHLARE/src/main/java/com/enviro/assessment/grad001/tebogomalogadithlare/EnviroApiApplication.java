package com.enviro.assessment.grad001.tebogomalogadithlare;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan(basePackages = {"com.enviro.assessment.grad001.tebogomalogadithlare.model"})
@SpringBootApplication
@ComponentScan
@EnableJpaRepositories
public class EnviroApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(EnviroApiApplication.class, args);
    }
}
