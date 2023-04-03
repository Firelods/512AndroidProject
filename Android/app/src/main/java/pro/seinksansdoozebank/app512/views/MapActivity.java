package pro.seinksansdoozebank.app512.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.osmdroid.api.IMapController;
import org.osmdroid.config.IConfigurationProvider;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus;
import org.osmdroid.views.overlay.OverlayItem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import android.widget.ImageButton;

import pro.seinksansdoozebank.app512.R;

public class MapActivity extends AppCompatActivity {
    private EditText address;
    private Button buyButton;
    int responseCode;
    InputStream inputStream;
    private ArrayList<ArrayList<Double>> coordinates;
    private ArrayList<OverlayItem> points;
    private MapView map;
    private IMapController mapController;

    private final Object waiter = new Object();


    private final String api_free_key = "46c2f2b1018e200de3b74d95f0006e5c";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IConfigurationProvider provider = org.osmdroid.config.Configuration.getInstance();
        provider.load(getApplicationContext(), PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));
        setContentView(R.layout.activity_map);
        /*
        INITIALISATION
         */
        int carId = getIntent().getIntExtra("carId", 0);
        ImageButton backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> finish());
        this.coordinates = new ArrayList<>();



        buyButton = findViewById(R.id.valid_place_button);
        buyButton.setVisibility(View.INVISIBLE);
        this.address = findViewById(R.id.address);
        Button rechercher = findViewById(R.id.research);

        this.map = findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK); // Design pattern factory -> render
        map.setBuiltInZoomControls(true); // zoom -> true
        GeoPoint startPoint = new GeoPoint(48.8534, 2.3488); // Paris
        mapController = map.getController();
        mapController.setZoom(4.0);
        mapController.setCenter(startPoint);
        this.points = new ArrayList<>();


        /*
        ONCLICK LISTENER
         */
        rechercher.setOnClickListener(e->{
            //TODO trouver pq c'est lent parfois (ca fait crash)
            //TODO faire ne sorte que la touche Entrée face la recherche (par de l'async pcq la tout est bloqué
            final InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

            String txt = address.getText().toString();
            if(txt.length() > 0){
                map.getOverlays().clear();
                this.coordinates.clear();

                this.points.clear();
                research(txt);

                synchronized (waiter){
                    try {
                            waiter.wait();
                    } catch (InterruptedException interruptedException) {
                        interruptedException.printStackTrace();
                    }
                }
                if(this.points.size()>0)
                {
                    map.getController().animateTo(new GeoPoint(this.coordinates.get(0).get(0), this.coordinates.get(0).get(1)));
                    map.getController().setZoom(13.0);
                }
                else{
                    map.getController().setCenter(startPoint);
                    map.getController().setZoom(4.0);
                    Animation anim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_out);
                    buyButton.setAnimation(anim);
                    buyButton.setVisibility(View.INVISIBLE);

                    Toast toast = Toast.makeText(getApplicationContext(), "Adresse Invalide", Toast.LENGTH_SHORT);
                    toast.show();

                }

            }

        });
        buyButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, PaymentActivity.class);
            intent.putExtra("carId", carId);
            System.out.println("CAR ID : " + carId);
            intent.putExtra("latitude", this.coordinates.get(0).get(0));
            System.out.println("LATITUDE : " + this.coordinates.get(0).get(0));
            intent.putExtra("longitude", this.coordinates.get(0).get(1));
            System.out.println("LONGITUDE : " + this.coordinates.get(0).get(1));
            startActivity(intent);
        });
    }
    private void createListOfPoint()
    {
        for(int i =0;i<this.coordinates.size();i++)
        {
            int pts = i+1;
            OverlayItem point = new OverlayItem("Adresse " +pts+"/"+this.coordinates.size(), "Est-ce votre adresse ?", new GeoPoint(this.coordinates.get(i).get(0), this.coordinates.get(i).get(1)));

            Drawable marker = point.getMarker(0);
            point.setMarker(marker);
            this.points.add(point);
        }

        ItemizedOverlayWithFocus<OverlayItem> mOverlay =
                new ItemizedOverlayWithFocus<>(getApplicationContext(), this.points, new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
                    @Override
                    public boolean onItemSingleTapUp(int index, OverlayItem item) {
                        /*
                        ANIMATION
                         */
                        Animation anim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in);
                        buyButton.setAnimation(anim);
                        buyButton.setVisibility(View.VISIBLE);
                        return true;
                    }

                    @Override
                    public boolean onItemLongPress(int index, OverlayItem item) {
                        return false;
                    }
                });
        mOverlay.setFocusItemsOnTap(true);
        map.getOverlays().add(mOverlay);
        synchronized (waiter)
        {

            waiter.notify();
        }
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
                createListOfPoint();


            }

        }).start();
    }

    /*
    Gestion des ressources
     */

    @Override
    protected void onPause() {
        super.onPause();
        map.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        map.onResume();
    }
}
