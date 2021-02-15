package com.microitindustry.khatasystem.Model;

public class PaymentModel {
    private int ID;
    private String PhoneNo;
    private String TotalBill;
    private String PaidBill;
    private String RemainingBill;
    private String Date;

    public PaymentModel() {
    }

    public PaymentModel(int ID, String phoneNo, String totalBill, String paidBill, String remainingBill, String date) {
        this.ID = ID;
        PhoneNo = phoneNo;
        TotalBill = totalBill;
        PaidBill = paidBill;
        RemainingBill = remainingBill;
        Date = date;
    }

    public PaymentModel(String phoneNo, String totalBill, String paidBill, String remainingBill, String date) {
        PhoneNo = phoneNo;
        TotalBill = totalBill;
        PaidBill = paidBill;
        RemainingBill = remainingBill;
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

    public String getTotalBill() {
        return TotalBill;
    }

    public void setTotalBill(String totalBill) {
        TotalBill = totalBill;
    }

    public String getPaidBill() {
        return PaidBill;
    }

    public void setPaidBill(String paidBill) {
        PaidBill = paidBill;
    }

    public String getRemainingBill() {
        return RemainingBill;
    }

    public void setRemainingBill(String remainingBill) {
        RemainingBill = remainingBill;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
}
