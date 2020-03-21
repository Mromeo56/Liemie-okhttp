package com.example.liemie;

import java.util.Date;

public class Visite {
    private int id;
    private int idPatient;
    private Date date;
    private int duree;

    public Visite(int unId, int unIdPatient, Date uneDate, int uneDuree) {
        this.id = unId;
        this.idPatient = unIdPatient;
        this.date = uneDate;
        this.duree = uneDuree;
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
}
