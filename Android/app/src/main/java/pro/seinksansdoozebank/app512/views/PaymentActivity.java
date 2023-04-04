package pro.seinksansdoozebank.app512.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.text.HtmlCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.JsonWriter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Calendar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Date;

import pro.seinksansdoozebank.app512.R;
import pro.seinksansdoozebank.app512.util.JSONTool;

public class PaymentActivity extends AppCompatActivity {
    private DatePickerDialog datePickerDialog;
    private Button dateButton;

    private EditText firstName;
    private EditText cardNumber;
    private EditText cvv;
    private EditText dateExpiration;
    private EditText lastName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        ImageButton backButton = findViewById(R.id.back_button);
        int carId = getIntent().getIntExtra("carId", -1);
        String adresse = getIntent().getStringExtra("adresse");
        backButton.setOnClickListener(v -> finish());
        Button buyButton = findViewById(R.id.buy_button);
        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        cardNumber = findViewById(R.id.cardNumber);
        cvv = findViewById(R.id.cvv);
        dateExpiration = findViewById(R.id.expirationDate);

        initDatePicker();
        dateButton = findViewById(R.id.datePickerButton);
        dateButton.setText(getTodaysDate());
        dateButton.setOnClickListener(v -> datePickerDialog.show());

        buyButton.setOnClickListener(v -> {
            if (JSONTool.savePurchaseToJSON("purchases.json", this.getApplicationContext(), carId, String.valueOf(firstName.getText()), String.valueOf(lastName.getText()), String.valueOf(dateButton.getText()), adresse)) {
                checkPayement();

//                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                startActivity(intent);
            }
        });
    }


    private void sendConfirmationNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), getString(R.string.channel_id))
                .setSmallIcon(R.drawable.car)
                .setAutoCancel(true)
                .setContentTitle(HtmlCompat.fromHtml("<font color=\"" + getColor(R.color.blue) + "\"><b>" + getString(R.string.notification_title) + "</b></font>", HtmlCompat.FROM_HTML_MODE_LEGACY))
                .setContentText(getString(R.string.notification_text))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            Log.d("512Cars", "PERMISSION NOT GRANTED");
            return;
        }
        notificationManager.notify(1, builder.build());
    }


    private String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = makeDateString(day, month, year);
                dateButton.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);


        datePickerDialog = new DatePickerDialog(this, dateSetListener, year, month, day);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

    }

    private String makeDateString(int day, int month, int year) {
        return getMonthFormat(month) + " " + day + " " + year;
    }

    private String getMonthFormat(int month) {
        if (month == 1)
            return "JAN";
        if (month == 2)
            return "FEB";
        if (month == 3)
            return "MAR";
        if (month == 4)
            return "APR";
        if (month == 5)
            return "MAY";
        if (month == 6)
            return "JUN";
        if (month == 7)
            return "JUL";
        if (month == 8)
            return "AUG";
        if (month == 9)
            return "SEP";
        if (month == 10)
            return "OCT";
        if (month == 11)
            return "NOV";
        if (month == 12)
            return "DEC";

        //default should never happen
        return "JAN";
    }

    private void checkPayement() {
        boolean cardNumberValid = cardNumber.getText().toString().matches("^(?:4[0-9]{12}(?:[0-9]{3})?|[25][1-7][0-9]{14}|6(?:011|5[0-9][0-9])[0-9]{12}|3[47][0-9]{13}|3(?:0[0-5]|[68][0-9])[0-9]{11}|(?:2131|1800|35\\d{3})\\d{11})$");
        boolean firstNameValid=firstName.getText().toString().matches("^[a-zA-Z]+$");
        boolean lastNameValid=lastName.getText().toString().matches("^[a-zA-Z]+$");
        boolean cvvValid=cvv.getText().toString().matches("^[0-9]{3}$");
        boolean dateValid=dateExpiration.getText().toString().matches("^(0[1-9]|1[0-2])\\/([0-9]{2})$");
        if (cardNumberValid
                && firstNameValid
                && lastNameValid
                && cvvValid
                && dateValid
        ) {
            paymentValidate();

        } else {

            paymentRefuse(cardNumberValid, firstNameValid, lastNameValid, cvvValid, dateValid);
        }
    }

    private void paymentValidate() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Paiement");
        builder.setMessage("Paiement accepté");
        sendConfirmationNotification();

        builder.setPositiveButton("Yes", (dialog, which) -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });

        builder.show();
    }

    private void paymentRefuse(boolean cardValid, boolean firstNameValid, boolean lastNameValid, boolean cvvValid, boolean dateValid) {
//        Toast.makeText(getApplicationContext(), cardValid==false, Toast.LENGTH_LONG).show();
        // send a toast message with all the errors when present
        String message = "";
        if (!cardValid) {
            message += "Numéro de carte invalide\n";
        }
        if (!firstNameValid) {
            message += "Prénom invalide\n";
        }
        if (!lastNameValid) {
            message += "Nom invalide\n";
        }
        if (!cvvValid) {
            message += "CVV invalide\n";
        }
        if (!dateValid) {
            message += "Date expiration invalide";
        }
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();

    }

}
