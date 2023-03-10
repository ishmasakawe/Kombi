package com.example.justwork;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class DriverRegistration extends AppCompatActivity {
    Switch btn_is_driver;
    Button bt_submit;
    EditText et_car_model, et_car_reg;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_registration);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            email = extras.getString("Email");
            System.out.println(email);
        } else {
            Toast.makeText(DriverRegistration.this, "data not passed", Toast.LENGTH_SHORT).show();
        }
        btn_is_driver = (Switch) findViewById(R.id.btn_driver_question);
        bt_submit = findViewById(R.id.btn_submit_driver);

        et_car_reg = findViewById(R.id.et_car_reg_number);
        et_car_model = findViewById(R.id.et_car_model);

        btn_is_driver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_car_model.setVisibility(View.VISIBLE);
                et_car_reg.setVisibility(View.VISIBLE);
            }
        });


        bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btn_is_driver.isChecked()) {
                    et_car_model.setVisibility(View.VISIBLE);
                    et_car_reg.setVisibility(View.VISIBLE);
                    System.out.println(email);
                    RegisterDriver();
                } else {
                    Intent intent = new Intent(getApplicationContext(), UserProfileHome.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }





    public void RegisterDriver() {

        String carREeg = et_car_reg.getText().toString().trim();
        String carModel = et_car_model.getText().toString().trim();

        if (carREeg.isEmpty()) {
            et_car_reg.setError("CarReg required");
            et_car_reg.requestFocus();
            return;
        }
        if (carModel.isEmpty()) {
            et_car_model.setError("CarModel is required");
            et_car_model.requestFocus();
            return;
        }

        Map<String, Object> driver = new HashMap<>();
        driver.put("CAR_REG", carREeg);
        driver.put("CAR_MODEL", carModel);
        System.out.println(email);

        db.collection("driver").document(email).set(driver).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(DriverRegistration.this, "Success", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(), UserProfileHome.class);
                startActivity(intent);
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(DriverRegistration.this, "fail", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
