package dao;

import model.Post;
import model.PostDetails;

public interface PostDao {
    /**
     * Receives a new instance of {@link Post} and saves it
     */
    void savePost(Post post);

    /**
     * Receives a new instance of {@link PostDetails} and saves it
     */
    void savePostDetails(PostDetails post);

    /**
     * Receives a new instance of {@link PostDetails} , post id and saves it
     */
    Post savePostDetails(long postId, PostDetails postDetails);

    /**
     * Receives a new instance of {@link Post} and updates it
     */
    Post updatePost(Post post);

    /**
     * Receives a instance of {@link Post} and deletes it
     */
    void deletePost(Post post);
}
