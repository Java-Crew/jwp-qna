package qna.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static qna.fixture.AnswerFixture.getAnswer;
import static qna.fixture.QuestionFixture.getQuestion;
import static qna.fixture.UserFixture.getUser;

import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import qna.RepositoryTest;
import qna.domain.model.Answer;
import qna.domain.model.Question;
import qna.domain.model.User;
import qna.domain.repository.AnswerRepository;

@DisplayName("Answer 레포지토리 테스트")
public class AnswerRepositoryTest extends RepositoryTest {

    @Autowired
    private AnswerRepository answerRepository;

    private User writer;
    private Question question;
    private Answer answer;

    @BeforeEach
    void setUp() {
        writer = save(getUser());
        question = save(getQuestion(writer));
        answer = answerRepository.save(getAnswer(writer, question));
    }

    @Test
    void 답변을_저장한_후_조회한다() {
        Answer actual = answerRepository.findById(answer.getId()).get();
        Assertions.assertAll(
            () -> assertThat(actual).isNotNull(),
            () -> assertThat(actual.getId()).isEqualTo(answer.getId()),
            () -> assertThat(actual.getWriter().getId()).isEqualTo(answer.getWriter().getId()),
            () -> assertThat(actual.getQuestion().getId()).isEqualTo(answer.getQuestion().getId()),
            () -> assertThat(actual.getContents()).isEqualTo(answer.getContents()),
            () -> assertThat(actual.isDeleted()).isFalse()
        );
    }

    @Test
    void Where_어노테이션이_정상적으로_작동하고_답변을_삭제하면_조회되지_않는다() {
        answerRepository.deleteById(answer.getId());
        List<Answer> actual = answerRepository.findAll();
        assertThat(actual.size()).isEqualTo(0);
    }
}
