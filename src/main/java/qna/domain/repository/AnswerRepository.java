package qna.domain.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import qna.domain.model.Answer;

public interface AnswerRepository extends JpaRepository<Answer, Long> {

    @Query("select a from Answer a where a.question.id = ?1 and a.contents.deleted = false")
    List<Answer> findByQuestionIdAndDeletedFalse(Long questionId);

    @Query("select a from Answer a where a.id = ?1 and a.contents.deleted = false")
    Optional<Answer> findByIdAndDeletedFalse(Long id);
}
