package com.example.studentmanagement.model;

import android.content.ContentValues;

import com.example.studentmanagement.database.Database;

import java.io.Serializable;
import java.util.List;

public class Lop implements Serializable {

    private int id;
    private String ten;
    private String moTa;


    public Lop() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public Lop(int id, String ten, String moTa) {
        this.id = id;
        this.ten = ten;
        this.moTa = moTa;
    }

    public Lop(String ten, String moTa) {
        this.ten = ten;
        this.moTa = moTa;
    }

    public String toString() {
        return this.ten;
    }
}
