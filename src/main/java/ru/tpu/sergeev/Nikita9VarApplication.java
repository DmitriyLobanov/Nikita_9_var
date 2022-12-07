package ru.tpu.sergeev;

import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.tpu.sergeev.enums.RoleEnum;
import ru.tpu.sergeev.models.User;
import ru.tpu.sergeev.repositories.UserRepository;

@SpringBootApplication
@AllArgsConstructor
public class Nikita9VarApplication implements CommandLineRunner {

    private UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(Nikita9VarApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
//        userRepository.save(new User(null,"1234","$2a$10$EOzoc8eqOMIGNZ0qyzcAGeIvgrS5hswS2DorYzuhuO.ASTEu6TJma", RoleEnum.ROLE_ADMIN ));
    }
}
