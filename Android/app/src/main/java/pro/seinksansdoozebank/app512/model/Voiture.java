package pro.seinksansdoozebank.app512.model;

import androidx.annotation.NonNull;

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
    private final int id;
    private final String nom;
    private final String marque;
    private final String description;
    private final double prix;
    private final String image;


    public Voiture(int id, String nom, String marque, String description, double prix, String image) {
        this.id = id;
        this.nom = nom;
        this.marque = marque;
        this.description = description;
        this.prix = prix;
        this.image = image;
    }

    @NonNull
    @Override
    public String toString() {
        return "Voiture{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", marque='" + marque + '\'' +
                ", description='" + description + '\'' +
                ", prix=" + prix +
                ", image='" + image + '\'' +
                '}';
    }
}
