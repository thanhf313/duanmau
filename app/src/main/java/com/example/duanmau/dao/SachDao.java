package com.example.duanmau.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ImageView;

import com.example.duanmau.database.DbHelper;
import com.example.duanmau.model.Sach;

import java.util.ArrayList;

public class SachDao {
    DbHelper dbHelper;
    public SachDao(Context context){
        dbHelper = new DbHelper(context);
    }

    // viết hàm thực hiện cn
    // lấy đầu sách trong thư viện
    public ArrayList<Sach> getDSDauSach(){
        ArrayList<Sach> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT sc.masach,sc.tensach,sc.giathue,sc.maloai,ls.tenloai FROM SACH sc,LOAISACH ls WHERE sc.maloai = ls.maloai",null);
        if (cursor.getCount() != 0){
            cursor.moveToFirst();
            do {
                list.add(new Sach(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getInt(3),cursor.getString(4)));
            }while (cursor.moveToNext()); // còn dl còn
        }
        return list;
    }

    public boolean themSachMoi(String tenSach, int giatien, int maLoai){
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tenSach",tenSach);
        contentValues.put("giaThue",giatien);
        contentValues.put("maLoai",maLoai);
        long check = sqLiteDatabase.insert("SACH",null,contentValues);
        if (check == -1)
            return false;
        return true;
    }
    public boolean xuathongtinSach(int maSach,String tenSach,int giathue,int maLoai){
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tenSach",tenSach);
        contentValues.put("giaThue",giathue);
        contentValues.put("maLoai",maLoai);
        long check = sqLiteDatabase.update("SACH",contentValues,"maSach=?",new String[]{String.valueOf(maSach)});
        if (check==-1)
            return false;
        return true;
    }
    public int xoaSach(int maSach){
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from PHIEUMUON where maSach=?",new String[]{String.valueOf(maSach)});
        if (cursor.getCount() != 0)
            return -1;

        long check = sqLiteDatabase.delete("SACH","maSach=?",new String[]{String.valueOf(maSach)});
        if (check==-1)
            return 0;
        return 1;
    }

}
