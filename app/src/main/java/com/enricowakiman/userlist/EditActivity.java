package com.enricowakiman.userlist;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import model.User;

public class EditActivity extends AppCompatActivity {

    private TextInputLayout nameInput, ageInput, addressInput;
    private Button inputButton, backButton;
    private Intent intent;
    private Boolean validateName, validateAge, validateAddress;
    private String name, address;
    private int age, position;
    private Loading loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        init();
        initIntent();
        listener();
    }

    private void init() {
        nameInput = findViewById(R.id.nameInput2);
        ageInput = findViewById(R.id.ageInput2);
        addressInput = findViewById(R.id.addressInput2);
        inputButton = findViewById(R.id.inputButton_edit);
        backButton = findViewById(R.id.backButton_edit);
        validateName = true;
        validateAge = true;
        validateAddress = true;
        loading = new Loading(EditActivity.this);
        intent = getIntent();
    }

    private void initIntent() {
        name = intent.getStringExtra("userName");
        age = intent.getIntExtra("userAge", 0);
        address = intent.getStringExtra("userAddress");
        position = intent.getIntExtra("position", -1);

        Log.d("wdawa", ""+name+" "+age+" "+address);

        nameInput.getEditText().setText(name);
        ageInput.getEditText().setText(String.valueOf(age));
        addressInput.getEditText().setText(address);
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

                    String name2 = nameInput.getEditText().getText().toString().trim();
                    int age2 = Integer.parseInt(ageInput.getEditText().getText().toString().trim());
                    String address2 = addressInput.getEditText().getText().toString().trim();
                    User temp = new User(name2, age2, address2);

                    Intent intent = new Intent();
                    intent.putExtra("editedUser", temp);
                    intent.putExtra("position", position);
                    setResult(12343, intent);

                    Log.d("wowa", name2+" "+age2+" "+address2);

                    Toast.makeText(getBaseContext(), "Successfully edited "+name.split(" ")[0]+" to "+name2.split(" ")[0]+"!", Toast.LENGTH_LONG).show();

                    loading.dismissLoading();

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