package com.microitindustry.khatasystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import com.microitindustry.khatasystem.Adapter.UserLedgerAdapter;
import com.microitindustry.khatasystem.Common.Common;
import com.microitindustry.khatasystem.Database.Database;
import com.microitindustry.khatasystem.Model.PaymentModel;
import com.microitindustry.khatasystem.Model.UserLedgerModel;


import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class UserLedger extends AppCompatActivity {

    FloatingActionButton addRecord;
    TextView username,userphone;
    String id ="";
    TextInputEditText itemName,edtMeasure,edtPrice;
    TextInputEditText edtClearTotal;
    TextInputEditText edtPayment;
    RelativeLayout rootLayout;

    TextView recordIsEmpty;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    List<UserLedgerModel> userLedgerModels = new ArrayList<>();
    UserLedgerAdapter adapter;
    public TextView txtTotalPrice,totalmilk,paid,remaining,totalNet;
    MaterialButton btnClearLedger,btnPaid;
    ImageButton shareWhatsApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_ledger);

        recyclerView = (RecyclerView) findViewById(R.id.listUserRecord);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        txtTotalPrice = findViewById(R.id.total);
        paid = findViewById(R.id.paid);
        totalmilk = findViewById(R.id.totalmilk);
        remaining = findViewById(R.id.remaining);
        totalNet = findViewById(R.id.totalNet);
        btnClearLedger = findViewById(R.id.btnClearLedger);
        btnPaid = findViewById(R.id.btnPaid);
        username = findViewById(R.id.username);
        userphone = findViewById(R.id.userphone);
        addRecord = findViewById(R.id.addRecord);
        rootLayout = findViewById(R.id.rootLayout);
        shareWhatsApp = findViewById(R.id.shareWhatsApp);
        recordIsEmpty = findViewById(R.id.recordIsEmpty);

        if(getIntent() != null)
            id = getIntent().getStringExtra("UserPhone");
        if (!id.isEmpty() && id!=null){
            loadListLedger(id);
            loadUserName(id);
            loadUserPhone(id);
            loadPaymentDetail(id);
        }

        btnClearLedger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearDialog();
            }
        });
        btnPaid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPaymentDialog();
            }
        });
        addRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogAddRecord();

            }
        });


        shareWhatsApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(adapter.getItemCount()<=0)
                {
                    Snackbar.make(rootLayout,"Your Record is Empty",Snackbar.LENGTH_SHORT)
                            .show();
                }
                else {
                    openWhatsApp();
                }

            }
        });


    }

    private void loadPaymentDetail(String id) {
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

    private void showPaymentDialog() {
        AlertDialog.Builder alertDialog  =  new AlertDialog.Builder(UserLedger.this);
        alertDialog.setTitle("Payment of Bill");
        alertDialog.setMessage("Please Enter Correct Payment");

        LayoutInflater inflater = this.getLayoutInflater();
        View add_menu_layout = inflater.inflate(R.layout.add_payment_layout,null);


        edtPayment = add_menu_layout.findViewById(R.id.edtPayment);

        alertDialog.setView(add_menu_layout);
        alertDialog.setIcon(R.drawable.ic_baseline_payment_24);

        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                String check = edtPayment.getText().toString();
                if(!check.equals(""))
                {
                    int paymentInt = Integer.parseInt(edtPayment.getText().toString());
                    String total = new Database(getBaseContext()).getTotalBill(id);

                    int totalInt = Integer.parseInt(total);
                    if (paymentInt == 0) {
                        Snackbar.make(rootLayout, "Enter Payment Not Equal to Zero", Snackbar.LENGTH_SHORT)
                                .show();
                    }
                   else if(paymentInt < 0)
                    {
                        Snackbar.make(rootLayout, "Enter Payment Not Less than Zero", Snackbar.LENGTH_SHORT)
                                .show();
                    }
                    else if(paymentInt > totalInt) {
                        Snackbar.make(rootLayout, "Payment Should not be greater than Total Bill", Snackbar.LENGTH_SHORT)
                                .show();
                    }
                    else {
                            int oldPay = Integer.parseInt(paid.getText().toString());
                            int newPaidInt = oldPay + paymentInt;
                            String newPaidStr = String.valueOf(newPaidInt);
                            new Database(getBaseContext()).updatePaid(newPaidStr,id);
                            int upTotal = totalInt - paymentInt;
                            String stupTotal = String.valueOf(upTotal);
                            new Database(getBaseContext()).updateTotal(stupTotal,id);
                            int rem = Integer.parseInt(remaining.getText().toString());
                            int remInt = rem - paymentInt;
                            String remSt = String.valueOf(remInt);

                            new Database(getBaseContext()).updateRemaining(remSt,id);
                        Snackbar.make(rootLayout, "Payment is Added", Snackbar.LENGTH_SHORT)
                                .show();
                        loadPaymentDetail(id);
                    }
                }
                else{
                    Snackbar.make(rootLayout, "Payment is Empty", Snackbar.LENGTH_SHORT)
                            .show();
                }
            }
        });

        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        alertDialog.show();
    }


    private void clearDialog() {
        AlertDialog.Builder alertDialog  =  new AlertDialog.Builder(UserLedger.this);
        alertDialog.setTitle("All Your Record Removed Permanently");
        alertDialog.setMessage("Please Enter Total Bill To Confirm");

        LayoutInflater inflater = this.getLayoutInflater();
        View add_menu_layout = inflater.inflate(R.layout.clear_ledger_layout,null);


        edtClearTotal = add_menu_layout.findViewById(R.id.edtClearTotal);

        alertDialog.setView(add_menu_layout);
        alertDialog.setIcon(R.drawable.ic_baseline_warning_24);

        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                String total =  txtTotalPrice.getText().toString();
                total = total.replace(",","");
                if(total.equals("Rs"+edtClearTotal.getText().toString()+".00"))
                {
                    String phoneId = userphone.getText().toString();
                    clearUserRecord(phoneId);
                    clearUserPayment(phoneId);
                    Snackbar.make(rootLayout,"All Record is Removed Permanently",Snackbar.LENGTH_SHORT)
                            .show();
                }
                else{
                    Snackbar.make(rootLayout,"You Enter wrong Payment. Please Try with correct!",Snackbar.LENGTH_SHORT)
                            .show();
                }
            }
        });

        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        alertDialog.show();
    }

    private void openWhatsApp() {
        String data = getData();
        String smsNumber = userphone.getText().toString();
        try {
            Intent sendIntent = new Intent("android.intent.action.MAIN");
            //sendIntent.setComponent(new ComponentName("com.whatsapp", "com.whatsapp.Conversation"));
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.setType("text/plain");
            sendIntent.putExtra(Intent.EXTRA_TEXT, data);
            sendIntent.putExtra(username.getText().toString(), smsNumber + "@s.whatsapp.net"); //phone number without "+" prefix
            sendIntent.setPackage("com.whatsapp");
            startActivity(sendIntent);
        } catch(Exception e) {
            Toast.makeText(this, "Error/n" + e.toString(), Toast.LENGTH_SHORT).show();
        }

    }

    private String getData() {

        String gdata = null;
        int size  = adapter.getItemCount();
        String[] Data = new String[size];
        String milkname,measure,price,date,total,DeveloperInfo,totalMilk;
        String payment,netTotal,PaidAmount,RemainingAmount;
        String customerName = username.getText().toString();
        int i=0;
        for(UserLedgerModel item : userLedgerModels)
        {
            milkname = item.getMilkName();
            measure = item.getMeasure();
            price = item.getPrice();
            date = item.getDate();
            Data[i] = "\nMilk Name : " + milkname+"\n"+"Kilogram : " + measure + "\n"+"Price : "+price+"\n"+"Date : "+date+"\n";
            i++;
        }
        for(String j : Data)
        {
            gdata += j;
        }
        gdata = gdata.replace("null", "");
        total = txtTotalPrice.getText().toString();
        total = total.replace("Rs","Rs: ");
        totalMilk = totalmilk.getText().toString();
        netTotal = totalNet.getText().toString();
        PaidAmount = paid.getText().toString();
        RemainingAmount = remaining.getText().toString();
        payment = "\n*Payment:*"+"\nNet Total : "+netTotal+"\nPaid Amount : " +PaidAmount+"\n Remaining Amount : "+RemainingAmount+"\n";
        DeveloperInfo = "----------------------------------\n*Developer Info:*\n"+"Milk System App is developed by *Micro IT Industry*\n"+"Phone No: *03157466519*"+"\n";
        customerName = "*"+customerName+"*\n"+ gdata + "\n"+ "Total : *"+total+"*\n"+"Total Milk : *"+totalMilk+" KG*"+payment+"\n"+DeveloperInfo;
        return customerName;
    }

    private void clearUserRecord(String phoneId) {
        new Database(this).deleteUserAllRecord(phoneId);
        loadListLedger(id);
        txtTotalPrice.setText("$00,000");
    }
    private void clearUserPayment(String phoneId) {
        new Database(this).deleteUserAllPayment(phoneId);
        loadPaymentDetail(id);
    }

    private void showDialogAddRecord() {
        AlertDialog.Builder alertDialog  =  new AlertDialog.Builder(UserLedger.this);
        alertDialog.setTitle("Add New Record");
        alertDialog.setMessage("Please fill full information");

        LayoutInflater inflater = this.getLayoutInflater();
        View add_menu_layout = inflater.inflate(R.layout.add_record_layout,null);



        itemName = add_menu_layout.findViewById(R.id.itemName);
        edtMeasure = add_menu_layout.findViewById(R.id.edtMeasure);
        edtPrice = add_menu_layout.findViewById(R.id.edtPrice);

        alertDialog.setView(add_menu_layout);
        alertDialog.setIcon(R.drawable.ic_baseline_add_box_24);

        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                Boolean valueBool = checktheFields();
                if(valueBool)
                {
                    String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());


                    new Database(getBaseContext()).addUserLedger(new UserLedgerModel(
                            userphone.getText().toString(),
                            itemName.getText().toString(),
                            edtMeasure.getText().toString(),
                            edtPrice.getText().toString(),
                            date

                    ));
                    adapter.notifyDataSetChanged();
                    loadListLedger(id);

                    paymentMethod();


                }
            }
        });

        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();

            }
        });
        alertDialog.show();

    }

    private void paymentMethod() {
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        if(new Database(getBaseContext()).checkPaymentExists(userphone.getText().toString()))
        {
            String total =  txtTotalPrice.getText().toString();
            total = total.substring(2);
            total = total.replace(".00","");
            total = total.replace(",","");
            String paidBill =  paid.getText().toString();
            int totl = Integer.parseInt(total);
            int paidt = Integer.parseInt(paidBill);
            int finalRem = totl - paidt;
            String strRem = String.valueOf(finalRem);

            new Database(getBaseContext()).updateUserPayment(strRem, paidBill, strRem ,userphone.getText().toString());

            Snackbar.make(rootLayout,"Payment is added",Snackbar.LENGTH_SHORT)
                    .show();
        }
        else{

            int m = Integer.parseInt(edtMeasure.getText().toString());
            int p = Integer.parseInt(edtPrice.getText().toString());

            int t = m*p;
            String totalb = String.valueOf(t);
            new Database(getBaseContext()).addUserPayment(new PaymentModel(
                    userphone.getText().toString(),
                    totalb,
                    "0",
                    totalb,
                    date
            ));
        }

        loadPaymentDetail(id);
    }

    private Boolean checktheFields() {
        String milkName = itemName.getText().toString().trim();
        String measure = edtMeasure.getText().toString().trim();
        String price = edtPrice.getText().toString().trim();
        if(milkName.isEmpty())
        {
            Snackbar.make(rootLayout,"Please Enter Item Name and Try Again",Snackbar.LENGTH_SHORT)
                    .show();
            return false;
        }
        if(measure.isEmpty())
        {
            Snackbar.make(rootLayout,"Please Enter Quantity and Try Again",Snackbar.LENGTH_SHORT)
                    .show();
            return false;
        }
        if(price.isEmpty())
        {
            Snackbar.make(rootLayout,"Please Enter Price and Try Again",Snackbar.LENGTH_SHORT)
                    .show();
            return false;
        }
        return true;
    }

    private void loadUserPhone(String id) {
        userphone.setText(id);
    }

    private void loadUserName(String id) {
        String name = new Database(this).getName(id);
        username.setText(name);
    }

    private void loadListLedger(String id) {
        userLedgerModels = new Database(this).getUserLedgerDetail(id);
        adapter = new UserLedgerAdapter(userLedgerModels,this);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
        String totalMilk;
        float total = 0;
        float totalMilkInt = 0;
        for(UserLedgerModel user:userLedgerModels)
        {
            totalMilkInt+=Float.parseFloat(user.getMeasure());
            total+=(Float.parseFloat(user.getPrice()))*(Float.parseFloat(user.getMeasure()));
        }
        totalMilk = String.valueOf(totalMilkInt);
        Locale locale = new Locale("en" ,"PK");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
        txtTotalPrice.setText(fmt.format(total));
        totalmilk.setText(totalMilk);

        if(adapter.getItemCount()<=0)
        {
            recordIsEmpty.setVisibility(View.VISIBLE);
        }
        else{
            recordIsEmpty.setVisibility(View.GONE);
        }

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if(item.getTitle().equals(Common.UPDATE))
        {
            showUpdateDialog(adapter.getItem(item.getOrder()).getID(),adapter.getItem(item.getOrder()));
        }
        else if(item.getTitle().equals(Common.DELETE))
        {
            deleteItem(adapter.getItem(item.getOrder()).getID(), item.getOrder());

        }

        return super.onContextItemSelected(item);
    }

    private void showUpdateDialog(int idd, UserLedgerModel item) {
        AlertDialog.Builder alertDialog  =  new AlertDialog.Builder(UserLedger.this);
        alertDialog.setTitle("Update Record");
        alertDialog.setMessage("Update information");

        LayoutInflater inflater = this.getLayoutInflater();
        View add_menu_layout = inflater.inflate(R.layout.add_record_layout,null);


        itemName = add_menu_layout.findViewById(R.id.itemName);
        itemName.setEnabled(false);
        edtMeasure = add_menu_layout.findViewById(R.id.edtMeasure);
        edtPrice = add_menu_layout.findViewById(R.id.edtPrice);
        itemName.setText(item.getMilkName());
        edtMeasure.setText(item.getMeasure());
        edtPrice.setText(item.getPrice());
        String datee = item.getDate();
        alertDialog.setView(add_menu_layout);
        alertDialog.setIcon(R.drawable.ic_baseline_add_box_24);

        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                Boolean valueBool = checktheFields();
                if(valueBool)
                {

                    new Database(getBaseContext()).updateUserLedger(new UserLedgerModel(
                            userphone.getText().toString(),
                            itemName.getText().toString(),
                            edtMeasure.getText().toString(),
                            edtPrice.getText().toString(),
                            datee

                    ), item.getID()
                    );
                    loadListLedger(id);

                    updatePayment();
                    Snackbar.make(rootLayout,"Record is Updated",Snackbar.LENGTH_SHORT)
                            .show();
                }
            }
        });

        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        alertDialog.show();
    }

    private void updatePayment() {
        if(new Database(getBaseContext()).checkPaymentExists(userphone.getText().toString()))
        {
            String total =  txtTotalPrice.getText().toString();
            total = total.substring(2);
            total = total.replace(".00","");
            total = total.replace(",","");
            String paidBill =  paid.getText().toString();
            int totl = Integer.parseInt(total);
            int paidt = Integer.parseInt(paidBill);
            int finalRem = totl - paidt;
            String strRem = String.valueOf(finalRem);

            new Database(getBaseContext()).updateUserPayment(strRem, paidBill, strRem ,userphone.getText().toString());

            Snackbar.make(rootLayout,"Updated",Snackbar.LENGTH_SHORT)
                    .show();
        }

        loadPaymentDetail(id);
    }

    private void deleteItem(int position, int p) {
        userLedgerModels.remove(p);
        new Database(this).deleteRecord(position);
        if(adapter.getItemCount()<=0)
        {
            txtTotalPrice.setText("$00,000");
        }
        loadListLedger(id);

        updatePayment();

    }
}