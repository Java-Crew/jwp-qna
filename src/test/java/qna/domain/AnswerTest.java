package qna.domain;

public class AnswerTest {

    public static final Answer A1 = Answer.builder()
            .writer(UserTest.JAVAJIGI)
            .question(QuestionTest.Q1)
            .contents("Answers Content1")
            .build();

    public static final Answer A2 = Answer.builder()
            .writer(UserTest.SANJIGI)
            .question(QuestionTest.Q1)
            .contents("Answers Content2")
            .build();
}
