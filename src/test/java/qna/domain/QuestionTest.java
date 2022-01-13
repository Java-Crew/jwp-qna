package qna.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import qna.exception.ExceptionWithMessageAndCode;
import qna.fixture.QuestionFixture;
import qna.fixture.UserFixture;

@ExtendWith(MockitoExtension.class)
@DisplayName("Question 테스트")
class QuestionTest {

    @InjectMocks
    private Question question = QuestionFixture.Q1;

    @Test
    @DisplayName("글쓴이가 없으면 Question 객체를 생성할 수 없다.")
    void create_exception() {
        assertThatThrownBy(() -> new Question(1L, "title", "contents", null, null, false))
            .isInstanceOf(ExceptionWithMessageAndCode.NOT_EXISTS_WRITER_FOR_CONTENT.getException().getClass())
            .hasMessage(ExceptionWithMessageAndCode.NOT_EXISTS_WRITER_FOR_CONTENT.getException().getMessage());
    }

    @Test
    @DisplayName("자신의 질문이 아닌 질문을 제거할 수 없다.")
    void delete_another_writer() {
        assertThatThrownBy(() -> question.validateDelete(UserFixture.SANJIGI))
            .isInstanceOf(ExceptionWithMessageAndCode.UNAUTHORIZED_FOR_QUESTION.getException().getClass())
            .hasMessage(ExceptionWithMessageAndCode.UNAUTHORIZED_FOR_QUESTION.getException().getMessage());
    }

    @Test
    @DisplayName("비로그인 유저는 질문을 삭제할 수 없다.")
    void delete_guest_user() {
        assertThatThrownBy(() -> question.validateDelete(User.GUEST_USER))
            .isInstanceOf(ExceptionWithMessageAndCode.UNAUTHORIZED_FOR_QUESTION.getException().getClass())
            .hasMessage(ExceptionWithMessageAndCode.UNAUTHORIZED_FOR_QUESTION.getException().getMessage());
    }
}