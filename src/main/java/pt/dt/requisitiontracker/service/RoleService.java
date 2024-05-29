package pt.dt.requisitiontracker.service;

import pt.dt.requisitiontracker.model.Role;

import java.util.List;

public interface RoleService {
    Role createRole(Role role);

    List<Role> findAll();
}
