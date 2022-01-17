package qna.repository;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static qna.fixture.QuestionFixture.getQuestion;
import static qna.fixture.UserFixture.getUser;

import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import qna.RepositoryTest;
import qna.domain.model.Question;
import qna.domain.model.User;
import qna.domain.repository.QuestionRepository;

@DisplayName("Question 레포지토리 테스트")
public class QuestionRepositoryTest extends RepositoryTest {

    @Autowired
    private QuestionRepository questionRepository;

    private User writer;
    private Question question;

    @BeforeEach
    void setUp() {
        writer = save(getUser());
        question = questionRepository.save(getQuestion(writer));
    }

    @Test
    void 질문을_저장한_후_조회한다() {
        Question actual = questionRepository.findById(question.getId()).get();
        Assertions.assertAll(
            () -> assertThat(actual).isNotNull(),
            () -> assertThat(actual.getId()).isEqualTo(question.getId()),
            () -> assertThat(actual.getTitle()).isEqualTo(question.getTitle()),
            () -> assertThat(actual.getWriter().getId()).isEqualTo(question.getWriter().getId()),
            () -> assertThat(actual.getContents()).isEqualTo(question.getContents()),
            () -> assertThat(actual.isDeleted()).isFalse()
        );
    }

    @Test
    void where_어노테이션이_정상적으로_작동하고_질문을_삭제하면_조회되지_않는다() {
        questionRepository.deleteById(question.getId());
        List<Question> actual = questionRepository.findAll();
        assertThat(actual.size()).isEqualTo(0);
    }
}
