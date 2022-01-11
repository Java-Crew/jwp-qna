package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static qna.domain.model.ContentType.ANSWER;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import qna.CannotDeleteException;
import qna.domain.model.Answer;
import qna.domain.model.DeleteHistories;
import qna.domain.model.Question;
import qna.domain.model.User;

@DisplayName("Answer POJO 테스트")
public class AnswerTest {

    private User user1;
    private User user2;
    private Answer answer;

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
        answer = Answer.builder()
            .id(1L)
            .question(
                Question.builder()
                    .id(1L)
                    .title("제목")
                    .writer(user1)
                    .contents("내용")
                    .build()
            )
            .writer(user1)
            .contents("내용")
            .build();
    }

    @Test
    @DisplayName("답변 삭제 테스트 성공")
    void delete_success() throws CannotDeleteException {
        DeleteHistories deleteHistories = DeleteHistories.empty();

        answer.delete(user1, deleteHistories);

        Assertions.assertAll(
            () -> assertThat(answer.getContents().isDeleted()).isTrue(),
            () -> assertThat(deleteHistories.getDeleteHistories().size()).isEqualTo(1),
            () -> assertThat(deleteHistories.getDeleteHistories().get(0).getContentType()).isEqualTo(ANSWER),
            () -> assertThat(deleteHistories.getDeleteHistories().get(0).getContentId()).isEqualTo(answer.getId()),
            () -> assertThat(deleteHistories.getDeleteHistories().get(0).getDeletedByUser().getId())
                .isEqualTo(answer.getContents().getWriter().getId())
        );
    }

    @Test
    @DisplayName("답변 삭제 테스트 실패 - 다른 사람이 쓴 답변이 있어 삭제할 수 없습니다")
    void delete_fail() {
        assertThatThrownBy(() -> answer.delete(user2, DeleteHistories.empty()))
            .isInstanceOf(CannotDeleteException.class)
            .hasMessage("다른 사람이 쓴 답변이 있어 삭제할 수 없습니다.");
    }
}
