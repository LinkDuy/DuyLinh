package com.example.quanliactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class DangNhapMainActivity extends AppCompatActivity {
    EditText edtUser, edtPass;
    Button btnIn,btnUp;
    private List<User> mListUser;
    private RecyclerView rcUsers;
    private UserAdapter userAdapter;
    ImageButton btnHienthi;
    Boolean check = true;
    Boolean ck1 = false, ck2 = false;
    private ProgressBar progressBar3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap_main);
        edtUser  = findViewById(R.id.edtUserin);
        edtPass = findViewById(R.id.edtPassin);
        btnIn = findViewById(R.id.btnSignin);
        btnHienthi = findViewById(R.id.btnPass1);
        btnUp = findViewById(R.id.btnSignUp);
        progressBar3 = findViewById(R.id.progressBar3);
        btnIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar3.setVisibility(ProgressBar.VISIBLE);
                checkDataEntered();
            }
        });
        btnUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DangNhapMainActivity.this, DangKyActivity.class);
                startActivity(intent);
            }
        });
        btnHienthi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(check){
                    edtPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }else{
                    edtPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                check=!check;
            }
        });
    }
    void checkDataEntered(){
        if (isEmpty(edtUser)) {
            edtUser.setError("Enter the User Name!");
            progressBar3.setVisibility(ProgressBar.GONE);
        }else{
            ck1 = true;
        }
        if (isEmpty(edtPass)) {
            edtPass.setError("Enter the Password!");
            progressBar3.setVisibility(ProgressBar.GONE);
        }else{
            ck2 = true;
        }
        if(ck1&&ck2){
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myref = database.getReference("hehe");
            myref.addValueEventListener(new ValueEventListener() {
            @Override
//            no tra ve tong cac item cos trong list minh dung for de lay ra tung cai item
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int dem = 0;
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                        User us = dataSnapshot.getValue(User.class);
                        if((us.getUser().equals(edtUser.getText().toString().trim()))&&(us.getPassword().equals(edtPass.getText().toString().trim()))){
                            dem++;
                            break;
                        }
                }
                if(dem!=0){
                    Intent intent = new Intent(DangNhapMainActivity.this,MainActivity.class);
                    startActivity(intent);
                }else{
//                    Toast.makeText(DangNhapMainActivity.this, "User or Password is incorrect", Toast.LENGTH_LONG).show();
                    progressBar3.setVisibility(ProgressBar.GONE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(DangNhapMainActivity.this, "Fail", Toast.LENGTH_SHORT).show();
            }
        });
        }
    }
    boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }
}