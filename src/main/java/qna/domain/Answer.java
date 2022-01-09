package qna.domain;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import qna.exception.ExceptionWithMessageAndCode;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Answer extends Content {

    @ManyToOne
    private Question question;

    @Builder
    public Answer(Long id, User writer, Question question, String contents, boolean deleted) {
        super(id, contents, writer, deleted);
        if (Objects.isNull(question)) {
            throw ExceptionWithMessageAndCode.NOT_FOUND_QUESTION.getException();
        }

        this.question = question;
    }

    public void changeQuestion(Question question) {
        this.question = question;
    }
}
