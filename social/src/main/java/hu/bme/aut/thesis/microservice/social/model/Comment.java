package hu.bme.aut.thesis.microservice.social.model;

import javax.persistence.*;

@Entity(name = "Comment")
@Table(name = "comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false)
    private Integer id;

    @Column(name = "user_id", updatable = false, nullable = false)
    private Integer userId;

    @Column(name = "post_id", updatable = false, nullable = false)
    private Integer postId;

    @Column(name = "comment", nullable = false)
    private String comment;

    public Comment() {
    }

    public Comment(Integer userId, Integer postId, String comment) {
        this.userId = userId;
        this.postId = postId;
        this.comment = comment;
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
