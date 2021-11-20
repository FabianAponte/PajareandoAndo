package com.example.pajareando;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pajareando.models.Bird;
import com.example.pajareando.models.ModelDb;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class BirdFormActivity extends AppCompatActivity {

    AutoCompleteTextView autoCompleteBirdTypeView;
    AutoCompleteTextView autoCompleteBirdSizeView;

    final String []birdTypes =  {"Tipo 1", "Tipo 2", "Tipo 3"};
    final String []birdSize =  {"Pequeño", "Mediano", "Grande"};

    boolean hasImage = false;
    Button registerButton;
    ImageView birdImage;
    Bitmap imgBitmap;
    EditText birdName, color1, color2, color3, color4;
    AutoCompleteTextView birdTypeInput, birdSizeInput;
    CheckBox hasMoreColors;
    TextView review;
    String absoluteImagePath;

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bird_form);

        int permission = ActivityCompat.checkSelfPermission(BirdFormActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    BirdFormActivity.this,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }

        registerButton = findViewById(R.id.registerButton);
        birdImage = findViewById(R.id.birdImage);

        startCombos();

        birdName = findViewById(R.id.birdName);
        birdTypeInput = findViewById(R.id.autoCompleteBirdType);
        birdSizeInput = findViewById(R.id.autoCompleteBirdSize);
        color1 = findViewById(R.id.color1);
        color2 = findViewById(R.id.color2);
        color3 = findViewById(R.id.color3);
        color4 = findViewById(R.id.color4);
        hasMoreColors = findViewById(R.id.hasMoreColors);
        review = findViewById(R.id.review);

//        ArrayList<Bird> birds = Bird.getAll(getApplicationContext());
//
//        for (Bird bird: birds) {
//            Log.i("birds", bird.toString());
//        }
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
            if (verifyInfo()) {
                try {
                    saveBirdImage();
                    Bird bird = new Bird(
                            birdName.getText().toString(),
                            birdTypeInput.getText().toString(),
                            birdSizeInput.getText().toString(),
                            color1.getText().toString(),
                            color2.getText().toString(),
                            color3.getText().toString(),
                            color4.getText().toString(),
                            hasMoreColors.isChecked(),
                            review.getText().toString(),
                            "absoluteImagePath",
                            getApplicationContext()
                    );
                    bird.save();

                    Toast.makeText(getApplicationContext(), "Información de ave guardada", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(getApplicationContext(), "Falta información necesaria", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Verifica si falta información obligatoria
     *
     * @return bool
     */
    private boolean verifyInfo() {
        if (birdName.getText().toString().isEmpty()) return false;

        return true;
    }

    protected void saveBirdImage() throws Exception {
        String path = Environment.getExternalStorageDirectory().toString();
        OutputStream out = null;

        File file = new File(path + "/MyPics", birdName.getText().toString().replace(" ", "_").toString() + ".jpeg");
        File fileFolder = new File(path + "/MyPics");

        try {
            if (!fileFolder.exists()) { // Si no existe, crea el archivo.
                fileFolder.createNewFile();
            }

            out = new FileOutputStream(file);

            imgBitmap.compress(Bitmap.CompressFormat.JPEG, 85, out); // saving the Bitmap to a file compressed as a JPEG with 85% compression rate
            out.flush(); // Not really required
            out.close(); // do not forget to close the stream

            absoluteImagePath = file.getAbsolutePath();

            MediaStore.Images.Media.insertImage(getContentResolver(), absoluteImagePath, file.getName(),file.getName());
            Toast.makeText(getApplicationContext(), "Image saved", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            throw e;
        }
    }

    protected void onActivityResult(int requestCode,
                                    int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imgBitmap = (Bitmap) extras.get("data");
            birdImage.setImageBitmap(imgBitmap);
            hasImage = true;
            registerButton.setText(R.string.register);
        }
    }
}