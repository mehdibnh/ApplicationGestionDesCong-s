package com.esprit.gestiondesconges;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;


@EnableScheduling
@SpringBootApplication
@EnableScheduling
public class GestionDesCongesApplication {

    public static void main(String[] args) {
        SpringApplication.run(GestionDesCongesApplication.class, args);

    }

}
