package com.example.duanmau.model;

public class ThanhVien {
    private int matv;
    private String hotentv;
    private String namsinh;

    public ThanhVien() {
    }

    public ThanhVien(int matv, String hotentv, String namsinh) {
        this.matv = matv;
        this.hotentv = hotentv;
        this.namsinh = namsinh;
    }

    public int getMatv() {
        return matv;
    }

    public void setMatv(int matv) {
        this.matv = matv;
    }

    public String getHotentv() {
        return hotentv;
    }

    public void setHotentv(String hotentv) {
        this.hotentv = hotentv;
    }

    public String getNamsinh() {
        return namsinh;
    }

    public void setNamsinh(String namsinh) {
        this.namsinh = namsinh;
    }
}
