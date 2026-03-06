package com.example.bai1_startactivityforresult;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * MainActivity - Man hinh chinh sau khi dang nhap thanh cong.
 *
 * CHUC NANG:
 * - Nhan du lieu username tu LoginActivity thong qua getIntent()
 * - Hien thi loi chao nguoi dung
 *
 * GIAI THICH getIntent():
 * - getIntent() tra ve Intent da duoc dung de khoi dong Activity nay
 * - getStringExtra("key") lay gia tri String da duoc putExtra() tu Activity truoc
 */
public class MainActivity extends AppCompatActivity {

    private TextView tvWelcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvWelcome = findViewById(R.id.tvWelcome);

        String username = getIntent().getStringExtra("username");

        if (username != null) {
            tvWelcome.setText("Xin chao, " + username + "!\nDang nhap thanh cong.");
        }
    }
}
