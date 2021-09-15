package com.enricowakiman.userlist;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

import model.User;

public class AddActivity extends AppCompatActivity {

    private TextInputLayout nameInput, ageInput, addressInput;
    private Button inputButton, backButton;
    private Boolean validateName, validateAge, validateAddress;
    private Loading loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        init();
        listener();
    }

    private void init() {
        nameInput = findViewById(R.id.nameInput);
        ageInput = findViewById(R.id.ageInput);
        addressInput = findViewById(R.id.addressInput);
        inputButton = findViewById(R.id.inputButton);
        backButton = findViewById(R.id.backButton_edit);
        validateName = false;
        validateAge = false;
        validateAddress = false;
        loading = new Loading(AddActivity.this);
    }

    private void listener() {
        nameInput.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String name = nameInput.getEditText().getText().toString().trim();

                if (name.isEmpty()) {
                    nameInput.setError("Must input name.");
                    validateName = false;
                } else {
                    if (name.length() > 20) {
                        nameInput.setError("Title must not have more than 20 characters.");
                        validateName = false;
                    } else {
                        nameInput.setError("");
                        validateName = true;
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (validateName && validateAge && validateAddress) {
                    inputButton.setEnabled(true);
                }
            }
        });

        addressInput.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String address = addressInput.getEditText().getText().toString().trim();

                if (address.isEmpty()) {
                    addressInput.setError("Must input address");
                    validateAddress = false;
                } else {
                    addressInput.setError("");
                    validateAddress = true;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (validateName && validateAge && validateAddress) {
                    inputButton.setEnabled(true);
                }
            }
        });

        ageInput.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String age = ageInput.getEditText().getText().toString().trim();

                if (age.isEmpty()) {
                    ageInput.setError("Must input age");
                    validateAge = false;
                } else {
                    ageInput.setError("");
                    validateAge = true;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (validateName && validateAge && validateAddress) {
                    inputButton.setEnabled(true);
                }
            }
        });

        inputButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading.startLoading();
                Handler handler = new Handler();
                handler.postDelayed(() -> {

                    String name = nameInput.getEditText().getText().toString().trim();
                    int age = Integer.parseInt(ageInput.getEditText().getText().toString().trim());
                    String address = addressInput.getEditText().getText().toString().trim();
                    User temp = new User(name, age, address);

                    Intent intent = new Intent();
                    intent.putExtra("newUsers", temp);
                    setResult(9000, intent);

                    loading.dismissLoading();

                    Toast.makeText(getBaseContext(), "Successfully added "+name.split(" ")[0]+" into the list!", Toast.LENGTH_LONG).show();

                    finish();

                }, 5000);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}