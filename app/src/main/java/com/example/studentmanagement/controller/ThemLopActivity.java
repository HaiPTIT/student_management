package com.example.studentmanagement.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.studentmanagement.R;
import com.example.studentmanagement.database.Database;
import com.example.studentmanagement.model.Lop;

public class ThemLopActivity extends AppCompatActivity {

    private EditText edtTenLop, edtMtLop;
    private Lop lop;
    private String mode;
    private Database db;

    private Boolean refresh;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.them_lop);

        Intent in = getIntent();
        db = new Database(this);

        edtTenLop = (EditText) findViewById(R.id.edt_ten_lop);
        edtMtLop = (EditText) findViewById(R.id.edt_mt_lop);

        mode = in.getStringExtra("mode");

        if (mode.equals("xem") || mode.equals("sua")) {
            lop = (Lop) in.getSerializableExtra("lop");
            edtTenLop.setText(lop.getTen());
            edtMtLop.setText(lop.getMoTa());
        }

        if (mode.equals("xem")) {
            edtTenLop.setEnabled(false);
            edtTenLop.setInputType(InputType.TYPE_NULL);
            edtMtLop.setEnabled(false);
            edtMtLop.setInputType(InputType.TYPE_NULL);
        }
    }

    public void luuLop(View view) {
        if (mode.equals("them")) {
            lop =  new Lop(edtTenLop.getText().toString(), edtMtLop.getText().toString());
            db.addLop(lop);
        }

        if (mode.equals("sua")) {
            lop.setTen(edtTenLop.getText().toString());
            lop.setMoTa(edtMtLop.getText().toString());
            db.updateLop(lop);
        }

        refresh = true;
        onBackPressed();
    }

    @Override
    public void finish() {
        Intent data = new Intent();
        data.putExtra("refresh", refresh);
        this.setResult(Activity.RESULT_OK, data);

        super.finish();
    }
}
