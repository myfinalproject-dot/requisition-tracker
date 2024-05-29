package pt.dt.requisitiontracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.dt.requisitiontracker.model.Role;
import pt.dt.requisitiontracker.repository.RoleRepository;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    private RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role createRole(Role role) {
//        Optional<Role> roleOptional = roleRepository.findByName(role.getRole());
//
//        if (roleOptional.isPresent()) {
//            throw new IllegalArgumentException("Role already exists");
//        }

        return roleRepository.save(role);
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }
}

