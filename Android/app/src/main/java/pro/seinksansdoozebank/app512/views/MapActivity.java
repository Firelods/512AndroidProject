package pro.seinksansdoozebank.app512.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
import pro.seinksansdoozebank.app512.util.ToolBarFragment;

public class MapActivity extends AppCompatActivity {
    private EditText address;
    private Button buyButton;
    int responseCode;
    InputStream inputStream;
    private ArrayList<ArrayList<Double>> coordinates;
    private ArrayList<OverlayItem> points;
    private MapView map;

    private final GeoPoint startPoint = new GeoPoint(48.8534, 2.3488); // Paris


    private final Object waiter = new Object();

    /**
     * FREE API KEY du site positionstack.com
     */
    private final String api_free_key = "46c2f2b1018e200de3b74d95f0006e5c";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*
        Gestion des preferences / Config
         */
        IConfigurationProvider provider = org.osmdroid.config.Configuration.getInstance();
        provider.load(getApplicationContext(), PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));
        setContentView(R.layout.activity_map);
        /*
        INITIALISATION
         */
        int carId = getIntent().getIntExtra("carId", 0); //Recupere les extras de l'activité précédente

        ToolBarFragment toolBarFragment = new ToolBarFragment(v->finish(),getString(R.string.map_activity_title),28);
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,toolBarFragment).commit();

        this.coordinates = new ArrayList<>(); //  init de la liste des coordonnees des points correspondant a l adresse
        this.points = new ArrayList<>(); // init de la liste des points correspondant a l adresse (marker)
        /*
        Gestion du bouton d achat
         */
        buyButton = findViewById(R.id.valid_place_button);
        buyButton.setVisibility(View.INVISIBLE);

        /*
        Recuperation des ressources de la vue
         */
        this.address = findViewById(R.id.address);
        Button rechercher = findViewById(R.id.research);
        this.map = findViewById(R.id.map);

        rechercher.setEnabled(false);




        gestionMap(); // Gestion de la map




        /*
        Definition de la recherche d adresse
         */
        rechercher.setOnClickListener(e->{
            /*
            Repli automatique du clavier (cool)
             */
            final InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);


            String txt = address.getText().toString();//Recuperation du texte correspondant a l adresse a chercher
            // Si l adresse n est pas vide
            if(txt.length() > 0){

                /*
                Suppression des anciens marqueurs
                 */
                map.getOverlays().clear();
                this.coordinates.clear();
                this.points.clear();

                //Debut de la recherche (dans un autre thread)
                research(txt);



                // Une fois la recherche finie on devrait subscribe pour rendre le traitement async mais par manque de temps on attends que la recherche soit finie
                synchronized (waiter){
                    try {
                            waiter.wait();
                    } catch (InterruptedException interruptedException) {
                        interruptedException.printStackTrace();
                    }
                }

                // Une fois la recherche finie on affiche les marqueurs
                if(this.points.size()>0)
                {
                    map.getController().animateTo(new GeoPoint(this.coordinates.get(0).get(0), this.coordinates.get(0).get(1)));
                    map.getController().setZoom(13.0);
                }
                else{
                    // Ou si aucuns points n'a ete trouve on affiche un toast

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

        this.address.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(i== EditorInfo.IME_ACTION_DONE){
                    rechercher.performClick();
                    return true;
                }
                return true;
            }
        });

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length()>0){
                    rechercher.setEnabled(true);
                }
                else{
                    rechercher.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        };
        address.addTextChangedListener(textWatcher);

        // Dans le cas ou le buyButton est visible on lui ajoute un listener pour lancer l activite de paiement
        buyButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, PaymentActivity.class);
            intent.putExtra("carId", carId);
            intent.putExtra("adresse", this.address.getText().toString());
            startActivity(intent);
        });
    }

    /**
     * On cree une liste de points pour chaque coordonnees trouves
     */
    private void createListOfPoint()
    {
        /* Pour chaque coordonnees trouvees, on cree un point */
        for(int i =0;i<this.coordinates.size();i++)
        {
            int pts = i+1;
            OverlayItem point = new OverlayItem("Adresse " +pts+"/"+this.coordinates.size(), "Est-ce votre adresse ?", new GeoPoint(this.coordinates.get(i).get(0), this.coordinates.get(i).get(1)));
            Drawable marker = point.getMarker(0);
            point.setMarker(marker);
            this.points.add(point); // On ajoute a une liste de points
        }

        /* On ajoute un overlay avec les points ou si on clique sur un point on peut ensuite cliquer sur le bouton d achat  */
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
        /*
        On notifie le thread principal que la recherche est terminee
         */
        synchronized (waiter)
        {

            waiter.notify();
        }
    }


    /**
     * Recherche d une adresse
     * @param address adresse a rechercher
     */
    private void research(String address) {

        new Thread(() -> {
            try {
                //Connection a l API et emission de la query
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
            /* Si on a une reponse */
            if (inputStream != null) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                StringBuilder data = new StringBuilder();
                /* On recupere les donnees */
                try {
                    while ((line = reader.readLine()) != null) {
                        data.append(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    inputStream.close(); // Fermeture du flux (optimisation)
                    // Si on a des donnees trouvees
                    if (data.length() > 0) {
                        /*
                        Parse des donnees
                         */
                        JSONObject jsonObject = new JSONObject(data.toString());
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            ArrayList<Double> coord = new ArrayList<>();
                            coord.add(object.getDouble("latitude"));
                            coord.add(object.getDouble("longitude"));
                            this.coordinates.add(coord); // ajout de la longitudes et de la latitude dans la liste
                        }

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

    /**
    Gestion des ressources
     */

    @Override
    protected void onPause() {
        super.onPause();
        map.onPause();
    }
    /**
     Gestion des ressources
     */

    @Override
    protected void onResume() {
        super.onResume();
        map.onResume();
    }

    /**
     * Gestion de la map
     */
    private void gestionMap()
    {
        /* Animation de fade in pour la map */
        Animation mapAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        map.setAnimation(mapAnim);

        /* EntryPoint de la map */
        map.setTileSource(TileSourceFactory.MAPNIK); // Design pattern factory -> render
        map.setBuiltInZoomControls(true); // zoom -> true
        IMapController mapController = map.getController();
        mapController.setZoom(4.0);
        mapController.setCenter(startPoint);
    }

}
