package qna.domain;

import javax.persistence.PreUpdate;

import qna.common.util.BeanUtil;
import qna.repository.DeleteHistoryRepository;

import java.util.Locale;

public class ContentListener {

    @PreUpdate
    public void preUpdate(Object o) {
        DeleteHistoryRepository deleteHistoryRepository = BeanUtil.getBean(DeleteHistoryRepository.class);
        Content content = (Content) o;

        ContentType contentType = ContentType.valueOf(content.getClass().getSimpleName().toUpperCase(Locale.ROOT));
        DeleteHistory deleteHistory = new DeleteHistory(contentType, content.getId(), content.getWriter().getId());

        deleteHistoryRepository.save(deleteHistory);
    }

}
