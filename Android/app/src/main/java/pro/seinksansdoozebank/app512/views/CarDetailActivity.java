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

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Locale;

import pro.seinksansdoozebank.app512.R;
import pro.seinksansdoozebank.app512.model.Car;
import pro.seinksansdoozebank.app512.model.ListCar;
import pro.seinksansdoozebank.app512.util.ToolBarFragment;

public class CarDetailActivity extends AppCompatActivity {

    private Bitmap carBitmap;
    private final Object synchro = new Object();
    private Car car;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_detail);
        int carId = getIntent().getIntExtra("carId", 0);

        ToolBarFragment toolBarFragment = new ToolBarFragment(v->finish(), getString(R.string.detail_activity_title), 30);
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,toolBarFragment).commit();

        ImageView carImage = findViewById(R.id.car_image);
        TextView carName = findViewById(R.id.car_name);
        TextView carPrice = findViewById(R.id.car_price);
        TextView carDescription = findViewById(R.id.car_description);

        car = ListCar.getInstance().get(carId);
        new Thread(()->{
            try {
                synchronized (synchro){

                    this.carBitmap = BitmapFactory.decodeStream((InputStream)new URL(car.getImage()).getContent());
                    synchro.notify();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();
        synchronized (synchro){
            try {
                synchro.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            carImage.setImageBitmap(this.carBitmap);
        }
        carName.setText(String.format("%s %s",car.getMarque(),car.getName()));
        carPrice.setText(String.format(Locale.FRANCE,"%.2f€",car.getPrice()));
        carDescription.setText(car.getDescription());

        Button buyButton = findViewById(R.id.buy_button);
        buyButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, MapActivity.class);
            intent.putExtra("carId", carId);
            startActivity(intent);
        });
    }


}