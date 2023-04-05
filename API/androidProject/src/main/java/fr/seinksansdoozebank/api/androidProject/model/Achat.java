package fr.seinksansdoozebank.api.androidProject.model;

public class Achat {
    private int carID;
    private String prenom;
    private String nom;
    private String date;
    private String adresse;


    public Achat(int carID, String prenom, String nom, String date, String adresse) {

        this.carID = carID;
        this.prenom = prenom;
        this.nom = nom;
        this.date = date;
        this.adresse = adresse;
    }

    public int getCarID() {
        return carID;
    }

    public void setCarID(int carID) {
        this.carID = carID;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    @Override
    public String toString() {
        return "Achat{" +
                "carID=" + carID +
                ", prenom='" + prenom + '\'' +
                ", nom='" + nom + '\'' +
                ", date='" + date + '\'' +
                ", adresse='" + adresse + '\'' +
                '}';
    }

}
