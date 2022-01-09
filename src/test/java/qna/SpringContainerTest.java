package qna;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.parallel.Isolated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Isolated
@SpringBootTest
public class SpringContainerTest {

    @Autowired
    protected DatabaseCleaner databaseCleaner;

    @AfterEach
    void tearDown() {
        databaseCleaner.tableClear();
    }

}
