package com.github.douglasmiguel7.bookflix.output;

public class TokenOutput {

    private String token;

    public TokenOutput(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TokenOutput{");
        sb.append("token='").append(token).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
