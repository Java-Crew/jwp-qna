package qna;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@Disabled
@DataJpaTest
@Import(DatabaseCleaner.class)
public class RepositoryTest {

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @PersistenceContext
    protected EntityManager em;

    @AfterEach
    void tearDown() {
        databaseCleaner.cleanUp();
    }

    protected <T> T save(T entity) {
        em.persist(entity);
        return entity;
    }

    protected <T> Iterable<T> saveAll(Iterable<T> entities) {
        for (T entity : entities) {
            em.persist(entity);
        }
        return entities;
    }
}
