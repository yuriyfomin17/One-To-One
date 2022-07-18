package dao;

import util.EntityManagerUtil;
import model.Post;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import util.PostDataGenerator;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class PostDaoTest {
    private static EntityManagerUtil emUtil;

    private static PostDao postDao;

    private static EntityManagerFactory entityManagerFactory;

    @BeforeAll
    static void setup() {
        entityManagerFactory = Persistence.createEntityManagerFactory("One-to-One");
        emUtil = new EntityManagerUtil(entityManagerFactory);
        postDao = new PostDaoImpl(entityManagerFactory);
    }

    @AfterAll
    static void destroy() {
        entityManagerFactory.close();
    }

    @Test
    void testSavePost(){
        Post post = PostDataGenerator.createRandomPost();
        postDao.save(post);

        assertThat(post.getId(), notNullValue());
    }

}
