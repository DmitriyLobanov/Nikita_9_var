package ru.tpu.sergeev.services;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import ru.tpu.sergeev.dto.UserDto;
import ru.tpu.sergeev.enums.RoleEnum;
import ru.tpu.sergeev.models.User;
import ru.tpu.sergeev.repositories.UserRepository;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    public static Logger slf4jLogger = LoggerFactory.getLogger(FileDataService.class);

    public boolean registerUser(UserDto userDto) {
        Optional<User> userOptional = userRepository.findByUsername(userDto.getUsername());
        if (userOptional.isPresent()) {
          return false;
        }
        slf4jLogger.info("Create new user with username {}", userDto.getUsername());

        User user = new User(null, userDto.getUsername(), bCryptPasswordEncoder.encode(userDto.getPassword()), RoleEnum.ROLE_USER);
        userRepository.save(user);
        return true;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("Пользователь не существует");
        }
        return user.get();
    }
}
