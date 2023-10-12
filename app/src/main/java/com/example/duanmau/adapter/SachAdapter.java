package com.example.duanmau.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanmau.R;
import com.example.duanmau.dao.SachDao;
import com.example.duanmau.model.Sach;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.HashMap;

public class SachAdapter extends RecyclerView.Adapter<SachAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Sach> list;
private ArrayList<HashMap<String,Object>> listHM;
private SachDao sachDao;


    public SachAdapter(Context context, ArrayList<Sach> list, ArrayList<HashMap<String, Object>> listHM, SachDao sachDao) {
        this.context = context;
        this.list = list;
        this.listHM = listHM;
        this.sachDao = sachDao;
    }

    @NonNull
    @Override
    public SachAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater i = ((Activity)context).getLayoutInflater();
        View view = i.inflate(R.layout.item_recyc_sach,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SachAdapter.ViewHolder holder, int position) {
        holder.txtmaSach.setText("Mã Sách:" + list.get(position).getMasach());
        holder.txttenSach.setText("Tên Sách: " + list.get(position).getTensach());
        holder.txtgiaThue.setText("Gía Thuê: " + list.get(position).getGiathue());
        holder.txtmaLoai.setText("Mã Loại: " + list.get(position).getMaloai());
        holder.txttenLoai.setText("Tên loại:" + list.get(position).getTenloai());

        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showdialog(list.get(holder.getAdapterPosition()));
            }
        });
        holder.imgXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int check = sachDao.xoaSach(list.get(holder.getAdapterPosition()).getMasach());
                switch (check){
                    case 1:
                        Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                        loadData();
                        break;
                    case 0:
                        Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                        break;
                    case -1:
                        Toast.makeText(context, "Không được xóa,vì sách có trong phiếu mượn", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtmaSach,txttenSach,txtgiaThue,txtmaLoai,txttenLoai;
        ImageView imgXoa,imgEdit;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtmaSach = itemView.findViewById(R.id.txtmaSachSach);
            txttenSach = itemView.findViewById(R.id.txttenSachSach);
            txtgiaThue = itemView.findViewById(R.id.txtgiathueSachSach);
            txtmaLoai = itemView.findViewById(R.id.txtmaLoaiSachSach);
            txttenLoai= itemView.findViewById(R.id.txttenloaiSachSach);
            imgXoa = itemView.findViewById(R.id.imgXoaSach);
            imgEdit= itemView.findViewById(R.id.imgEditSach);
        }
    }
    private void showdialog(Sach sach) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_edit_sach,null );

        builder.setView(view);
        AlertDialog alertDialog = builder.create();


        TextInputEditText edttensach = view.findViewById(R.id.edtTenSachnew);
        TextInputEditText edttien = view.findViewById(R.id.edtTienSachnew);
        TextView txtmaSach = view.findViewById(R.id.txtMaSachnew);
        Spinner spnloaisach = view.findViewById(R.id.spnLoaisachnew);
        Button btnadd = view.findViewById(R.id.btnLuusachnew);
        Button btnhuy = view.findViewById(R.id.btnhuysachnew);

        txtmaSach.setText("Mã sách: "+ sach.getMasach());
        edttensach.setText(sach.getTensach());
        edttien.setText(String.valueOf(sach.getGiathue()));

        SimpleAdapter simpleAdapter = new SimpleAdapter(
                context,
                listHM,
                android.R.layout.simple_list_item_1,
                new String[]{"tenLoai"},
                new int[]{android.R.id.text1}
        );
        spnloaisach.setAdapter(simpleAdapter);

        int index = 0; // tìm vị trị tên laoij
        int postion =-1; // vị trí của tên laoij đó , nằm trg ds
        for (HashMap<String,Object> item : listHM){
            if ((int)item.get("maLoai") == sach.getMasach()){
                postion = index;
            }
            index++;
        }
        spnloaisach.setSelection(postion);
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tensach = edttensach.getText().toString();
                int tien = Integer.parseInt(edttien.getText().toString());
                HashMap<String,Object> hs = (HashMap<String, Object>) spnloaisach.getSelectedItem();
                int maLoai = (int) hs.get("maLoai");

                boolean check = sachDao.xuathongtinSach(sach.getMasach(),tensach,tien,maLoai);
                if (check){
                    Toast.makeText(context, "Cập nhật sách thành công", Toast.LENGTH_SHORT).show();
                    loadData();
                }else {
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
    public  void loadData(){
        list.clear();
        list = sachDao.getDSDauSach();
        notifyDataSetChanged();
    }
}
