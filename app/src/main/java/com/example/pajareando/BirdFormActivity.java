package com.example.pajareando;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;

public class BirdFormActivity extends AppCompatActivity {

    AutoCompleteTextView autoCompleteBirdTypeView;
    AutoCompleteTextView autoCompleteBirdSizeView;

    final String []birdTypes =  {"Tipo 1", "Tipo 2", "Tipo 3"};
    final String []birdSize =  {"Pequeño", "Mediano", "Grande"};

    boolean hasImage = false;
    Button registerButton;
    ImageView birdImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bird_form);

        registerButton = findViewById(R.id.registerButton);
        birdImage = findViewById(R.id.birdImage);

        startCombos();
    }

    private void startCombos() {
        autoCompleteBirdTypeView = findViewById(R.id.autoCompleteBirdType);
        autoCompleteBirdSizeView = findViewById(R.id.autoCompleteBirdSize);

        ArrayAdapter arrayAdapter1 = new ArrayAdapter(this, R.layout.bird_array, birdTypes);
        ArrayAdapter arrayAdapter2 = new ArrayAdapter(this, R.layout.bird_array, birdSize);

        autoCompleteBirdTypeView.setAdapter(arrayAdapter1);
        autoCompleteBirdTypeView.setText(arrayAdapter1.getItem(0).toString(), false);
        autoCompleteBirdSizeView.setText(arrayAdapter2.getItem(0).toString(), false);
        autoCompleteBirdSizeView.setAdapter(arrayAdapter2);
    }

    public void goToMain(View view) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    public void register(View view) {
        if (!hasImage) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if(intent.resolveActivity(getPackageManager()) != null){
                startActivityForResult(intent, 1);
            }
        } else {
            this.goToMain(view);
        }
    }

    protected void onActivityResult(int requestCode,
                                    int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imgBitmap = (Bitmap) extras.get("data");
            birdImage.setImageBitmap(imgBitmap);
            hasImage = true;
        }
    }
}