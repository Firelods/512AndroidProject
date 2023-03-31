package pro.seinksansdoozebank.app512.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.text.HtmlCompat;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;

import pro.seinksansdoozebank.app512.R;

public class PaymentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        ImageButton backButton = findViewById(R.id.back_button);
        int carId = getIntent().getIntExtra("carId", 0);
        backButton.setOnClickListener(v -> finish());
        Button buyButton = findViewById(R.id.buy_button);
        buyButton.setOnClickListener(v -> {
            sendConfirmationNotification();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });
    }

    private void sendConfirmationNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, getString(R.string.channel_id))
                .setSmallIcon(R.drawable.car)
                .setAutoCancel(true)
                .setContentTitle(HtmlCompat.fromHtml("<font color=\"" + getColor(R.color.blue) + "\"><b>" + getString(R.string.notification_title) + "</b></font>", HtmlCompat.FROM_HTML_MODE_LEGACY))
                .setContentText(getString(R.string.notification_text))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            Log.d("512Cars", "PERMISSION NOT GRANTED");
            return;
        }
        notificationManager.notify(1, builder.build());
    }
}