package qna.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import qna.exception.httpbasicexception.BadRequestException;
import qna.exception.httpbasicexception.CustomException;
import qna.exception.httpbasicexception.NotFoundException;
import qna.exception.httpbasicexception.UnauthorizedException;

@RequiredArgsConstructor
@Getter
public enum ExceptionWithMessageAndCode {

    // 질문 관련: 6xx
    UNAUTHORIZED_FOR_QUESTION(new UnauthorizedException("질문을 삭제할 권한이 없습니다.", 600)),
    CANNOT_DELETE_QUESTION_WITH_ANOTHER_WRITER(new BadRequestException("다른 사람이 쓴 답변이 있어 질문을 삭제할 수 없습니다.", 601)),
    NOT_FOUND_QUESTION(new NotFoundException("해당하는 질문을 찾을 수 없습니다.", 602)),

    // 답변 관련: 7xx
    NOT_EXISTS_WRITER_FOR_ANSWER(new BadRequestException("작성자가 없는 답변을 생성할 수 없습니다.", 700));

    private final CustomException exception;
}
