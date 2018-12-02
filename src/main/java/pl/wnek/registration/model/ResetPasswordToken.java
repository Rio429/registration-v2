package pl.wnek.registration.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class ResetPasswordToken {

    @Id
    @GeneratedValue
    private Long id;

    private String tokenText;
    private LocalDateTime createdDate;

    @OneToOne
    @JoinColumn
    private User user;

    public ResetPasswordToken() {}

    public ResetPasswordToken(String tokenText, LocalDateTime createdDate, User user) {
        this.tokenText = tokenText;
        this.createdDate = createdDate;
        this.user = user;
    }

    public String getTokenText() {
        return tokenText;
    }

    public void setTokenText(String tokenText) {
        this.tokenText = tokenText;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
