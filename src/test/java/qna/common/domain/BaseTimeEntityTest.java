package qna.common.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import qna.domain.User;
import qna.fixture.UserFixture;
import qna.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DisplayName("BaseTimeEntity 테스트")
class BaseTimeEntityTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("자동으로 생성 날짜와 수정 날짜 컬럼을 생성하는지 확인한다.")
    void create() {
        LocalDateTime now = LocalDateTime.now();
        userRepository.save(UserFixture.JAVAJIGI);

        List<User> users = userRepository.findAll();
        User user = users.get(0);

        System.out.println("createdAt: " + user.getCreatedAt() + " updatedAt: " + user.getUpdatedAt());

        assertThat(user.getCreatedAt()).isAfter(now);
        assertThat(user.getUpdatedAt()).isAfter(now);
    }
}