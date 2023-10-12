package com.example.duanmau.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanmau.R;
import com.example.duanmau.adapter.SachAdapter;
import com.example.duanmau.dao.LoaiSachDao;
import com.example.duanmau.dao.SachDao;
import com.example.duanmau.model.LoaiSach;
import com.example.duanmau.model.Sach;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.HashMap;

public class QLSachFragment extends Fragment {
    RecyclerView recyclerViewSach;
    ArrayList<Sach> list;
    SachDao sachDao;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_qlsach,container,false);
        recyclerViewSach = view.findViewById(R.id.ryeSach);
        FloatingActionButton floatSach = view.findViewById(R.id.floadSach);

        sachDao = new SachDao(getContext());
        loadData();

        floatSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showdialog();

            }

        });
        return view;
    }
    private void showdialog() {
        AlertDialog.Builder builder= new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_them_sach,null);
        builder.setView(view);

        TextInputEditText edttensach = view.findViewById(R.id.edtTenSach);
        TextInputEditText edttien = view.findViewById(R.id.edtTienSach);
        Spinner spnloaisach = view.findViewById(R.id.spnLoaisach);
        Button btnadd = view.findViewById(R.id.btnLuusach);
        Button btnhuy = view.findViewById(R.id.btnhuysach);
        AlertDialog alertDialog = builder.create();
        // load ds lên spinner
        SimpleAdapter simpleAdapter = new SimpleAdapter(getContext(),
                getDSLoaiSach(),
                android.R.layout.simple_list_item_1,
                new String[]{"tenLoai"},
                new int[]{android.R.id.text1});
    spnloaisach.setAdapter(simpleAdapter);

    btnadd.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String tensach = edttensach.getText().toString();
            int tien = Integer.parseInt(edttien.getText().toString());
            HashMap<String,Object> hs = (HashMap<String, Object>) spnloaisach.getSelectedItem();
            int maLoai = (int) hs.get("maLoai");

            boolean check = sachDao.themSachMoi(tensach,tien,maLoai);
            if (check){
                Toast.makeText(getContext(), "Thêm sách thành công", Toast.LENGTH_SHORT).show();
                loadData();
            }else {
                Toast.makeText(getContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
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
    public ArrayList<HashMap<String ,Object>> getDSLoaiSach(){
        LoaiSachDao loaiSachDao = new LoaiSachDao(getContext());
        ArrayList<LoaiSach> list1 = loaiSachDao.getLoaiSach();
        ArrayList<HashMap<String,Object>> listH = new ArrayList<>();
        for (LoaiSach loaiSach : list1 ){
            HashMap<String,Object > hs = new HashMap<>();
            hs.put("maLoai",loaiSach.getId());
            hs.put("tenLoai",loaiSach.getTenLoai());
            listH.add(hs);
        }
        return listH;
    }
    public void loadData(){
        list = sachDao.getDSDauSach();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerViewSach.setLayoutManager(layoutManager);
        SachAdapter adapter = new SachAdapter(getContext(),list,getDSLoaiSach(),sachDao);
        recyclerViewSach.setAdapter(adapter);
    }
}
