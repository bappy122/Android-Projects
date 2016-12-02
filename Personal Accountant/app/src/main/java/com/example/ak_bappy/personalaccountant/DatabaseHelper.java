package com.example.ak_bappy.personalaccountant;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {


    public static final String DATABASE_NAME = "account.db";
    public static final String TABLE_NAME2 = "password_table";
    public static final String TABLE_NAME = "account_table";
    public static final String COL1 = "ID";
    public static final String COL2 = "DESCRIPTION";
    public static final String COL3 = "DATE";
    public static final String COL4 = "TIME";
    public static final String COL5 = "CATEGORY";
    public static final String COL6 = "TYPE";
    public static final String COL7 = "AMOUNT";

    public DatabaseHelper(Context context)
    {

        super(context, DATABASE_NAME, null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("create table "+TABLE_NAME2+ " (PASSWORD TEXT)");
        db.execSQL("create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, DESCRIPTION TEXT, DATE TEXT, TIME TEXT, CATEGORY TEXT, TYPE TEXT,AMOUNT TEXT)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("drop table if exists "+TABLE_NAME2);
        db.execSQL("drop table if exists "+TABLE_NAME );
        onCreate(db);
    }

    public boolean insertIntoPassword(String pass)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("PASSWORD", pass);
        long result = db.insert(TABLE_NAME2 ,null, contentValues);
        if(result==-1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public boolean updatePassword(String previousPassword,String newPassword)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("PASSWORD", newPassword);

        db.update(TABLE_NAME2, contentValues,"PASSWORD = ?",new String[] { previousPassword });
        return true;
    }

    public Cursor getPassword()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME2, null);
        return res;
    }

    public boolean insertData(String description, String date, String time, String category, String type, String amount)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL2, description);
        contentValues.put(COL3, date);
        contentValues.put(COL4, time);
        contentValues.put(COL5, category);
        contentValues.put(COL6, type);
        contentValues.put(COL7, amount);

        long result = db.insert(TABLE_NAME ,null, contentValues);

        if(result==-1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }
    public Cursor getAllData()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME, null);
        return res;
    }
    public boolean updateData(String id, String description, String date, String time, String category, String type, String amount)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL1, id);
        contentValues.put(COL2, description);
        contentValues.put(COL3, date);
        contentValues.put(COL4, time);
        contentValues.put(COL5, category);
        contentValues.put(COL6, type);
        contentValues.put(COL7, amount);

        db.update(TABLE_NAME, contentValues,"ID = ?",new String[] { id });
        return true;
    }
    public Integer deleteData(String id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?", new String[] {id});
    }
    public void deleteAllData()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+TABLE_NAME);
    }
    public Cursor getSearchedData(String searchKey)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME+" where DATE like '"+searchKey+"'"+" or CATEGORY like '"+searchKey+"'"+" or TYPE like '"+searchKey+"'"+" or ID like '"+searchKey+"'", null);
        return res;
    }
    public Cursor getDataBetweenDate(String from, String to)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("Select * from "+TABLE_NAME +" where "+COL3+" BETWEEN '"+from+"' AND '"+to+"' ",null);
        return res;
    }
    public Cursor getExpenditure(String from, String to)
    {
        String type = "Expense";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("Select AMOUNT from "+TABLE_NAME+" where TYPE like'"+type+"' AND (DATE BETWEEN '"+from+"' AND '"+to+"')",null);
        return res;
    }

    public Cursor getEarning(String from, String to)
    {
        String type = "Earning";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("Select AMOUNT from "+TABLE_NAME+" where TYPE like '"+type+"' AND (DATE BETWEEN '"+from+"' AND '"+to+"')",null);
        return res;
    }


}