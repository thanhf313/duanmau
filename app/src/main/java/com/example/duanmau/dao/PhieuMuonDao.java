package com.example.duanmau.dao;

import android.content.Context;

import com.example.duanmau.database.DbHelper;

public class PhieuMuonDao {
    DbHelper dbHelper;
    public PhieuMuonDao(Context context){
        dbHelper = new DbHelper(context);
    }
}
