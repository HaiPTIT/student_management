package com.example.studentmanagement.controller;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.studentmanagement.R;
import com.example.studentmanagement.database.Database;
import com.example.studentmanagement.model.Lop;
import com.example.studentmanagement.model.SinhVien;

import java.util.ArrayList;
import java.util.List;

public class ThemSVActivity extends AppCompatActivity {

    private EditText edt_ho, edt_ten, edt_khoa;
    private Spinner lop;
    private String mode;

    private Lop l;
    private SinhVien sv;
    private Database db;
    private List<Lop> listLop;

    private boolean refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.them_sv);

        Intent in = getIntent();
        sv = (SinhVien) in.getSerializableExtra("sv");
        mode = in.getStringExtra("mode");

        db = new Database(this);
        listLop = new ArrayList<Lop>();

        edt_ho = (EditText) findViewById(R.id.edt_ho);
        edt_ten = (EditText) findViewById(R.id.edt_ten);
        lop = (Spinner) findViewById(R.id.sp_lop);
        edt_khoa = (EditText) findViewById(R.id.edt_khoa);

        listLop.addAll(db.getAllLop());
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, listLop);
        lop.setAdapter(aa);

        if (mode.equals("xem") || mode.equals("sua")) {
            l = db.getLop(sv.getLop());
            edt_ho.setText(sv.getHo());
            edt_ten.setText(sv.getTen());
            lop.setSelection(getIndex(lop, l.getTen()));
            edt_khoa.setText(sv.getNganh());
        }

        if (mode.equals("xem")) {
            edt_ho.setEnabled(false);
            edt_ho.setInputType(InputType.TYPE_NULL);
            edt_ten.setEnabled(false);
            edt_ten.setInputType(InputType.TYPE_NULL);
            lop.setEnabled(false);
            edt_khoa.setEnabled(false);
            edt_khoa.setInputType(InputType.TYPE_NULL);
        }

    }

    public void luuSV(View view) {

        l = (Lop) lop.getSelectedItem();
        String ho = edt_ho.getText().toString();
        String ten = edt_ten.getText().toString();
        int lop = l.getId();
        String khoa = edt_khoa.getText().toString();

        if (mode.equals("them")) {
            SinhVien sv = new SinhVien(ho, ten, lop, khoa);
            db.addSV(sv);
        }

        if (mode.equals("sua")) {
            sv.setHo(ho);
            sv.setTen(ten);
            sv.setLop(lop);
            sv.setNganh(khoa);

            db.updateSV(sv);
        }

        refresh = true;
        onBackPressed();
    }

    public int getIndex(Spinner spin, String text) {
        int count = spin.getCount();
        for (int i = 0; i <= count; i++) {
            if (spin.getItemAtPosition(i).toString().equalsIgnoreCase(text)) {
                return i;
            }
        }
        return 0;
    }

    @Override
    public void finish() {
        Intent data = new Intent();
        data.putExtra("refresh", refresh);

        this.setResult(Activity.RESULT_OK, data);
        super.finish();
    }
}
