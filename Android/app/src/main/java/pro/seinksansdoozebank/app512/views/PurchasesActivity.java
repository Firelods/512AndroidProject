package pro.seinksansdoozebank.app512.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.ImageButton;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import pro.seinksansdoozebank.app512.R;
import pro.seinksansdoozebank.app512.model.Purchase;
import pro.seinksansdoozebank.app512.util.JSONTool;
import pro.seinksansdoozebank.app512.util.PurchaseAdapter;

public class PurchasesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchases);
        ImageButton backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });
        fillListView();
    }

    private void fillListView() {
        JSONObject obj = JSONTool.readJSON(this.getApplicationContext(), "purchases.json");
        JSONArray arr;
        try {
            arr = obj.getJSONArray("purchases");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        ArrayList<Purchase> purchases = new ArrayList<>();
        for (int i = 0; i < arr.length(); i++) {
            try {
                System.out.println(arr.getJSONObject(i));
                purchases.add(new Purchase(arr.getJSONObject(i)));
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
        ListView listView = findViewById(R.id.purchase_list);
        PurchaseAdapter adapter = new PurchaseAdapter(this, purchases);
        listView.setAdapter(adapter);
    }


}