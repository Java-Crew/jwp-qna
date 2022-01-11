package qna.domain.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import qna.domain.model.Question;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    @Query("select q from Question q where q.contents.deleted = false")
    List<Question> findByDeletedFalse();

    @Query("select q from Question q where q.id = ?1 and q.contents.deleted = false")
    Optional<Question> findByIdAndDeletedFalse(Long id);
}
