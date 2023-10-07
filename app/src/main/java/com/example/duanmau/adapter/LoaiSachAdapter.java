package com.example.duanmau.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanmau.R;
import com.example.duanmau.dao.LoaiSachDao;
import com.example.duanmau.model.ItemClick;
import com.example.duanmau.model.LoaiSach;

import java.util.ArrayList;

public class LoaiSachAdapter extends RecyclerView.Adapter<LoaiSachAdapter.ViewHolder> {
    private Context context;
    private ArrayList<LoaiSach> list;
    private ItemClick itemClick;

    public LoaiSachAdapter(Context context, ArrayList<LoaiSach> list, ItemClick itemClick) {
        this.context = context;
        this.list = list;
        this.itemClick = itemClick;
    }

    @NonNull
    @Override
    public LoaiSachAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_recycler_loaisach,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LoaiSachAdapter.ViewHolder holder, int position) {
    holder.txtMaLoai.setText("Mã loại: " + String.valueOf(list.get(position).getId()));
    holder.txtTenLoai.setText("Tên loại: "+list.get(position).getTenLoai());

    holder.imgxoa.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            LoaiSachDao loaiSachDao = new LoaiSachDao(context);
            int check = loaiSachDao.xoaLoaiSach(list.get(holder.getAdapterPosition()).getId());
            switch (check){
                case 1:
                    list.clear();
                    list = loaiSachDao.getLoaiSach();
                    notifyDataSetChanged();
                    break;
                case -1:
                    Toast.makeText(context, "Không thể xóa loại sách này vì có sách thuộc thể loại này", Toast.LENGTH_SHORT).show();
                    break;
                case 0:
                    Toast.makeText(context, "Xóa loại sách không thành công", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    });
    holder.imgedit.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            LoaiSach loaiSach = list.get(holder.getAdapterPosition());
            itemClick.onClickLoaiSach(loaiSach);
        }
    });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtMaLoai,txtTenLoai;
        ImageView imgxoa,imgedit;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMaLoai = itemView.findViewById(R.id.txtMaLoai);
            txtTenLoai = itemView.findViewById(R.id.txtTenLoai);
            imgxoa = itemView.findViewById(R.id.imgXoaLS);
            imgedit = itemView.findViewById(R.id.imgEditLS);
        }
    }

}
