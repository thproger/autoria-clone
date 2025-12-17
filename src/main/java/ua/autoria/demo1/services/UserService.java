package ua.autoria.demo1.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ua.autoria.demo1.dao.UserDAO;
import ua.autoria.demo1.models.Role;
import ua.autoria.demo1.models.User;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {
    private UserDAO userDAO;

    public void blockUser(long userId) throws RuntimeException {
        var user = userDAO.findUserById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        user.setBlocked(true);
        userDAO.save(user);
    }

    public void unBlockUser(long userId) throws RuntimeException {
        var user = userDAO.findUserById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        user.setBlocked(false);
        userDAO.save(user);
    }


    public void deleteUser(long userId) throws RuntimeException {
        userDAO.deleteUserById(userId);
    }

    public void createManager(long userId) throws RuntimeException {
        var user = userDAO.findUserById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        user.setRole(Role.ROLE_MANAGER);
        userDAO.save(user);
    }

    public void deleteManager(long userId) throws RuntimeException {
        var user = userDAO.findUserById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        user.setRole(Role.ROLE_CUSTOMER);
        userDAO.save(user);
    }

    public void setPremium(long userId) throws RuntimeException {
        var user = userDAO.findUserById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        user.setPremium(true);
        userDAO.save(user);
    }

    public void setNotPremium(long userId) throws RuntimeException {
        var user = userDAO.findUserById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        user.setPremium(false);
        userDAO.save(user);
    }

    public User getUser(long userId) throws RuntimeException {
        return userDAO.findUserById(userId).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public List<User> getAllManagers() {
        return userDAO.getAllUsersByRole(Role.ROLE_MANAGER);
    }

    public void createSeller(long userId) throws RuntimeException {
        var user = userDAO.findUserById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        user.setRole(Role.ROLE_SELLER);
    }
}
