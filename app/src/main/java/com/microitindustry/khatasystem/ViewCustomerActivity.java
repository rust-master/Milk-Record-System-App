package com.microitindustry.khatasystem;



import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;


import android.view.MenuItem;

import com.microitindustry.khatasystem.Adapter.UserAdapter;
import com.microitindustry.khatasystem.Common.Common;
import com.microitindustry.khatasystem.Database.Database;
import com.microitindustry.khatasystem.Model.Users;

import java.util.ArrayList;
import java.util.List;

public class ViewCustomerActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    List<Users> users = new ArrayList<>();
    UserAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_customer);


        recyclerView = findViewById(R.id.listUsers);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        loadListUsers();

    }

    private void loadListUsers() {
        users = new Database(this).getUsers();
        adapter = new UserAdapter(users,this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if(item.getTitle().equals(Common.DELETE))
        {
            deleteItem(adapter.getItem(item.getOrder()).getID(), item.getOrder());

        }

        return super.onContextItemSelected(item);
    }

    private void deleteItem(int position, int p) {
        users.remove(p);
        new Database(this).deleteUser(position);
        loadListUsers();

    }

}