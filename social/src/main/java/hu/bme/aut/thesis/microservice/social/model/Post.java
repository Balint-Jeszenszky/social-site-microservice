package hu.bme.aut.thesis.microservice.social.model;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "Post")
@Table(name = "post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false)
    private Integer id;

    @Column(name = "user_id", nullable = false, updatable = false)
    private Integer userId;

    @Column(name = "text", nullable = false)
    private String text;

    @Column(name = "created", nullable = false, updatable = false)
    private Date created = new Date();

    @Column(name = "hasMedia", nullable = false)
    private Boolean hasMedia = Boolean.FALSE;

    @Column(name = "media_processed")
    private Boolean processedMedia;

    @Column(name = "media_name")
    private String mediaName;

    @Column(name = "comments", nullable = false)
    private Integer comments = 0;

    public Post() {}

    public Post(Integer userId, String text) {
        this.userId = userId;
        this.text = text;
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
        this.userId = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getCreated() {
        return created;
    }

    public Boolean getHasMedia() {
        return hasMedia;
    }

    public void setHasMedia(Boolean hasMedia) {
        this.hasMedia = hasMedia;
    }

    public Boolean getProcessedMedia() {
        return processedMedia;
    }

    public void setProcessedMedia(Boolean processedMedia) {
        this.processedMedia = processedMedia;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getMediaName() {
        return mediaName;
    }

    public void setMediaName(String mediaName) {
        this.mediaName = mediaName;
    }

    public Integer getComments() {
        return comments;
    }

    public void setComments(Integer comments) {
        this.comments = comments;
    }
}
