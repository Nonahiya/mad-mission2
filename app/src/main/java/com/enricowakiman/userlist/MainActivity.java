package com.enricowakiman.userlist;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import model.User;

public class MainActivity extends AppCompatActivity {

    private RecyclerView usersList;
    private ArrayList<User> dataUsers;
    private UserRVAdapter adapter;
    private FloatingActionButton addButton;
    private TextView noData;
    private Boolean doubleBackOnce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        setup();
        listener();
    }

    @Override
    public void onBackPressed() {
        if (doubleBackOnce) {
            super.onBackPressed();
            this.finishAffinity();
        }

        this.doubleBackOnce = true;
        Toast.makeText(getApplicationContext(), "Press back again to exit", Toast.LENGTH_SHORT).show();

        Handler handler = new Handler();
        handler.postDelayed(() -> doubleBackOnce = false, 2000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1337) {
            if (resultCode == 9000) {
                User newUser = data.getParcelableExtra("newUsers");
                dataUsers.add(newUser);
                adapter.notifyDataSetChanged();
            }
        }
        if (requestCode == 1234) {
            if (resultCode == 12341) { // delete, receive delete result
                int position = data.getIntExtra("position", -1);

                dataUsers.remove(position);
                adapter.notifyDataSetChanged();
            }
            else if (resultCode == 12342) { // edit, passing to edit
                String name = data.getStringExtra("userName");
                int age = data.getIntExtra("userAge", 0);
                String address = data.getStringExtra("userAddress");
                int position = data.getIntExtra("position", -1);

                Intent intent = new Intent(getBaseContext(), EditActivity.class);
                intent.putExtra("userName", name);
                intent.putExtra("userAge", age);
                intent.putExtra("userAddress", address);
                intent.putExtra("position", position);

                startActivityForResult(intent, 12342);
            }
        }
        if (requestCode == 12342) {
            if (resultCode == 12343) { // edit, receive edit result
                User editedUser = data.getParcelableExtra("editedUser");
                int position = data.getIntExtra("position", -1);

                dataUsers.set(position, editedUser);
                adapter.notifyDataSetChanged();
            }
        }

        if (dataUsers.size() > 0) {
            noData.setText("");
        }
        else {
            noData.setText("No Data");
        }
    }

    private void init() {
        usersList = findViewById(R.id.usersList);
        dataUsers = new ArrayList<>();
        adapter = new UserRVAdapter(this, dataUsers);
        addButton = findViewById(R.id.addButton);
        noData = findViewById(R.id.noData);
        doubleBackOnce = false;
    }

    private void setup() {
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getBaseContext());
        usersList.setLayoutManager(manager);
        usersList.setAdapter(adapter);
    }

    private void listener() {
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), AddActivity.class);
                startActivityForResult(intent, 1337);
            }
        });
    }
}