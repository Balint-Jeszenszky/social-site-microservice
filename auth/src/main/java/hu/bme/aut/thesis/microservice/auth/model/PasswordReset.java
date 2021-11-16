package hu.bme.aut.thesis.microservice.auth.model;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "PasswordReset")
@Table(name = "password_reset")
public class PasswordReset {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false)
    private Integer id;

    @Column(name = "user_id", updatable = false)
    private Integer userId;

    @Column(name = "expiration", updatable = false)
    private Date expiration;

    @Column(name = "key", updatable = false, unique = true)
    private String key;

    public PasswordReset() {
    }

    public PasswordReset(Integer userId, Date expiration, String key) {
        this.userId = userId;
        this.expiration = expiration;
        this.key = key;
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

    public Date getExpiration() {
        return expiration;
    }

    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
