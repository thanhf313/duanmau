package com.example.duanmau.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.duanmau.database.DbHelper;

public class ThuThuDao  {
    DbHelper dbHelper;
    public  ThuThuDao(Context context){
        dbHelper = new DbHelper(context);
    }
    // đăng nhập
    public  boolean checkDN(String maTT, String matKhau){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("select * from THUTHU WHERE maTT = ? and matKhau = ?", new String[]{maTT,matKhau});
        if (c.getCount() != 0){
            return  true;

        }else {
            return  false;
        }
    }
}
