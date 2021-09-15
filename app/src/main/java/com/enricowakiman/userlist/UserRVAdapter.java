package com.enricowakiman.userlist;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Objects;

import model.User;

public class UserRVAdapter extends RecyclerView.Adapter<UserRVAdapter.UserViewHolder> {

    private ArrayList<User> listUsers;
    private Context context;

    public UserRVAdapter(Context context, ArrayList<User> listUsers) {
        this.listUsers = listUsers;
        this.context = context;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.card_user, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        holder.nameCard.setText(listUsers.get(position).getName());
        holder.ageCard.setText((listUsers.get(position).getAge()) + " Years Old");
        holder.addressCard.setText(listUsers.get(position).getAddress());

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = listUsers.get(position).getName();
                int age = listUsers.get(position).getAge();
                String address = listUsers.get(position).getAddress();

                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("userName", name);
                intent.putExtra("userAge", age);
                intent.putExtra("userAddress", address);
                intent.putExtra("position", position);

                ((Activity) context).startActivityForResult(intent, 1234);
                // context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listUsers.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        private TextView nameCard, ageCard, addressCard;
        private ConstraintLayout parentLayout;
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);

            nameCard = itemView.findViewById(R.id.nameCard);
            ageCard = itemView.findViewById(R.id.ageCard);
            addressCard = itemView.findViewById(R.id.addressCard);
            parentLayout = itemView.findViewById(R.id.parentLayout);
        }
    }
}
