package util;
import model.PostDetails;
import org.apache.commons.lang3.RandomStringUtils;
import model.Post;

public class PostDataGenerator {

    public static Post createRandomPost(){
        Post post = new Post();
        post.setName(RandomStringUtils.randomAlphabetic(3));
        return post;
    }

    public static PostDetails createPostDetails(){
        PostDetails postDetails = new PostDetails();
        postDetails.setVisible(true);
        return postDetails;
    }
}
