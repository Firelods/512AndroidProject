package pro.seinksansdoozebank.app512.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Voiture {
    private int id;
    private String nom;
    private String marque;
    private String description;
    private double prix;
    private String image;


    public Voiture(int id, String nom, String marque, String description, double prix, String image) {
        this.id = id;
        this.nom = nom;
        this.marque = marque;
        this.description = description;
        this.prix = prix;
        this.image = image;
    }



}
