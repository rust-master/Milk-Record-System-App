package com.microitindustry.khatasystem.ViewHolder;

import android.view.ContextMenu;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.microitindustry.khatasystem.Common.Common;
import com.microitindustry.khatasystem.Interface.ItemClickListener;
import com.microitindustry.khatasystem.R;

public class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener ,
        View.OnCreateContextMenuListener{

    public TextView txt_name,txt_phone,txt_address;

    private ItemClickListener itemClickListener;


    public UserViewHolder(@NonNull View itemView) {
        super(itemView);
        txt_name = (TextView)itemView.findViewById(R.id.userName);
        txt_phone = (TextView)itemView.findViewById(R.id.userPhone);
        txt_address = (TextView)itemView.findViewById(R.id.userAddress);

        itemView.setOnCreateContextMenuListener(this);
        itemView.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

    }
    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("Select the action");
        menu.add(0,0,getAdapterPosition(), Common.DELETE);
    }
}