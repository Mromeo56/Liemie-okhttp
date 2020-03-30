package com.example.liemie;

public class Patient {
    private int id;
    private String nom;
    private String prenom;
    private int age;
    private String address;

    public Patient (int unId, String unNom, String unPrenom, int unAge, String uneAddress) {
        this.id = unId;
        this.nom = unNom;
        this.prenom = unPrenom;
        this.age = unAge;
        this.address = uneAddress;
    }

    public int getId() {
        return this.id;
    }

    public String getNom() {
        return this.nom;
    }

    public String getPrenom() {
        return this.prenom;
    }

    public int getAge() {
        return this.age;
    }

    public String getAddress() {
        return this.address;
    }
}

