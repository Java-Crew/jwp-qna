package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static qna.domain.model.ContentType.QUESTION;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import qna.CannotDeleteException;
import qna.domain.model.DeleteHistories;
import qna.domain.model.Question;
import qna.domain.model.User;

@DisplayName("Question POJO 테스트")
public class QuestionTest {

    private User user1;
    private User user2;
    private Question question;

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
        question = Question.builder()
            .id(1L)
            .title("제목")
            .writer(user1)
            .contents("내용")
            .build();
    }

    @Test
    @DisplayName("질문 삭제 테스트 성공")
    void delete_success() throws CannotDeleteException {
        DeleteHistories deleteHistories = DeleteHistories.empty();

        question.delete(user1, deleteHistories);

        Assertions.assertAll(
            () -> assertThat(question.getContents().isDeleted()).isTrue(),
            () -> assertThat(deleteHistories.getDeleteHistories().size()).isEqualTo(1),
            () -> assertThat(deleteHistories.getDeleteHistories().get(0).getContentType()).isEqualTo(QUESTION),
            () -> assertThat(deleteHistories.getDeleteHistories().get(0).getContentId()).isEqualTo(question.getId()),
            () -> assertThat(deleteHistories.getDeleteHistories().get(0).getDeletedByUser().getId())
                .isEqualTo(question.getContents().getWriter().getId())
        );
    }

    @Test
    @DisplayName("질문 삭제 테스트 실패 - 질문을 삭제할 권한이 없습니다")
    void delete_fail() {
        assertThatThrownBy(() -> question.delete(user2, any()))
            .isInstanceOf(CannotDeleteException.class)
            .hasMessage("질문을 삭제할 권한이 없습니다.");
    }
}
