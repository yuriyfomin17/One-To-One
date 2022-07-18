package model;


import javax.persistence.*;

@Entity(name = "Post")
@Table(name = "post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;

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

    public void addPostDetails(PostDetails postDetails){
        this.postDetails = postDetails;
        postDetails.setPost(this);
    }

    public void removePostDetails(PostDetails postDetails){
        this.postDetails = null;
        postDetails.setPost(null);
    }
}
