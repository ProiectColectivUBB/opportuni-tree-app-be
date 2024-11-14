package org.example.RestServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "org.example")
public class RestServer {
    public static void main(String[] args) {
        SpringApplication.run(RestServer.class, "--server.port=10333");
    }
}
