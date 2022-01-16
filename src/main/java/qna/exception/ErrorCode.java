package qna.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    CANNOT_DELETE_QUESTION(401, "질문을 삭제할 권한이 없습니다."),
    CANNOT_DELETE_ANSWER(401, "다른 사람이 쓴 답변이 있어 삭제할 수 없습니다."),
    USER_ACCESS_DENIED(401, "접근 권한이 없습니다.");

    private final int status;
    private final String message;

    ErrorCode(int status, String message) {
        this.status = status;
        this.message = message;
    }
}