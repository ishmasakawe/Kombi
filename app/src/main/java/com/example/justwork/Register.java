package com.example.justwork;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Map;
import java.util.regex.Pattern;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class Register extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    private EditText inpName, inpPassword, inpUni, inpHomePostCode, inpStudentNumber, inpDob, inpAddress, inpPhoneNumber, inpEmail;
    private TextView regUserButton;
    private ProgressBar progressBar;
    private Button backButton;

    FirebaseFirestore db = FirebaseFirestore.getInstance();


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();

        regUserButton = (Button) findViewById(R.id.button);
        regUserButton.setOnClickListener(this);

        inpAddress = findViewById(R.id.etd_Addressline1);
        inpDob = findViewById(R.id.edt_Dob);
        inpHomePostCode = findViewById(R.id.edt_HomePostCode);
        inpUni = findViewById(R.id.edt_University);
        inpPhoneNumber = findViewById(R.id.edt_PhoneNumber);
        inpStudentNumber = findViewById(R.id.edt_studentNumber);
        inpEmail = findViewById(R.id.edt_email);
        inpName = findViewById(R.id.edt_Name);
        progressBar = findViewById(R.id.progressBar);
        inpPassword = findViewById(R.id.edt_passwordREG);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                registerUser();
                break;
        }
    }

    private void registerUser() {
        String email = inpEmail.getText().toString().trim().toLowerCase();
        String Name = inpName.getText().toString().trim();
        String PhoneNumber = inpPhoneNumber.getText().toString().trim();
        String Uni = inpUni.getText().toString().trim();
        String HomePC = inpHomePostCode.getText().toString().trim();
        String Dob = inpDob.getText().toString().trim();
        String HomeAddress = inpAddress.getText().toString().trim();
        String StudentNumber = inpStudentNumber.getText().toString().trim();
        String password = inpPassword.getText().toString().trim();

        //checks if the input are correct
        if (Name.isEmpty()) {
            inpName.setError("Full Name is required");
            inpName.requestFocus();
            return;
        }

        if (Dob.isEmpty()) {
            inpDob.setError("Dob is required");
            inpDob.requestFocus();
            return;
        }
        if (PhoneNumber.isEmpty()) {
            inpPhoneNumber.setError("Phone number is required");
            inpPhoneNumber.requestFocus();
            return;
        }
        if (Uni.isEmpty()) {
            inpUni.setError("Uni name is required");
            inpUni.requestFocus();
            return;
        }
        if (HomePC.isEmpty()) {
            inpHomePostCode.setError("home postcode is required");
            inpHomePostCode.requestFocus();
            return;
        }
        if (HomeAddress.isEmpty()) {
            inpAddress.setError("Home address line  is required");
            inpAddress.requestFocus();
            return;
        }
        if (StudentNumber.isEmpty()) {
            inpStudentNumber.setError("Student Number is required");
            inpStudentNumber.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            inpPassword.setError("enter Password");
            inpPassword.requestFocus();
            return;
        }
        if (password.length() < 6) {
            inpPassword.setError("password is too short, min length is 6 characters");
            inpPassword.requestFocus();
            return;
        }
        if (email.isEmpty()) {
            inpEmail.setError("email is required");
            inpEmail.requestFocus();
            return;
        }
        //checks if the email is real
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            inpEmail.setError("invalid email");
            inpEmail.requestFocus();
            return;
        }

        Integer pRank = 0;
        Integer carbonBalance = 0;
        Integer carbonRank = 0;
        Integer ridesCounter = 0;
        Integer CCGenerated = 0;
        Integer TreesPlanted = 0;


        Map<String, Object> users = new HashMap<>();
        users.put("FULL_NAME", Name.toUpperCase());
        users.put("STUDENT_NUMBER", StudentNumber);
        users.put("DOB", Dob);
        users.put("HOME_ADDRESS", HomeAddress.toUpperCase());
        users.put("HOME_POSTCODE", HomePC.toUpperCase());
        users.put("PHONE_NUMBER", PhoneNumber);
        users.put("PASSWORD", password);
        users.put("UNI", Uni.toUpperCase());
        users.put("EMAIL", email);
        users.put("CARBON_BALANCE", carbonBalance);
        users.put("CARBON_RANK", carbonRank);
        users.put("P_RANK", pRank);
        users.put("RIDES_COUNTED", ridesCounter);
        users.put("CC_GENERATED", CCGenerated);
        users.put("TREES_PLANTED", TreesPlanted);

        db.collection("users").document(email).set(users).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(Register.this, "Success", Toast.LENGTH_SHORT).show();
                String Email_sent = email;
                System.out.println(email+ " from reg class");
                Intent i = new Intent(Register.this, DriverRegistration.class);
                i.putExtra("Email", Email_sent);
                startActivity(i);

                Intent intent = new Intent(getApplicationContext(), DriverRegistration.class);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Register.this, "fail", Toast.LENGTH_SHORT).show();
            }
        });

    }

}