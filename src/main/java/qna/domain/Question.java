package qna.domain;

import java.util.Collections;
import java.util.Objects;
import javax.persistence.*;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import qna.exception.ExceptionWithMessageAndCode;

@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@OnDelete(action = OnDeleteAction.CASCADE)
@PrimaryKeyJoinColumn(foreignKey = @ForeignKey(name = "question_fk_id"))
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

    @Override
    public void validateDelete(User user) {
        if (!isOwner(user) || user.isGuestUser()) {
            throw ExceptionWithMessageAndCode.UNAUTHORIZED_FOR_QUESTION.getException();
        }
        answers.validateDelete(user);
    }

    public void addAnswer(Answer answer) {
        answer.changeQuestion(this);
        answers.addAnswer(answer);
    }

    public Answers getAnswers() {
        return answers;
    }
}
