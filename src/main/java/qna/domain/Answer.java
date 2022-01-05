package qna.domain;

import lombok.*;
import org.hibernate.Hibernate;
import qna.NotFoundException;
import qna.UnAuthorizedException;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long writerId;

    private Long questionId;

    @Lob
    private String contents;

    @Column(nullable = false)
    private boolean deleted = false;

    private LocalDateTime createdAt;

    @Builder
    public Answer(Long id, User writer, Question question, String contents, boolean deleted, LocalDateTime createdAt) {
        if (Objects.isNull(writer)) {
            throw new UnAuthorizedException();
        }

        if (Objects.isNull(question)) {
            throw new NotFoundException();
        }

        this.id = id;
        this.writerId = writer.getId();
        this.questionId = question.getId();
        this.contents = contents;
        this.deleted = deleted;
        this.createdAt = createdAt;
    }

    public boolean isOwner(User writer) {
        return this.writerId.equals(writer.getId());
    }

    public void toQuestion(Question question) {
        this.questionId = question.getId();
    }

    public void changeDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
