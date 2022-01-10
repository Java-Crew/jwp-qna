package qna.domain;

import java.util.Objects;
import javax.persistence.*;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import qna.common.domain.BaseTimeEntity;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class DeleteHistory extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ContentType contentType;

    private Long contentId;

    private Long deleteById;

    public DeleteHistory(ContentType contentType, Long contentId, Long deleteById) {
        this.contentType = contentType;
        this.contentId = contentId;
        this.deleteById = deleteById;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DeleteHistory that = (DeleteHistory) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(contentType, that.contentType) &&
                Objects.equals(contentId, that.contentId) &&
                Objects.equals(deleteById, that.deleteById);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, contentType, contentId, deleteById);
    }
}
