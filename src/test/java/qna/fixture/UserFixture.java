package qna.fixture;

import qna.domain.User;

public class UserFixture {

    public static final User JAVAJIGI = User.builder()
        .userId("javajigi")
        .password("password")
        .name("name")
        .email("javajigi@slipp.net")
        .build();

    public static final User SANJIGI = User.builder()
        .userId("sanjigi")
        .password("password")
        .name("name")
        .email("sanjigi@slipp.net")
        .build();
}
