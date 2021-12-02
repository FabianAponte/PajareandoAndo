package com.example.pajareando;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.pajareando.models.Bird;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class BirdFormActivity extends AppCompatActivity {

    AutoCompleteTextView autoCompleteBirdTypeView;
    AutoCompleteTextView autoCompleteBirdSizeView;

    final String []birdTypes =  {"Tipo 1", "Tipo 2", "Tipo 3"};
    final String []birdSize =  {"Pequeño", "Mediano", "Grande"};

    boolean hasImage = false;
    Button registerButton, getUbication;
    ImageView birdImage;
    Bitmap imgBitmap;
    EditText birdName, color1, color2, color3, color4;
    AutoCompleteTextView birdTypeInput, birdSizeInput;
    CheckBox hasMoreColors;
    TextView review, photoDate, longitud, latitud, title;
    String absoluteImagePath;
    int id = 0;
    Bird editBird;

    private FusedLocationProviderClient fusedLocationClient;
    LocationRequest locationRequest;
    LocationCallback locationCallback;

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static final int PERMISSION_FINE_LOCATION = 99;
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
        review = findViewById(R.id.reviewMultiAutoCompleteTextView);
        photoDate = findViewById(R.id.photoDate);
        getUbication = findViewById(R.id.getUbication);
        title = findViewById(R.id.title);

        /**
         * Lógica de location
         */
        locationRequest = new LocationRequest();

        locationRequest.setInterval(1000 * 30);
        locationRequest.setFastestInterval(1000 * 5);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        longitud = findViewById(R.id.longitud);
        latitud = findViewById(R.id.latitud);

        Intent intent = getIntent();

        if (intent.hasExtra("id")) {
            id = intent.getExtras().getInt("id");

            setEditForm(id);
        }
    }

    private void setEditForm(int id) {
        editBird = Bird.findById(getApplicationContext(), id);

        File imgFile = new File(editBird.getImagePath());

        if (imgFile.exists()) {
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            birdImage.setImageBitmap(myBitmap);
        }

        birdName.setText(editBird.getName());
        birdTypeInput.setText(editBird.getType());
        birdSizeInput.setText(editBird.getSize());
        color1.setText(editBird.getColor1());
        color2.setText(editBird.getColor2());
        color3.setText(editBird.getColor3());
        color4.setText(editBird.getColor4());
        hasMoreColors.setChecked(editBird.hasMoreColors());
        review.setText(editBird.getReview());
        photoDate.setText(editBird.getPhotoDate());
        longitud.setText(editBird.getLongitud());
        latitud.setText(editBird.getLatitud());

        registerButton.setText(R.string.editar);
        title.setText(R.string.edit_bird);
        hasImage = true;
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
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(intent, 1);
            }
        } else {
            if (verifyInfo()) {
                try {
                    if (editBird == null) {
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
                                absoluteImagePath,
                                longitud.getText().toString(),
                                latitud.getText().toString(),
                                photoDate.getText().toString(),
                                getApplicationContext()
                        );

                        bird.save();

                        Toast.makeText(getApplicationContext(), "Información de ave guardada", Toast.LENGTH_SHORT).show();
                        cleanForm();
                    } else {
                        editBird.setName(birdName.getText().toString());
                        editBird.setType(birdTypeInput.getText().toString());
                        editBird.setSize(birdSizeInput.getText().toString());
                        editBird.setColor1(color1.getText().toString());
                        editBird.setColor2(color2.getText().toString());
                        editBird.setColor3(color3.getText().toString());
                        editBird.setColor4(color4.getText().toString());
                        editBird.setMoreColors(hasMoreColors.isChecked());
                        editBird.setReview(review.getText().toString());
                        editBird.setImagePath(absoluteImagePath);
                        editBird.setLongitud(longitud.getText().toString());
                        editBird.setLatitud(latitud.getText().toString());
                        editBird.setPhotoDate(photoDate.getText().toString());

                        editBird.edit();
                        Toast.makeText(getApplicationContext(), "Información de ave editada", Toast.LENGTH_SHORT).show();

                        goToDetail(editBird.getId());
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(getApplicationContext(), "Falta información necesaria", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void goToDetail(int id) {
        Intent intent = new Intent(getApplicationContext(), BirdDetailActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }

    private void setPhotoDate() {
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c);
        photoDate.setText(getResources().getString(R.string.tomada_el) + " " + formattedDate);
    }

    private void cleanForm() {
        birdName.setText("");
        birdTypeInput.setText("");
        birdSizeInput.setText("");
        color1.setText("");
        color2.setText("");
        color3.setText("");
        color4.setText("");
        hasMoreColors.setChecked(false);
        review.setText("");
        registerButton.setText(R.string.takePicture);
        hasImage = false;
        photoDate.setText(getResources().getString(R.string.tomada_el));
        latitud.setText(getResources().getString(R.string.latitud));
        longitud.setText(getResources().getString(R.string.longitud));

        latitud.setVisibility(View.GONE);
        longitud.setVisibility(View.GONE);
        getUbication.setVisibility(View.VISIBLE);

        Bitmap icon = BitmapFactory.decodeResource(getApplicationContext().getResources(),
                R.drawable.pajaro);

        birdImage.setImageBitmap(icon);
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

        File file = new File(path, birdName.getText().toString().replace(" ", "_").toString() + ".jpeg");
        File fileFolder = new File(path );

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
            setPhotoDate();

        }
    }

    public void getGps(View view) {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        if (checkIfLocationOpened() && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    view.setVisibility(View.GONE);
                    latitud.setVisibility(View.VISIBLE);
                    longitud.setVisibility(View.VISIBLE);
                    longitud.setText(getResources().getString(R.string.longitud) + " " + location.getLongitude());
                    latitud.setText(getResources().getString(R.string.latitud) + " " + location.getLatitude());
                }
            });
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_FINE_LOCATION);
            }
        }
    }

    private boolean checkIfLocationOpened() {
        String provider = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
        System.out.println("Provider contains=> " + provider);
        if (provider.contains("gps")){
            return true;
        }
        Toast.makeText(getApplicationContext(), "Debes activar la localicación para obtener tu ubicación actual.", Toast.LENGTH_LONG).show();
        return false;
    }
}