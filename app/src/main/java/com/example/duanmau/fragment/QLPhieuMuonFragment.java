package com.example.duanmau.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanmau.R;
import com.example.duanmau.adapter.PhieuMuonAdapter;
import com.example.duanmau.dao.PhieuMuonDao;
import com.example.duanmau.dao.SachDao;
import com.example.duanmau.dao.ThanhvienDao;
import com.example.duanmau.model.PhieuMuon;
import com.example.duanmau.model.Sach;
import com.example.duanmau.model.ThanhVien;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class QLPhieuMuonFragment extends Fragment {
    PhieuMuonDao phieuMuonDao;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_qlphieumuon,container,false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerwiewQLPhieumuon);
        FloatingActionButton floatingActionButton = view.findViewById(R.id.floatAdd);

        // giao diện

        // data
         phieuMuonDao = new PhieuMuonDao(getContext());
        ArrayList<PhieuMuon> list = phieuMuonDao.getDSPhieuMuon();

        // adapter
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        PhieuMuonAdapter adapter = new PhieuMuonAdapter(list,getContext());
        recyclerView.setAdapter(adapter);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }


        });
        return view;
    }
    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_them_phieumuon,null);
        Spinner spnTV = view.findViewById(R.id.spnTV);
        Spinner spnSach = view.findViewById(R.id.spnSach);
        EditText edtTien = view.findViewById(R.id.edtTien);
        getDataThanhVien(spnTV);
        getDataSach(spnSach);

        Button btnAdd = view.findViewById(R.id.btnAdd);
        Button btnCancel = view.findViewById(R.id.btnCancel);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // lấy mã thành viên
                // gọi từu hàm hashMap ra
                HashMap<String, Object>  hsTV = (HashMap<String, Object>) spnTV.getSelectedItem();
                //mình gọi thg spin -> lấy đc gtri ng dung đang chọn -> ép gtri qua kiểu hashmap -> lấy về maTv
                int maTV = (int) hsTV.get("maTV");

                //lấy mã sách
                HashMap<String, Object> hsSc = (HashMap<String, Object>) spnSach.getSelectedItem();
                int maSach = (int) hsSc.get("maSach");
                int tien = Integer.parseInt(edtTien.getText().toString());
                themPhieuMuon(maTV, maSach, tien);
            }


        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        alertDialog.show();
    }
    private  void  getDataThanhVien(Spinner spinnerTv){
        ThanhvienDao thanhvienDao = new ThanhvienDao(getContext());
        ArrayList<ThanhVien> list = thanhvienDao.getDSThanhVien();

        ArrayList<HashMap<String,Object>> listHasmap = new ArrayList<>();
        // để hashMap nhận hết kiểu dl nên để nó thành ojb
        for (ThanhVien tv : list){
            HashMap<String, Object> hs = new HashMap<>();
            hs.put("maTV", tv.getMatv());
            hs.put("hoTen", tv.getHotentv());
            listHasmap.add(hs);
            // duyệt qua toàn bộ những thông tin của list
        }
        // gọi simAdapter để chuyển vào
       SimpleAdapter simpleAdapter = new SimpleAdapter(
               getContext(), listHasmap, android.R.layout.simple_list_item_1, new String[]{"hoTen"},new int[]{android.R.id.text1}

       );
        spinnerTv.setAdapter(simpleAdapter);
    }
    private  void  getDataSach(Spinner spinnerSc){
        SachDao sachDao = new SachDao(getContext());
        ArrayList<Sach> list = sachDao.getDSDauSach();

        ArrayList<HashMap<String,Object>> listSc = new ArrayList<>();
        // để hashMap nhận hết kiểu dl nên để nó thành ojb
        for (Sach Sc : list){
            HashMap<String, Object> sc = new HashMap<>();
            sc.put("maSach", Sc.getMasach());
            sc.put("tenSach", Sc.getTensach());
            listSc.add(sc);
            // duyệt qua toàn bộ những thông tin của list
        }
        // gọi simAdapter để chuyển vào
        SimpleAdapter simpleAdapter = new SimpleAdapter(
                getContext(), listSc, android.R.layout.simple_list_item_1, new String[]{"tenSach"},new int[]{android.R.id.text1}

        );
        spinnerSc.setAdapter(simpleAdapter);
    }

    // hàm cn
    private void themPhieuMuon(int maTV, int maSach, int tien) {
        // lấy mã thuthu, gọi sharePre...
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("THONGTIN", Context.MODE_PRIVATE);
        String maTT = sharedPreferences.getString("maTT","");

        // LẤY NGÀY HIỆN TẠI
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String ngay = simpleDateFormat.format(date);

        PhieuMuon phieuMuon = new PhieuMuon(maTV, maTT, maSach, ngay, 0, tien);
    }
}

