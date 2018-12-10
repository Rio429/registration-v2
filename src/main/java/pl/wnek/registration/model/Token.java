package pl.wnek.registration.model;

import pl.wnek.registration.dictionary.TokenType;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Token {

    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    private TokenType tokenType;

    private String tokenText;
    private LocalDateTime createdDate;
    private boolean enabled;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn
    private User user;

    public Token() {}

    public Token(TokenType tokenType, String tokenText, LocalDateTime createdDate, User user) {
        this.tokenType = tokenType;
        this.tokenText = tokenText;
        this.createdDate = createdDate;
        this.user = user;
        this.enabled = true;
    }

    public TokenType getTokenType() {
        return tokenType;
    }

    public void setTokenType(TokenType tokenType) {
        this.tokenType = tokenType;
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

    public boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Token{" +
                "id=" + id +
                ", tokenType=" + tokenType +
                ", tokenText='" + tokenText + '\'' +
                ", createdDate=" + createdDate +
                ", user=" + user +
                ", enabled=" + enabled +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Token)) return false;
        Token token = (Token) o;
        return getEnabled() == token.getEnabled() &&
                getTokenType() == token.getTokenType() &&
                Objects.equals(getTokenText(), token.getTokenText()) &&
                Objects.equals(getCreatedDate(), token.getCreatedDate()) &&
                Objects.equals(getUser(), token.getUser());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTokenType(), getTokenText(), getCreatedDate(), getEnabled(), getUser());
    }
}
