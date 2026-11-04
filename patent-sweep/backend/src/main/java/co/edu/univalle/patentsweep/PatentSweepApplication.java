package co.edu.univalle.patentsweep;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/** Arranque de la API REST Patent Sweep (DAS DA-02: Java 21 y Spring Boot 3). */
@SpringBootApplication
public class PatentSweepApplication {

    public static void main(String[] args) {
        SpringApplication.run(PatentSweepApplication.class, args);
    }
}
