package qna.domain;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class Question {
    private Long id;
    private String title;
    private String contents;
    private Long writerId;
    private boolean deleted = false;

    public Question(String title, String contents) {
        this(null, title, contents);
    }

    public Question(Long id, String title, String contents) {
        this.id = id;
        this.title = title;
        this.contents = contents;
    }

    public Question writeBy(User writer) {
        this.writerId = writer.getId();
        return this;
    }

    public boolean isOwner(User writer) {
        return this.writerId.equals(writer.getId());
    }

    public void addAnswer(Answer answer) {
        answer.toQuestion(this);
    }

    public void changeDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
