package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import qna.fixture.TestUser;

@DisplayName("User 테스트")
class UserTest {

    @Test
    @DisplayName("게스트 유저인지 판단한다.")
    void isGuestUser() {
        User guestUser = User.GUEST_USER;
        assertThat(guestUser.isGuestUser()).isTrue();
        assertThat(TestUser.createWithId().isGuestUser()).isFalse();
    }

}