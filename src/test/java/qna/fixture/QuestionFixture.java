package qna.fixture;

import qna.domain.model.Question;

public class QuestionFixture {

    public static final Question Q1 = Question.builder()
        .title("title1")
        .writer(UserFixture.JAVAJIGI)
        .contents("contents1")
        .build();

    public static final Question Q2 = Question.builder()
        .title("title2")
        .writer(UserFixture.SANJIGI)
        .contents("contents2")
        .build();
}
