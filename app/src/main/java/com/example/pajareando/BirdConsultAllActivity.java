package com.example.pajareando;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.pajareando.models.Bird;

import java.io.File;
import java.util.ArrayList;

public class BirdConsultAllActivity extends AppCompatActivity {

    AutoCompleteTextView autoCompleteBirdTypeView;
    AutoCompleteTextView autoCompleteBirdSizeView;
    LinearLayout layout;

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bird_consult_all);

        int permission = ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    BirdConsultAllActivity.this,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }

        layout = findViewById(R.id.birdContainer);

        ArrayList<Bird> birds = Bird.getAll(getApplicationContext());

        for (Bird bird: birds) {
            addBirdToList(bird);
        }
    }

    public void goToActivityBirdDetail(View view, int id) {
        Intent intent = new Intent(getApplicationContext(), BirdDetailActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }

    public void goToMain(View view) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    private void addBirdToList(Bird bird) {
        View view = getLayoutInflater().inflate(R.layout.bird_list_item, null);

        TextView name = view.findViewById(R.id.birdName);
        Button detail = view.findViewById(R.id.birdDetail);
        ImageView imageView = view.findViewById(R.id.birdImage);
        name.setText(bird.getName());

        File imgFile = new File(bird.getImagePath());

        if (imgFile.exists()) {
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            imageView.setImageBitmap(myBitmap);
        }

        detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToActivityBirdDetail(view, bird.getId());
            }
        });

        layout.addView(view);
    }

}