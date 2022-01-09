package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import qna.fixture.AnswerFixture;
import qna.fixture.UserFixture;

@DisplayName("Answers 테스트")
class AnswersTest {

    @Test
    @DisplayName("다른 사람이 쓴 답변이 있는지 확인한다.")
    void existAnotherWriterOfAnswers() {
        Answers answers = new Answers(Arrays.asList(AnswerFixture.A1, AnswerFixture.A2));
        assertThat(answers.existAnotherWriterOfAnswers(UserFixture.JAVAJIGI)).isTrue();
    }
}