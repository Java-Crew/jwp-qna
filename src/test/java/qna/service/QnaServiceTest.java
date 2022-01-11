package qna.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import qna.SpringContainerTest;
import qna.domain.*;
import qna.exception.ExceptionWithMessageAndCode;
import qna.fixture.UserFixture;
import qna.repository.ContentRepository;
import qna.repository.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("QnaService 테스트")
class QnaServiceTest extends SpringContainerTest {

    @Autowired
    private ContentRepository contentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QnaService qnaService;

    private Question question;

    private Answers answers;

    @BeforeEach
    void setUp() {
        userRepository.save(UserFixture.JAVAJIGI);

        question = Question.builder()
                .title("title1")
                .contents("contents1")
                .writer(UserFixture.JAVAJIGI)
                .build();

        question.addAnswer(Answer.builder()
                .writer(UserFixture.JAVAJIGI)
                .question(question)
                .contents("Answers Contents1")
                .build());
        question.addAnswer(Answer.builder()
                .writer(UserFixture.JAVAJIGI)
                .question(question)
                .contents("Answers Contents2")
                .build());
        question.addAnswer(Answer.builder()
                .writer(UserFixture.JAVAJIGI)
                .question(question)
                .contents("Answers Contents3")
                .deleted(true)
                .build());

        answers = question.getAnswers();
        contentRepository.save(question);
    }

    @Test
    @DisplayName("ID를 이용하여 Content 객체를 조회한다.")
    void findQuestionById() {
        assertThat(qnaService.findQuestionById(question.getId())).isEqualTo(question);
    }

    @Test
    @DisplayName("자신의 질문이 아닌 질문을 제거할 수 없다.")
    void delete_another_writer() {
        assertThatThrownBy(() -> question.delete(UserFixture.SANJIGI))
                .isInstanceOf(ExceptionWithMessageAndCode.UNAUTHORIZED_FOR_QUESTION.getException().getClass())
                .hasMessage(ExceptionWithMessageAndCode.UNAUTHORIZED_FOR_QUESTION.getException().getMessage());
    }

    @Test
    @DisplayName("비로그인 유저는 질문을 삭제할 수 없다.")
    void delete_guest_user() {
        assertThatThrownBy(() -> question.delete(User.GUEST_USER))
                .isInstanceOf(ExceptionWithMessageAndCode.UNAUTHORIZED_FOR_QUESTION.getException().getClass())
                .hasMessage(ExceptionWithMessageAndCode.UNAUTHORIZED_FOR_QUESTION.getException().getMessage());
    }

    @Test
    @DisplayName("질문을 제거하면, 질문과 답변이 모두 삭제 상태가 된다.")
    void delete() {
        qnaService.deleteQuestion(UserFixture.JAVAJIGI, question.getId());
        assertThat(contentRepository.existsById(question.getId())).isFalse();
        assertThat(answers.getAnswerGroup()).noneMatch(answer -> contentRepository.existsById(answer.getId()));
    }

}