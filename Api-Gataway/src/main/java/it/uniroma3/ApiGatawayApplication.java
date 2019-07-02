package it.uniroma3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;



@SpringBootApplication
@EnableZuulProxy
public class ApiGatawayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiGatawayApplication.class, args);
    }

}

