package qna.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;
import qna.common.domain.BaseTimeEntity;
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
