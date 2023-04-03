package pro.seinksansdoozebank.app512.model;

import org.json.JSONException;
import org.json.JSONObject;

public class Purchase {
    private int carId;
    private String name;
    private String lastName;
    private String date;
    private double latitude;
    private double longitude;

    public Purchase(int carId, String name, String lastName, String date, double latitude, double longitude) {
        this.carId = carId;
        this.name = name;
        this.lastName = lastName;
        this.date = date;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Purchase(JSONObject obj) {
        try {
            this.carId = obj.getInt("carId");
            this.name = obj.getString("name");
            this.lastName = obj.getString("lastName");
            this.date = obj.getString("date");
            this.latitude = obj.getDouble("latitude");
            this.longitude = obj.getDouble("longitude");
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
        return "Lat: " + latitude + " Long: " + longitude;
    }

    public int getId() {
        return carId;
    }
}
