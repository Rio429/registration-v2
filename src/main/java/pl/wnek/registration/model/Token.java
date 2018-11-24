package pl.wnek.registration.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Token {

    @Id
    @GeneratedValue
    private Long id;

    private String token;
    private LocalDateTime createdDate;

    @OneToOne
    @JoinColumn
    private User user;

    public Token() {}

    public Token(String token, LocalDateTime createdDate, User user) {
        this.token = token;
        this.createdDate = createdDate;
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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
}
