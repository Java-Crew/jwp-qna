package qna.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.PreUpdate;

import qna.common.util.BeanUtil;
import qna.repository.DeleteHistoryRepository;

public class ContentListener {

    @PreUpdate
    public void preUpdate(Object o) {
        System.out.println("호출");
        DeleteHistoryRepository deleteHistoryRepository = BeanUtil.getBean(DeleteHistoryRepository.class);
        Content content = (Content) o;

        DiscriminatorValue annotation = Content.class.getAnnotation(DiscriminatorValue.class);
        DeleteHistory deleteHistory = new DeleteHistory(ContentType.valueOf(annotation.value()), content.getId(), content.getWriter().getId());

        deleteHistoryRepository.save(deleteHistory);
    }

}
