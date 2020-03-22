package com.example.liemie;

public class Utilisateur {
    private int id;
    private String mail;
    private String passWord;

    public Utilisateur(int unId, String unMail, String unPassWord) {
        this.id = unId;
        this.mail = unMail;
        this.passWord = unPassWord;
    }

    public String getMail() {
        return this.mail;
    }

    public String getPassWord() {
        return this.passWord;
    }
}
