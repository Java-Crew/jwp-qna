package qna.domain.model;

import static qna.exception.ErrorCode.CANNOT_DELETE_QUESTION;

import javax.persistence.Entity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import qna.exception.CustomException;

@Getter
@Entity
@ToString
@OnDelete(action = OnDeleteAction.CASCADE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Question extends Post {

    private String title;

    @Builder
    public Question(Long id, String title, User writer, String contents) {
        super(id, writer, contents);
        this.title = title;
    }

    public void addAnswer(Answer answer) {
        answer.toQuestion(this);
    }

    @Override
    public void validateDelete(User loginUser) {
        if (!isOwner(loginUser)) {
            throw new CustomException(CANNOT_DELETE_QUESTION);
        }
    }
}
