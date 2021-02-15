package com.microitindustry.khatasystem.Adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.microitindustry.khatasystem.Database.Database;
import com.microitindustry.khatasystem.Model.UserLedgerModel;

import com.microitindustry.khatasystem.R;
import com.microitindustry.khatasystem.UserLedger;
import com.microitindustry.khatasystem.ViewHolder.UserLedgerViewHolder;


import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class UserLedgerAdapter extends RecyclerView.Adapter<UserLedgerViewHolder> {

    private List<UserLedgerModel> listData = new ArrayList<>();
    private UserLedger userLedger;

    public UserLedgerAdapter(List<UserLedgerModel> listData, UserLedger userLedger) {
        this.listData = listData;
        this.userLedger = userLedger;
    }

    @NonNull
    @Override
    public UserLedgerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(userLedger);
        View itemView = inflater.inflate(R.layout.view_record_layout,parent,false);
        return new UserLedgerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull UserLedgerViewHolder holder, int position) {
        holder.txt_milkName.setText(listData.get(position).getMilkName());
        holder.txt_measure.setText(listData.get(position).getMeasure());
        holder.txt_price.setText(listData.get(position).getPrice());
        holder.txt_date.setText(listData.get(position).getDate());

        String totalMilk;
        float totalMilkInt = 0;

        float total = 0;
        List<UserLedgerModel> uuu = new Database(userLedger).getUserLedgerDetail(listData.get(position).getPhoneNo());
        for(UserLedgerModel item : uuu)
        {
            totalMilkInt+=Float.parseFloat(item.getMeasure());
            total+=(Float.parseFloat(item.getPrice()))*(Float.parseFloat(item.getMeasure()));
        }

        totalMilk = String.valueOf(totalMilkInt);
        Locale locale = new Locale("en" ,"PK");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);

        userLedger.txtTotalPrice.setText(fmt.format(total));
        userLedger.totalmilk.setText(totalMilk);

        
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(context, "ID " + listData.get(position).getPhoneNo(), Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(context, UserLedger.class);
//                intent.putExtra("UserPhone",String.valueOf(listData.get(position).getPhoneNo()));
//                context.startActivity(intent);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public UserLedgerModel getItem(int position)
    {
        return listData.get(position);
    }
}
