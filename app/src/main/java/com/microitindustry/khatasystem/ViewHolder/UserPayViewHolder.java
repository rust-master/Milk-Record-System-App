package com.microitindustry.khatasystem.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.microitindustry.khatasystem.Interface.ItemClickListener;
import com.microitindustry.khatasystem.R;

public class UserPayViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView txt_name,txt_phone,txt_address;

    private ItemClickListener itemClickListener;

    public UserPayViewHolder(@NonNull View itemView) {
        super(itemView);
        txt_name = (TextView)itemView.findViewById(R.id.userName);
        txt_phone = (TextView)itemView.findViewById(R.id.userPhone);
        txt_address = (TextView)itemView.findViewById(R.id.userAddress);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}
