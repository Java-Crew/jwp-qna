package qna.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import qna.CannotDeleteException;
import qna.NotFoundException;
import qna.domain.model.Answer;
import qna.domain.model.DeleteHistories;
import qna.domain.model.Question;
import qna.domain.model.User;
import qna.domain.repository.AnswerRepository;
import qna.domain.repository.QuestionRepository;

@Service
@RequiredArgsConstructor
public class QnaService {

    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final DeleteHistoryService deleteHistoryService;

    @Transactional(readOnly = true)
    public Question findQuestionById(Long id) {
        return questionRepository.findByIdAndDeletedFalse(id)
            .orElseThrow(NotFoundException::new);
    }

    @Transactional
    public void deleteQuestion(User loginUser, Long questionId) throws CannotDeleteException {
        DeleteHistories deleteHistories = DeleteHistories.empty();

        Question question = findQuestionById(questionId);
        question.delete(loginUser, deleteHistories);

        List<Answer> answers = answerRepository.findByQuestionIdAndDeletedFalse(questionId);
        for (Answer answer : answers) {
            answer.delete(loginUser, deleteHistories);
        }

        deleteHistoryService.saveAll(deleteHistories.getDeleteHistories());
    }
}
