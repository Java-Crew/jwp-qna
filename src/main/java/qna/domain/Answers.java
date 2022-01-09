package qna.domain;

import java.util.ArrayList;
import java.util.List;

public class Answers {

    private final List<Answer> answerGroup;

    public Answers(List<Answer> answerGroup) {
        this.answerGroup = new ArrayList<>(answerGroup);
    }

    public boolean existAnotherWriterOfAnswers(User user) {
        return answerGroup.stream()
            .anyMatch(answer -> !answer.isOwner(user));
    }

    public List<Answer> getAnswerGroup() {
        return new ArrayList<>(answerGroup);
    }
}
