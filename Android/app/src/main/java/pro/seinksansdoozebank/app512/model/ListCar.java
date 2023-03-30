package pro.seinksansdoozebank.app512.model;

import android.util.Log;

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
     * Constructeur de la liste des voitures en faisant une requête à l'API
     */
    private ListCar() {
        super();
        new Thread(() -> {
            try {
                URL url = new URL("http://192.168.1.91:8080/allItems");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Content-Type", "application/json; utf-8");
                connection.setRequestProperty("Accept", "application/json");
                responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    inputStream = connection.getInputStream();
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
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
