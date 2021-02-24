package com.microitindustry.khatasystem.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.microitindustry.khatasystem.Model.Users;
import com.microitindustry.khatasystem.R;
import com.microitindustry.khatasystem.UserLedger;
import com.microitindustry.khatasystem.ViewHolder.UserViewHolder;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserViewHolder>{

    private List<Users> listData = new ArrayList<>();
    private Context context;

    public UserAdapter(List<Users> listData, Context context) {
        this.listData = listData;
        this.context = context;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.users_layout,parent,false);
        return new UserViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {

        holder.txt_name.setText(listData.get(position).getUserName());
        holder.txt_phone.setText(listData.get(position).getPhoneNo());
        holder.txt_address.setText(listData.get(position).getAddress());
        holder.nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Phone No : " + listData.get(position).getPhoneNo(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, UserLedger.class);
                intent.putExtra("UserPhone", String.valueOf(listData.get(position).getPhoneNo()));
                context.startActivity(intent);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Phone No : " + listData.get(position).getPhoneNo(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, UserLedger.class);
                intent.putExtra("UserPhone", String.valueOf(listData.get(position).getPhoneNo()));
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return listData.size();
    }

    public Users getItem(int position)
    {
        return listData.get(position);
    }
}