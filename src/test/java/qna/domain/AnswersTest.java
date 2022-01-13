package qna.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Arrays;
import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import qna.exception.ExceptionWithMessageAndCode;
import qna.exception.httpbasicexception.BadRequestException;
import qna.fixture.TestAnswer;
import qna.fixture.TestQuestion;
import qna.fixture.TestUser;

@DisplayName("Answers 테스트")
class AnswersTest {

    private Answers answers;

    @BeforeEach
    void setUp() {
        User testUser = TestUser.createWithId();
        Question testQuestion = TestQuestion.createWithId(testUser, null, false);
        Answer testAnswer1 = TestAnswer.createWithId(testUser, testQuestion, false);
        Answer testAnswer2 = TestAnswer.createWithId(testUser, testQuestion, false);
        testQuestion.addAnswer(testAnswer1);
        testQuestion.addAnswer(testAnswer2);
        answers = testQuestion.getAnswers();
    }

    @Test
    @DisplayName("다른 사람이 쓴 답변이 있으면 모든 답변을 삭제할 수 없다.")
    void existAnotherWriterOfAnswers() {
        assertThatThrownBy(() -> answers.validateDelete(TestUser.createWithId()))
            .isInstanceOf(BadRequestException.class)
            .hasMessage(ExceptionWithMessageAndCode.CANNOT_DELETE_ANSWERS_WITH_ANOTHER_WRITER.getException().getMessage());
    }

}