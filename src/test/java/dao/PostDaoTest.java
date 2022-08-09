package dao;

import model.PostDetails;
import util.EntityManagerUtil;
import model.Post;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import util.PostDataGenerator;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

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
        assertThat(post.getId(), nullValue());
        postDao.savePost(post);

        assertThat(post.getId(), notNullValue());
    }

    @Test
    void testSavePostAndPostDetails(){
        Post randomPost = PostDataGenerator.createRandomPost();
        PostDetails randomPostDetails = PostDataGenerator.createPostDetails();

        assertThat(randomPost.getId(), nullValue());
        assertThat(randomPostDetails.getId(), nullValue());

        postDao.savePost(randomPost);
        assertThat(randomPost.getId(), notNullValue());
        postDao.savePostDetails(randomPost.getId(), randomPostDetails);
    }

    @Test
    void testDeletePost(){

        Post randomPost = PostDataGenerator.createRandomPost();
        PostDetails randomPostDetails = PostDataGenerator.createPostDetails();

        randomPostDetails.setPost(randomPost);
        postDao.savePostDetails(randomPostDetails);

        assertThat(randomPostDetails.getId(), notNullValue());

        emUtil.performWithinTx(entityManager -> {
            Post managedPost = entityManager.find(Post.class, randomPost.getId());
            PostDetails managedPostDetails = entityManager.find(PostDetails.class, randomPostDetails.getId());
            entityManager.remove(managedPostDetails);
            entityManager.remove(managedPost);
        });
        Post deletedPost = emUtil.performReturningWithinTx(entityManager -> entityManager.find(Post.class, randomPost.getId()));
        PostDetails deletedPostDetails = emUtil.performReturningWithinTx(entityManager -> entityManager.find(PostDetails.class, randomPostDetails.getId()));
        assertThat(deletedPost, nullValue());
        assertThat(deletedPostDetails, nullValue());

    }

    @Test
    void testUpdatePost(){

        Post randomPost = PostDataGenerator.createRandomPost();
        PostDetails randomPostDetails = PostDataGenerator.createPostDetails();

        postDao.savePost(randomPost);
        Post managedPost = postDao.savePostDetails(randomPost.getId(), randomPostDetails);
        assertThat(managedPost.getId(), notNullValue());
        System.out.println("Post details ID: " + randomPostDetails.getPost().getId());

        managedPost.setName("My Post for Yuriy");
        randomPostDetails.setVisible(true);

        Post updatedPost = postDao.updatePost(managedPost);

        assertThat(updatedPost.getName(), equalTo("My Post for Yuriy"));
        assertThat(randomPostDetails.isVisible(), equalTo(true));

    }

    @Test
    void deletePostDetails(){
        Post randomPost = PostDataGenerator.createRandomPost();
        PostDetails randomPostDetails = PostDataGenerator.createPostDetails();

        randomPostDetails.setPost(randomPost);
        emUtil.performWithinTx(entityManager -> {
            entityManager.persist(randomPostDetails);
            entityManager.persist(randomPost);
        });
        emUtil.performWithinTx(entityManager -> {
            PostDetails managedPostDetails = entityManager.find(PostDetails.class, randomPostDetails.getId());
            entityManager.remove(managedPostDetails);

        });

        PostDetails updatedPostDetails = emUtil.performReturningWithinTx(entityManager -> entityManager.find(PostDetails.class, randomPostDetails.getId()));
        assertThat(updatedPostDetails, nullValue());
    }

    @Test
    void orphanRemovalTest(){
        Post randomPost = PostDataGenerator.createRandomPost();
        PostDetails randomPostDetails = PostDataGenerator.createPostDetails();

        randomPostDetails.setPost(randomPost);
        postDao.savePostDetails(randomPostDetails);
        emUtil.performWithinTx(entityManager -> {
            PostDetails managedPostDetails = entityManager.find(PostDetails.class, randomPostDetails.getId());
            entityManager.remove(managedPostDetails);
        });
    }
}
