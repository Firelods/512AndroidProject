package pro.seinksansdoozebank.app512.views;

import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ListView;

import pro.seinksansdoozebank.app512.R;
import pro.seinksansdoozebank.app512.model.ListCar;
import pro.seinksansdoozebank.app512.util.CarAdapter;
import pro.seinksansdoozebank.app512.model.Car;
import pro.seinksansdoozebank.app512.util.CarAdapterListener;

public class MainActivity extends AppCompatActivity implements CarAdapterListener {

    public static final Object sync = new Object();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListCar.getInstance();
        synchronized (sync) {
            try {
                Log.d("MainActivity", "Waiting for sync");
                sync.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        createNotificationChannel();
        ImageButton button = findViewById(R.id.purchases_button);
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

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }


    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(getString(R.string.channel_id), name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

}