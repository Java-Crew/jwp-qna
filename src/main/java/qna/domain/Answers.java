package qna.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import qna.exception.ExceptionWithMessageAndCode;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Answers {

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<Answer> answerGroup;

    public Answers(List<Answer> answerGroup) {
        this.answerGroup = new ArrayList<>(answerGroup);
    }

    public Answers deleteAll(User user) {
        if (existAnotherWriterOfAnswers(user)) {
            throw ExceptionWithMessageAndCode.CANNOT_DELETE_ANSWERS_WITH_ANOTHER_WRITER.getException();
        }
        for (Answer answer : answerGroup) {
            answer.delete(user);
        }
        return this;
    }

    private boolean existAnotherWriterOfAnswers(User user) {
        return answerGroup.stream()
                .filter(answer -> !answer.isDeleted())
                .anyMatch(answer -> !answer.isOwner(user));
    }

    public void addAnswer(Answer answer) {
        answerGroup.add(answer);
    }

    public List<Answer> getAnswerGroup() {
        return new ArrayList<>(answerGroup);
    }
}
