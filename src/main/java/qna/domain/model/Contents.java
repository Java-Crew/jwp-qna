package qna.domain.model;

import static qna.exception.ErrorCode.USER_ACCESS_DENIED;

import java.util.Objects;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import qna.exception.CustomException;
import qna.exception.ErrorCode;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Contents {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id")
    private User writer;

    private String contents;

    private boolean deleted = false;

    @Builder
    public Contents(User writer, String contents) {
        if (Objects.isNull(writer)) {
            throw new CustomException(USER_ACCESS_DENIED);
        }
        this.writer = writer;
        this.contents = contents;
    }

    public void validateOwner(User loginUser, ErrorCode errorCode) {
        if (!isOwner(loginUser)) {
            throw new CustomException(errorCode);
        }
    }

    private boolean isOwner(User writer) {
        return this.writer.matchUserId(writer.getUserId());
    }

    public void changeDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
