package com.example.duanmau.dao;

import android.content.ContentValues;
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

    public  int capNhapMK (String user, String oldPass,String newPass){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from THUTHU where maTT=? and matKhau=? ",new String[]{user,oldPass});
        // check xem thử có nhập đúng mật khẩu kh > tìm ra update mật khẩu
        if (cursor.getCount() > 0) {
            // cursor > 0 : nhập đúng thông tin -> cập nhật thông tin
            ContentValues contentValues = new ContentValues();
            contentValues.put("matKhau", newPass);
            long check = sqLiteDatabase.update("THUTHU", contentValues, "maTT=?", new String[]{user});
            if (check == -1)
                return -1;
            return 1; // nếu lớn 0 > true,
        }
        return 0; // < 0 trả về false
    }

}
