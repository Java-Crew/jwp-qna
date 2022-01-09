package qna.domain.model;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import qna.NotFoundException;
import qna.UnAuthorizedException;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id")
    private User writer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;

    private String contents;

    private boolean deleted = false;

    @Builder
    public Answer(Long id, User writer, Question question, String contents) {
        this.id = id;
        if (Objects.isNull(writer)) {
            throw new UnAuthorizedException();
        }
        if (Objects.isNull(question)) {
            throw new NotFoundException();
        }
        this.writer = writer;
        this.question = question;
        this.contents = contents;
    }

    public boolean isOwner(User writer) {
        return this.writer.matchUserId(writer.getUserId());
    }

    public void toQuestion(Question question) {
        this.question = question;
    }

    public void changeDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
