package qna.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import qna.domain.Content;
import qna.domain.Question;
import qna.domain.User;
import qna.exception.ExceptionWithMessageAndCode;
import qna.repository.ContentRepository;

@RequiredArgsConstructor
@Service
public class QnaService {

    private final ContentRepository contentRepository;

    @Transactional(readOnly = true)
    public Question findQuestionById(Long id) {
        return contentRepository.findById(id)
            .orElseThrow(ExceptionWithMessageAndCode.NOT_FOUND_QUESTION::getException);
    }

    @Transactional
    public void deleteQuestion(User loginUser, Long questionId) {
        Content question = findQuestionById(questionId);
        question.delete(loginUser);
    }
}
