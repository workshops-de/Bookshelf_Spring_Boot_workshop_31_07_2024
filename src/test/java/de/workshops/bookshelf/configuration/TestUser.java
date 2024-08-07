package de.workshops.bookshelf.configuration;

import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

public class TestUser {

    private final User user;
    private final String password;
    private final UserRepository userRepository;

    private TestUser(User user, String password, UserRepository userRepository) {
        this.user = user;
        this.password = password;
        this.userRepository = userRepository;
    }

    public String getUsername() {
        return user.getUsername();
    }

    public String getPassword() {
        return password;
    }

    public void delete() {
        userRepository.delete(user);
    }

    public static TestUser create(ApplicationContext context) {
        UserRepository userRepository = context.getBean(UserRepository.class);
        PasswordEncoder passwordEncoder = context.getBean(PasswordEncoder.class);

        String username = "testuser" + System.currentTimeMillis();
        String password = UUID.randomUUID().toString();
        User user = userRepository.save(new User(username, passwordEncoder.encode(password)));

        return new TestUser(user, password, userRepository);
    }
}
