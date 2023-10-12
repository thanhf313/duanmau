package com.example.duanmau;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duanmau.dao.SachDao;
import com.example.duanmau.dao.ThuThuDao;
import com.example.duanmau.fragment.QLLoaiSachFragment;
import com.example.duanmau.fragment.QLPhieuMuonFragment;
import com.example.duanmau.fragment.QLSachFragment;
import com.example.duanmau.fragment.QLThanhVienFragment;
import com.example.duanmau.fragment.ThongKeDoanhThuFragment;
import com.example.duanmau.fragment.ThongKeTop10Fragment;
import com.example.duanmau.screen.LoginActivity;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        FrameLayout frameLayout = findViewById(R.id.frm);
        NavigationView navigationView = findViewById(R.id.nav);
        drawerLayout = findViewById(R.id.drawerLayout);
        View headLayout = navigationView.getHeaderView(0);
        TextView txtTen = findViewById(R.id.txtGTten);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar(); //chuyển đổi actionBar thành toobar
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.baseline_menu_24);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                if (item.getItemId() == R.id.QLPhieuMuon) {
                    fragment = new QLPhieuMuonFragment();
                } else if (item.getItemId() == R.id.QLLoaiSach) {
                    fragment = new QLLoaiSachFragment();
                } else if (item.getItemId() == R.id.dangXuat) {
                    fragment = new Fragment();
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    Toast.makeText(MainActivity.this, "Thoát thành công", Toast.LENGTH_SHORT).show();
                } else if (item.getItemId() == R.id.doiMatKhau) {
                    fragment = new Fragment();
                    showDialogDoiMatKhau();
                }else if(item.getItemId() == R.id.top10){
                    fragment = new ThongKeTop10Fragment();
                }else  if (item.getItemId() == R.id.doanhThu){
                    fragment = new ThongKeDoanhThuFragment();
                }else  if (item.getItemId() == R.id.QLThanhVien){
                    fragment = new QLThanhVienFragment();
                } else  if (item.getItemId() == R.id.QLSach){
                    fragment = new QLSachFragment();
                }
                else {
                    fragment = new QLPhieuMuonFragment();
                }
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.frm, fragment).commit();
                drawerLayout.closeDrawer(GravityCompat.START);
                toolbar.setTitle(item.getTitle());
                return false;
            }
        });
        // hiện thị 1 so cn cho admin
        SharedPreferences sharedPreferences = getSharedPreferences("THONGTIN",MODE_PRIVATE);
        String loaiTK = sharedPreferences.getString("loaiTK","");
        if (!loaiTK.equals("ADMIN")){
            Menu menu = navigationView.getMenu();
            menu.findItem(R.id.doanhThu).setVisible(false); // kh hiện thị
            menu.findItem(R.id.top10).setVisible(false);
        }
        String hoten = sharedPreferences.getString("hoTen","");
        txtTen.setText("Xin chào " + hoten);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }

    private void showDialogDoiMatKhau() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_doimatkhau, null);

        TextInputEditText edtPassOld = view.findViewById(R.id.edtPassOld);
        TextInputEditText edtNewPass = view.findViewById(R.id.edtNewPass);
        TextInputEditText edtReNewPass = view.findViewById(R.id.edtReNewPass);
        Button btnLuu = view.findViewById(R.id.btnLuu);
        Button btnHuy = view.findViewById(R.id.btnHuy);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldPass = edtPassOld.getText().toString();
                String newPass = edtNewPass.getText().toString();
                String reNewPass = edtReNewPass.getText().toString();
                if (oldPass.equals("") || newPass.equals("") || reNewPass.equals("")) {
                    Toast.makeText(MainActivity.this, "Vui lòng nhập thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    if (newPass.equals(reNewPass)) {
                        // lấy tên đn
                        SharedPreferences sharedPreferences = getSharedPreferences("THONGTIN", MODE_PRIVATE);
                        String matt = sharedPreferences.getString("maTT", "");
                        // cập nhật
                        ThuThuDao thuThuDao = new ThuThuDao(MainActivity.this);
                        int check = thuThuDao.capNhapMK(matt, oldPass, newPass);
                        if (check == 1) {
                            Toast.makeText(MainActivity.this, "Cập nhật mật khẩu thành công", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        } else if (check == 0) {
                            Toast.makeText(MainActivity.this, "Mật khẩu cũ không đúng", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "Cập nhật mật khẩu Thất bại", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "Mật khẩu không giống nhau", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        alertDialog.setCancelable(false);
        alertDialog.show();
    }
}