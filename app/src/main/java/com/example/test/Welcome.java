package com.example.test;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;


public class Welcome extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Tìm nút bằng ID và gán trình nghe sự kiện
        findViewById(R.id.buttonBatDau).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển sang layout mới
                Intent intent = new Intent(Welcome.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
