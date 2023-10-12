package com.example.duanmau.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {
    public DbHelper(@Nullable Context context) {
        super(context,"SQL",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // nơi tạo bảng
        String thuThu = "CREATE TABLE THUTHU(maTT text primary key, hoTen text, matKhau text, loaiTK TEXT )";
        db.execSQL(thuThu);

        String thanhVien ="CREATE TABLE THANHVIEN(maTV integer primary key autoincrement, hoTen text, namSinh text)";
        db.execSQL(thanhVien);

        String loaiSach = "create table LOAISACH(maLoai integer PRIMARY KEY AUTOINCREMENT, tenLoai text)";
        db.execSQL(loaiSach);

        String sach = "create table SACH(maSach integer primary key autoincrement, tenSach text, giaThue integer, maLoai integer references LOAISACH(maLoai))";
        db.execSQL(sach);

        String phieuMuon= "create table PHIEUMUON(maPM integer primary key autoincrement, maTV integer references THANHVIEN(maTV),maTT text references THUTHU(maTT)," +
                "maSach integer references SACH(maSach), ngay text, traSach integer, tienThue integer)";
        db.execSQL(phieuMuon);

        // nhập dl
        db.execSQL("insert into LOAISACH values " +
                "(1,'Thiếu nhi'), (2,'Tình cảm'),(3,'Phiêu lưu'),(4,'Hành động')");
        db.execSQL("insert into SACH values" +
                "(1,'Thánh giống',10000,1),(2,'Titanic',30000,2),(3,'Qúa nhanh quá nguy hiểm',30200,4)");
        db.execSQL("insert into THUTHU values ('thuthu01','nguyễn trung thành','abc123','ADMIN'),('thuthu02','Thành','abc123','THỦ THƯ')");
        db.execSQL("insert into THANHVIEN values(1,'Nguyễn Văn A','2000'),(2,'Nguyễn Thị C','2001'),(3,'Lê Thị L','2000')");
        // trả sách:1 đã trả,0 chưa trả
        db.execSQL("insert into PHIEUMUON values(1,1,'thuthu01',2,'19/02/2023',1,3500),(2,2,'thuthu02',3,'25/09/2023',0,5000),(3,1,'thuthu02',3,'05/10/2023',1,5000)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion){
            db.execSQL("drop table if exists THUTHU");
            db.execSQL("drop table if exists THANHVIEN");
            db.execSQL("drop table if exists LOAISACH");
            db.execSQL("drop table if exists SACH");
            db.execSQL("drop table if exists PHIEUMUON");
            onCreate(db);
        }
    }
}
