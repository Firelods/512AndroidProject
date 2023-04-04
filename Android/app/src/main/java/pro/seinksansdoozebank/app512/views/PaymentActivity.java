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

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
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
import pro.seinksansdoozebank.app512.util.ToolBarFragment;

public class PaymentActivity extends AppCompatActivity {
    private DatePickerDialog datePickerDialog;
    private Button dateButton;
    private EditText firstName;
    private EditText cardNumber;
    private EditText cvv;
    private EditText dateExpiration;
    private EditText lastName;
    private int responseCode;
    private InputStream inputStream;
    private String adresse;
    private int carId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        ToolBarFragment toolBarFragment = new ToolBarFragment(v->finish(),getString(R.string.payment_activity_title),32);
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,toolBarFragment).commit();


        carId = getIntent().getIntExtra("carId", -1);
        adresse = getIntent().getStringExtra("adresse");
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
            }
        });
    }

    /**
     * Fontion qui envoie une notification de confirmation de commande
     */
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

    /**
     * Reinitialise le DatePicker à la date du jour
     *
     * @return La date du jour
     */
    private String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    /**
     * Initialise le DatePicker à la date du jour
     */
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
    }

    /**
     * Fonction qui transforme la date en format String
     *
     * @param day   Jour
     * @param month Mois
     * @param year  Année
     * @return La date en format String
     */
    private String makeDateString(int day, int month, int year) {
        return day + " " + getMonthFormat(month) + " " + year;
    }

    /**
     * Fonction qui transforme le mois en format String
     *
     * @param month Mois
     * @return Le mois en format String
     */
    private String getMonthFormat(int month) {
        if (month == 1)
            return "JAN";
        if (month == 2)
            return "FEV";
        if (month == 3)
            return "MAR";
        if (month == 4)
            return "AVR";
        if (month == 5)
            return "MAI";
        if (month == 6)
            return "JUIN";
        if (month == 7)
            return "JUL";
        if (month == 8)
            return "AOUT";
        if (month == 9)
            return "SEP";
        if (month == 10)
            return "OCT";
        if (month == 11)
            return "NOV";
        if (month == 12)
            return "DEC";
        return "JAN";
    }

    /**
     * Fonction qui vérifie si les informations de paiement sont valides
     */
    private void checkPayement() {
        boolean cardNumberValid = cardNumber.getText().toString().matches("^(?:4[0-9]{12}(?:[0-9]{3})?|[25][1-7][0-9]{14}|6(?:011|5[0-9][0-9])[0-9]{12}|3[47][0-9]{13}|3(?:0[0-5]|[68][0-9])[0-9]{11}|(?:2131|1800|35\\d{3})\\d{11})$");
        boolean firstNameValid = firstName.getText().toString().matches("^[a-zA-Z]+$");
        boolean lastNameValid = lastName.getText().toString().matches("^[a-zA-Z]+$");
        boolean cvvValid = cvv.getText().toString().matches("^[0-9]{3}$");
        boolean dateValid = dateExpiration.getText().toString().matches("^(0[1-9]|1[0-2])\\/([0-9]{2})$");
        if (cardNumberValid
                && firstNameValid
                && lastNameValid
                && cvvValid
                && dateValid
        ) { // Si toutes les informations sont valides
            paymentValidate();

        } else { // Si une ou plusieurs informations sont invalides
            paymentRefuse(cardNumberValid, firstNameValid, lastNameValid, cvvValid, dateValid);
        }
    }

    /**
     * Fonction qui affiche une boite de dialogue de confirmation de paiement et qui renvoie vers la mainActivity après validation
     */
    private void paymentValidate() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Paiement");
        builder.setMessage("Paiement accepté");
        sendConfirmationNotification();
        new Thread(() -> {
            try {
                URL url = new URL("http://64.225.109.223:443/ajouter");// TODO : URL du POST de l'API
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json; utf-8");
                connection.setRequestProperty("Accept", "application/json");
                // send the post body with carId", c
                //name"
                //lastName"
                //date"
                //adresse"

                connection.setDoOutput(true);
                String jsonInputString = "{\"carID\": \"" + carId + "\", \"prenom\": \"" + firstName.getText().toString() + "\", \"nom\": \"" + lastName.getText().toString() + "\", \"date\": \"" + dateButton.getText().toString() + "\", \"adresse\": \"" + adresse + "\"}";
                try (OutputStream os = connection.getOutputStream()) {
                    byte[] input = jsonInputString.getBytes("utf-8");
                    os.write(input, 0, input.length);
                }

                responseCode = connection.getResponseCode();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();


        builder.setPositiveButton("Yes", (dialog, which) -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_from_right, R.anim.dontmove);
        });

        builder.show();
    }

    /**
     * Fonction qui affiche une boite de dialogue d'erreur de paiement
     *
     * @param cardValid      Numéro de carte valide
     * @param firstNameValid Prénom valide
     * @param lastNameValid  Nom valide
     * @param cvvValid       CVV valide
     * @param dateValid      Date valide
     */
    private void paymentRefuse(boolean cardValid, boolean firstNameValid, boolean lastNameValid, boolean cvvValid, boolean dateValid) {
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

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.dontmove, R.anim.slide_out_from_left);
    }
}
