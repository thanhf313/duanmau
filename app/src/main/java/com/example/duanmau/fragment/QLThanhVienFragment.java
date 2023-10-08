package com.example.duanmau.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanmau.R;
import com.example.duanmau.adapter.ThanhVienAdapter;
import com.example.duanmau.dao.ThanhvienDao;
import com.example.duanmau.model.ThanhVien;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class QLThanhVienFragment extends Fragment {
    ThanhvienDao thanhvienDao;
    ArrayList<ThanhVien> list;
    RecyclerView recyclerViewTV;
    ThanhVienAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_qlthanhvien,container,false);

         recyclerViewTV = view.findViewById(R.id.recyclerThanhvien);
        FloatingActionButton floatTV = view.findViewById(R.id.floatAddTV);
         thanhvienDao =new ThanhvienDao(getContext());
        loaddata();

        floatTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showdialogAddTv();
            }


        });
        return view;
    }
    private void showdialogAddTv() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater= getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_themthanhvien,null);

        TextInputEditText edthoTen = view.findViewById(R.id.edtHotenTV);
        TextInputEditText edtnamsinh = view.findViewById(R.id.edtnamSinhTV);
        Button btnadd = view.findViewById(R.id.btnAddTV);
        Button btncancel = view.findViewById(R.id.btnCancelTV);
        builder.setView(view);
        AlertDialog alertDialog =builder.create();

        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hoTen = edthoTen.getText().toString();
                String namSinh = edtnamsinh.getText().toString();

                boolean check = thanhvienDao.themThanhvien(hoTen,namSinh);
                if (check ){
                    Toast.makeText(getContext(), "Thêm thành viên thành công", Toast.LENGTH_SHORT).show();
                        loaddata();
                }else {
                    Toast.makeText(getContext(), "Thêm thành viên thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        alertDialog.show();
    }
    public void loaddata(){
        list = thanhvienDao.getDSThanhVien();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerViewTV.setLayoutManager(layoutManager);
        adapter = new ThanhVienAdapter(getContext(),list);
        recyclerViewTV.setAdapter(adapter);
    }

}
