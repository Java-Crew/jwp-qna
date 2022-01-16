package qna.fixture;

import qna.domain.model.User;

public class UserFixture {

    public static final String USER_ID = "userId";
    public static final String PASSWORD = "password";
    public static final String NAME = "name";
    public static final String EMAIL = "email";

    private static Long INCREASE_ID = 0L;

    public static User getUser() {
        INCREASE_ID++;
        return User.builder()
            .userId(USER_ID + INCREASE_ID)
            .password(PASSWORD + INCREASE_ID)
            .name(NAME + INCREASE_ID)
            .email(EMAIL + INCREASE_ID)
            .build();
    }

    public static User getUserWithId() {
        INCREASE_ID++;
        return User.builder()
            .id(INCREASE_ID)
            .userId(USER_ID + INCREASE_ID)
            .password(PASSWORD + INCREASE_ID)
            .name(NAME + INCREASE_ID)
            .email(EMAIL + INCREASE_ID)
            .build();
    }
}
