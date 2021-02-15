package com.microitindustry.khatasystem.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;


import com.microitindustry.khatasystem.Model.PaymentModel;
import com.microitindustry.khatasystem.Model.UserLedgerModel;
import com.microitindustry.khatasystem.Model.Users;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteAssetHelper {

    private static final String DB_Name="KhataSystemdb.db";
    private static final int DB_VER=1;

    public Database(Context context) {
        super(context, DB_Name,null, DB_VER);
    }
    public List<Users> getUsers()
    {

        SQLiteDatabase db =getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect={"ID","Name","Phone","Address"};
        String sqlTable="Users";

        qb.setTables(sqlTable);
        Cursor c = qb.query(db,sqlSelect,null,null,null,null,null);
        final List<Users> result = new ArrayList<>();
        if(c.moveToFirst()){
            do{
                result.add(new Users(
                        c.getInt(c.getColumnIndex("ID")),
                        c.getString(c.getColumnIndex("Name")),
                        c.getString(c.getColumnIndex("Phone")),
                        c.getString(c.getColumnIndex("Address"))
                        ));
            }while (c.moveToNext());
        }
        return  result;

    }

    public void addUser(Users users){
        SQLiteDatabase db = getReadableDatabase();
        String query  = String.format("INSERT INTO Users(Name,Phone,Address) VALUES('%s','%s','%s');",
                users.getUserName(),
                users.getPhoneNo(),
                users.getAddress()
                );
        db.execSQL(query);
    }

    public boolean checkPhoneExists(String phoneNo)
    {
        boolean flag = false;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;
        String query = String.format("SELECT * FROM Users WHERE Phone='%s'",phoneNo);
        cursor = db.rawQuery(query,null);
        if(cursor.getCount() > 0){
            flag = true;
        }
        else{
            flag = false;
        }
        cursor.close();
        return flag;
    }

    public String getName(String ID) {
        SQLiteDatabase db = getReadableDatabase();
        String Name = null;
        String query = String.format("SELECT Name FROM Users WHERE Phone ='%s'",ID);
        Cursor  cursor = db.rawQuery(query,null);
        if (cursor.moveToFirst()) {
            do
            {
                Name =  cursor.getString(cursor.getColumnIndex("Name"));


            }while (cursor.moveToNext());
        }
        cursor.close();
        return Name;
    }

    public List<UserLedgerModel> getUserLedgerDetail(String id)
    {

        SQLiteDatabase db =getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect={"ID","PhoneNo","MilkName","Measure","Price","Date"};
        String sqlTable="UserLedger";

        qb.setTables(sqlTable);
        Cursor c = qb.query(db,sqlSelect,"PhoneNo=?",new String[]{id},null,null,null);
        final List<UserLedgerModel> result = new ArrayList<>();
        if(c.moveToFirst()){
            do{
                result.add(new UserLedgerModel(
                        c.getInt(c.getColumnIndex("ID")),
                        c.getString(c.getColumnIndex("PhoneNo")),
                        c.getString(c.getColumnIndex("MilkName")),
                        c.getString(c.getColumnIndex("Measure")),
                        c.getString(c.getColumnIndex("Price")),
                        c.getString(c.getColumnIndex("Date"))
                ));
            }while (c.moveToNext());
        }
        return  result;

    }



    public void addUserLedger(UserLedgerModel userLedgerModel){
        SQLiteDatabase db = getReadableDatabase();
        String query  = String.format("INSERT INTO UserLedger(PhoneNo,MilkName,Measure,Price,Date) VALUES('%s','%s','%s','%s','%s');",
                userLedgerModel.getPhoneNo(),
                userLedgerModel.getMilkName(),
                userLedgerModel.getMeasure(),
                userLedgerModel.getPrice(),
                userLedgerModel.getDate()
        );
        db.execSQL(query);
    }

    public void updateUserLedger(UserLedgerModel userLedgerModel , int id){
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("UPDATE UserLedger SET Measure = %s , Price = %s WHERE ID = %d",userLedgerModel.getMeasure(),userLedgerModel.getPrice(),id);
        db.execSQL(query);
    }

    public void deleteRecord(int position) {
        SQLiteDatabase db = getReadableDatabase();
        String query  = String.format("DELETE FROM UserLedger WHERE ID ='%d'",position);
        db.execSQL(query);
    }

    public void deleteUserAllRecord(String phoneId) {
        SQLiteDatabase db = getReadableDatabase();
        String query  = String.format("DELETE FROM UserLedger WHERE PhoneNo ='%s'",phoneId);
        db.execSQL(query);
    }
    public void deleteUserAllPayment(String phoneId) {
        SQLiteDatabase db = getReadableDatabase();
        String query  = String.format("DELETE FROM PaymentManage WHERE PhoneNo ='%s'",phoneId);
        db.execSQL(query);
    }

    public void deleteUser(int position) {
        SQLiteDatabase db = getReadableDatabase();
        String query  = String.format("DELETE FROM Users WHERE ID ='%d'",position);
        db.execSQL(query);
    }

    public void addUserPayment(PaymentModel paymentModel){
        SQLiteDatabase db = getReadableDatabase();
        String query  = String.format("INSERT INTO PaymentManage(PhoneNo,Total_Bill,PaidBill,RemaingBill,Date) VALUES('%s','%s','%s','%s','%s');",
                paymentModel.getPhoneNo(),
                paymentModel.getTotalBill(),
                paymentModel.getPaidBill(),
                paymentModel.getRemainingBill(),
                paymentModel.getDate()
        );
        db.execSQL(query);
    }
    public boolean checkPaymentExists(String phoneNo)
    {
        boolean flag = false;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;
        String query = String.format("SELECT * FROM PaymentManage WHERE PhoneNo='%s'",phoneNo);
        cursor = db.rawQuery(query,null);
        if(cursor.getCount() > 0){
            flag = true;
        }
        else{
            flag = false;
        }
        cursor.close();
        return flag;
    }

    public void updateUserPayment(String t, String p, String r, String id){
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("UPDATE PaymentManage SET Total_Bill = %s , PaidBill = %s, RemaingBill = %s WHERE PhoneNo='%s'",t,p,r,id);
        db.execSQL(query);
    }

    public String getPaidBill(String ID) {
        SQLiteDatabase db = getReadableDatabase();
        String PaidBill = null;
        String query = String.format("SELECT PaidBill FROM PaymentManage WHERE PhoneNo='%s'",ID);
        Cursor  cursor = db.rawQuery(query,null);
        if (cursor.moveToFirst()) {
            do
            {
                PaidBill =  cursor.getString(cursor.getColumnIndex("PaidBill"));


            }while (cursor.moveToNext());
        }
        cursor.close();
        return PaidBill;
    }
    public String getRemBill(String ID) {
        SQLiteDatabase db = getReadableDatabase();
        String remBill = null;
        String query = String.format("SELECT RemaingBill FROM PaymentManage WHERE PhoneNo='%s'",ID);
        Cursor  cursor = db.rawQuery(query,null);
        if (cursor.moveToFirst()) {
            do
            {
                remBill =  cursor.getString(cursor.getColumnIndex("RemaingBill"));


            }while (cursor.moveToNext());
        }
        cursor.close();
        return remBill;
    }
    public String getTotalBill(String ID) {
        SQLiteDatabase db = getReadableDatabase();
        String totBill = null;
        String query = String.format("SELECT Total_Bill FROM PaymentManage WHERE PhoneNo='%s'",ID);
        Cursor  cursor = db.rawQuery(query,null);
        if (cursor.moveToFirst()) {
            do
            {
                totBill =  cursor.getString(cursor.getColumnIndex("Total_Bill"));


            }while (cursor.moveToNext());
        }
        cursor.close();
        return totBill;
    }
    public void updateTotal(String t,String id){
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("UPDATE PaymentManage SET Total_Bill = %s  WHERE PhoneNo='%s'",t,id);
        db.execSQL(query);
    }
    public void updatePaid(String p,String id){
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("UPDATE PaymentManage SET PaidBill = %s  WHERE PhoneNo='%s'",p,id);
        db.execSQL(query);
    }
    public void updateRemaining(String r,String id){
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("UPDATE PaymentManage SET RemaingBill = %s  WHERE PhoneNo='%s'",r,id);
        db.execSQL(query);
    }



//    public List<CourseDetailModel> getCourseDetail(String id)
//    {
//
//        SQLiteDatabase db =getReadableDatabase();
//        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
//
//        String[] sqlSelect={"ID","CID","LanguageFrom","LanguageTo"};
//        String sqlTable="CourseDetail";
//
//        qb.setTables(sqlTable);
//        Cursor c = qb.query(db,sqlSelect,"CID=?",new String[]{id},null,null,null);
//        final List<CourseDetailModel> result = new ArrayList<>();
//        if(c.moveToFirst()){
//            do{
//                result.add(new CourseDetailModel(
//                        c.getInt(c.getColumnIndex("ID")),
//                        c.getInt(c.getColumnIndex("CID")),
//                        c.getString(c.getColumnIndex("LanguageFrom")),
//                        c.getString(c.getColumnIndex("LanguageTo"))
//                ));
//            }while (c.moveToNext());
//        }
//        return  result;
//
//    }
//
//    public String getName(String ID) {
//        int id = Integer.parseInt(ID);
//        SQLiteDatabase db = getReadableDatabase();
//        String Name = null;
//        String query = String.format("SELECT Course_Name FROM Courses WHERE ID ='%d'",id);
//        Cursor  cursor = db.rawQuery(query,null);
//        if (cursor.moveToFirst()) {
//            do
//            {
//                Name =  cursor.getString(cursor.getColumnIndex("Course_Name"));
//
//
//            }while (cursor.moveToNext());
//        }
//        cursor.close();
//        return Name;
//    }



//    public void cleanCart(){
//        SQLiteDatabase db = getReadableDatabase();
//        String query  = String.format("DELETE FROM Courses");
//        db.execSQL(query);
//    }
//
//    public int getCountCart() {
//        int count=0;
//
//        SQLiteDatabase db = getReadableDatabase();
//        String query = String.format("SELECT COUNT(*) FROM Courses");
//        Cursor cursor = db.rawQuery(query,null);
//        if(cursor.moveToFirst())
//        {
//            do{
//                count = cursor.getInt(0);
//            }
//            while (cursor.moveToNext());
//        }
//        cursor.close();
//        return count;
//    }

//    public void updateCart(Courses courses) {
//        SQLiteDatabase db = getReadableDatabase();
//        String query = String.format("UPDATE OrderDetail SET Quantity = %s WHERE ID = %d",order.getQuantity(),order.getID());
//        db.execSQL(query);
//    }

//    public void IncreaseCart(String productId,String QTY) {
//
//        int mainQTY = getQTY(productId ,QTY ) ;
//        String strQRY = String.valueOf(mainQTY);
//        SQLiteDatabase db = getReadableDatabase();
//        String query = String.format("UPDATE OrderDetail SET Quantity = %s WHERE ProductId ='%s'",strQRY,productId);
//        db.execSQL(query);
//    }

//    public void removeFromCart(String productId) {
//        SQLiteDatabase db = getReadableDatabase();
//        String query  = String.format("DELETE FROM OrderDetail WHERE ProductId ='%s' ",productId);
//        db.execSQL(query);
//    }

//    private int getQTY(String productId , String enterQty) {
//        int intqty = Integer.parseInt(enterQty);
//        String qty = "0";
//        SQLiteDatabase db = getReadableDatabase();
//        String query = String.format("SELECT Quantity FROM OrderDetail  WHERE ProductId ='%s'",productId);
//        Cursor  cursor = db.rawQuery(query,null);
//        if (cursor.moveToFirst()) {
//            do
//            {
//                qty =  cursor.getString(cursor.getColumnIndex("Quantity"));
//
//
//            }while (cursor.moveToNext());
//        }
//        cursor.close();
//        int getqty = Integer.parseInt(qty);
//        int mainQty = intqty + getqty;
//        return mainQty;
//    }

//    public boolean checkProductExists(String productId)
//    {
//        boolean flag = false;
//        SQLiteDatabase db = getReadableDatabase();
//        Cursor cursor = null;
//        String query = String.format("SELECT * FROM OrderDetail WHERE ProductId='%s'",productId);
//        cursor = db.rawQuery(query,null);
//        if(cursor.getCount() > 0){
//            flag = true;
//        }
//        else{
//            flag = false;
//        }
//        cursor.close();
//        return flag;
//    }


}
