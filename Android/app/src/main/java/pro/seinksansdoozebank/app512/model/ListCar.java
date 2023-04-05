package pro.seinksansdoozebank.app512.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import pro.seinksansdoozebank.app512.views.MainActivity;

/**
 * Liste des voitures (utilisation du design pattern Singleton)
 */
public class ListCar extends ArrayList<Car> {
    /**
     * Objet de connection à l'API
     */
    InputStream inputStream;
    /**
     * Code de réponse de l'API
     */
    int responseCode;

    /**
     * Singleton de la liste des voitures
     */
    private static ListCar instance;

    /**
     * Indique si la liste des voitures a déjà été récupérée auprès de l'API
     */

    private boolean alreadyLoad = false;


    /**
     * Retourne l'instance de la liste des voitures
     *
     * @return le singleton de la liste des voitures
     */
    public static ListCar getInstance() {
        if (instance == null) {
            instance = new ListCar();
        }
        return instance;
    }

    /**
     * Indique si la liste des voitures a déjà été récupérée auprès de l'API
     * @return true si la liste des voitures a déjà été récupérée auprès de l'API
     */
    public static boolean requireLoading()
    {
        return !instance.alreadyLoad;
    }
    /**
     * Constructeur de la liste des voitures en faisant une requête à l'API
     */
    private ListCar() {
        super();
        //création d'un nouveau thread gérant la récupéation de la liste des voitures par le réseau pour ne pas bloquer l'interface
        new Thread(() -> {
            try {
                //création de la connexion à l'API
                URL url = new URL("http://64.225.109.223:443/allItems"); // Connexion par le port 443 car le port 80 est déjà utilisé pour un autre projet
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                //préparation de la requête
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Content-Type", "application/json; utf-8");
                connection.setRequestProperty("Accept", "application/json");
                //envoi de la requête
                responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    inputStream = connection.getInputStream();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            //lecture de la réponse
            if (inputStream != null) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                StringBuilder data = new StringBuilder();
                try {
                    while ((line = reader.readLine()) != null) {
                        data.append(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    inputStream.close();
                    if (data.length() > 0) {
                        //conversion de la réponse en JSONArray pour faciliter la lecture et l'ajout des voitures dans la liste
                        JSONArray jsonArray = new JSONArray(data.toString());
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            int id = jsonObject.getInt("id");
                            String nom = jsonObject.getString("nom");
                            String marque = jsonObject.getString("marque");
                            String description = jsonObject.getString("description");
                            double prix = jsonObject.getDouble("prix");
                            String image = jsonObject.getString("image");
                            Car car = new Car(id, nom, marque, description, prix, image);
                            this.add(car);
                        }
                       synchronized (MainActivity.sync) {
                            //confirmation que la liste des voitures a été récupérée
                           MainActivity.sync.notify();
                           alreadyLoad = true;
                       }
                 }

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }

}
