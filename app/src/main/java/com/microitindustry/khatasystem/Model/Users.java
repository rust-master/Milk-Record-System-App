package com.microitindustry.khatasystem.Model;

public class Users {
    private int ID;
    private String UserName;
    private String PhoneNo;
    private String Address;

    public Users() {
    }

    public Users(int ID, String userName, String phoneNo, String address) {
        this.ID = ID;
        UserName = userName;
        PhoneNo = phoneNo;
        Address = address;
    }

    public Users(String userName, String phoneNo, String address) {
        UserName = userName;
        PhoneNo = phoneNo;
        Address = address;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPhoneNo() {
        return PhoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        PhoneNo = phoneNo;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }
}
