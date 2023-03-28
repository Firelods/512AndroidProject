package fr.seinksansdoozebank.api.androidProject.model;

public class Voiture {

    private int id;
    private String nom;
    private String marque;
    private String description;

    private double prix;

    private String image;



    public Voiture(int id, String nom, String marque,double prix,String image,String description)
    {
        this.id=id;
        this.nom = nom;
        this.marque = marque;
        this.prix=prix;
        this.image = image;
        this.description = description;
    }


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

    public int getId()
    {
        return this.id;
    }
}
