package com.example.pajareando;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

public class BirdFormActivity extends AppCompatActivity {

    AutoCompleteTextView autoCompleteBirdTypeView;
    AutoCompleteTextView autoCompleteBirdSizeView;

    final String []birdTypes =  {"Tipo 1", "Tipo 2", "Tipo 3"};
    final String []birdSize =  {"Pequeño", "Mediano", "Grande"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bird_form);

        autoCompleteBirdTypeView = findViewById(R.id.autoCompleteBirdType);
        autoCompleteBirdSizeView = findViewById(R.id.autoCompleteBirdSize);

        ArrayAdapter arrayAdapter1 = new ArrayAdapter(this, R.layout.bird_array, birdTypes);
        ArrayAdapter arrayAdapter2 = new ArrayAdapter(this, R.layout.bird_array, birdSize);

        autoCompleteBirdTypeView.setAdapter(arrayAdapter1);
        autoCompleteBirdTypeView.setText(arrayAdapter1.getItem(0).toString(), false);
        autoCompleteBirdSizeView.setText(arrayAdapter2.getItem(0).toString(), false);
        autoCompleteBirdSizeView.setAdapter(arrayAdapter2);
    }
}