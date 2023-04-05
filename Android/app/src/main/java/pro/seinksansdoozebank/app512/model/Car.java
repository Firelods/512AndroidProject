package pro.seinksansdoozebank.app512.model;

import androidx.annotation.NonNull;

/**
 * Classe représentant une voiture vendue sur l'application
 */
public class Car {

    /**
     * Identifiant de la voiture (sa  position dans le singleton ListCar)
     */
    private final int id;
    /**
     * Modèle de la voiture
     */
    private final String nom;
    /**
     * Marque de la voiture
     */
    private final String marque;
    /**
     * Description de la voiture
     */
    private final String description;
    /**
     * Prix de vente de la voiture
     */
    private final double prix;
    /**
     * URL de l'image de la voiture
     */
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
