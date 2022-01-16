package qna.domain.model;

import static qna.exception.ErrorCode.USER_ACCESS_DENIED;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import qna.exception.CustomException;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userId;
    private String password;
    private String name;
    private String email;

    @Builder
    public User(Long id, String userId, String password, String name, String email, LocalDateTime createdDate,
        LocalDateTime lastModifiedDate) {
        super(createdDate, lastModifiedDate);
        this.id = id;
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public void update(User loginUser, User target) {
        if (!matchUserId(loginUser.userId)) {
            throw new CustomException(USER_ACCESS_DENIED);
        }

        if (!matchPassword(target.password)) {
            throw new CustomException(USER_ACCESS_DENIED);
        }

        this.name = target.name;
        this.email = target.email;
    }

    public boolean matchUserId(String userId) {
        return this.userId.equals(userId);
    }

    public boolean matchPassword(String targetPassword) {
        return this.password.equals(targetPassword);
    }
}
