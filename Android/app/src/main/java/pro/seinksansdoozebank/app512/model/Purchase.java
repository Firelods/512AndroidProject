package pro.seinksansdoozebank.app512.model;

public class Purchase {
    private final int carId;

    private final String date;
    private final String adresse;

    public Purchase(int carID,String date, String adresse) {
        this.carId = carID;
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
