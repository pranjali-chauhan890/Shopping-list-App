package com.v2v.shoppinglistapp;



import android.app.AlertDialog;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    ArrayAdapter<String> adapter;
    ArrayList<String>  items = new ArrayList<>();
    int selectedPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        listView= findViewById(R.id.listView);
        items.add("cake");
        items.add("Rice");
        items.add(0,"Sugar");
        adapter= new ArrayAdapter<>(this,R.layout.list_item,items);
        listView.setAdapter(adapter);
        registerForContextMenu(listView);
        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            selectedPosition = position;
            return  false;
        });


    }
    @Override
    public void onCreateContextMenu(ContextMenu cm, View v, ContextMenu.ContextMenuInfo info){
        getMenuInflater().inflate(R.menu.context_menu,cm);

    }
    @Override
    public boolean onContextItemSelected(MenuItem m){
        if(m.getItemId()==R.id.remove){

            String selectedItem = items.get(selectedPosition);

            new AlertDialog.Builder(this)
                    .setTitle("Remove Item")
                    .setMessage("Are you sure you want to remove \"" + selectedItem + "\"?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        items.remove(selectedPosition);
                        adapter.notifyDataSetChanged();
                        selectedPosition = -1;
                    })
                    .setNegativeButton("No", null)
                    .show();


        }
        return  true;
    }
    @Override
    public  boolean onCreateOptionsMenu(Menu m){
        getMenuInflater().inflate(R.menu.menu_main,m);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem m){
        if (m.getItemId()==R.id.add){
            EditText et = new EditText(this);
            new AlertDialog.Builder(this)
                    .setTitle("Add new Item")
                    .setView(et)
                    .setPositiveButton("Add",(dialog, which) -> {
                        items.add(et.getText().toString());
                        adapter.notifyDataSetChanged();
                    })
                    .setNegativeButton("Cancel",null)
                    .show();

        } else if (m.getItemId()==R.id.clear) {
            EditText et = new EditText(this);
            new AlertDialog.Builder(this)
                    .setTitle("Clear All")
                    .setMessage("Are you sure you want to clear the entire shopping list?")
                    .setView(et)
                    .setPositiveButton("Yes",(dialog, which) -> {
                        items.add(et.getText().toString());
                        adapter.notifyDataSetChanged();
                    })
                    .setNegativeButton("No",null)
                    .show();


        }else if (m.getItemId()==R.id.about){
            EditText et = new EditText(this);
            new AlertDialog.Builder(this)
                    .setTitle("About")
                    .setMessage(" Shopping List v1.0\nDeveloped by Pranjali Chauhan")
                    .setPositiveButton("OK", null)
                    .show();

        }
        return true;
    }
}