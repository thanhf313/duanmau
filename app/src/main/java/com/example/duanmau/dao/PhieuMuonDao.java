package com.example.duanmau.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.duanmau.database.DbHelper;
import com.example.duanmau.model.PhieuMuon;

import java.util.ArrayList;

public class PhieuMuonDao {
    DbHelper dbHelper;
    public PhieuMuonDao(Context context){
        dbHelper = new DbHelper(context);
    }
    public ArrayList<PhieuMuon> getDSPhieuMuon(){
        ArrayList<PhieuMuon> list = new ArrayList<>();
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT pm.mapm, pm.maTV, tv.hoten, pm.matt, tt.hoten, pm.masach, sc.tensach, pm.ngay, pm.trasach, pm.tienthue FROM PHIEUMUON pm, THANHVIEN tv, THUTHU tt, SACH sc WHERE pm.matv = tv.matv and pm.matt = tt.matt and pm.masach = sc.masach", null);
        if (cursor.getCount() != 0 ){
            cursor.moveToFirst();
            do {
                list.add(new PhieuMuon(cursor.getInt(0), cursor.getInt(1), cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getInt(5),cursor.getString(6),cursor.getString(7),cursor.getInt(8),cursor.getInt(9)));
            }while (cursor.moveToNext());
        }
        return list;
    }

    public boolean TraSach(int maPM){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("traSach", 1);
       long check =  sqLiteDatabase.update("PHIEUMUON", contentValues, "maPM = ?", new String[]{String.valueOf(maPM)});
        if (check == -1)return false;
        else {
            return true;
        }
    }
    public boolean themPhieuMuon(PhieuMuon phieuMuon){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("maPM", phieuMuon.getMapm());
        contentValues.put("maTV", phieuMuon.getMatv());
        contentValues.put("maTT", phieuMuon.getMatt());
        contentValues.put("maSach", phieuMuon.getMasach());
        contentValues.put("ngay", phieuMuon.getNgay());
        contentValues.put("traSach", phieuMuon.getTrasach());
        contentValues.put("tienThue", phieuMuon.getTienthue());

        long check = sqLiteDatabase.insert("PHIEUMUON", null, contentValues);
        if (check == -1){
            return  false;
        }else {
            return  true;
        }
    }
}
