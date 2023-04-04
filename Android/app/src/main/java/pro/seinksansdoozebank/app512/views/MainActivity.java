package pro.seinksansdoozebank.app512.views;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.PopupWindow;

import com.squareup.picasso.Picasso;


import pro.seinksansdoozebank.app512.R;
import pro.seinksansdoozebank.app512.model.ListCar;
import pro.seinksansdoozebank.app512.util.CarAdapter;
import pro.seinksansdoozebank.app512.model.Car;
import pro.seinksansdoozebank.app512.util.CarAdapterListener;
import pro.seinksansdoozebank.app512.util.JSONTool;

public class MainActivity extends AppCompatActivity implements CarAdapterListener {

    public static final Object sync = new Object();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // On charge la liste des voitures (il faudrait subscribe pour l avoir en asyncrone mais manque de temps donc on attends que la liste soit chargee et on l affiche)
        ListCar.getInstance();
        if(!ListCar.isLoad()) // Si la liste n est pas chargee on la charge
        {
            synchronized (sync) {
                try {
                    sync.wait(); // On attend que la liste soit chargee
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        createNotificationChannel(); // On cree le channel pour les notifications


        ImageButton button = findViewById(R.id.purchases_button); // On recupere le bouton pour aller sur la page des achats
        button.setOnClickListener(v -> {
            Intent intent = new Intent(this, PurchasesActivity.class); // Si on click sur un bouton on va sur la page des achats qui repertories nos commandes
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_from_right, R.anim.dontmove);
        });
        ListView listView = findViewById(R.id.car_list); // On recupere la liste des voitures
        listView.setAdapter(new CarAdapter(this)); // On lui definit son contenu grace au car Adapter
    }


    /**
     * Lorsqu un item de la liste est clique
     * @param item la voiture cliquee
     */
    @Override
    public void onClickProduct(Car item) {
        //On demarre une nouvelle activite qui affiche les details de la voiture
        Intent intent = new Intent(this, CarDetailActivity.class);
        intent.putExtra("carId", item.getID());
        startActivity(intent);

        overridePendingTransition(R.anim.slide_in_from_right, R.anim.dontmove);
    }

    /**
     *
     * Permet d afficher en grand l image de la voiture
     * @param item correspond a la voiture sur laquelle on clique sur l image
     */
    @Override
    public void onClickImage(Car item) {
        // On affiche une popup avec l image en plus grand
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE); // On recupere le layout inflater
        @SuppressLint("InflateParams") View popupView = inflater.inflate(R.layout.image_pop_up, null); // On recupere la vue de la popup
        ImageView imageView = popupView.findViewById(R.id.bigger_image); // On recupere l image de la popup
        Picasso.get().load(item.getImage()).into(imageView); // On charge l image de la voiture dans l image de la popup

        popupView.setBackgroundColor(Color.argb(180,0,0,0));


        /* On affiche la popup */
        PopupWindow popupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);


        Animation animationPopup = AnimationUtils.loadAnimation(this, R.anim.fade_in); // On charge l animation de la popup
        animationPopup.setDuration(200);
        imageView.startAnimation(animationPopup); // On demarre l animation de la popup





        // Si on clique dessus on l enleve
        popupView.setOnClickListener(v -> {
            popupWindow.dismiss();
        });

    }

    /**
     * Impletation de la methode getContext de l interface CarAdapterListener
     * @return contexte de l application
     */
    @Override
    public Context getContext() {
        return this.getApplicationContext();
    }

    /**
     * Permet de gerer le bouton de menu
     */
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }


    /**
     * Cree les notifications
     */
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            /* On manage le channel de notifications */
            NotificationChannel channel = new NotificationChannel(getString(R.string.channel_id), name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}