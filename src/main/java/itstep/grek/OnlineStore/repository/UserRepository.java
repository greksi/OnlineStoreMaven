package itstep.grek.OnlineStore.repository;

import itstep.grek.OnlineStore.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
