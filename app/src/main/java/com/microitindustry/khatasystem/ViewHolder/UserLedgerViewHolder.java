package com.microitindustry.khatasystem.ViewHolder;

import android.view.ContextMenu;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.microitindustry.khatasystem.Common.Common;
import com.microitindustry.khatasystem.Interface.ItemClickListener;
import com.microitindustry.khatasystem.R;

public class UserLedgerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
        View.OnCreateContextMenuListener{

    public TextView txt_milkName,txt_measure,txt_price,txt_date;

    private ItemClickListener itemClickListener;

    public UserLedgerViewHolder(@NonNull View itemView) {
        super(itemView);
        txt_milkName = (TextView)itemView.findViewById(R.id.milkName);
        txt_measure = (TextView)itemView.findViewById(R.id.measure);
        txt_price = (TextView)itemView.findViewById(R.id.price);
        txt_date = (TextView)itemView.findViewById(R.id.date);

        itemView.setOnCreateContextMenuListener(this);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

    }
    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("Select the action");
        menu.add(0,0,getAdapterPosition(), Common.UPDATE);
        menu.add(0,1,getAdapterPosition(), Common.DELETE);
    }
}
