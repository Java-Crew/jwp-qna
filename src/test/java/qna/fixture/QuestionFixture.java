package qna.fixture;

import qna.domain.Question;

public class QuestionFixture {

    public static final Question Q1 = Question.builder()
        .title("title1")
        .contents("contents1")
        .writer(UserFixture.JAVAJIGI)
        .deleted(false)
        .build();

    public static final Question Q2 = Question.builder()
        .title("title2")
        .contents("contents2")
        .writer(UserFixture.SANJIGI)
        .deleted(false)
        .build();
}
