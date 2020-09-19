package com.example.studentmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.studentmanagement.controller.LopActivity;
import com.example.studentmanagement.controller.QLActivity;
import com.example.studentmanagement.controller.SVActivity;
import com.example.studentmanagement.database.Database;

public class MainActivity extends AppCompatActivity {

    EditText edtTK, edtMK;
    Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        edtTK = (EditText) findViewById(R.id.edt_tk);
        edtMK = (EditText) findViewById(R.id.edt_mk);
        db = new Database(this);
        db.addRecord();
    }

    public void login(View view) {
        String tk = edtTK.getText().toString();
        String mk = edtMK.getText().toString();

        boolean check = db.login(tk,mk);

        if (check) {
            Intent in = new Intent(this, QLActivity.class);
            startActivity(in);
        } else {
            Toast.makeText(this,"username or password was wrong!", Toast.LENGTH_SHORT).show();
        }
    }
}