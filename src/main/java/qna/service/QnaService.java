package qna.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import qna.domain.Question;
import qna.domain.User;
import qna.exception.ExceptionWithMessageAndCode;
import qna.repository.QuestionRepository;

@RequiredArgsConstructor
@Service
public class QnaService {

    private final QuestionRepository questionRepository;

    @Transactional(readOnly = true)
    public Question findQuestionById(Long id) {
        return questionRepository.findByIdAndDeletedFalse(id)
            .orElseThrow(ExceptionWithMessageAndCode.NOT_FOUND_QUESTION::getException);
    }

    @Transactional
    public void deleteQuestion(User loginUser, Long questionId) {
        Question question = findQuestionById(questionId);
        question.delete(loginUser);
    }
}
