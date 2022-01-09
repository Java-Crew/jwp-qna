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

    private final AnswerRepository answerRepository;

    private final DeleteHistoryService deleteHistoryService;

    @Transactional(readOnly = true)
    public Question findQuestionById(Long id) {
        return questionRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(ExceptionWithMessageAndCode.NOT_FOUND_QUESTION::getException);
    }

    @Transactional
    public void deleteQuestion(User loginUser, Long questionId) {
        Question question = findQuestionById(questionId);
        if (!question.isOwner(loginUser)) {
            throw ExceptionWithMessageAndCode.UNAUTHORIZED_FOR_QUESTION.getException();
        }

        Answers answers = new Answers(answerRepository.findByQuestionIdAndDeletedFalse(questionId));
        if (answers.existAnotherWriterOfAnswers(loginUser)) {
            throw ExceptionWithMessageAndCode.CANNOT_DELETE_QUESTION_WITH_ANOTHER_WRITER.getException();
        }

        List<DeleteHistory> deleteHistories = new ArrayList<>();
        question.changeDeleted(true);
        deleteHistories.add(new DeleteHistory(ContentType.QUESTION, questionId, question.getWriter()));
        for (Answer answer : answers.getAnswerGroup()) {
            answer.changeDeleted(true);
            deleteHistories.add(new DeleteHistory(ContentType.ANSWER, answer.getId(), answer.getWriter()));
        }
        deleteHistoryService.saveAll(deleteHistories);
    }
}
