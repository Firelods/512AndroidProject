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

public class TestFetch extends Thread {
    String data = "";

    public void run() {
        try {
            URL url = new URL("http://localhost:8080/allItems");

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
   /*
            InputStream inputStream = connection.getInputStream(); //FIXME

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;

            while ((line = reader.readLine()) != null) {
                data += line;
            }
            Log.d(data, "run: ");

            if (!data.isEmpty()) {
                JSONArray jsonArray = new JSONArray(data);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    System.out.println(jsonObject.getString("nom"));
                }
            }

             */

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
