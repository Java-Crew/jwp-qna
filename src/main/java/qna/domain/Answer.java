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
public class Answer extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User writer;

    @ManyToOne
    private Question question;

    @Lob
    private String contents;

    @Column(nullable = false)
    private boolean deleted = false;

    @Builder
    public Answer(Long id, User writer, Question question, String contents, boolean deleted) {
        if (Objects.isNull(writer)) {
            throw ExceptionWithMessageAndCode.NOT_EXISTS_WRITER_FOR_ANSWER.getException();
        }

        if (Objects.isNull(question)) {
            throw ExceptionWithMessageAndCode.NOT_FOUND_QUESTION.getException();
        }

        this.id = id;
        this.writer = writer;
        this.question = question;
        this.contents = contents;
        this.deleted = deleted;
    }

    public boolean isOwner(User writer) {
        return this.writer.equals(writer);
    }

    public void changeQuestion(Question question) {
        this.question = question;
    }

    public void changeDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
