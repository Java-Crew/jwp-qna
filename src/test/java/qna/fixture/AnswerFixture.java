package qna.fixture;

import qna.domain.model.Answer;
import qna.domain.model.Question;
import qna.domain.model.User;

public class AnswerFixture {

    public static final String CONTENTS = "contents";

    private static Long INCREASE_ID = 0L;

    public static Answer getAnswer(User writer, Question question) {
        INCREASE_ID++;
        return Answer.builder()
            .writer(writer)
            .question(question)
            .contents(CONTENTS + INCREASE_ID)
            .build();
    }

    public static Answer getAnswerWithId(User writer, Question question) {
        INCREASE_ID++;
        return Answer.builder()
            .id(INCREASE_ID)
            .writer(writer)
            .question(question)
            .contents(CONTENTS + INCREASE_ID)
            .build();
    }
}
