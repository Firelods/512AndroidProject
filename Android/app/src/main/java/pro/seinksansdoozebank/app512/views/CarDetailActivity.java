package pro.seinksansdoozebank.app512.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import pro.seinksansdoozebank.app512.R;
import pro.seinksansdoozebank.app512.model.Car;
import pro.seinksansdoozebank.app512.model.ListCar;

public class CarDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_detail);
        int carId = getIntent().getIntExtra("carId", 0);
        ImageButton backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> finish());

        TextView carName = findViewById(R.id.car_name);
        TextView carPrice = findViewById(R.id.car_price);
        TextView carDescription = findViewById(R.id.car_description);
        Car car = ListCar.getInstance().get(carId);
        carName.setText(String.format("%s %s",car.getMarque(),car.getName()));
        carPrice.setText(String.format("%.2f€",car.getPrice()));
        carDescription.setText(car.getDescription());

        Button buyButton = findViewById(R.id.buy_button);
        buyButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, MapActivity.class);
            intent.putExtra("carId", carId);
            startActivity(intent);
        });
    }
}