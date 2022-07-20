package dao;

import model.Post;
import model.PostDetails;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.function.Consumer;
import java.util.function.Function;

public class PostDaoImpl implements PostDao {
    private final EntityManagerFactory emf;

    public PostDaoImpl(EntityManagerFactory entityManagerFactory) {
        emf = entityManagerFactory;
    }

    @Override
    public void savePost(Post post) {
        performWithinPersistentContext(entityManager -> entityManager.persist(post));
    }


    @Override
    public Post savePostDetails(long postId, PostDetails postDetails) {
        return performReturningWithinPersistentContext(entityManager -> {
            Post post = entityManager.find(Post.class, postId);
            post.addPostDetails(postDetails);
            return post;
        });
    }


    @Override
    public Post updatePost(Post post) {
        return performReturningWithinPersistentContext(entityManager -> entityManager.merge(post));
    }

    @Override
    public void deletePost(Post post) {
        performWithinPersistentContext(entityManager -> {
            Post managedPost = entityManager.merge(post);
            entityManager.remove(managedPost);
        });
    }

    private void performWithinPersistentContext(Consumer<EntityManager> entityManagerConsumer) {
        performReturningWithinPersistentContext(entityManager -> {
            entityManagerConsumer.accept(entityManager);
            return null;
        });
    }

    private <T> T performReturningWithinPersistentContext(Function<EntityManager, T> entityManagerTFunction) {
        EntityManager entityManager = this.emf.createEntityManager();
        entityManager.getTransaction().begin();
        T result;
        try {
            result = entityManagerTFunction.apply(entityManager);
            entityManager.getTransaction().commit();
            return result;
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw e;
        } finally {
            entityManager.close();
        }
    }
}
