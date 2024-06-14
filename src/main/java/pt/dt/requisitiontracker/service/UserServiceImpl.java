package pt.dt.requisitiontracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pt.dt.requisitiontracker.model.Role;
import pt.dt.requisitiontracker.model.User;
import pt.dt.requisitiontracker.repository.RoleRepository;
import pt.dt.requisitiontracker.repository.TaskRepository;
import pt.dt.requisitiontracker.repository.UserRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private static final String ADMIN="ADMIN";
    private static final String USER="USER";

    private UserRepository userRepository;
    private TaskRepository taskRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           TaskRepository taskRepository,
                           RoleRepository roleRepository,
                           BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public String  createUser(User user) {
        Optional<User> userOptional = Optional.ofNullable(userRepository.findByEmail(user.getEmail()));
        if(userOptional.isPresent()) {
            return "Email already in use";
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        Role userRole = roleRepository.findByRole(USER);
        user.setRoles(new ArrayList<>(Collections.singletonList(userRole)));
        userRepository.save(user);

        return "User created successfully";
    }

    @Override
    public User changeRoleToAdmin(User user) {
        Role adminRole = roleRepository.findByRole(ADMIN);
        user.setRoles(new ArrayList<>(Collections.singletonList(adminRole)));
        return userRepository.save(user);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public boolean isUserEmailPresent(String email) {
        return userRepository.findByEmail(email) != null;
    }
    @Override
    public boolean isUserNamePresent(String name) {
        return userRepository.findByName(name) != null;
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepository.getOne(id);
        user.getTasksOwned().forEach(task -> task.setOwner(null));
        userRepository.delete(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<User> findAllConditions(User signedUser) {

        // Check if the signed-in user is a regular user
        if (signedUser.getRoles().contains(roleRepository.findByRole("USER"))) {
            // Filter HOD based on the user's department
            return userRepository.findAll()
                    .stream()
                    .filter(user -> user.getEmail().contains("hod") && user.getDepartment().contains(signedUser.getDepartment()))
                    .collect(Collectors.toList());
        }
        else if (signedUser.getEmail().contains("hod") && signedUser.getRoles().contains(roleRepository.findByRole("ADMIN"))) {
            return userRepository.findAll()
                    .stream()
                    .filter(user -> user.getEmail().contains("hod") && user.getDepartment().contains(signedUser.getDepartment()) || user.getEmail().contains("accounts")|| user.getEmail().contains("dean") && user.getSchool().contains(signedUser.getSchool()))
                    .collect(Collectors.toList());
        }else if (signedUser.getEmail().contains("dean") && signedUser.getRoles().contains(roleRepository.findByRole("ADMIN"))) {
            return userRepository.findAll()
                    .stream()
                    .filter(user -> user.getEmail().contains("dean") && user.getSchool().contains(signedUser.getSchool())  || user.getEmail().contains("chancellor") || user.getEmail().contains("accounts") )
                    .collect(Collectors.toList());
        }else if (signedUser.getEmail().contains("chancellor") && signedUser.getRoles().contains(roleRepository.findByRole("ADMIN"))) {
            return userRepository.findAll()
                    .stream()
                    .filter(user -> user.getEmail().contains("chancellor")|| user.getEmail().contains("accounts") )
                    .collect(Collectors.toList());
        }else if (signedUser.getEmail().contains("accounts") && signedUser.getRoles().contains(roleRepository.findByRole("ADMIN"))) {
            return userRepository.findAll()
                    .stream()
                    .filter(user -> user.getEmail().contains("chancellor") )
                    .collect(Collectors.toList());
        }
        else {
            return userRepository.findAll();
        }
    }

}

