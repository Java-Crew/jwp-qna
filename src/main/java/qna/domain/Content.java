package qna.domain;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import qna.common.domain.BaseTimeEntity;
import qna.exception.ExceptionWithMessageAndCode;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "ContentType")
public abstract class Content extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private String contents;

    @ManyToOne
    private User writer;

    @Column(nullable = false)
    private boolean deleted = false;

    public Content(Long id, String contents, User writer, boolean deleted) {
        if (Objects.isNull(writer)) {
            throw ExceptionWithMessageAndCode.NOT_EXISTS_WRITER_FOR_CONTENT.getException();
        }

        this.id = id;
        this.contents = contents;
        this.writer = writer;
        this.deleted = deleted;
    }

    protected boolean isOwner(User writer) {
        return this.writer.equals(writer);
    }

    protected void changeDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
