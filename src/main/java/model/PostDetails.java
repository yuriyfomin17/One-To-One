package model;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity(name = "PostDetails")
@Table(name = "post_details")
public class PostDetails {
    @Id
    private Long id;

    @Column(name = "created_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn = new Date();
    private boolean visible = false;
    @OneToOne
    @MapsId
    private Post post;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public void setPost(Post post) {
        this.post = post;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostDetails that = (PostDetails) o;
        return visible == that.visible && Objects.equals(id, that.id) && Objects.equals(createdOn, that.createdOn) && Objects.equals(post, that.post);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, createdOn, visible, post);
    }
}
