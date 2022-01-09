package qna.domain;

import java.util.Collections;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import qna.exception.ExceptionWithMessageAndCode;

@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Question extends Content {

    @Column(length = 100, nullable = false)
    private String title;

    @Embedded
    private Answers answers;

    @Builder
    public Question(Long id, String title, String contents, Answers answers, User writer, boolean deleted) {
        super(id, contents, writer, deleted);
        if (Objects.isNull(answers)) {
            this.answers = new Answers(Collections.emptyList());
        }
        this.title = title;
    }

    public void delete(User user) {
        if (!isOwner(user)) {
            throw ExceptionWithMessageAndCode.UNAUTHORIZED_FOR_QUESTION.getException();
        }

        answers.validateDeleteAnswers(user);
        answers.deleteAll();

        changeDeleted(true);
    }

    public void addAnswer(Answer answer) {
        answer.changeQuestion(this);
        answers.addAnswer(answer);
    }
}
