package qna.domain;

import lombok.*;
import qna.NotFoundException;
import qna.UnAuthorizedException;

import javax.persistence.*;
import java.util.Objects;
import qna.common.domain.BaseTimeEntity;

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
            throw new UnAuthorizedException();
        }

        if (Objects.isNull(question)) {
            throw new NotFoundException();
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
