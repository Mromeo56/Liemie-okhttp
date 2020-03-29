package com.example.liemie;

public class Patient {
    private int id;
    private String nom;
    private String prenom;
    private int age;

    public Patient (int unId, String unNom, String unPrenom, int unAge) {
        this.id = unId;
        this.nom = unNom;
        this.prenom = unPrenom;
        this.age = unAge;
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
}

