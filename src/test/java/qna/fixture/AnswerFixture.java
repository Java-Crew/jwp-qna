package qna.fixture;

import qna.domain.model.Answer;

public class AnswerFixture {

    public static final Answer A1 = Answer.builder()
        .writer(UserFixture.JAVAJIGI)
        .question(QuestionFixture.Q1)
        .contents("Answers Contents1")
        .build();

    public static final Answer A2 = Answer.builder()
        .writer(UserFixture.SANJIGI)
        .question(QuestionFixture.Q1)
        .contents("Answers Contents2")
        .build();
}
