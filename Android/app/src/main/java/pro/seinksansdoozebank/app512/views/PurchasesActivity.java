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
import pro.seinksansdoozebank.app512.util.ToolBarFragment;

public class PurchasesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchases);

        ToolBarFragment toolBarFragment = new ToolBarFragment(v -> finish(), getString(R.string.purchases_activity_title), 32);
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,toolBarFragment).commit();

        // On remplie la liste des achats
        fillListView();
    }


    /**
     * Remplie la liste des achats
     */
    private void fillListView() {
        //Recuperation du JSON en local
        JSONObject obj = JSONTool.readJSON(this.getApplicationContext(), "purchases.json");
        JSONArray arr;
        try {
            arr = obj.getJSONArray("purchases");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        ArrayList<Purchase> purchases = new ArrayList<>();
        // On ajout dans une array list, tous les achats presents dans le JSON
        for (int i = 0; i < arr.length(); i++) {
            try {
                purchases.add(new Purchase(arr.getJSONObject(i)));
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }

        // Une fois tous les elements dans l arraylist on demarre l adapter avec tous les items
        ListView listView = findViewById(R.id.purchase_list);
        PurchaseAdapter adapter = new PurchaseAdapter(this, purchases);
        listView.setAdapter(adapter);
    }


}