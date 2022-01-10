package qna.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import qna.domain.Question;

public interface ContentRepository extends JpaRepository<Question, Long> {

}
