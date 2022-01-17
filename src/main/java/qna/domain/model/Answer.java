package qna.domain.model;

import static qna.exception.ErrorCode.CANNOT_DELETE_ANSWER;
import static qna.exception.ErrorCode.NOT_FOUND_CONTENTS;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
public class Answer extends Post {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;

    @Builder
    public Answer(Long id, User writer, Question question, String contents) {
        super(id, writer, contents);
        if (Objects.isNull(question)) {
            throw new CustomException(NOT_FOUND_CONTENTS);
        }
        this.question = question;
    }

    public void toQuestion(Question question) {
        this.question = question;
    }

    @Override
    public void validateDelete(User loginUser) {
        if (!isOwner(loginUser)) {
            throw new CustomException(CANNOT_DELETE_ANSWER);
        }
    }
}
