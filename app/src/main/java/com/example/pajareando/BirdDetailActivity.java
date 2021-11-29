package com.example.pajareando;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pajareando.models.Bird;

import java.io.File;

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

        ImageView birdPhoto = findViewById(R.id.birdImage);

        File imgFile = new File(bird.getImagePath());

        if (imgFile.exists()) {
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            birdPhoto.setImageBitmap(myBitmap);
        }

        TextView nameBird = findViewById(R.id.textNameBird);
        nameBird.setText(bird.getName());
        Toast.makeText(getApplicationContext(), bird.getName(), Toast.LENGTH_SHORT).show();
        Toast.makeText(getApplicationContext(), bird.getMoreColors(), Toast.LENGTH_SHORT).show();


        TextView photoDate = findViewById(R.id.textDatePhoto);
        photoDate.setText(bird.getPhotoDate());

        TextView birdLocation = findViewById(R.id.textLocationBird);
        birdLocation.setText(getString(R.string.ubicacion_del_ave) + bird.getLatitud() + ", "+ bird.getLongitud());

        TextView birdType = findViewById(R.id.textTypeBird);
        birdType.setText(getString(R.string.bird_type) + ": " + bird.getType());

        TextView sizeBird = findViewById(R.id.textSizeBird);
        sizeBird.setText(getString(R.string.bird_size) + ": " + bird.getSize());

        TextView coloresAve = findViewById(R.id.textColorsBird);
        coloresAve.setText(getString(R.string.Colores) + bird.getColor1() + " " + bird.getColor2() + " " + bird.getColor3() + " " + bird.getColor4());

        TextView mayorcuatrocolores = findViewById(R.id.textMoreColorsBird);
        mayorcuatrocolores.setText(bird.getMoreColors());

        TextView reviewBird = findViewById(R.id.textReviewBird);
        reviewBird.setText(getString(R.string.descripcion_del_ave) + " "+ bird.getReview());


    }




    public void goToConsultAll(View view) {
        Intent intent = new Intent(getApplicationContext(), BirdConsultAllActivity.class);
        startActivity(intent);
    }
}