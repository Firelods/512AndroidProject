package pro.seinksansdoozebank.app512;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageButton;

import pro.seinksansdoozebank.app512.model.ListVoiture;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageButton button = findViewById(R.id.purchases_button);
        button.setOnClickListener(v -> ListVoiture.getInstance());
    }
}