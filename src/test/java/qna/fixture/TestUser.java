package qna.fixture;

import qna.domain.User;

public class TestUser {

    public static final String USER_ID = "testUserId";
    public static final String PASSWORD = "testPassword";
    public static final String NAME = "testName";
    public static final String EMAIL = "test@test.com";

    private static Long INCREASE_ID = 0L;

    public static User create() {
        return User.builder()
            .userId(USER_ID + INCREASE_ID)
            .password(PASSWORD + INCREASE_ID)
            .name(NAME + INCREASE_ID)
            .email(EMAIL + INCREASE_ID)
            .build();
    }

    public static User createWithId() {
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
