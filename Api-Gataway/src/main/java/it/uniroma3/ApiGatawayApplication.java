package it.uniroma3;

import it.uniroma3.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;


@SpringBootApplication
@EnableZuulProxy
public class ApiGatawayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiGatawayApplication.class, args);
    }
}

