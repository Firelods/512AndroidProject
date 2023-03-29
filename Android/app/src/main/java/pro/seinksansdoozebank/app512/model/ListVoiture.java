package pro.seinksansdoozebank.app512.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class ListVoiture extends ArrayList<Voiture> {


    /**
     * Creating a singleton a list of cars
     */
    private ListVoiture instance = null;
    public ListVoiture getInstance()
    {
        if (instance == null)
        {
            instance = new ListVoiture();
        }
        return instance;
    }




}
