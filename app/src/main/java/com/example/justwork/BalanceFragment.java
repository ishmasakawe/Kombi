package com.example.justwork;

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


public class BalanceFragment extends Fragment implements View.OnClickListener {

    String carbonBalance;
    Button btnDonate, btnPlantTree;
    String Email;
    TextView carbonBalanceTextView;


    FirebaseFirestore db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_balance, container, false);
        carbonBalanceTextView = view.findViewById(R.id.carbon_balance_text);
        // define the text view

        Bundle args = getArguments();
        if (args != null) {
            Email = args.getString("Email");
            String cb = args.getString("CARBON_BALANCE");
            //may be reduntant code but it grabs the value from the activity class
            if (carbonBalance == null) {
                cb = carbonBalance;
            }
        }

        //grabbs cbalance and puts into the app
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

        System.out.println("before change" + carbonBalance);

        btnDonate = view.findViewById(R.id.btn_donate);
        btnPlantTree = view.findViewById(R.id.btn_plantTree);

        btnPlantTree.setOnClickListener(this);
        btnDonate.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_donate:
                Donate();
                break;
            case R.id.btn_plantTree:
                PlantTree();
                break;
        }
    }

    private void PlantTree() {

        int currentBalance = Integer.parseInt(carbonBalance);
        int num = 50;
        int newBalance = currentBalance - num;
        if (newBalance <= 0) {
            Toast.makeText(getActivity(), "not enough coins", Toast.LENGTH_SHORT).show();
            return;
        }
        db.collection("users")
                .document(Email)
                .update("CARBON_BALANCE", newBalance);
        carbonBalance = Integer.toString(newBalance);
        carbonBalanceTextView.setText("Balance: " + carbonBalance);
        Toast.makeText(getActivity(), "Tree planted!", Toast.LENGTH_SHORT).show();
        return;
    }

    private void Donate() {
        int currentBalance = Integer.parseInt(carbonBalance);
        int num = 10;
        int newBalance = currentBalance - num;
        if (newBalance <= 0) {
            Toast.makeText(getActivity(), "not enough coins", Toast.LENGTH_SHORT).show();
            return;
        }
        db.collection("users")
                .document(Email)
                .update("CARBON_BALANCE", newBalance);
        carbonBalance = Integer.toString(newBalance);
        carbonBalanceTextView.setText("Balance: " + carbonBalance);
        Toast.makeText(getActivity(), "Coins Donated!", Toast.LENGTH_SHORT).show();
        return;
    }

}