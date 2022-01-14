package qna.fixture;

import qna.domain.Answer;
import qna.domain.Question;
import qna.domain.User;

public class TestAnswer {

    public static final String CONTENTS = "TestContents";

    private static Long INCREASE_ID = 0L;

    public static Answer create(User writer, Question question, boolean deleted) {
        return Answer.builder()
            .contents(CONTENTS + INCREASE_ID)
            .writer(writer)
            .question(question)
            .deleted(deleted)
            .build();
    }

    public static Answer createWithId(User writer, Question question, boolean deleted) {
        INCREASE_ID++;
        return Answer.builder()
            .id(INCREASE_ID)
            .contents(CONTENTS + INCREASE_ID)
            .writer(writer)
            .question(question)
            .deleted(deleted)
            .build();
    }

}
