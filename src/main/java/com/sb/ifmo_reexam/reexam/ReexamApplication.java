package com.sb.ifmo_reexam.reexam;

import com.sb.ifmo_reexam.reexam.config.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class ReexamApplication {
    public static void main(String[] args) {
        SpringApplication.run(ReexamApplication.class, args);
    }
}
