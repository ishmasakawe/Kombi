package com.example.justwork;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RideshareRequest extends AppCompatActivity {
    String[] uni = {"BRUNEL"};
    String[] item = {"1", "2", "3"};
    String[] DAY = {"MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY"};

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String email;
    EditText et_PostCode, et_address, et_ETA, et_ETD;
    String destination, seatNumber, rideShareDate, PostCode, address, ETA, ETD;
    ;
    AutoCompleteTextView autoCompleteTextView, autoCompleteTextView2, autoCompleteTextView3;
    ArrayAdapter<String> adapterItem, adapterDay, adapterUni;

    Button btn_submit;
    String DRIVERS_EMAIL, FULL_NAME, PHONE_NUMBER, STUDENT_NUMBER;
    Boolean rideStatus = true;
    Boolean seat_one_empty = true;
    Boolean seat_two_empty = true;
    Boolean seat_three_empty = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rideshare_request);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            DRIVERS_EMAIL = extras.getString("Email");
            FULL_NAME = extras.getString("FULL_NAME");
            PHONE_NUMBER = extras.getString("PHONE_NUMBER");
            STUDENT_NUMBER = extras.getString("STUDENT_NUMBER");
        } else {
            Toast.makeText(RideshareRequest.this, "data not passed", Toast.LENGTH_SHORT).show();
        }

        autoCompleteTextView = findViewById(R.id.tv_requestForm);
        autoCompleteTextView2 = findViewById(R.id.tv_requestForm2);
        autoCompleteTextView3 = findViewById(R.id.tv_requestForm3);
        btn_submit = findViewById(R.id.btn_submitRequest);
        et_PostCode = findViewById(R.id.et_postCode);
        et_address = findViewById(R.id.et_PostalAddress);
        et_ETA = findViewById(R.id.et_ETA);
        et_ETD = findViewById(R.id.et_ETD);


        adapterItem = new ArrayAdapter<String>(this, R.layout.list_item, item);
        adapterDay = new ArrayAdapter<String>(this, R.layout.list_item, DAY);
        adapterUni = new ArrayAdapter<String>(this, R.layout.list_item, uni);

        autoCompleteTextView3.setAdapter(adapterDay);
        autoCompleteTextView.setAdapter(adapterItem);
        autoCompleteTextView2.setAdapter(adapterUni);

        autoCompleteTextView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                rideShareDate = adapterDay.getItem(position).toString();
                Toast.makeText(RideshareRequest.this, "item", Toast.LENGTH_SHORT).show();
            }
        });
        autoCompleteTextView3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                seatNumber = adapterItem.getItem(position).toString();
                Toast.makeText(RideshareRequest.this, "item", Toast.LENGTH_SHORT).show();
               /* if (seatNumber.equals("1")) {
                    seat_one_empty = true;
                    seat_two_empty = false;
                    seat_three_empty = false;
                }
                if (seatNumber.equals("2")) {
                    seat_one_empty = true;
                    seat_two_empty = true;
                    seat_three_empty = false;
                }
                if (seatNumber.equals("3")) {
                    seat_one_empty = true;
                    seat_two_empty = true;
                    seat_three_empty = true;
                }*/
            }

        });
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                destination = adapterUni.getItem(position).toString();
                Toast.makeText(RideshareRequest.this, "item", Toast.LENGTH_SHORT).show();
            }

        });
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PostCode = et_PostCode.getText().toString().trim().toLowerCase();
                address = et_address.getText().toString().trim().toLowerCase();
                ETA = et_ETA.getText().toString().trim().toLowerCase();
                ETD = et_ETD.getText().toString().trim().toLowerCase();

                if (PostCode.isEmpty()) {
                    et_PostCode.setError("PostCode is required");
                    et_PostCode.requestFocus();
                    return;
                }

                if (address.isEmpty()) {
                    et_address.setError("Address is required");
                    et_address.requestFocus();
                    return;
                }

                if (ETA.isEmpty()) {
                    et_ETA.setError("ETA is required");
                    et_ETA.requestFocus();
                    return;
                }

                if (ETD.isEmpty()) {
                    et_ETD.setError("ETD is required");
                    et_ETD.requestFocus();
                    return;
                }


                Map<String, Object> RideRequests = new HashMap<>();
                RideRequests.put("DESTINATION", destination.toUpperCase());
                RideRequests.put("RIDE_DATE", rideShareDate.toUpperCase());
                RideRequests.put("SEAT_NUMBER", seatNumber);
                RideRequests.put("POSTCODE", PostCode.toUpperCase());
                RideRequests.put("ADDRESS", address.toUpperCase());
                RideRequests.put("ETA", ETA.toUpperCase());
                RideRequests.put("ETD", ETD.toUpperCase());
                RideRequests.put("RIDE_STATUS", rideStatus);
                RideRequests.put("SEAT_ONE_EMPTY", seat_one_empty);
                RideRequests.put("SEAT_TWO_EMPTY", seat_two_empty);
                RideRequests.put("SEAT_THREE_EMPTY", seat_three_empty);
                RideRequests.put("DRIVERS_EMAIL", DRIVERS_EMAIL);
                RideRequests.put("FULL_NAME", FULL_NAME);
                RideRequests.put("PHONE_NUMBER", PHONE_NUMBER);
                RideRequests.put("STUDENT_NUMBER", STUDENT_NUMBER);


                db.collection("RideRequests").document().set(RideRequests).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(RideshareRequest.this, "Success", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RideshareRequest.this, "fail", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}