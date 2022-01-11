package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import qna.domain.model.DeleteHistories;
import qna.domain.model.DeleteHistory;

@DisplayName("DeleteHistories POJO 테스트")
public class DeleteHistoriesTest {

    @Test
    @DisplayName("DeleteHistories 생성 테스트")
    void createDeleteHistories_success() {
        DeleteHistories actual = DeleteHistories.empty();
        assertThat(actual).isNotNull();
    }

    @Test
    @DisplayName("DeleteHistories 불변 테스트")
    void getDeleteHistories() {
        DeleteHistories deleteHistories = DeleteHistories.empty();
        List<DeleteHistory> actual = deleteHistories.getDeleteHistories();

        assertThatThrownBy(() -> actual.add(any()))
            .isInstanceOf(UnsupportedOperationException.class);
    }
}
