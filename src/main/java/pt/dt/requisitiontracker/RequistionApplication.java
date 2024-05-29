package pt.dt.requisitiontracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import pt.dt.requisitiontracker.configuration.FileStoragePojo;


@EnableConfigurationProperties({FileStoragePojo.class})
@SpringBootApplication
public class RequistionApplication {

    public static void main(String[] args) {
        SpringApplication.run(RequistionApplication.class, args);
    }

}
