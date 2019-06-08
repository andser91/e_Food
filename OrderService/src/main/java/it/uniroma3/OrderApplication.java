package it.uniroma3;


import it.uniroma3.domain.OrderServiceConfiguration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


@SpringBootApplication
@Configuration
@Import({CommonSwaggerConfig.class, OrderServiceConfiguration.class})
public class OrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }

}
