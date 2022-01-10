package qna.domain.model;

import java.util.Objects;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import qna.CannotDeleteException;
import qna.NotFoundException;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;

    @Embedded
    private Contents contents;

    @Builder
    public Answer(Long id, User writer, Question question, String contents) {
        if (Objects.isNull(question)) {
            throw new NotFoundException();
        }
        this.id = id;
        this.question = question;
        this.contents = Contents.builder()
            .writer(writer)
            .contents(contents)
            .build();
    }

    public void toQuestion(Question question) {
        this.question = question;
    }

    public void delete(User loginUser, DeleteHistories deleteHistories) throws CannotDeleteException {
        contents.validateOwner(loginUser, "다른 사람이 쓴 답변이 있어 삭제할 수 없습니다.");
        contents.changeDeleted(true);
        deleteHistories.addAnswerDeleteHistories(loginUser, this);
    }
}
