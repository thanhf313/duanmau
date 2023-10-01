package com.example.duanmau.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanmau.R;
import com.example.duanmau.dao.PhieuMuonDao;
import com.example.duanmau.model.PhieuMuon;

import java.util.ArrayList;

public class PhieuMuonAdapter extends RecyclerView.Adapter<PhieuMuonAdapter.ViewHolder> {
    private ArrayList<PhieuMuon> list;
    private Context context;

    public PhieuMuonAdapter(ArrayList<PhieuMuon> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public PhieuMuonAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_recycler_phieumuon, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhieuMuonAdapter.ViewHolder holder, int position) {
        holder.txtmaPM.setText("Mã PM: " + list.get(position).getMapm());
        holder.txtmaTv.setText("Mã TV: " + list.get(position).getMatt());
        holder.txttenTV.setText("Tên TV: " + list.get(position).getTentv());
        holder.txtmaTT.setText("Mã TT: " + list.get(position).getMatt());
        holder.txttenTT.setText("Tên TT: " + list.get(position).getTentt());
        holder.txtmaSach.setText("Mã Sách: " + list.get(position).getMasach());
        holder.txttenSach.setText("Tên sách: " + list.get(position).getTensach());
        holder.txtngay.setText("Ngày: " + list.get(position).getNgay());
        String trangthai = " ";
        if (list.get(position).getTrasach() == 1) {
            trangthai = "Đã trả sách";
            holder.btnTraSach.setVisibility(View.GONE);
        } else {
            trangthai = "Chủa trả sách";
            holder.btnTraSach.setVisibility(View.VISIBLE);
        }
        holder.txttrangThai.setText("Trạng thái: " + trangthai);
        holder.txttienthue.setText("Tiền: " + list.get(position).getTienthue());

        holder.btnTraSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhieuMuonDao phieuMuonDao = new PhieuMuonDao(context);
                boolean kiemtra = phieuMuonDao.TraSach(list.get(holder.getAdapterPosition()).getMapm());
                if (kiemtra == true) {
                    list.clear();
                    list = phieuMuonDao.getDSPhieuMuon();
                    notifyDataSetChanged();
                }else {
                    Toast.makeText(context, "Thay đổi thất bại", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtmaPM, txtmaTv, txttenTV, txtmaTT, txttenTT, txtmaSach, txttenSach, txtngay, txttrangThai, txttienthue;
        Button btnTraSach;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtmaPM = itemView.findViewById(R.id.txtmaPM);
            txtmaTv = itemView.findViewById(R.id.txtmaTV);
            txttenTV = itemView.findViewById(R.id.txttenTV);
            txtmaTT = itemView.findViewById(R.id.txtmaTT);
            txttenTT = itemView.findViewById(R.id.txttenTT);
            txtmaSach = itemView.findViewById(R.id.txtmaSach);
            txttenSach = itemView.findViewById(R.id.txttenSach);
            txtngay = itemView.findViewById(R.id.txtngay);
            txttrangThai = itemView.findViewById(R.id.txttrangThai);
            txttienthue = itemView.findViewById(R.id.txttienthue);
            btnTraSach = itemView.findViewById(R.id.btnTrasach);
        }
    }
}
