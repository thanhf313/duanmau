package com.example.duanmau.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanmau.R;
import com.example.duanmau.adapter.LoaiSachAdapter;
import com.example.duanmau.dao.LoaiSachDao;
import com.example.duanmau.model.ItemClick;
import com.example.duanmau.model.LoaiSach;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class QLLoaiSachFragment extends Fragment {
    LinearLayoutManager layoutManager;
    LoaiSachDao loaiSachDao;
    ArrayList<LoaiSach> list;
//    LoaiSachAdapter adapter;
    RecyclerView recyclerViewLoaiSach;
    TextInputEditText edtLoaiSach;
    int maloai;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_qlloaisach, container, false);
        recyclerViewLoaiSach = view.findViewById(R.id.recyclerSach);
         edtLoaiSach = view.findViewById(R.id.edtLoaiSach);
        Button btnThem = view.findViewById(R.id.btnThemSach);
        Button btnSua = view.findViewById(R.id.btnSuaSach);

        loaiSachDao = new LoaiSachDao(getContext());
        load();

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String edttenloai = edtLoaiSach.getText().toString();
                if (loaiSachDao.themLoaiSach(edttenloai)) {
                    // thông báo + load ds
                    Toast.makeText(getActivity(), "Thêm LOẠI SÁCH thành công", Toast.LENGTH_SHORT).show();
                    load();
                    edtLoaiSach.setText("");
                } else {
                    Toast.makeText(getActivity(), "Thêm loại sách thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenloai = edtLoaiSach.getText().toString();
                LoaiSach loaiSach = new LoaiSach(maloai,tenloai);
                if (loaiSachDao.suaThongTinLoaiSach(loaiSach)){
                    load();
                    edtLoaiSach.setText("");
                    Toast.makeText(getContext(), "Thay đổi thành công", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getContext(), "Thay đổi thông tin không thành công", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

    private void load() {
        layoutManager = new LinearLayoutManager(getContext());
        recyclerViewLoaiSach.setLayoutManager(layoutManager);
        list = loaiSachDao.getLoaiSach();
        LoaiSachAdapter adapter = new LoaiSachAdapter(getContext(), list, new ItemClick() {
            @Override
            public void onClickLoaiSach(LoaiSach loaiSach) {
                edtLoaiSach.setText(loaiSach.getTenLoai()); // hiên lên thành edtext
                maloai = loaiSach.getId(); // có đc mã loại
            }
        });
        recyclerViewLoaiSach.setAdapter(adapter);
    }
}
