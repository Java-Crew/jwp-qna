package qna.domain.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DeleteHistories {

    private final List<DeleteHistory> deleteHistories = new ArrayList<>();

    public static DeleteHistories empty() {
        return new DeleteHistories();
    }

    public List<DeleteHistory> getDeleteHistories() {
        return Collections.unmodifiableList(deleteHistories);
    }

    public void addQuestionDeleteHistory(User loginUser, Question question) {
        deleteHistories.add(
            DeleteHistory.create(ContentType.QUESTION, question.getId(), loginUser)
        );
    }

    public void addAnswerDeleteHistories(User loginUser, Answer answer) {
        deleteHistories.add(
            DeleteHistory.create(ContentType.ANSWER, answer.getId(), loginUser)
        );
    }
}
