package qna.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import qna.exception.ExceptionWithMessageAndCode;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Answers {

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<Answer> answerGroup;

    public Answers(List<Answer> answerGroup) {
        this.answerGroup = new ArrayList<>(answerGroup);
    }

    public void validateDeleteAnswers(User user) {
        if (existAnotherWriterOfAnswers(user)) {
            throw ExceptionWithMessageAndCode.CANNOT_DELETE_QUESTION_WITH_ANOTHER_WRITER.getException();
        }
    }

    private boolean existAnotherWriterOfAnswers(User user) {
        return answerGroup.stream()
            .filter(answer -> !answer.isDeleted())
            .anyMatch(answer -> !answer.isOwner(user));
    }

    public void deleteAll() {
        for (Answer answer : answerGroup) {
            answer.changeDeleted(true);
        }
    }

    public void addAnswer(Answer answer) {
        answerGroup.add(answer);
    }

    public List<Answer> answerGroup() {
        return new ArrayList<>(answerGroup);
    }
}
