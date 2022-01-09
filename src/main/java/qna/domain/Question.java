package qna.domain;

import lombok.*;

import javax.persistence.*;
import qna.common.domain.BaseTimeEntity;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Question extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String title;

    @Lob
    private String contents;

    @ManyToOne
    private User writer;

    @Column(nullable = false)
    private boolean deleted = false;

    @Builder
    public Question(Long id, String title, String contents, User writer, boolean deleted) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.writer = writer;
        this.deleted = deleted;
    }

    public boolean isOwner(User writer) {
        return this.writer.equals(writer);
    }

    public void addAnswer(Answer answer) {
        answer.changeQuestion(this);
    }

    public void changeDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
