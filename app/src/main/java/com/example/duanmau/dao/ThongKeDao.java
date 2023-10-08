package com.example.duanmau.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.duanmau.database.DbHelper;
import com.example.duanmau.model.Sach;

import java.util.ArrayList;

public class ThongKeDao {
    DbHelper dbHelper;
    public  ThongKeDao (Context context){
        dbHelper = new DbHelper(context);
    }

    public ArrayList<Sach> getTop10(){
        ArrayList<Sach> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT pm.masach, sc.tensach, COUNT(pm.masach) FROM PHIEUMUON pm,SACH sc WHERE pm.masach = sc.masach GROUP BY pm.masach, sc.tensach ORDER by COUNT(pm.masach) DESC LIMIT 10",null);
        if (cursor.getCount() != 0){
            cursor.moveToFirst();
            do {
                list.add(new Sach(cursor.getInt(0),cursor.getString(1),cursor.getInt(2)));
            }while (cursor.moveToNext());
        }
        return list;
    }

    public int getDoanhthu(String ngaybd, String ngaykt){
        ngaybd =ngaybd.replace("/","");// thay đổi / thành kh có dấu
        ngaykt =ngaykt.replace("/","");
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT SUM(tienthue) FROM PHIEUMUON  WHERE substr(ngay,7) || substr(ngay,4,2) || substr(ngay,1,2) BETWEEN ? AND ?",new String[]{ngaybd,ngaykt});
        if (cursor.getCount() != 0){
            cursor.moveToFirst();
            return cursor.getInt(0);
        }
        return 0;
      }
}
