package qna.domain.model;

import static qna.exception.ErrorCode.USER_ACCESS_DENIED;

import java.util.Objects;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import qna.exception.CustomException;

@Getter
@Entity
@ToString
@DiscriminatorColumn
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Where(clause = "deleted=false")
@SQLDelete(sql = "UPDATE post SET deleted = true WHERE id=?")
public abstract class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id")
    private User writer;

    private String contents;

    private boolean deleted = false;

    public Post(Long id, User writer, String contents) {
        if (Objects.isNull(writer)) {
            throw new CustomException(USER_ACCESS_DENIED);
        }
        this.id = id;
        this.writer = writer;
        this.contents = contents;
    }

    public abstract void validateDelete(User loginUser);

    public boolean isOwner(User writer) {
        return this.writer.matchUserId(writer.getUserId());
    }
}
