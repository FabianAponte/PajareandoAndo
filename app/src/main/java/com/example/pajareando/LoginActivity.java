package com.example.pajareando;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pajareando.models.ModelDb;

import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    EditText email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.etPersonName);
        password = findViewById(R.id.etTextPassword);

        /**
         * Logica para guardar un usuario
         */
        //        Map<String, String> birdInfo = new HashMap<String, String>();
        //
        //        birdInfo.put("name", "ramon");
        //        birdInfo.put("email", "ramoncito@test.com");
        //        birdInfo.put("password", "123456789");
        //        birdInfo.put("role", "admin");
        //
        //        ModelDb.save(birdInfo, "users", getApplicationContext());
//

    }

    public void goToMain(View view) {
        Map<String, String> user = ModelDb.findByParameter("email", email.getText().toString(), "users", getApplicationContext());

        if (user.get("password").equals(password.getText().toString())) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), "Oye que haces esto no es tuyo", Toast.LENGTH_SHORT).show();
        }
    }
}