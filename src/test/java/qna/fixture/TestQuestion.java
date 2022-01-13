package qna.fixture;

import qna.domain.Answers;
import qna.domain.Question;
import qna.domain.User;

public class TestQuestion {

    public static final String TITLE = "testTitle";
    public static final String CONTENTS = "TestContents";

    private static Long INCREASE_ID = 0L;

    public static Question create(User writer, Answers answers, boolean deleted) {
        return Question.builder()
            .title(TITLE + INCREASE_ID)
            .contents(CONTENTS + INCREASE_ID)
            .writer(writer)
            .answers(answers)
            .deleted(deleted)
            .build();
    }

        public static Question createWithId(User writer, Answers answers, boolean deleted) {
        INCREASE_ID++;
        return Question.builder()
            .id(INCREASE_ID)
            .title(TITLE + INCREASE_ID)
            .contents(CONTENTS + INCREASE_ID)
            .writer(writer)
            .answers(answers)
            .deleted(deleted)
            .build();
    }

}
