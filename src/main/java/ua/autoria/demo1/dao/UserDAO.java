package ua.autoria.demo1.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.autoria.demo1.models.Role;
import ua.autoria.demo1.models.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserDAO extends JpaRepository<User, Long> {
    Optional<User> findUserByEmail(String email);
    Optional<User> findUserById(long id);
    void deleteUserById(long id);
    List<User> getAllUsersByRole(Role role);
}
