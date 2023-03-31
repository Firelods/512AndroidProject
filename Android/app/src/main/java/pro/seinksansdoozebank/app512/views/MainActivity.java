package pro.seinksansdoozebank.app512.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ListView;

import pro.seinksansdoozebank.app512.views.CarDetailActivity;
import pro.seinksansdoozebank.app512.R;
import pro.seinksansdoozebank.app512.util.CarAdapter;
import pro.seinksansdoozebank.app512.model.Car;
import pro.seinksansdoozebank.app512.model.ListCar;
import pro.seinksansdoozebank.app512.util.CarAdapterListener;

public class MainActivity extends AppCompatActivity implements CarAdapterListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageButton button = findViewById(R.id.purchases_button);
//        button.setOnClickListener(v -> ListCar.getInstance());
        ListView listView = findViewById(R.id.car_list);
        listView.setAdapter(new CarAdapter(this));
    }

    @Override
    public void onClickProduct(Car item) {
        Intent intent = new Intent(this, CarDetailActivity.class);
        intent.putExtra("carId", item.getID());
        startActivity(intent);
    }

    @Override
    public Context getContext() {
        return this.getApplicationContext();
    }
}