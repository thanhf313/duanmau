package com.example.duanmau.model;

public class Sach {
    private  int masach;
    private String tensach;
    private int giathue;
    private int maloai;

    private int soluongdamuonsach;
    private String tenloai;
    public Sach() {
    }

    public Sach(int masach, String tensach, int giathue, int maloai) {
        this.masach = masach;
        this.tensach = tensach;
        this.giathue = giathue;
        this.maloai = maloai;
    }

    public Sach(int masach, String tensach, int soluongdamuonsach) {
        this.masach = masach;
        this.tensach = tensach;
        this.soluongdamuonsach = soluongdamuonsach;
    }

    public int getSoluongdamuonsach() {
        return soluongdamuonsach;
    }

    public Sach(int masach, String tensach, int giathue, int maloai, String tenloai) {
        this.masach = masach;
        this.tensach = tensach;
        this.giathue = giathue;
        this.maloai = maloai;
        this.tenloai = tenloai;
    }

    public String getTenloai() {
        return tenloai;
    }

    public void setTenloai(String tenloai) {
        this.tenloai = tenloai;
    }

    public void setSoluongdamuonsach(int soluongdamuonsach) {
        this.soluongdamuonsach = soluongdamuonsach;
    }

    public int getMasach() {
        return masach;
    }

    public void setMasach(int masach) {
        this.masach = masach;
    }

    public String getTensach() {
        return tensach;
    }

    public void setTensach(String tensach) {
        this.tensach = tensach;
    }

    public int getGiathue() {
        return giathue;
    }

    public void setGiathue(int giathue) {
        this.giathue = giathue;
    }

    public int getMaloai() {
        return maloai;
    }

    public void setMaloai(int maloai) {
        this.maloai = maloai;
    }
}
