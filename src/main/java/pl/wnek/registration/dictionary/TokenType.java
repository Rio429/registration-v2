package pl.wnek.registration.dictionary;

public enum TokenType {
    REGISTRATION("registration"),
    RESET_PASSWORD("reset-password");

    private String tokenType;

    TokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getTokenType() {
        return tokenType;
    }
}
