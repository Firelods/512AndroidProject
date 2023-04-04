package pro.seinksansdoozebank.app512.views;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

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

import pro.seinksansdoozebank.app512.R;
import pro.seinksansdoozebank.app512.model.Purchase;
import pro.seinksansdoozebank.app512.util.PurchaseAdapter;
import pro.seinksansdoozebank.app512.util.ToolBarFragment;

public class PurchasesActivity extends AppCompatActivity {
    private int responseCode;
    private InputStream inputStream;
    private ArrayList<Purchase> purchases ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchases);
        //initialsation
        purchases = new ArrayList<>();

        ToolBarFragment toolBarFragment = new ToolBarFragment(v -> finish(), getString(R.string.purchases_activity_title), 32);
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,toolBarFragment).commit();

        // On remplie la liste des achats
        fillListView();

    }


    /**
     * Remplie la liste des achats
     */
    private void fillListView() {
        new Thread(() -> {
            try {
                URL url = new URL("http://64.225.109.223:443/display"); // Port 80 already used
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
                            int carID = jsonObject.getInt("carID");
                            String date = jsonObject.getString("date");
                            String adresse = jsonObject.getString("adresse");
                            Purchase purchase = new Purchase(carID, date, adresse);
                            System.out.println(purchase);
                            purchases.add(purchase);
                        }

                        // Une fois tous les elements dans l arraylist on demarre l adapter avec tous les items
                        runOnUiThread(() -> {
                            ListView listView = findViewById(R.id.purchase_list);
                            PurchaseAdapter adapter = new PurchaseAdapter(this, purchases);
                            listView.setAdapter(adapter);
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();


    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.dontmove, R.anim.slide_out_from_left);

    }


}