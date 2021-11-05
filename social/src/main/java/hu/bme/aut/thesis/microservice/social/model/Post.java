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
}
