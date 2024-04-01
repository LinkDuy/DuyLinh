package com.example.quanliactivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<User> mListUser;
    private RecyclerView rcUsers;
    private UserAdapter userAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rcUsers = findViewById(R.id.recycelUser);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcUsers.setLayoutManager(linearLayoutManager);
        //Dong ke phan tach cac item trong list
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rcUsers.addItemDecoration(itemDecoration);


        mListUser = new ArrayList<>();
        //khoi moi khai bao interface phai override laij nos
        userAdapter = new UserAdapter(mListUser, new UserAdapter.IClickListener() {
            @Override
            public void onClickItem(User us) {
                openDialogUpdateItem(us);
            }

            @Override
            public void onClickDeleteItem(User us) {
                onClickDeleteData(us);
            }
        });
        rcUsers.setAdapter(userAdapter);
        getListUserfromDataBase();
    }

    private void getListUserfromDataBase() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myref = database.getReference("hehe");
//Cach 1
//        myref.addValueEventListener(new ValueEventListener() {
//            @Override
//            no tra ve tong cac item cos trong list minh dung for de lay ra tung cai item
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if(mListUser!=null){
//                    mListUser.clear();
//                }
//                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
//                        user us = dataSnapshot.getValue(user.class);
//                        mListUser.add(us);
//                }
//
//                userAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(MainActivity.this, "Fail", Toast.LENGTH_SHORT).show();
//            }
//        });
//      CACH2:
        myref.addChildEventListener(new ChildEventListener() {
            @Override
//            tra ve datasapshot cua cai item
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                User us = snapshot.getValue(User.class);
                if (us != null) {
                    mListUser.add(us);
                    userAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                User us = snapshot.getValue(User.class);
                if (us == null || mListUser == null || mListUser.isEmpty()) {
                    return;
                }
                for (int i = 0; i < mListUser.size(); i++) {
                    if ((us.getName() == mListUser.get(i).getName())&&(us.getPassword() == mListUser.get(i).getPassword())) {
                        mListUser.set(i, us);
                        break;
                    }
                }

                userAdapter.notifyDataSetChanged();
            }
            //khi ma dc xoa no se nhay vao su kien trong nay
            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                //get ra thong tin cua cai thang muon xoa
                User us = snapshot.getValue(User.class);
                if (us == null || mListUser == null || mListUser.isEmpty()) {
                    return;
                }
                for (int i = 0; i < mListUser.size(); i++) {
                    if (us.getUser() == mListUser.get(i).getUser()) {
                        mListUser.remove(mListUser.get(i));
                        break;
                    }
                }
                userAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void openDialogUpdateItem(User us) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //tao layout custom
        dialog.setContentView(R.layout.layout_dialog_update);
        //Khai bao window de chinh sua
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //khi ma click ra ngoai dialog k thoat ddc nen can 1 button de cancel
        dialog.setCancelable(false);

        EditText etName = dialog.findViewById(R.id.edtUpdateName);
        Button btCancel = dialog.findViewById(R.id.btnCancel);
        Button btUpdate = dialog.findViewById(R.id.btnUpdate);
        EditText etPass = dialog.findViewById(R.id.edtUpdatePass);

        etPass.setText(us.getPassword());
        etName.setText(us.getName());

        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myref = database.getReference("hehe");
                String newName = etName.getText().toString().trim();
                String newPass = etPass.getText().toString().trim();
                us.setName(newName);
                us.setPassword(newPass);
                //truy cap den referent
                myref.child(us.getUser()).updateChildren(us.toMap(), new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        Toast.makeText(MainActivity.this, "Update Success", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
                myref.child(us.getUser()).updateChildren(us.toMap1());
            }
        });
        dialog.show();
    }

    private void onClickDeleteData(User user) {
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.app_name))
                .setMessage("Ban co chac chan muon xoa khong")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference("hehe");
                        myRef.child(user.getUser()).removeValue(new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                Toast.makeText(MainActivity.this, "Delete success", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                })
                .setNegativeButton("Cancel",null)
                .show();
    }

}