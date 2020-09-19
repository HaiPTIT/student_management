package com.example.studentmanagement.controller;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.studentmanagement.R;
import com.example.studentmanagement.database.Database;
import com.example.studentmanagement.model.SinhVien;

import java.util.ArrayList;
import java.util.List;

public class SVActivity extends AppCompatActivity {

    private ListView lvSV;
    private List<SinhVien> listSV;
    private ArrayAdapter<SinhVien> aa;
    private Database db;

    private static final int CREATE_REQUEST = 111;
    private static final int UPDATE_REQUEST = 222;
    private static final int item_view = 1;
    private static final int item_update = 2;
    private static final int item_delete = 3;

    private String action;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qlsv);

        lvSV = (ListView) findViewById(R.id.lv_sv);
        listSV = new ArrayList<SinhVien>();
        db = new Database(this);

        listSV.addAll(db.getAllSV());
        aa = new ArrayAdapter<SinhVien>(this, android.R.layout.simple_list_item_1, listSV);
        lvSV.setAdapter(aa);

        registerForContextMenu(lvSV);
    }

    public void themSV(View view) {
        Intent in = new Intent(this, ThemSVActivity.class);
        in.putExtra("mode", "them");

        startActivityForResult(in, CREATE_REQUEST);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        menu.setHeaderTitle("Select action");

        menu.add(0, item_view, 0, "Xem");
        menu.add(0, item_update, 1, "Sua");
        menu.add(0, item_delete, 2, "Xoa");
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        action = item.getTitle().toString();

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        final SinhVien sv = (SinhVien) this.lvSV.getItemAtPosition(info.position);

        if (action.equals("Xem")) {
            Intent in = new Intent(this, ThemSVActivity.class);
            in.putExtra("sv", sv);
            in.putExtra("mode", "xem");
            startActivity(in);
        } else if(action.equals("Sua")) {
            Intent in = new Intent(this, ThemSVActivity.class);
            in.putExtra("sv", sv);
            in.putExtra("mode", "sua");
            startActivityForResult(in, UPDATE_REQUEST);
        } else if(action.equals("Xoa")) {
            new AlertDialog.Builder(this)
                    .setMessage("Ban co chac chan muon xoa khong?")
                    .setNegativeButton("Khong", null)
                    .setPositiveButton("Co", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            xoaSV(sv);
                        }
                    });
        } else
            return false;

        return true;
    }

    public void xoaSV(SinhVien sv) {
        db.deleteSV(sv);
        listSV.remove(sv);

        aa.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && (requestCode == CREATE_REQUEST || requestCode == UPDATE_REQUEST)) {
            boolean refresh = data.getBooleanExtra("refresh", true);

            if (refresh) {
                this.listSV.clear();
                List<SinhVien> list = db.getAllSV();
                listSV.addAll(list);
                aa.notifyDataSetChanged();
            }

            if (requestCode == CREATE_REQUEST) {
                Toast.makeText(this, "Da them sinh vien", Toast.LENGTH_SHORT).show();
            }

            if (requestCode == UPDATE_REQUEST) {
                Toast.makeText(this, "Da cap nhat sinh vien", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
