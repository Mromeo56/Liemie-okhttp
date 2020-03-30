package com.example.liemie.http;

import android.util.Log;

import com.example.liemie.Patient;
import com.example.liemie.Visite;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.osmdroid.util.GeoPoint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutionException;

public class Modele {
    //http
    private Http http;
    private Async async;
    private Response response;

    // token
    private String token;

    // cookie
    private String cookie;


    public Modele() {
        http = new Http();
    }

    public String getCookie() {
        return this.cookie;
    }

    public String getToken() { return this.token; }

    public boolean Connexion(String username, String password) {
        async = new Async();

        http.addUrl("http://192.168.1.32:8000/auth/")
                .addBody("{\"mail\": \"" + username + "\", \"password\": \"" + password + "\"}", Http.Methode.POST);

        try {
            response = async.execute(http.build()).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String json = response.getBody();
        try {
            JSONObject jsonObject = new JSONObject(json);
            if (jsonObject.getString("Auth").equals("Ok")) {
                this.token = response.getHeader("Authorization");
                Log.i("info", token);
                this.cookie = response.getHeader("Set-Cookie");
                return true;
            }
        }
        catch (JSONException e) {
        }
        catch (Exception e) {
        }

        return false;
    }

    public ArrayList<Visite> getListeVisite() {
        ArrayList<Visite> vRetour = new ArrayList<Visite>();

        async = new Async();

        http.addUrl("http://192.168.1.32:8000/visite/")
                .addHeader("Authorization", token);

        try {
            response = async.execute(http.build()).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            JSONArray lesVisites = new JSONArray(response.getBody());
            for (int i = 0; i < lesVisites.length(); i++) {
                JSONObject visite = lesVisites.getJSONObject(i);
                Date date = null;
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    date = sdf.parse(visite.getString("date"));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                int id = visite.getInt("id");
                int id_patient = visite.getInt("id_patient");
                int duree = visite.getInt("duree");
                vRetour.add(new Visite(id, id_patient, this.GetPatientById(id_patient).getPrenom(), date, duree));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return vRetour;
    }

    public Patient GetPatientById(int unId) {
        Patient vRetour = null;

        async = new Async();

        http.addUrl("http://192.168.1.32:8000/patient/")
                .addHeader("Authorization", token);

        try {
            response = async.execute(http.build()).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            JSONArray lesPatients = new JSONArray(response.getBody());
            for (int i = 0; i < lesPatients.length(); i++) {
                JSONObject patient = lesPatients.getJSONObject(i);
                if (patient.getInt("id") == unId) {
                    int id = patient.getInt("id");
                    String prenom = patient.getString("prenom");
                    String nom = patient.getString("nom");
                    int age = patient.getInt("age");
                    String address = patient.getString("address");
                    String numTel = patient.getString("numtel");
                    vRetour = new Patient(id, nom, prenom, age, address, numTel);
                    break;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return vRetour;
    }

    public GeoPoint GetGeoPointByAdress(String address) {

        GeoPoint vRetour = null;

        async = new Async();

        String formattedAddress = address.replace(' ', '+');

        http.addUrl("http://nominatim.openstreetmap.org/search?q=" + formattedAddress + "&format=json");

        try {
            response = async.execute(http.build()).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            JSONArray lesBatiments = new JSONArray((response.getBody()));
            for (int i= 0; i < lesBatiments.length(); i++) {
                JSONObject batiment = lesBatiments.getJSONObject(i);
                vRetour = new GeoPoint(Double.parseDouble(batiment.getString("lat")), Double.parseDouble(batiment.getString("lon")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return vRetour;
    }
}
