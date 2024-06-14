package pt.dt.requisitiontracker.service;

import pt.dt.requisitiontracker.model.User;

import java.util.List;

public interface UserService {
    String createUser(User user);

    User changeRoleToAdmin(User user);

    List<User> findAll();

    User getUserByEmail(String email);

    User save(User user);

    User findByEmail(String email);

    User getUserById(Long userId);

    void deleteUser(Long id);

    List<User> getAllUsers();

    List<User> findAllConditions(User signedUser);


    boolean isUserEmailPresent(String email);

    boolean isUserNamePresent(String name);

}
