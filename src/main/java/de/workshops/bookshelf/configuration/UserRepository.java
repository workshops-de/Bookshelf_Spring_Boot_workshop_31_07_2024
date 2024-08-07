package de.workshops.bookshelf.configuration;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends org.springframework.data.repository.Repository<User, Long> {

    Optional<User> findUserByUsername(String username);

    User save(User user);

    void delete(User user);
}
