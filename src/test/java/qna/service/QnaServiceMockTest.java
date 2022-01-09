package qna.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
import qna.domain.Answer;
import qna.domain.DeleteHistory;
import qna.domain.Question;
import qna.exception.ExceptionWithMessageAndCode;
import qna.fixture.UserFixture;
import qna.repository.QuestionRepository;

@ExtendWith(MockitoExtension.class)
@DisplayName("QnaServiceMockTest")
class QnaServiceMockTest {

    @Mock
    private QuestionRepository questionRepository;

    @Mock
    private DeleteHistoryService deleteHistoryService;

    @InjectMocks
    private QnaService qnaService;

    private Question question;

    private Answer answer;

    @BeforeEach
    public void setUp() {
        question = Question.builder()
            .id(1L)
            .title("title1")
            .contents("contents1")
            .writer(UserFixture.JAVAJIGI)
            .build();

        answer = Answer.builder()
            .id(1L)
            .writer(UserFixture.JAVAJIGI)
            .question(question)
            .contents("Answers Contents1")
            .build();

        question.addAnswer(answer);
    }

    @Test
    public void delete_성공() throws Exception {
        when(questionRepository.findByIdAndDeletedFalse(question.getId())).thenReturn(Optional.of(question));

        assertThat(question.isDeleted()).isFalse();
        qnaService.deleteQuestion(UserFixture.JAVAJIGI, question.getId());

        assertThat(question.isDeleted()).isTrue();
        //verifyDeleteHistories();
    }

    @Test
    public void delete_다른_사람이_쓴_글() {
        when(questionRepository.findByIdAndDeletedFalse(question.getId())).thenReturn(Optional.of(question));

        assertThatThrownBy(() -> qnaService.deleteQuestion(UserFixture.SANJIGI, question.getId()))
            .isInstanceOf(ExceptionWithMessageAndCode.UNAUTHORIZED_FOR_QUESTION.getException().getClass());
    }

    @Test
    public void delete_성공_질문자_답변자_같음() {
        when(questionRepository.findByIdAndDeletedFalse(question.getId())).thenReturn(Optional.of(question));

        qnaService.deleteQuestion(UserFixture.JAVAJIGI, question.getId());

        assertThat(question.isDeleted()).isTrue();
        assertThat(answer.isDeleted()).isTrue();
        //verifyDeleteHistories();
    }

    @Test
    public void delete_답변_중_다른_사람이_쓴_글() {
        Answer answer2 = Answer.builder()
            .id(2L)
            .writer(UserFixture.SANJIGI)
            .question(question)
            .contents("Answers Contents1")
            .build();
        question.addAnswer(answer2);

        when(questionRepository.findByIdAndDeletedFalse(question.getId())).thenReturn(Optional.of(question));

        assertThatThrownBy(() -> qnaService.deleteQuestion(UserFixture.JAVAJIGI, question.getId()))
            .isInstanceOf(ExceptionWithMessageAndCode.CANNOT_DELETE_QUESTION_WITH_ANOTHER_WRITER.getException().getClass());
    }

    private void verifyDeleteHistories() {
        List<DeleteHistory> deleteHistories = Arrays.asList(
            new DeleteHistory(question),
            new DeleteHistory(answer)
        );
        verify(deleteHistoryService).saveAll(deleteHistories);
    }
}
