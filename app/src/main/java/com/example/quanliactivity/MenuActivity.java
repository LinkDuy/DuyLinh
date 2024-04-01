package com.example.quanliactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

public class MenuActivity extends AppCompatActivity {
//    String danhsach[] = {"Go shopping", "Chăm sóc khách hàng", "Admin"};
//    ArrayAdapter<String> myAdapter;
//    ListView lvList;
//    TextView txtSelect;
//    DrawerLayout drawerLayout;
//    NavigationView navigationView;
//    ActionBarDrawerToggle drawerToggle;

    //    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        if(drawerToggle.onOptionsItemSelected(item)){
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

//        txtSelect = findViewById(R.id.txtList);
//        lvList = findViewById(R.id.lv1);
//        myAdapter = new ArrayAdapter<>(MenuActivity.this, android.R.layout.simple_list_item_1, danhsach);
//        lvList.setAdapter(myAdapter);
        //Bat su kien
//        lvList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                if(danhsach.getClass(MenuActivity.this,danhsach[position]));
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//        drawerLayout = findViewById(R.id.drawer_layout);
//        navigationView = findViewById(R.id.navView);
//        drawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close);
//        drawerLayout.addDrawerListener(drawerToggle);
//        drawerToggle.syncState();
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
//            @SuppressLint("NonConstantResourceId")
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                switch (item.getItemId()){
//                    case R.id.home:
//                }
//                return false;
//            }
//        });
//
//    }
//
//    @Override
//    public void onBackPressed() {
//        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
//            drawerLayout.closeDrawer(GravityCompat.START);
//        }
//        else{
//            super.onBackPressed();
//        }
//        super.onBackPressed();
//    }
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_content, menu);
        return true;
    }
}