package qna.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import qna.domain.model.DeleteHistory;

public interface DeleteHistoryRepository extends JpaRepository<DeleteHistory, Long> {
}