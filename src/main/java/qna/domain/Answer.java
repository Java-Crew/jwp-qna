package qna.domain;

import java.util.Objects;
import javax.persistence.*;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import qna.exception.ExceptionWithMessageAndCode;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@OnDelete(action = OnDeleteAction.CASCADE)
@PrimaryKeyJoinColumn(foreignKey = @ForeignKey(name = "answer_fk_id"))
@Entity
public class Answer extends Content {

    @ManyToOne(fetch = FetchType.LAZY)
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
