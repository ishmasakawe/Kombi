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

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private TextView register;
    private EditText edtEmail, edtPassword;
    private Button btnLogin;

    private FirebaseAuth authLogin;
    FirebaseFirestore firebaseFirestore;


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
                startActivity( new Intent(this, UserProfileHome.class));
                break;

        }
    }

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

                                    System.out.print("User email: ");
                                    System.out.println(doc.getString("PASSWORD"));
                                    String HOME_ADDRESS = doc.getString("HOME_ADDRESS");
                                    String HOME_POSTCODE = doc.getString("HOME_POSTCODE");
                                    String UNI = doc.getString("UNI");
                                    String PHONE_NUMBER = doc.getString("PHONE_NUMBER");
                                    String STUDENT_NUMBER = doc.getString("STUDENT_NUMBER");
                                    String PASSWORD = doc.getString("PASSWORD");
                                    String FULL_NAME = doc.getString("FULL_NAME");
                                    String EMAIL = doc.getString("EMAIL");
                                    String DOB = doc.getString("DOB");
                                    Integer CARBON_RANK = Integer.parseInt(String.valueOf(doc.getLong("CARBON_RANK")));
                                    Integer CARBON_BALANCE = Integer.parseInt(String.valueOf(doc.getLong("CARBON_BALANCE")));
                                    Integer P_RANK = Integer.parseInt(String.valueOf(doc.getLong("P_RANK")));

                                    User regUser = new User(HOME_ADDRESS, HOME_POSTCODE, FULL_NAME, PASSWORD, STUDENT_NUMBER, PHONE_NUMBER, DOB, UNI, CARBON_BALANCE
                                            , EMAIL, CARBON_RANK, P_RANK);
                                    regUser.setCarbonBalance(CARBON_RANK);
                                    System.out.println(regUser.getCarbonBalance());

                                    Intent intent = new Intent(getApplicationContext(), UserProfileHome.class);
                                    intent.putExtra("message", email);
                                    startActivity(intent);
                                    finish();


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