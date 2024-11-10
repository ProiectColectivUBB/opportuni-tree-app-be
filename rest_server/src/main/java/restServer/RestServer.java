package restServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RestServer {
    public static void main(String[] args) {
        SpringApplication.run(RestServer.class, "--server.port=10333");
    }
}
