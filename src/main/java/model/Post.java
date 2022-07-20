package model;


import javax.persistence.*;
import java.util.Objects;

@Entity(name = "Post")
@Table(name = "post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name = "null";

    public Post(){}

    @OneToOne(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private PostDetails postDetails;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PostDetails getPostDetails() {
        return postDetails;
    }

    public void addPostDetails(PostDetails postDetails){
        this.postDetails = postDetails;
        postDetails.setPost(this);
    }

    public void removePostDetails(){
        this.postDetails.setPost(null);
        this.postDetails = null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return Objects.equals(id, post.id) && Objects.equals(name, post.name) && Objects.equals(postDetails, post.postDetails);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, postDetails);
    }
}
