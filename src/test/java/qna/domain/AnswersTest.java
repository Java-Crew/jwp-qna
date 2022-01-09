package qna.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Arrays;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import qna.exception.ExceptionWithMessageAndCode;
import qna.fixture.AnswerFixture;
import qna.fixture.UserFixture;

@DisplayName("Answers 테스트")
class AnswersTest {

    @Test
    @DisplayName("다른 사람이 쓴 답변이 있으면 모든 답변을 삭제할 수 없다.")
    void existAnotherWriterOfAnswers() {
        Answers answers = new Answers(Arrays.asList(AnswerFixture.A1, AnswerFixture.A2));
        assertThatThrownBy(() -> answers.validateDeleteAnswers(UserFixture.JAVAJIGI))
            .isInstanceOf(ExceptionWithMessageAndCode.CANNOT_DELETE_QUESTION_WITH_ANOTHER_WRITER.getException().getClass())
            .hasMessage(ExceptionWithMessageAndCode.CANNOT_DELETE_QUESTION_WITH_ANOTHER_WRITER.getException().getMessage());
    }
}