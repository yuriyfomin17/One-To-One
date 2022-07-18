package dao;

import model.Post;

public interface PostDao {
    /**
     * Receives a new instance of {@link Post} and 
     */
    void save(Post post);
}
