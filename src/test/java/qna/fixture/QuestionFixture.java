package qna.fixture;

import qna.domain.Question;

import java.time.LocalDateTime;

public class QuestionFixture {

    public static final Question Q1 = Question.builder()
            .title("title1")
            .contents("contents1")
            .writerId(UserFixture.JAVAJIGI.getId())
            .deleted(false)
            .build();

    public static final Question Q2 = Question.builder()
            .title("title2")
            .contents("contents2")
            .writerId(UserFixture.SANJIGI.getId())
            .deleted(false)
            .build();
}
