package pt.dt.requisitiontracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pt.dt.requisitiontracker.model.Role;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    Role findByRole(String user);

   // Optional<Role> findByName(String role);
    /*
    Optional<Role> findByName(String role);*/
}
