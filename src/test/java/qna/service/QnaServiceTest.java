package qna.service;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import qna.SpringContainerTest;
import qna.domain.*;
import qna.exception.ExceptionWithMessageAndCode;
import qna.fixture.TestAnswer;
import qna.fixture.TestQuestion;
import qna.fixture.TestUser;
import qna.repository.ContentRepository;
import qna.repository.DeleteHistoryRepository;
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
    private DeleteHistoryRepository deleteHistoryRepository;

    @Autowired
    private QnaService qnaService;

    private Question question;

    private User user;

    private Answers answers;

    @BeforeEach
    void setUp() {
        user = userRepository.save(TestUser.create());
        question = TestQuestion.create(user, null, false);

        question.addAnswer(TestAnswer.create(user, question, false));
        question.addAnswer(TestAnswer.create(user, question, false));
        question.addAnswer(TestAnswer.create(user, question, false));

        Question savedQuestion = contentRepository.save(question);
        answers = savedQuestion.getAnswers();
    }

    @Test
    @DisplayName("ID를 이용하여 Content 객체를 조회한다.")
    void findQuestionById() {
        assertThat(qnaService.findQuestionById(question.getId())).isEqualTo(question);
    }

    @Test
    @DisplayName("자신의 질문이 아닌 질문을 제거할 수 없다.")
    void delete_another_writer() {
        assertThatThrownBy(() -> qnaService.deleteQuestion(TestUser.createWithId(), question.getId()))
                .isInstanceOf(ExceptionWithMessageAndCode.UNAUTHORIZED_FOR_QUESTION.getException().getClass())
                .hasMessage(ExceptionWithMessageAndCode.UNAUTHORIZED_FOR_QUESTION.getException().getMessage());
    }

    @Test
    @DisplayName("비로그인 유저는 질문을 삭제할 수 없다.")
    void delete_guest_user() {
        assertThatThrownBy(() -> qnaService.deleteQuestion(User.GUEST_USER, question.getId()))
                .isInstanceOf(ExceptionWithMessageAndCode.UNAUTHORIZED_FOR_QUESTION.getException().getClass())
                .hasMessage(ExceptionWithMessageAndCode.UNAUTHORIZED_FOR_QUESTION.getException().getMessage());
    }

    @Test
    @DisplayName("질문을 제거하면, 질문과 답변이 모두 삭제 상태가 된다.")
    void delete() {
        qnaService.deleteQuestion(user, question.getId());
        assertThat(contentRepository.existsById(question.getId())).isFalse();
        assertThat(answers.getAnswerGroup()).noneMatch(answer -> contentRepository.existsById(answer.getId()));
        verifyDeleteHistories();
    }

    private void verifyDeleteHistories() {
        List<DeleteHistory> deleteHistories = deleteHistoryRepository.findAll();
        assertThat(deleteHistories.stream().filter(deleteHistory -> deleteHistory.getContentType() == ContentType.QUESTION))
            .hasSize(1);
        assertThat(deleteHistories.stream().filter(deleteHistory -> deleteHistory.getContentType() == ContentType.ANSWER))
            .hasSize(question.getAnswers().getAnswerGroup().size());
    }

}