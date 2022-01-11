package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import qna.domain.model.User;

@DisplayName("User POJO 테스트")
public class UserTest {

    private User user1;
    private User user2;

    @BeforeEach
    void setUp() {
        user1 = User.builder()
            .id(1L)
            .userId("유저1")
            .name("이름")
            .password("비밀번호")
            .email("이메일")
            .build();
        user2 = User.builder()
            .id(2L)
            .userId("유저2")
            .name("이름")
            .password("비밀번호")
            .email("이메일")
            .build();
    }

    @Test
    @DisplayName("동일한 유저 확인 테스트 true")
    void matchUserId_true() {
        boolean actual = user1.matchUserId(user1.getUserId());
        assertThat(actual).isTrue();
    }

    @Test
    @DisplayName("동일한 유저 확인 테스트 false")
    void matchUserId_false() {
        boolean actual = user1.matchUserId(user2.getUserId());
        assertThat(actual).isFalse();
    }
}
