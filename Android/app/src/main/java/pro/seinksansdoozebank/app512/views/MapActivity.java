package pro.seinksansdoozebank.app512.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import pro.seinksansdoozebank.app512.PaymentActivity;
import pro.seinksansdoozebank.app512.R;
import pro.seinksansdoozebank.app512.model.Car;

public class MapActivity extends AppCompatActivity {
    private EditText address;
    InputStream inputStream;
    int responseCode;
    ArrayList<ArrayList<Double>> coordinates;


    private final String api_free_key = "46c2f2b1018e200de3b74d95f0006e5c";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        int carId = getIntent().getIntExtra("carId", 0);
        this.coordinates = new ArrayList<>();
        Button buyButton = findViewById(R.id.valid_place_button);
        buyButton.setEnabled(false);
        this.address = findViewById(R.id.address);
        Button rechercher = findViewById(R.id.research);
        rechercher.setOnClickListener(e->{
            String txt = address.getText().toString();
            if(txt.length() > 0){
                this.coordinates.clear();
                research(txt);
                buyButton.setEnabled(true);

            }

        });
        buyButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, PaymentActivity.class);
            intent.putExtra("carId", carId);
            startActivity(intent);
        });
    }

    private void research(String address) {
        new Thread(() -> {
            try {
                URL url = new URL("http://api.positionstack.com/v1/forward?access_key="+this.api_free_key+"&query="+address+"&results.map_url");
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
                        JSONObject jsonObject = new JSONObject(data.toString());
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            ArrayList<Double> coord = new ArrayList<>();
                            coord.add(object.getDouble("latitude"));
                            coord.add(object.getDouble("longitude"));
                            this.coordinates.add(coord);
                        }
                        Log.d("coord", coordinates.toString());

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
