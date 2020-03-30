package com.example.liemie;

public class Patient {
    private int id;
    private String nom;
    private String prenom;
    private int age;
    private String address;
    private String numTel;

    public Patient (int unId, String unNom, String unPrenom, int unAge, String uneAddress, String unNumTel) {
        this.id = unId;
        this.nom = unNom;
        this.prenom = unPrenom;
        this.age = unAge;
        this.address = uneAddress;
        this.numTel = unNumTel;
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

    public String getNumTel() {
        return this.numTel;
    }
}

