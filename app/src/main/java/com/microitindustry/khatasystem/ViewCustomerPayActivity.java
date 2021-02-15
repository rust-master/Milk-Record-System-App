package com.microitindustry.khatasystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.microitindustry.khatasystem.Adapter.UserPayAdapter;
import com.microitindustry.khatasystem.Database.Database;
import com.microitindustry.khatasystem.Model.Users;

import java.util.ArrayList;
import java.util.List;

public class ViewCustomerPayActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    List<Users> users = new ArrayList<>();
    UserPayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_customer_pay);

        recyclerView =  findViewById(R.id.listUsersPay);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        loadListUsers();
    }

    private void loadListUsers() {
        users = new Database(this).getUsers();
        adapter = new UserPayAdapter(users,this);
        recyclerView.setAdapter(adapter);
    }
}