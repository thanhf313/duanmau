package com.example.duanmau.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanmau.R;
import com.example.duanmau.model.ThanhVien;

import java.util.ArrayList;

public class ThanhVienAdapter extends RecyclerView.Adapter<ThanhVienAdapter.ViewHolder> {
    private Context context;
    private ArrayList<ThanhVien> listTV;

    public ThanhVienAdapter(Context context, ArrayList<ThanhVien> listTV) {
        this.context = context;
        this.listTV = listTV;
    }

    @NonNull
    @Override
    public ThanhVienAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_recycler_thanhvien,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ThanhVienAdapter.ViewHolder holder, int position) {
    holder.txtmaTV.setText("Mã TV: "+ listTV.get(position).getMatv());
    holder.txttenTV.setText("Tên TV: "+ listTV.get(position).getHotentv());
    holder.txtnamsinhTV.setText("Năm sinh: " + listTV.get(position).getNamsinh());
    }

    @Override
    public int getItemCount() {
        return listTV.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtmaTV,txttenTV,txtnamsinhTV;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtmaTV = itemView.findViewById(R.id.txtmaTV1);
            txttenTV = itemView.findViewById(R.id.txttenTV1);
            txtnamsinhTV = itemView.findViewById(R.id.txtnamsinhTV1);
        }
    }
}
