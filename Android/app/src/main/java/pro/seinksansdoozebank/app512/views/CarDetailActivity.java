package pro.seinksansdoozebank.app512.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainerView;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Locale;

import pro.seinksansdoozebank.app512.R;
import pro.seinksansdoozebank.app512.model.Car;
import pro.seinksansdoozebank.app512.model.ListCar;
import pro.seinksansdoozebank.app512.util.ToolBarFragment;

public class CarDetailActivity extends AppCompatActivity {

    /**
     * La voiture a afficher
     */
    private Car car;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_detail);

        // Recupere les donnees de l intent
        int carId = getIntent().getIntExtra("carId", 0);

        ToolBarFragment toolBarFragment = new ToolBarFragment(v->finish(), getString(R.string.detail_activity_title), 30);
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,toolBarFragment).commit();
        // Recupere les elements de la vue
        ImageView carImage = findViewById(R.id.car_image);
        TextView carName = findViewById(R.id.car_name);
        TextView carPrice = findViewById(R.id.car_price);
        TextView carDescription = findViewById(R.id.car_description);

        // Affiche les donnees de la voiture
        car = ListCar.getInstance().get(carId);
        Picasso.get().load(car.getImage()).into(carImage); // image async

        carName.setText(String.format("%s %s",car.getMarque(),car.getName()));
        carPrice.setText(String.format(Locale.FRANCE,"%.2f€",car.getPrice()));
        carDescription.setText(car.getDescription());


        // Ajoute un listener au bouton d achat pour ouvrir la carte de choix de l adresse
        Button buyButton = findViewById(R.id.buy_button);
        buyButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, MapActivity.class);
            intent.putExtra("carId", carId);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_from_right, R.anim.dontmove);
        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.dontmove, R.anim.slide_out_from_left);
    }
}