package qna.fixture;

import qna.domain.model.Question;

public class QuestionFixture {

    public static final Question Q1 = Question.builder()
        .title("title1")
        .contents("contents1")
        .build()
        .writeBy(UserFixture.JAVAJIGI);

    public static final Question Q2 = Question.builder()
        .title("title2")
        .contents("contents2")
        .build()
        .writeBy(UserFixture.SANJIGI);
}
