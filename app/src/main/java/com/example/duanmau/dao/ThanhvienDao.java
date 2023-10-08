package com.example.duanmau.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.duanmau.database.DbHelper;
import com.example.duanmau.model.ThanhVien;

import java.util.ArrayList;

public class ThanhvienDao {
    DbHelper dbHelper;
    public ThanhvienDao(Context context){
        dbHelper = new DbHelper(context);
    }

    public  ArrayList<ThanhVien> getDSThanhVien(){
        ArrayList<ThanhVien> list = new ArrayList<>();
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("select * from THANHVIEN",null);
        if (cursor.getCount() != 0){
            cursor.moveToFirst();
            do {
                list.add(new ThanhVien(cursor.getInt(0), cursor.getString(1),cursor.getString(2)));
            }while (cursor.moveToNext());
        }
        return list;
    }
    public boolean themThanhvien(String hoTen, String namSinh){
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("hoTen",hoTen);
        contentValues.put("namSinh",namSinh);
        long check = sqLiteDatabase.insert("THANHVIEN",null,contentValues);
        if (check == -1)
            return  false;
        return true;
    }
}
