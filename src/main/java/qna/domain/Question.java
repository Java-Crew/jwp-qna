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
import qna.common.domain.BaseTimeEntity;
import qna.exception.ExceptionWithMessageAndCode;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Question extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String title;

    @Lob
    private String contents;

    @ManyToOne
    private User writer;

    @Embedded
    private Answers answers;

    @Column(nullable = false)
    private boolean deleted = false;

    @Builder
    public Question(Long id, String title, String contents, Answers answers, User writer, boolean deleted) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.writer = writer;
        this.deleted = deleted;

        if (Objects.isNull(answers)) {
            this.answers = new Answers(Collections.emptyList());
        }
    }

    public void delete(User user) {
        if (!isOwner(user)) {
            throw ExceptionWithMessageAndCode.UNAUTHORIZED_FOR_QUESTION.getException();
        }

        answers.validateDeleteAnswers(user);
        deleted = true;
        answers = answers.deleteAll();
    }

    private boolean isOwner(User writer) {
        return this.writer.equals(writer);
    }

    public void addAnswer(Answer answer) {
        answer.changeQuestion(this);
        answers.addAnswer(answer);
    }
}
