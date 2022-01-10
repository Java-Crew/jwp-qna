package qna.domain.model;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import qna.CannotDeleteException;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Embedded
    private Contents contents;

    @Builder
    public Question(Long id, String title, User writer, String contents) {
        this.id = id;
        this.title = title;
        this.contents = Contents.builder()
            .writer(writer)
            .contents(contents)
            .build();
    }

    public void addAnswer(Answer answer) {
        answer.toQuestion(this);
    }

    public void delete(User loginUser, DeleteHistories deleteHistories) throws CannotDeleteException {
        contents.validateOwner(loginUser, "질문을 삭제할 권한이 없습니다.");
        contents.changeDeleted(true);
        deleteHistories.addQuestionDeleteHistory(loginUser, this);
    }
}
