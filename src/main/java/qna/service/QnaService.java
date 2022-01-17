package qna.service;

import static qna.exception.ErrorCode.NOT_FOUND_CONTENTS;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import qna.domain.model.Answer;
import qna.domain.model.DeleteHistories;
import qna.domain.model.Question;
import qna.domain.model.User;
import qna.domain.repository.AnswerRepository;
import qna.domain.repository.QuestionRepository;
import qna.exception.CustomException;

@Service
@RequiredArgsConstructor
public class QnaService {

    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final DeleteHistoryService deleteHistoryService;

    @Transactional(readOnly = true)
    public Question findQuestionById(Long id) {
        return questionRepository.findByIdAndDeletedFalse(id)
            .orElseThrow(() -> new CustomException(NOT_FOUND_CONTENTS));
    }

    @Transactional
    public void deleteQuestion(User loginUser, Long questionId) {
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
