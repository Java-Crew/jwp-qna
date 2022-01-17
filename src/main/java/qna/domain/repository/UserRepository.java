package qna.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import qna.domain.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserId(String userId);
}
