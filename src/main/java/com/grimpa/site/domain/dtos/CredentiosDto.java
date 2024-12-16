package com.grimpa.site.domain.dtos;

public class CredentiosDto {

    private String email;
    private String senha;

    public CredentiosDto() {
    }

    public CredentiosDto(String email, String senha) {
        this.email = email;
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
