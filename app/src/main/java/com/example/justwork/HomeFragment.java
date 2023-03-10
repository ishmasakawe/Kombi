package com.example.justwork;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class HomeFragment extends Fragment {
    String carbonBalance;
    Button btnDonate, btnPlantTree;
    Integer nb;
    String Email,FULL_NAME;
    TextView carbonBalanceTextView, welcomeMessageTextView;

    Button btnMakeRequest;
    FirebaseFirestore db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        carbonBalanceTextView = view.findViewById(R.id.carbon_balance_home);
        welcomeMessageTextView = view.findViewById(R.id.txt_welcome);
        btnMakeRequest = view.findViewById(R.id.btn_makeRequest);

        // define the text view
        Bundle args = getArguments();
        if (args != null) {
            Email = args.getString("Email");
            FULL_NAME = args.getString("FULL_NAME");
        }

        welcomeMessageTextView.setText("Welcome "+ FULL_NAME);

        db = FirebaseFirestore.getInstance();
        db.collection("users")
                .document(Email)
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot doc = task.getResult();
                            String CARBON_BALANCE = String.valueOf(doc.getLong("CARBON_BALANCE"));
                            carbonBalance = CARBON_BALANCE;
                            carbonBalanceTextView.setText("Balance: C$" + carbonBalance);
                            System.out.println("varrible passed");
                            System.out.println("Balance: " + carbonBalance);
                        } else {
                            Toast.makeText(getActivity(), "failed to update", Toast.LENGTH_SHORT).show();

                        }
                    }
                });



        btnMakeRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RideshareRequest.class);
                startActivity(intent);
            }
        });

        return view;

    }
}