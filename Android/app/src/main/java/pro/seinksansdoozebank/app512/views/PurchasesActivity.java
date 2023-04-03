package pro.seinksansdoozebank.app512.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import pro.seinksansdoozebank.app512.R;
import pro.seinksansdoozebank.app512.util.JSONTool;

public class PurchasesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchases);
        System.out.println("PurchasesActivity.onCreate");
        File f = new File(getFilesDir()+"/"+"purchases.json");
        System.out.println(JSONTool.readJSON(this.getApplicationContext(), "purchases.json"));

    }


}