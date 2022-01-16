package qna.domain.model;

import static qna.exception.ErrorCode.CANNOT_DELETE_QUESTION;

import java.time.LocalDateTime;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Question extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Embedded
    private Contents contents;

    @Builder
    public Question(Long id, String title, User writer, String contents, LocalDateTime createdDate,
        LocalDateTime lastModifiedDate) {
        super(createdDate, lastModifiedDate);
        this.id = id;
        this.title = title;
        this.contents = Contents.builder()
            .writer(writer)
            .contents(contents)
            .build();
    }

    public void addAnswer(Answer answer) {
        answer.toQuestion(this);
    }

    public void delete(User loginUser, DeleteHistories deleteHistories) {
        contents.validateOwner(loginUser, CANNOT_DELETE_QUESTION);
        contents.changeDeleted(true);
        deleteHistories.addQuestionDeleteHistory(loginUser, this);
    }
}
