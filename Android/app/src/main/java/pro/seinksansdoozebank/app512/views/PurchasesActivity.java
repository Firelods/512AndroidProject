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

public class PurchasesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchases);
        System.out.println("PurchasesActivity.onCreate");
        File f = new File(getFilesDir()+"/"+"purchases.json");
        System.out.println(readJSON(this.getApplicationContext(), "purchases.json"));

    }

    private String readJSON(Context context, String fileName) {

        StringBuilder sb = new StringBuilder();
        try {
            FileInputStream fis = context.openFileInput(fileName);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
                System.out.println(line);
            }
        } catch (FileNotFoundException fileNotFound) {
            throw new RuntimeException(fileNotFound);
        } catch (IOException ioException) {
            throw new RuntimeException(ioException);
        }

        try {
            JSONObject obj = new JSONObject(sb.toString());
            JSONArray arr = obj.getJSONArray("purchases");
            for (int i = 0; i < arr.length(); i++)
            {
                String name = arr.getJSONObject(i).getString("name");
                System.out.println(name);
            }
            return sb.toString();
        } catch (JSONException fileNotFound) {
            throw new RuntimeException(fileNotFound);
        }
    }


}