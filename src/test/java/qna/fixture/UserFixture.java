package qna.fixture;

import qna.domain.model.User;

public class UserFixture {

    public static final User JAVAJIGI = User.builder()
        .id(1L)
        .userId("javajigi")
        .password("password")
        .name("name")
        .email("javajigi@slipp.net")
        .build();

    public static final User SANJIGI = User.builder()
        .id(2L)
        .userId("sanjigi")
        .password("password")
        .name("name")
        .email("sanjigi@slipp.net")
        .build();
}
