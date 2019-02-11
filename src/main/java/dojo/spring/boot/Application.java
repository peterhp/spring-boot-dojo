package dojo.spring.boot;

import dojo.spring.boot.model.Account;
import dojo.spring.boot.service.AccountService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import static java.util.Arrays.asList;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public ApplicationRunner setup(AccountService accountService) {
        return args -> {
            accountService.createAccount(new Account(
                    "admin", "admin", asList("ROLE_USER", "ROLE_ADMIN")));
            accountService.createAccount(new Account(
                    "teacher", "teacher", asList("ROLE_USER", "ROLE_TEACHER")));
            accountService.createAccount(new Account(
                    "student", "student", asList("ROLE_USER", "ROLE_STUDENT")));
            accountService.createAccount(new Account(
                    "user", "password", asList("ROLE_USER")));
        };
    }
}
