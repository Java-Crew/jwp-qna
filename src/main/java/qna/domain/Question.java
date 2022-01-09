package qna.domain;

import java.util.ArrayList;
import java.util.List;
import lombok.*;

import javax.persistence.*;
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

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Answer> answerGroup;

    @Column(nullable = false)
    private boolean deleted = false;

    @Builder
    public Question(Long id, String title, String contents, User writer, boolean deleted) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.writer = writer;
        this.answerGroup = new ArrayList<>();
        this.deleted = deleted;
    }

    public void delete(User user) {
        validateDeleteQuestion(user);
    }

    private void validateDeleteQuestion(User user) {
        if (!isOwner(user)) {
            throw ExceptionWithMessageAndCode.UNAUTHORIZED_FOR_QUESTION.getException();
        }

        Answers answers = findQuestionsDeletedFalse();
        if (answers.existAnotherWriterOfAnswers(user)) {
            throw ExceptionWithMessageAndCode.CANNOT_DELETE_QUESTION_WITH_ANOTHER_WRITER.getException();
        }
    }

    private boolean isOwner(User writer) {
        return this.writer.equals(writer);
    }

    public void addAnswer(Answer answer) {
        answer.changeQuestion(this);
        answerGroup.add(answer);
    }

    private Answers findQuestionsDeletedFalse() {
        return new Answers(this.answerGroup).findQuestionsDeletedFalse();
    }

    public void changeDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
