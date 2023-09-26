package com.example.duanmau.screen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.duanmau.R;

public class ManHinhChaoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_chao);
        ImageView imgLogo = findViewById(R.id.imgLogo);

        // with ở đâu (context), load : hiện thì hình nào, into : hiện thị hình đó ở đâu
        Glide.with(this).load(R.drawable.giphy).into(imgLogo);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(ManHinhChaoActivity.this, LoginActivity.class));
            }
        },5000);
    }
}