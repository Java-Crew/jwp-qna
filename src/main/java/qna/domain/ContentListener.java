package qna.domain;

import java.util.Locale;
import javax.persistence.PreRemove;
import qna.common.util.BeanUtil;
import qna.repository.DeleteHistoryRepository;

public class ContentListener {

    @PreRemove
    public void preUpdate(Object o) {
        DeleteHistoryRepository deleteHistoryRepository = BeanUtil.getBean(DeleteHistoryRepository.class);
        Content content = (Content) o;

        ContentType contentType = ContentType.valueOf(content.getClass().getSimpleName().toUpperCase(Locale.ROOT));
        DeleteHistory deleteHistory = new DeleteHistory(contentType, content.getId(), content.getWriter().getId());

        deleteHistoryRepository.save(deleteHistory);
    }

}
