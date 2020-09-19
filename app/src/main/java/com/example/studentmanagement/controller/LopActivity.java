package com.example.studentmanagement.controller;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.studentmanagement.R;
import com.example.studentmanagement.database.Database;
import com.example.studentmanagement.model.Lop;

import java.util.ArrayList;
import java.util.List;

public class LopActivity extends AppCompatActivity {

    private ListView lvLop;
    private List<Lop> listLop;
    private Database db;
    private ArrayAdapter<Lop> aa;

    private static final int ITEM_VIEW = 1;
    private static final int ITEM_UPDATE = 2;
    private static final int ITEM_DELETE = 3;

    private static final int CREATE_CODE = 111;
    private static final int UPDATE_CODE = 222;

    private String option;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.qllop);

        lvLop = (ListView) findViewById(R.id.lv_lop);
        db = new Database(this);
        listLop = new ArrayList<Lop>();

        listLop.addAll(db.getAllLop());
        aa = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listLop);
        lvLop.setAdapter(aa);

        registerForContextMenu(lvLop);
    }

    public void themLop(View view) {
        Intent in = new Intent(this, ThemLopActivity.class);
        in.putExtra("mode", "them");

        startActivityForResult(in, CREATE_CODE);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        menu.setHeaderTitle("Chon thao tac");
        menu.add(0, ITEM_VIEW, 0, "Xem");
        menu.add(0, ITEM_UPDATE, 1, "Sua");
        menu.add(0, ITEM_DELETE, 2, "Xoa");
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        option = item.getTitle().toString();

        AdapterView.AdapterContextMenuInfo infor =
                (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        final Lop lop = (Lop) this.lvLop.getItemAtPosition(infor.position);

        if (option.equals("Xem")) {
            Intent in = new Intent(this, ThemLopActivity.class);
            in.putExtra("lop", lop);
            in.putExtra("mode", "xem");
            startActivity(in);
        } else if (option.equals("Sua")) {
            Intent in = new Intent(this, ThemLopActivity.class);
            in.putExtra("lop", lop);
            in.putExtra("mode", "sua");
            startActivityForResult(in, UPDATE_CODE);
        } else if (option.equals("Xoa")) {
            new AlertDialog.Builder(this)
                    .setMessage("Ban co chac chan muon xoa lop nay?")
                    .setCancelable(false)
                    .setNegativeButton("Khong", null)
                    .setPositiveButton("co", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            xoaLop(lop);
                        }
                    });
        } else
            return false;

        return true;

    }

    public void xoaLop(Lop lop) {
        db.deleteLop(lop);
        listLop.remove(lop);
        aa.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && (requestCode == CREATE_CODE || requestCode == UPDATE_CODE)) {
            boolean refresh = data.getBooleanExtra("refresh", true);

            if (refresh) {
                this.listLop.clear();
                listLop.addAll(db.getAllLop());
                aa.notifyDataSetChanged();
            }

            if (requestCode == CREATE_CODE) {
                Toast.makeText(this, "Da them lop moi", Toast.LENGTH_SHORT).show();
            }

            if (requestCode == UPDATE_CODE) {
                Toast.makeText(this, "Da cap nhat lop", Toast.LENGTH_SHORT).show();
            }

        }
    }
}
