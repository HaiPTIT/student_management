package com.example.studentmanagement.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.studentmanagement.MainActivity;
import com.example.studentmanagement.R;

public class QLActivity extends AppCompatActivity {

    private Button qlsv, qlLop, ganSV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        qlsv = (Button) findViewById(R.id.btn_qlsv);
        qlLop = (Button) findViewById(R.id.btn_qllop);
        ganSV = (Button) findViewById(R.id.btn_gan);
    }

    public void openQLSV(View view) {
        Intent in = new Intent(this, SVActivity.class);
        startActivity(in);
    }

    public void openQLL(View view) {
        Intent in = new Intent(this, LopActivity.class);
        startActivity(in);
    }
}
