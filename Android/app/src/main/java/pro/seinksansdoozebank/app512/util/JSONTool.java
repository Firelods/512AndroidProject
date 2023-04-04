package pro.seinksansdoozebank.app512.util;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class JSONTool {

    public static boolean savePurchaseToJSON(String fileName, Context context, int carId, String name, String lastName, String date,String adresse) {
        JSONObject root = JSONTool.readJSON(context, fileName);
        JSONArray jsonArray = root.optJSONArray("purchases");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("carId", carId);
            jsonObject.put("name", name);
            jsonObject.put("lastName", lastName);
            jsonObject.put("date", date);
            jsonObject.put("adresse", adresse);
            assert jsonArray != null;
            jsonArray.put(jsonObject);
            root.put("purchases", jsonArray);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        String jsonString = root.toString();
        try {
            FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            fos.write(jsonString.getBytes());
            fos.close();
            return true;
        } catch (IOException fileNotFound) {
            return false;
        }
    }

    public static JSONObject readJSON(Context context, String fileName) {

        StringBuilder sb = new StringBuilder();
        try {
            FileInputStream fis = context.openFileInput(fileName);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            fis.close();
        } catch (IOException fileNotFound) {
            Log.d(TAG, "le fichier n'existe pas");
        }

        try {
            if (sb.toString().equals("")) {
                JSONObject obj = new JSONObject();
                JSONArray arr = new JSONArray();
                obj.put("purchases", arr);
                return obj;
            }

            return new JSONObject(sb.toString());
        } catch (JSONException fileNotFound) {
            throw new RuntimeException(fileNotFound);
        }
    }
}
