package com.example.duanmau.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanmau.R;
import com.example.duanmau.dao.ThanhvienDao;
import com.example.duanmau.model.ThanhVien;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class ThanhVienAdapter extends RecyclerView.Adapter<ThanhVienAdapter.ViewHolder> {
    private Context context;
    private ArrayList<ThanhVien> listTV;
    private ThanhvienDao thanhvienDao;


    public ThanhVienAdapter(Context context, ArrayList<ThanhVien> listTV, ThanhvienDao thanhvienDao) {
        this.context = context;
        this.listTV = listTV;
        this.thanhvienDao = thanhvienDao;
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
    holder.imgedittv.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
           showdialogUpdateTV(listTV.get(holder.getAdapterPosition()));
           // lấy thông tv cần sửa show lên dialog
        }
    });
    holder.imgedittv.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int check = thanhvienDao.xoathongtinTv(listTV.get(holder.getAdapterPosition()).getMatv());
            switch (check){
                case 1:
                    Toast.makeText(context, "Xóa thành viên thành công", Toast.LENGTH_SHORT).show();
                    loadDate();
                    break;
                case 0:
                    Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                    break;
                case -1:
                    Toast.makeText(context, "Thành viên tồn tại phiêu mượn, không được xóa", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    });
    }

    @Override
    public int getItemCount() {
        return listTV.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtmaTV,txttenTV,txtnamsinhTV;
        ImageView imgxoatv,imgedittv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtmaTV = itemView.findViewById(R.id.txtmaTV1);
            txttenTV = itemView.findViewById(R.id.txttenTV1);
            txtnamsinhTV = itemView.findViewById(R.id.txtnamsinhTV1);
            imgxoatv = itemView.findViewById(R.id.imgXoaTV);
            imgedittv = itemView.findViewById(R.id.imgEditTV);
        }
    }
    private void showdialogUpdateTV(ThanhVien thanhVien) { // ojb chứa thông tin Thanh vien muốn sửa
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_update_thanhvien,null);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();

        TextView txtmatvTv = view.findViewById(R.id.txtmatvTv);
        TextInputEditText edthoten = view.findViewById(R.id.edtupHotenTV);
        TextInputEditText edtnamsinh = view.findViewById(R.id.edtupnamSinhTV);
        Button btnLuu = view.findViewById(R.id.btnLuuTV);
        Button btnhuy = view.findViewById(R.id.btnhuyTV);

            txtmatvTv.setText("Mã TV:" + thanhVien.getMatv());
            edthoten.setText(thanhVien.getHotentv());
            edtnamsinh.setText(thanhVien.getNamsinh());

        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hoten = edthoten.getText().toString();
                String namsinh = edtnamsinh.getText().toString();
                // app thêm tvDao
                int matv = thanhVien.getMatv();
                boolean check = thanhvienDao.capnhapthongtinTV(matv,hoten,namsinh);
                if (check){
                    Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    loadDate();
                } else {
                    Toast.makeText(context, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnhuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }
    private  void loadDate(){
        listTV.clear();
        listTV = thanhvienDao.getDSThanhVien();
        notifyDataSetChanged();
    }
}
