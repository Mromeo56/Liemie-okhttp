package com.example.liemie;

import com.example.liemie.http.Modele;

import java.util.Date;

public class Visite {
    private int id;
    private int idPatient;
    private Date date;
    private int duree;
    private String prenomPatient;

    public Visite(int unId, int unIdPatient, String unPrenomPatient, Date uneDate, int uneDuree) {
        this.id = unId;
        this.idPatient = unIdPatient;
        this.date = uneDate;
        this.duree = uneDuree;
        this.prenomPatient = unPrenomPatient;
    }

    public int getId() {
        return this.id;
    }

    public int getIdPatient() {
        return this.idPatient;
    }

    public Date getDate() {
        return this.date;
    }

    public int getDuree() {
        return this.duree;
    }

    public String getPrenomPatient() {
        return this.prenomPatient;
    }
}
