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
        Question question = findQuestionById(questionId);
        validateQuestionOwner(question, loginUser);

        List<Answer> answers = answerRepository.findByQuestionIdAndDeletedFalse(questionId);
        for (Answer answer : answers) {
            validateAnswerOwner(answer, loginUser);
        }

        DeleteHistories deleteHistories = DeleteHistories.empty();
        deleteHistories.addQuestionDeleteHistory(question, loginUser);
        deleteHistories.addAnswerDeleteHistories(answers, loginUser);
        deleteHistoryService.saveAll(deleteHistories.getDeleteHistories());
    }

    private void validateQuestionOwner(Question question, User loginUser) throws CannotDeleteException {
        if (!question.isOwner(loginUser)) {
            throw new CannotDeleteException("질문을 삭제할 권한이 없습니다.");
        }
    }

    private void validateAnswerOwner(Answer answer, User loginUser) throws CannotDeleteException {
        if (!answer.isOwner(loginUser)) {
            throw new CannotDeleteException("다른 사람이 쓴 답변이 있어 삭제할 수 없습니다.");
        }
    }
}
