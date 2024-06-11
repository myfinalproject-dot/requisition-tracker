package pt.dt.requisitiontracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pt.dt.requisitiontracker.model.ForgetPasswordToken;

@Repository
public interface ForgetPasswordRepository extends JpaRepository<ForgetPasswordToken, Long> {
    ForgetPasswordToken findByToken(String token);

}
