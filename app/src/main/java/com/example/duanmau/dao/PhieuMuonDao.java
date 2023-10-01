package com.example.duanmau.dao;

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
//    public ArrayList<PhieuMuon> getDSPhieuMuon(){
//        ArrayList<PhieuMuon> list = new ArrayList<>();
//        SQLiteDatabase database = dbHelper.getReadableDatabase();
//        Cursor cursor = database.rawQuery(" ");
//        if (cursor.getCount() != 0 ){
//            cursor.moveToFirst();
//            do {
//                list.add(new PhieuMuon());
//            }while (cursor.moveToNext());
//        }
//
//        return list;
//    }
}
