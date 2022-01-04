package qna.domain;

import lombok.Getter;
import lombok.ToString;
import qna.NotFoundException;
import qna.UnAuthorizedException;

import java.util.Objects;

@ToString
@Getter
public class Answer {
    private Long id;
    private Long writerId;
    private Long questionId;
    private String contents;
    private boolean deleted = false;

    public Answer(User writer, Question question, String contents) {
        this(null, writer, question, contents);
    }

    public Answer(Long id, User writer, Question question, String contents) {
        this.id = id;

        if (Objects.isNull(writer)) {
            throw new UnAuthorizedException();
        }

        if (Objects.isNull(question)) {
            throw new NotFoundException();
        }

        this.writerId = writer.getId();
        this.questionId = question.getId();
        this.contents = contents;
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
