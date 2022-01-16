package qna.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import qna.domain.model.Answer;
import qna.domain.model.ContentType;
import qna.domain.model.DeleteHistory;
import qna.domain.model.Question;
import qna.domain.repository.AnswerRepository;
import qna.domain.repository.QuestionRepository;
import qna.exception.CustomException;
import qna.exception.ErrorCode;
import qna.fixture.QuestionFixture;
import qna.fixture.UserFixture;

@DisplayName("Qna 서비스 테스트")
@ExtendWith(MockitoExtension.class)
class QnaServiceTest {

    @Mock
    private QuestionRepository questionRepository;

    @Mock
    private AnswerRepository answerRepository;

    @Mock
    private DeleteHistoryService deleteHistoryService;

    @InjectMocks
    private QnaService qnaService;

    private Question question;
    private Answer answer;

    @BeforeEach
    public void setUp() {
        question = new Question(1L, "title1", UserFixture.JAVAJIGI, "contents1");
        answer = new Answer(1L, UserFixture.JAVAJIGI, question, "Answers Contents1");
        question.addAnswer(answer);
    }

    @Test
    public void delete_성공() throws Exception {
        when(questionRepository.findByIdAndDeletedFalse(question.getId())).thenReturn(Optional.of(question));
        when(answerRepository.findByQuestionIdAndDeletedFalse(question.getId())).thenReturn(Arrays.asList(answer));

        assertThat(question.getContents().isDeleted()).isFalse();
        qnaService.deleteQuestion(UserFixture.JAVAJIGI, question.getId());

        assertThat(question.getContents().isDeleted()).isTrue();
        verifyDeleteHistories();
    }

    @Test
    public void delete_다른_사람이_쓴_글() {
        when(questionRepository.findByIdAndDeletedFalse(question.getId())).thenReturn(Optional.of(question));

        assertThatThrownBy(() -> qnaService.deleteQuestion(UserFixture.SANJIGI, question.getId()))
            .isInstanceOf(CustomException.class)
            .hasMessage(ErrorCode.CANNOT_DELETE_QUESTION.getMessage());
    }

    @Test
    public void delete_성공_질문자_답변자_같음() {
        when(questionRepository.findByIdAndDeletedFalse(question.getId())).thenReturn(Optional.of(question));
        when(answerRepository.findByQuestionIdAndDeletedFalse(question.getId())).thenReturn(Arrays.asList(answer));

        qnaService.deleteQuestion(UserFixture.JAVAJIGI, question.getId());

        assertThat(question.getContents().isDeleted()).isTrue();
        assertThat(answer.getContents().isDeleted()).isTrue();
        verifyDeleteHistories();
    }

    @Test
    public void delete_답변_중_다른_사람이_쓴_글() {
        Answer answer2 = new Answer(2L, UserFixture.SANJIGI, QuestionFixture.Q1, "Answers Contents1");
        question.addAnswer(answer2);

        when(questionRepository.findByIdAndDeletedFalse(question.getId())).thenReturn(Optional.of(question));
        when(answerRepository.findByQuestionIdAndDeletedFalse(question.getId())).thenReturn(
            Arrays.asList(answer, answer2));

        assertThatThrownBy(() -> qnaService.deleteQuestion(UserFixture.JAVAJIGI, question.getId()))
            .isInstanceOf(CustomException.class)
            .hasMessage(ErrorCode.CANNOT_DELETE_ANSWER.getMessage());
    }

    private void verifyDeleteHistories() {
        List<DeleteHistory> deleteHistories = Arrays.asList(
            new DeleteHistory(
                ContentType.QUESTION, question.getId(), question.getContents().getWriter(), LocalDateTime.now()
            ),
            new DeleteHistory(ContentType.ANSWER, answer.getId(), answer.getContents().getWriter(), LocalDateTime.now())
        );
        verify(deleteHistoryService).saveAll(deleteHistories);
    }
}
