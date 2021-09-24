package hu.bme.aut.thesis.microservice.auth.model;

import javax.persistence.*;

@Entity(name = "email")
@Table(name = "email_verification")
public class EmailVerification {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false)
    private Integer id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "userId", nullable = false, unique = true)
    private Integer userId;

    @Column(name = "key", nullable = false, unique = true)
    private String key;

    public EmailVerification() {
    }

    public EmailVerification(String email, Integer userId, String key) {
        this.email = email;
        this.userId = userId;
        this.key = key;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
