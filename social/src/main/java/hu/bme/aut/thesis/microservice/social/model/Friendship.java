package hu.bme.aut.thesis.microservice.social.model;

import javax.persistence.*;

@Entity(name = "Friendship")
@Table(name = "friendship")
public class Friendship {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false)
    private Integer id;

    @Column(name = "user_id", nullable = false, updatable = false)
    private Integer firstUserId;

    @Column(name = "user_id", nullable = false, updatable = false)
    private Integer secondUserId;

    @Column(name = "pending", nullable = false)
    private Boolean pending = true;

    Friendship() {}

    public Friendship(Integer firstUserId, Integer secondUser) {
        this.firstUserId = firstUserId;
        this.secondUserId = secondUser;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFirstUserId() {
        return firstUserId;
    }

    public void setFirstUserId(Integer firstUserId) {
        this.firstUserId = firstUserId;
    }

    public Integer getSecondUserId() {
        return secondUserId;
    }

    public void setSecondUserId(Integer secondUserId) {
        this.secondUserId = secondUserId;
    }

    public Boolean getPending() {
        return pending;
    }

    public void setPending(Boolean pending) {
        this.pending = pending;
    }
}
