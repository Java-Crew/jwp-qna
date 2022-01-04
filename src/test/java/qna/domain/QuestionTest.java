package qna.domain;

import java.time.LocalDateTime;

public class QuestionTest {

    public static final Question Q1 = Question.builder()
            .title("title1")
            .contents("contents1")
            .writerId(UserTest.JAVAJIGI.getId())
            .createdAt(LocalDateTime.now())
            .deleted(false)
            .build();

    public static final Question Q2 = Question.builder()
            .title("title2")
            .contents("contents2")
            .writerId(UserTest.SANJIGI.getId())
            .createdAt(LocalDateTime.now())
            .deleted(false)
            .build();
}
