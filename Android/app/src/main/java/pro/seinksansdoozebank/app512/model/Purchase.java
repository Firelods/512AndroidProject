package pro.seinksansdoozebank.app512.model;

import org.json.JSONException;
import org.json.JSONObject;

public class Purchase {
    private int carId;
    private String name;
    private String lastName;
    private String date;
    private String adresse;

    public Purchase(int carID, String prenom, String nom, String date, String adresse) {
        this.carId = carID;
        this.name = prenom;
        this.lastName = nom;
        this.date = date;
        this.adresse = adresse;
    }

    public String getMarque() {
        return ListCar.getInstance().get(carId).getMarque();
    }

    public String getCarName() {
        return ListCar.getInstance().get(carId).getName();
    }


    public double getPrice() {
        return ListCar.getInstance().get(carId).getPrice();
    }

    public String getDeliveryDate() {
        return date;
    }

    public String getDeliveryPlace() {
        return adresse;
    }

    public int getId() {
        return carId;
    }
}
