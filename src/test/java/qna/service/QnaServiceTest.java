package qna.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static qna.fixture.AnswerFixture.getAnswerWithId;
import static qna.fixture.QuestionFixture.getQuestionWithId;
import static qna.fixture.UserFixture.getUserWithId;

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
import qna.domain.model.User;
import qna.domain.repository.AnswerRepository;
import qna.domain.repository.QuestionRepository;
import qna.exception.CustomException;
import qna.exception.ErrorCode;

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

    private User writer;
    private Question question;
    private Answer answer;

    @BeforeEach
    public void setUp() {
        writer = getUserWithId();
        question = getQuestionWithId(writer);
        answer = getAnswerWithId(writer, question);
        question.addAnswer(answer);
    }

    @Test
    public void delete_성공() {
        when(questionRepository.findByIdAndDeletedFalse(question.getId())).thenReturn(Optional.of(question));
        when(answerRepository.findByQuestionIdAndDeletedFalse(question.getId())).thenReturn(Arrays.asList(answer));

        assertThat(question.getContents().isDeleted()).isFalse();
        qnaService.deleteQuestion(writer, question.getId());

        assertThat(question.getContents().isDeleted()).isTrue();
        verifyDeleteHistories();
    }

    @Test
    public void delete_다른_사람이_쓴_글() {
        when(questionRepository.findByIdAndDeletedFalse(question.getId())).thenReturn(Optional.of(question));

        assertThatThrownBy(() -> qnaService.deleteQuestion(getUserWithId(), question.getId()))
            .isInstanceOf(CustomException.class)
            .hasMessage(ErrorCode.CANNOT_DELETE_QUESTION.getMessage());
    }

    @Test
    public void delete_성공_질문자_답변자_같음() {
        when(questionRepository.findByIdAndDeletedFalse(question.getId())).thenReturn(Optional.of(question));
        when(answerRepository.findByQuestionIdAndDeletedFalse(question.getId())).thenReturn(Arrays.asList(answer));

        qnaService.deleteQuestion(writer, question.getId());

        assertThat(question.getContents().isDeleted()).isTrue();
        assertThat(answer.getContents().isDeleted()).isTrue();
        verifyDeleteHistories();
    }

    @Test
    public void delete_답변_중_다른_사람이_쓴_글() {
        Answer answer2 = getAnswerWithId(getUserWithId(), question);
        question.addAnswer(answer2);

        when(questionRepository.findByIdAndDeletedFalse(question.getId())).thenReturn(Optional.of(question));
        when(answerRepository.findByQuestionIdAndDeletedFalse(question.getId())).thenReturn(
            Arrays.asList(answer, answer2));

        assertThatThrownBy(() -> qnaService.deleteQuestion(writer, question.getId()))
            .isInstanceOf(CustomException.class)
            .hasMessage(ErrorCode.CANNOT_DELETE_ANSWER.getMessage());
    }

    private void verifyDeleteHistories() {
        List<DeleteHistory> deleteHistories = Arrays.asList(
            DeleteHistory.create(ContentType.QUESTION, question.getId(), question.getContents().getWriter()),
            DeleteHistory.create(ContentType.ANSWER, answer.getId(), answer.getContents().getWriter())
        );
        verify(deleteHistoryService).saveAll(deleteHistories);
    }
}
