package pro.seinksansdoozebank.app512.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import pro.seinksansdoozebank.app512.PaymentActivity;
import pro.seinksansdoozebank.app512.R;

public class MapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        int carId = getIntent().getIntExtra("carId", 0);
        Button buyButton = findViewById(R.id.valid_place_button);
        buyButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, PaymentActivity.class);
            intent.putExtra("carId", carId);
            startActivity(intent);
        });
    }
}