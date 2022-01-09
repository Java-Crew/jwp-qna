package qna.domain;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import qna.common.util.BeanUtil;
import qna.repository.DeleteHistoryRepository;

public class ContentListener {

    @PrePersist
    @PreUpdate
    public void prePersistAndPreUpdate(Object o) {
        DeleteHistoryRepository deleteHistoryRepository = BeanUtil.getBean(DeleteHistoryRepository.class);
        Content content = (Content) o;

        DeleteHistory deleteHistory = new DeleteHistory(content);

        deleteHistoryRepository.save(deleteHistory);
    }

}
