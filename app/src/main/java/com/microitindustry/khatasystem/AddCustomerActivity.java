package com.microitindustry.khatasystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.textfield.TextInputEditText;
import com.microitindustry.khatasystem.Database.Database;
import com.microitindustry.khatasystem.Model.Users;

public class AddCustomerActivity extends AppCompatActivity {

    TextInputEditText edtName,edtPhone,edtAddress;
    CardView btnAddCustomer;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_customer);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adBannerView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        edtName = findViewById(R.id.edtName);
        edtPhone = findViewById(R.id.edtPhoneNo);
        edtAddress = findViewById(R.id.edtAddress);
        btnAddCustomer = findViewById(R.id.btnAddCustomer);
        btnAddCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean valueBool = checktheFields();
                if(valueBool)
                {
                    Boolean checkPhoneIsExists = new Database(getBaseContext()).checkPhoneExists(edtPhone.getText().toString());
                    if(!checkPhoneIsExists)
                    {
                        new Database(getBaseContext()).addUser(new Users(
                                edtName.getText().toString(),
                                edtPhone.getText().toString(),
                                edtAddress.getText().toString()
                        ));
                        Toast.makeText(AddCustomerActivity.this, "Add success", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AddCustomerActivity.this, ViewCustomerActivity.class);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(AddCustomerActivity.this, "This Phone No User is Already Exists", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
    }


    private Boolean checktheFields() {
        String name = edtName.getText().toString().trim();
        String phone = edtPhone.getText().toString().trim();
        String address = edtAddress.getText().toString().trim();
        if(name.isEmpty())
        {
            edtName.setError("Please Enter Name!");
            edtName.requestFocus();
            return false;
        }
        if(phone.isEmpty())
        {
            edtPhone.setError("Please Enter Phone!");
            edtPhone.requestFocus();
            return false;
        }
        if(address.isEmpty())
        {
            edtAddress.setError("Please Enter Address!");
            edtAddress.requestFocus();
            return false;
        }
        return true;
    }
}