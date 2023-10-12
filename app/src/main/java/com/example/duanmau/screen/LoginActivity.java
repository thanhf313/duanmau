package com.example.duanmau.screen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.duanmau.MainActivity;
import com.example.duanmau.R;
import com.example.duanmau.dao.ThuThuDao;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextInputEditText edtName = findViewById(R.id.edtName);
        TextInputEditText edtPass = findViewById(R.id.edtPass);
        Button btnLogin = findViewById(R.id.btnLogin);

        ThuThuDao thuThuDao = new ThuThuDao(this);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edtName.getText().toString();
                String pass = edtPass.getText().toString();
                if (thuThuDao.checkDN(name,pass)){
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                }else {
                    Toast.makeText(LoginActivity.this, "Tên đăng nhập và mật khẩu không đúng.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}