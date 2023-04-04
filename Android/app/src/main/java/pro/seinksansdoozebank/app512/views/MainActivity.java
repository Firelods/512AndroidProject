package pro.seinksansdoozebank.app512.views;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
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
        ListCar.getInstance();
        if(!ListCar.isLoad())
        {
            synchronized (sync) {
                try {
                    Log.d("MainActivity", "Waiting for sync");
                    sync.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
//        JSONTool.savePurchaseToJSON("purchases.json", this.getApplicationContext(), 4, "Clément", "lefevre", "15-09-2022", "34 avenue saint augustin Nice");

        createNotificationChannel();
        ImageButton button = findViewById(R.id.purchases_button);
        button.setOnClickListener(v -> {
            Intent intent = new Intent(this, PurchasesActivity.class);
            startActivity(intent);
        });
        ListView listView = findViewById(R.id.car_list);
        listView.setAdapter(new CarAdapter(this));
    }

    @Override
    public void onClickProduct(Car item) {
        Intent intent = new Intent(this, CarDetailActivity.class);
        intent.putExtra("carId", item.getID());
        startActivity(intent);
    }

    @SuppressLint({"InflateParams", "ResourceType"})
    @Override
    public void onClickImage(Car item) {

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.image_pop_up, null);
        ImageView imageView = popupView.findViewById(R.id.bigger_image);
        Picasso.get().load(item.getImage()).into(imageView);

        PopupWindow popupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);

        popupView.setOnClickListener(v -> popupWindow.dismiss());

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