package com.enigma.futsal_rental;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FutsalRentalApplication {

    public static void main(String[] args) {
        SpringApplication.run(FutsalRentalApplication.class, args);
    }

}
