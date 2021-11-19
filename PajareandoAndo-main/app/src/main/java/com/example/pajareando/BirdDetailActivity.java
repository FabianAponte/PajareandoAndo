package com.example.pajareando;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import androidx.appcompat.app.AppCompatActivity;

public class BirdDetailActivity extends AppCompatActivity {

    AutoCompleteTextView autoCompleteBirdTypeView;
    AutoCompleteTextView autoCompleteBirdSizeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bird_detail);
    }

    public void goToConsultAll(View view) {
        Intent intent = new Intent(getApplicationContext(), BirdConsultAllActivity.class);
        startActivity(intent);
    }
}