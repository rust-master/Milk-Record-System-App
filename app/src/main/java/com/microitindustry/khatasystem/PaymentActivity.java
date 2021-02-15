package com.microitindustry.khatasystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.microitindustry.khatasystem.Database.Database;



public class PaymentActivity extends AppCompatActivity {

    String id ="";
    TextView totalNet,paid,remaining;
    TextView userPhone,username;
    ImageButton shareWhatsApp;
    private AdView mAdView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        mAdView = findViewById(R.id.adBannerView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        totalNet = findViewById(R.id.totalNet);
        paid = findViewById(R.id.paid);
        remaining = findViewById(R.id.remaining);
        userPhone = findViewById(R.id.userPhone);
        username = findViewById(R.id.username);
        shareWhatsApp = findViewById(R.id.shareWhatsApp);

        if(getIntent() != null)
            id = getIntent().getStringExtra("UserPhone");
        if (!id.isEmpty() && id!=null){
            loadPayment(id);
            loadUserName(id);
            loadUserPhone(id);
        }
        shareWhatsApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             openWhatsApp();

            }
        });

    }

    private void openWhatsApp() {
        String data = getData();
        Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
        whatsappIntent.setType("text/plain");
        whatsappIntent.setPackage("com.whatsapp");
        whatsappIntent.putExtra(Intent.EXTRA_TEXT, data);
        startActivity(whatsappIntent);
    }

    private String getData() {
        String payment,netTotal,PaidAmount,RemainingAmount,DeveloperInfo;
        String customerName = username.getText().toString();
        netTotal = totalNet.getText().toString();
        PaidAmount = paid.getText().toString();
        RemainingAmount = remaining.getText().toString();
        payment = "\n*Payment:*"+"\nNet Total : "+netTotal+"\nPaid Amount : " +PaidAmount+"\nRemaining Amount : "+RemainingAmount+"\n";
        DeveloperInfo = "----------------------------------\n*Developer Info:*\n"+"Milk System App is developed by *Micro IT Industry*\n"+"Phone No: *03157466519*"+"\n";
        customerName = "*"+customerName+"*"+payment+"\n"+DeveloperInfo;
        return customerName;
    }

    private void loadUserName(String id) {
        String name = new Database(this).getName(id);
        username.setText(name);
    }

    private void loadPayment(String id) {
        String paidB = new Database(this).getPaidBill(id);
        if(paidB == null)
        {
            paidB = "0";
        }
        paid.setText(paidB);
        String remB = new Database(this).getRemBill(id);
        if(remB == null)
        {
            remB = "0";
        }
        remaining.setText(remB);
        String netTotal = new Database(getBaseContext()).getTotalBill(id);
        if(netTotal == null)
        {
            netTotal = "0";
        }
        totalNet.setText(netTotal);
    }

    private void loadUserPhone(String id) {
        userPhone.setText(id);
    }
}