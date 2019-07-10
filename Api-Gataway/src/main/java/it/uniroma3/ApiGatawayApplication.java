package it.uniroma3;

import it.uniroma3.domain.AuthorityRepository;
import it.uniroma3.domain.User;
import it.uniroma3.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;


@SpringBootApplication
@EnableZuulProxy
public class ApiGatawayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiGatawayApplication.class, args);
    }

}

