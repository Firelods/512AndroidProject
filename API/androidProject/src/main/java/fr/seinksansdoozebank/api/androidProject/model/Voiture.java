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



    public int getId()
    {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
