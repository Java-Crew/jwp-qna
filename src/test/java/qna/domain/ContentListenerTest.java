package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import qna.SpringContainerTest;
import qna.fixture.UserFixture;
import qna.repository.ContentRepository;
import qna.repository.DeleteHistoryRepository;
import qna.repository.UserRepository;

@DisplayName("ContentListener 테스트")
class ContentListenerTest extends SpringContainerTest {

    @Autowired
    private DeleteHistoryRepository deleteHistoryRepository;

    @Autowired
    private ContentRepository contentRepository;

    @Autowired
    private UserRepository userRepository;

    private Question question;

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

        contentRepository.save(question);
    }

    @Test
    @DisplayName("질문 또는 답변을 제거하면 Delete 이력 테이블에 로그가 쌓인다.")
    void deleteContent() {
        contentRepository.deleteAll();
        List<DeleteHistory> deleteHistories = deleteHistoryRepository.findAll();
        assertThat(deleteHistories.stream().filter(deleteHistory -> deleteHistory.getContentType() == ContentType.QUESTION))
            .hasSize(1);
        assertThat(deleteHistories.stream().filter(deleteHistory -> deleteHistory.getContentType() == ContentType.ANSWER))
            .hasSize(question.getAnswers().getAnswerGroup().size());
    }
}