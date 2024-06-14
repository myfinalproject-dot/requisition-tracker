package pt.dt.requisitiontracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;
import pt.dt.requisitiontracker.configuration.FileStoragePojo;


@EnableConfigurationProperties({FileStoragePojo.class})
@SpringBootApplication
@EnableScheduling
public class RequistionApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(RequisitionApplication.class);
        app.setDefaultProperties(Collections.singletonMap("server.port", System.getenv("PORT")));
        app.run(args);
        
    }

}
