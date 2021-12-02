package com.example.pajareando;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pajareando.models.User;




public class UserRegisterActivity extends AppCompatActivity {


    Button userRegisterButton;
    EditText userName,userEmail, userPassword,userRepeatPassword;


    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);

        int permission = ActivityCompat.checkSelfPermission(UserRegisterActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    UserRegisterActivity.this,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }

        userRegisterButton = findViewById(R.id.userregisterButton);
        userName=findViewById(R.id.etUserName);
        userEmail=findViewById(R.id.etUserEmail);
        userPassword=findViewById(R.id.etPassword);
        userRepeatPassword=findViewById(R.id.etRepeatPassword);

    }


    public void goToLogin(View view) {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }


    public void userRegister(View view) {

        if (verifyInfo()) {
            try {
                User user = new User(
                        userName.getText().toString(),
                        userEmail.getText().toString(),
                        userPassword.getText().toString(),
                        getApplicationContext()
                );
                user.save();

                goToLogin(view);
                Toast.makeText(getApplicationContext(), "Registro de usuario exitoso", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Falta información necesaria", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Verifica si falta información obligatoria
     *
     * @return bool
     */
    private boolean verifyInfo() {
        if (userName.getText().toString().isEmpty()) return false;
        if (userEmail.getText().toString().isEmpty()) return false;
        if (userPassword.getText().toString().isEmpty()) return false;
        if (!userPassword.getText().toString().equals(userRepeatPassword.getText().toString())) {
            Toast.makeText(getApplicationContext(), "El password y su confirmación no coinciden", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}