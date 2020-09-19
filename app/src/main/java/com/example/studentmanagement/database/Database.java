package com.example.studentmanagement.database;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.studentmanagement.model.Lop;
import com.example.studentmanagement.model.SinhVien;

import java.lang.reflect.Array;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteOpenHelper {

    private static final int version = 1;
    private static final String databaseName = "ql_sinh_vien";

    protected static final String tableSV = "sinh_vien";
    protected static final String tableLop = "lop";

    protected static final String maSV = "id";
    protected static final String firstName = "first_name";
    protected static final String lastName = "last_name";
    protected static final String lopSV = "class";
    protected static final String khoaSV = "mayor";
    private static final String mail = "email";
    private static final String matKhau = "mat_khau";

    protected static final String maLop = "id";
    protected static final String tenLop = "name_class";
    protected static final String moTaLop = "desc_class";

    public Database(@Nullable Context context) {
        super(context, databaseName, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String studentQuery = "CREATE TABLE " + tableSV + "(" +
            maSV + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            mail + " TEXT NOT NULL, " +
            matKhau + " TEXT NOT NULL, " +
            firstName + " TEXT NOT NULL, " +
            lastName + " TEXT NOT NULL, " +
            lopSV + " INTEGER, " +
            khoaSV + " TEXT NOT NULL);";

        String lopQuery = "CREATE TABLE " + tableLop + "(" +
                maLop + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                tenLop + " TEXT NOT NULL, " +
                moTaLop + " TEXT NOT NULL);";

        sqLiteDatabase.execSQL(studentQuery);
        sqLiteDatabase.execSQL(lopQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + tableSV);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + tableLop);
        onCreate(sqLiteDatabase);
    }

    public List<Lop> getAllLop() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + tableLop;
        List<Lop> listLop = new ArrayList<>();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                Lop l = new Lop();
                l.setId(Integer.parseInt(cursor.getString(0)));
                l.setTen(cursor.getString(1));
                l.setMoTa(cursor.getString(2));
                listLop.add(l);
            } while(cursor.moveToNext());
        }
        db.close();

        return listLop;
    }

    public Lop getLop(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + tableLop + " WHERE " + maLop + " = " + id;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null)
            cursor.moveToFirst();

        Lop l = new Lop(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2));
        db.close();

        return l;
    }

    public void addLop(Lop l) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(tenLop, l.getTen());
        values.put(moTaLop, l.getMoTa());

        db.insert(tableLop,null, values);
        db.close();
    }

    public void updateLop(Lop l) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(tenLop, l.getTen());
        values.put(moTaLop, l.getMoTa());

        db.update(tableLop, values, maLop + "?=", new String[] {String.valueOf(l.getId())});
        db.close();
    }

    public void deleteLop(Lop l) {
        SQLiteDatabase db=  this.getWritableDatabase();
        db.delete(tableLop, maLop + "?=", new String[] {String.valueOf(l.getId())});
        db.close();
    }

    public List<SinhVien> getAllSV() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<SinhVien> listSV = new ArrayList<SinhVien>();
        String query = "SELECT * FROM " + tableSV;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                SinhVien sv = new SinhVien();
                sv.setHo(cursor.getString(1));
                sv.setTen(cursor.getString(2));
                sv.setLop(Integer.parseInt(cursor.getString(3)));
                sv.setNganh(cursor.getString(2));

                listSV.add(sv);
            } while (cursor.moveToNext());
        }
        db.close();

        return listSV;
    }

    public SinhVien getSV(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + tableSV + "WHERE " + maSV + "=" + id;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null)
            cursor.moveToFirst();

        SinhVien sv = new SinhVien(
                Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),
                cursor.getString(2),
                Integer.parseInt(cursor.getString(3)),
                cursor.getString(4));

        db.close();
        return sv;
    }

    public void addSV(SinhVien sv) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(firstName, sv.getHo());
        values.put(lastName, sv.getTen());
        values.put(lopSV, sv.getLop());
        values.put(khoaSV, sv.getNganh());
        values.put(mail, sv.getEmail());
        values.put(matKhau, sv.getMatKhau());

        db.insert(tableSV, null, values);
        db.close();
    }

    public void updateSV(SinhVien sv) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(firstName, sv.getHo());
        values.put(lastName, sv.getTen());
        values.put(lopSV, sv.getLop());
        values.put(khoaSV, sv.getNganh());

        db.update(tableSV, values, maSV + "?=", new String[]{String.valueOf(sv.getId())});
        db.close();
    }

    public void deleteSV(SinhVien sv) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(tableSV, maSV + "?=", new String[]{String.valueOf(sv.getId())});
        db.close();
    }

    public Boolean login(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + tableSV + " WHERE " + mail + "=?";

        Cursor cursor = db.rawQuery(query, new String[] {email});

        if (cursor != null) {
            cursor.moveToFirst();
        }

        String tk = cursor.getString(1);
        String mk  = cursor.getString(2);

        cursor.close();
        db.close();

        if (email.equals(tk) && password.equals(mk))
            return true;

        return false;
    }

    public int countSV() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + tableSV;

        Cursor cursor = db.rawQuery(query, null);
        int count = cursor.getCount();

        cursor.close();
        db.close();

        return count;
    }

    public void addRecord() {
        int count = this.countSV();

        if (count == 0) {
            SinhVien sv1 = new SinhVien("Vu", "Hai", 1, "cntt", "haivtndc@gmail.com", "password");
            SinhVien sv2 = new SinhVien("Ngoc", "Trinh", 1, "attt", "ngoctrinh@gmail.com", "password");
            SinhVien sv3 = new SinhVien("Thuy", "Kieu", 1, "dpt", "thuykieu@gmail.com", "password");
            this.addSV(sv1);
            this.addSV(sv2);
            this.addSV(sv3);
        }
    }
}
