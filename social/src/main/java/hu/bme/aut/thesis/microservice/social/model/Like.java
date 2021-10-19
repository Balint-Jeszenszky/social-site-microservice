package hu.bme.aut.thesis.microservice.social.model;

import javax.persistence.*;

@Entity(name = "Like")
@Table(name = "like")
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false)
    private Integer id;

    @Column(name = "user_id", updatable = false, nullable = false)
    private Integer userId;

    @Column(name = "post_id", updatable = false, nullable = false)
    private Integer postId;

    public Like() {
    }

    public Like(Integer userId, Integer postId) {
        this.userId = userId;
        this.postId = postId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }
}
