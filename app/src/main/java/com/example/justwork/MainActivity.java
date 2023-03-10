package com.example.justwork;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.provider.ContactsContract;
import android.util.Patterns;
import android.view.View;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.checkerframework.checker.units.qual.C;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private TextView register;
    private EditText edtEmail, edtPassword;
    private Button btnLogin;

    private FirebaseAuth authLogin;
    FirebaseFirestore firebaseFirestore;
    private boolean userLocationPermissionsGranted = false ; //mlocation


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        register = findViewById(R.id.tv_register);
        register.setOnClickListener(this);

        btnLogin = findViewById(R.id.button_login);
        btnLogin.setOnClickListener(this);
        edtPassword = findViewById(R.id.edt_passwordLogin);
        edtEmail = findViewById(R.id.edt_emailLogin);
        firebaseFirestore = FirebaseFirestore.getInstance();




        }

    @Override
    public void onClick(View v) {
        //this is pretty much the navigation bar
        switch (v.getId()){
            case R.id.tv_register:
                startActivity( new Intent(this, Register.class));//open a new page
                break;
            case R.id.button_login:
                userLogin();
                break;

        }
    }
    //navigation logic
    private void userLogin() {
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();

        if (email.isEmpty()) {
            edtEmail.setError("email is required");
            edtEmail.requestFocus();

            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edtEmail.setError("invalid email error");
            edtEmail.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            edtPassword.setError("email is required");
            edtPassword.requestFocus();
            return;
        }
        firebaseFirestore.collection("users").document(email).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {//searching db for the users username
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot doc = task.getResult();
                            if (doc.exists()) { //verifying password

                                if( password.equalsIgnoreCase(doc.getString("PASSWORD"))) {
                                    //getting Data from the database
                                    String HOME_POSTCODE = doc.getString("HOME_POSTCODE");
                                    String PHONE_NUMBER = doc.getString("PHONE_NUMBER");
                                    String STUDENT_NUMBER = doc.getString("STUDENT_NUMBER");
                                    String PASSWORD = doc.getString("PASSWORD");
                                    String FULL_NAME = doc.getString("FULL_NAME");
                                    String EMAIL = doc.getString("EMAIL");
                                    String DOB = doc.getString("DOB");
                                    String CARBON_RANK = String.valueOf(doc.getLong("CARBON_RANK"));
                                    String CARBON_BALANCE = String.valueOf(doc.getLong("CARBON_BALANCE"));
                                    String P_RANK = String.valueOf(doc.getLong("P_RANK"));
                                    String UNI = doc.getString("UNI");
                                    String value="Hello world";

                                    Intent C = new Intent(MainActivity.this, RideshareRequest.class);
                                    C.putExtra("Email", EMAIL);
                                    C.putExtra("FULL_NAME", FULL_NAME);
                                    C.putExtra("PHONE_NUMBER", PHONE_NUMBER);
                                    C.putExtra("STUDENT_NUMBER", STUDENT_NUMBER);
                                    startActivity(C);

                                    Intent i = new Intent(MainActivity.this, UserProfileHome.class);
                                    i.putExtra("key", value);
                                    i.putExtra("Email", EMAIL);
                                    i.putExtra("HOME_POSTCODE", HOME_POSTCODE);
                                    i.putExtra("FULL_NAME", FULL_NAME);
                                    i.putExtra("UNI", UNI);
                                    i.putExtra("PHONE_NUMBER", PHONE_NUMBER);
                                    i.putExtra("STUDENT_NUMBER", STUDENT_NUMBER);
                                    i.putExtra("PASSWORD", PASSWORD);
                                    i.putExtra("DOB", DOB);
                                    i.putExtra("CARBON_RANK", CARBON_RANK);
                                    i.putExtra("CARBON_BALANCE", CARBON_BALANCE);
                                    i.putExtra("P_RANK", P_RANK);


                                    startActivity(i);
                                    Intent intent = new Intent(getApplicationContext(), UserProfileHome.class);
                                    //creating a new intent nullifies the old intent hence the issue I was experiencing

                                    //open the class to open the new activity do not do this again

                                }
                                else {
                                    Toast.makeText(MainActivity.this, "wrong password", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            }
                            else {
                                Toast.makeText(MainActivity.this, "email does not exist in this system", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                    }

                });
    }

}