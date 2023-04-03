package pro.seinksansdoozebank.app512.model;

import org.json.JSONException;
import org.json.JSONObject;

public class Purchase {
    private int carId;
    private String name;
    private String lastName;
    private String date;
    private String adresse;



    public Purchase(JSONObject obj) {
        try {
            this.carId = obj.getInt("carId");
            this.name = obj.getString("name");
            this.lastName = obj.getString("lastName");
            this.date = obj.getString("date");
            this.adresse = obj.getString("adresse");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
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
