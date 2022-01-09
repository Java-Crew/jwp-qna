package qna.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import qna.domain.*;
import qna.exception.ExceptionWithMessageAndCode;
import qna.repository.AnswerRepository;
import qna.repository.QuestionRepository;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class QnaService {

    private final QuestionRepository questionRepository;

    private final DeleteHistoryService deleteHistoryService;

    @Transactional(readOnly = true)
    public Question findQuestionById(Long id) {
        return questionRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(ExceptionWithMessageAndCode.NOT_FOUND_QUESTION::getException);
    }

    @Transactional
    public void deleteQuestion(User loginUser, Long questionId) {
        Question question = findQuestionById(questionId);
        question.delete(loginUser);

        Answers answers = question.getAnswers();
        List<DeleteHistory> deleteHistories = new ArrayList<>();
        deleteHistories.add(new DeleteHistory(question, question.getWriter()));
        for (Answer answer : answers.answerGroup()) {
            deleteHistories.add(new DeleteHistory(answer, answer.getWriter()));
        }
        deleteHistoryService.saveAll(deleteHistories);
    }
}
