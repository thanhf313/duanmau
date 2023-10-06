package com.example.duanmau.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.duanmau.database.DbHelper;
import com.example.duanmau.model.LoaiSach;

import java.util.ArrayList;

public class LoaiSachDao {
    DbHelper dbHelper;
    public LoaiSachDao (Context context){
        dbHelper = new DbHelper(context);
    }
    // lấy ds loại sách
    public ArrayList<LoaiSach> getLoaiSach(){
        ArrayList<LoaiSach> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery("select * from LOAISACH",null);
        if (c.getCount() != 0) {
            c.moveToFirst();
            do {
              list.add(new LoaiSach(c.getInt(0),c.getString(1)));
            }while (c.moveToNext());
        }
        return  list;
    }

    // thêm loại sách
    public  boolean themLoaiSach(String tenloai){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tenLoai",tenloai);
        long check = sqLiteDatabase.insert("LOAISACH",null,contentValues);
        if (check == -1) return false;
        return true;
    }
}
