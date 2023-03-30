package pro.seinksansdoozebank.app512.model;

import androidx.annotation.NonNull;

public class Car {
    private final int id;
    private final String nom;
    private final String marque;
    private final String description;
    private final double prix;
    private final String image;


    public Car(int id, String nom, String marque, String description, double prix, String image) {
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


    public int getID() {
        return id;
    }

    public String getName() {
        return nom;
    }

    public String getMarque() {
        return marque;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return prix;
    }

    public String getImage() {
        return image;
    }
}
