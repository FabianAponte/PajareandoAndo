package com.example.pajareando;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pajareando.models.Bird;

public class BirdDetailActivity extends AppCompatActivity {

    AutoCompleteTextView autoCompleteBirdTypeView;
    AutoCompleteTextView autoCompleteBirdSizeView;

    Bird bird;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bird_detail);

        int id = getIntent().getExtras().getInt("id");

        bird = Bird.findById(getApplicationContext(), id);

        Toast.makeText(getApplicationContext(), bird.getName(), Toast.LENGTH_SHORT).show();
    }

    public void goToConsultAll(View view) {
        Intent intent = new Intent(getApplicationContext(), BirdConsultAllActivity.class);
        startActivity(intent);
    }
}