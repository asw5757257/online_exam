package com.zyw.online_exam.graduation_design;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class GraduationDesignApplication {

    public static void main(String[] args) {
        SpringApplication.run(GraduationDesignApplication.class, args);
    }

}
