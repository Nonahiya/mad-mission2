package com.enricowakiman.userlist;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import model.User;

public class DetailsActivity extends AppCompatActivity {

    private TextView nameDetails, ageDetails, addressDetails;
    private Button backButton, editButton, deleteButton;
    private Intent intent;
    private String name, address;
    private int age, position;
    private Loading loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        init();
        initIntent();
        listener();
    }

    private void init() {
        nameDetails = findViewById(R.id.nameDetails);
        ageDetails = findViewById(R.id.ageDetails);
        addressDetails = findViewById(R.id.addressDetails);
        backButton = findViewById(R.id.backButton_details);
        editButton = findViewById(R.id.editButton);
        deleteButton = findViewById(R.id.deleteButton);
        loading = new Loading(DetailsActivity.this);
        intent = getIntent();
    }

    private void initIntent() {
        name = intent.getStringExtra("userName");
        age = intent.getIntExtra("userAge", 0);
        address = intent.getStringExtra("userAddress");
        position = intent.getIntExtra("position", -1);

        Log.d("wdawa", ""+name+" "+age+" "+address);

        nameDetails.setText((""+name));
        ageDetails.setText((age + " Years Old"));
        addressDetails.setText((""+address));
    }

    private void listener() {
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("userName", name);
                intent.putExtra("userAge", age);
                intent.putExtra("userAddress", address);
                intent.putExtra("position", position);

                setResult(12342, intent);

                finish();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading.startLoading();
                Handler handler = new Handler();
                handler.postDelayed(() -> {
                    Intent intent = new Intent();
                    intent.putExtra("position", position);

                    setResult(12341, intent);

                    Toast.makeText(getBaseContext(), "Successfully deleted "+name.split(" ")[0]+" from the list!", Toast.LENGTH_LONG).show();

                    loading.dismissLoading();

                    finish();
                }, 5000);
            }
        });
    }

}