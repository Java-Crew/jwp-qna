package qna.domain.model;

import static qna.exception.ErrorCode.CANNOT_DELETE_ANSWER;
import static qna.exception.ErrorCode.NOT_FOUND_CONTENTS;

import java.util.Objects;
import javax.persistence.Embedded;
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
import qna.exception.CustomException;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;

    @Embedded
    private Contents contents;

    @Builder
    public Answer(Long id, User writer, Question question, String contents) {
        if (Objects.isNull(question)) {
            throw new CustomException(NOT_FOUND_CONTENTS);
        }
        this.id = id;
        this.question = question;
        this.contents = Contents.builder()
            .writer(writer)
            .contents(contents)
            .build();
    }

    public void toQuestion(Question question) {
        this.question = question;
    }

    public void delete(User loginUser, DeleteHistories deleteHistories) {
        contents.validateOwner(loginUser, CANNOT_DELETE_ANSWER);
        contents.changeDeleted(true);
        deleteHistories.addAnswerDeleteHistories(loginUser, this);
    }
}
