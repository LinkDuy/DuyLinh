package com.example.quanliactivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHoder> {
    private List<User> mListUser;
    private IClickListener mIClickListener;
    public interface IClickListener{
        void onClickItem(User us);
        void onClickDeleteItem(User us);
    }
    public UserAdapter(List<User> mListUser, IClickListener listener) {
        this.mListUser = mListUser;
        this.mIClickListener = listener;
    }



    @NonNull
    @Override
    public UserViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user,parent,false);
        return new UserViewHoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHoder holder, int position) {
        User us = mListUser.get(position);
        if (us==null){
            return;
        }
        holder.txtUSER.setText("User: "+us.getUser());
        holder.txtNAME.setText("Name: "+us.getName());
        holder.txtPASS.setText("PassWord: "+us.getPassword());
        holder.btnUpDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIClickListener.onClickItem(us);
            }
        });
        holder.btnDele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIClickListener.onClickDeleteItem(us);
            }
        });

    }

    @Override
    public int getItemCount() {
        if(mListUser!=null){
            return mListUser.size();
        }
        return 0;
    }

    public class UserViewHoder extends RecyclerView.ViewHolder {
        private TextView txtNAME;
        private TextView txtPASS;
        private TextView txtUSER;
        private Button btnUpDate;
        private Button btnDele;

        public UserViewHoder(@NonNull View itemView) {
            super(itemView);
            txtUSER = itemView.findViewById(R.id.txtUser);
            btnDele = itemView.findViewById(R.id.btnDelete);
            txtNAME = itemView.findViewById(R.id.txtName);
            txtPASS = itemView.findViewById(R.id.txtPass);
            btnUpDate = itemView.findViewById(R.id.btnUpdate);
        }
    }
}
