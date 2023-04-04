package pro.seinksansdoozebank.app512.model;

/**
 * Classe représentant un achat effectué sur l'application
 */
public class Purchase {
    /**
     * Identifiant de la voiture achetée
     */
    private final int carId;

    /**
     * Date de livraison de la voiture
     */
    private final String date;

    /**
     * Adresse de livraison de la voiture
     */
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
