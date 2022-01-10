package qna.domain;

import java.util.Objects;
import javax.persistence.*;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import qna.exception.ExceptionWithMessageAndCode;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@PrimaryKeyJoinColumn(foreignKey = @ForeignKey(name = "answer_fk_id"))
@Entity
public class Answer extends Content {

    @ManyToOne
    @JoinColumn(name = "question_id", foreignKey = @ForeignKey(name = "answer_fk_question"))
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
