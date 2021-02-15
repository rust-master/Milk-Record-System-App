package com.microitindustry.khatasystem.Model;

public class UserLedgerModel {
    private int ID;
    private String PhoneNo;
    private String MilkName;
    private String Measure;
    private String Price;
    private String Date;

    public UserLedgerModel() {
    }

    public UserLedgerModel(int ID, String phoneNo, String milkName, String measure, String price, String date) {
        this.ID = ID;
        PhoneNo = phoneNo;
        MilkName = milkName;
        Measure = measure;
        Price = price;
        Date = date;
    }

    public UserLedgerModel(String phoneNo, String milkName, String measure, String price, String date) {
        PhoneNo = phoneNo;
        MilkName = milkName;
        Measure = measure;
        Price = price;
        Date = date;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getPhoneNo() {
        return PhoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        PhoneNo = phoneNo;
    }

    public String getMilkName() {
        return MilkName;
    }

    public void setMilkName(String milkName) {
        MilkName = milkName;
    }

    public String getMeasure() {
        return Measure;
    }

    public void setMeasure(String measure) {
        Measure = measure;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
}
