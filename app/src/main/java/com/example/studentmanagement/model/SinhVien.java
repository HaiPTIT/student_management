package com.example.studentmanagement.model;

import java.io.Serializable;

public class SinhVien implements Serializable {

    private int id;
    private String ho;
    private String ten;
    private int lop;
    private String nganh;
    private String email;
    private String matKhau;

    public SinhVien() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public SinhVien(String ho, String ten, int lop, String nganh, String email, String matKhau) {
        this.ho = ho;
        this.ten = ten;
        this.lop = lop;
        this.nganh = nganh;
        this.email = email;
        this.matKhau = matKhau;
    }

    public SinhVien(int id, String ho, String ten, int lop, String nganh) {
        this.id = id;
        this.ho = ho;
        this.ten = ten;
        this.lop = lop;
        this.nganh = nganh;
    }

    public SinhVien(String ho, String ten, int lop, String nganh) {
        this.ho = ho;
        this.ten = ten;
        this.lop = lop;
        this.nganh = nganh;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHo() {
        return ho;
    }

    public void setHo(String ho) {
        this.ho = ho;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public int getLop() {
        return lop;
    }

    public void setLop(int lop) {
        this.lop = lop;
    }

    public String getNganh() {
        return nganh;
    }

    public void setNganh(String nganh) {
        this.nganh = nganh;
    }

    public String toString() {
        return this.ho + " " + this.ten;
    }
}
