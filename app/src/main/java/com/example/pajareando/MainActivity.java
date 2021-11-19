package com.example.pajareando;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void goToBirdForm(View view) {
        Intent intent = new Intent(getApplicationContext(), BirdFormActivity.class);
        startActivity(intent);
    }
	    public void goToBirdConsultAll(View view) {
        Intent intent = new Intent(getApplicationContext(), BirdConsultAllActivity.class);
        startActivity(intent);
    }

    public void goToActivityBirdDetail(View view) {
        Intent intent = new Intent(getApplicationContext(), BirdDetailActivity.class);
        startActivity(intent);
    }
}