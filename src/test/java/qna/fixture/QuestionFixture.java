package qna.fixture;

import qna.domain.model.Question;
import qna.domain.model.User;

public class QuestionFixture {

    public static final String TITLE = "title";
    public static final String CONTENTS = "contents";

    private static Long INCREASE_ID = 0L;

    public static Question getQuestion(User writer) {
        INCREASE_ID++;
        return Question.builder()
            .title(TITLE + INCREASE_ID)
            .writer(writer)
            .contents(CONTENTS + INCREASE_ID)
            .build();
    }

    public static Question getQuestionWithId(User writer) {
        INCREASE_ID++;
        return Question.builder()
            .id(INCREASE_ID)
            .title(TITLE + INCREASE_ID)
            .writer(writer)
            .contents(CONTENTS + INCREASE_ID)
            .build();
    }
}
