package nl.tudelft.sem.template.boat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Boat microservice application.
 */
@SpringBootApplication
@EnableJpaRepositories(basePackages = "nl.tudelft.sem.template.boat.repositories")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
