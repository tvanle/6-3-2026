package com.example.bai1_startactivityforresult;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * MainActivity - Màn hình chính sau khi đăng nhập thành công.
 *
 * CHỨC NĂNG:
 * - Nhận dữ liệu username từ LoginActivity thông qua getIntent()
 * - Hiển thị lời chào người dùng
 *
 * GIẢI THÍCH getIntent():
 * - getIntent() trả về Intent đã được dùng để khởi động Activity này
 * - getStringExtra("key") lấy giá trị String đã được putExtra() từ Activity trước
 */
public class MainActivity extends AppCompatActivity {

    private TextView tvWelcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvWelcome = findViewById(R.id.tvWelcome);

        // Nhận dữ liệu từ Intent của LoginActivity
        String username = getIntent().getStringExtra("username");

        if (username != null) {
            tvWelcome.setText("Xin chào, " + username + "!\nĐăng nhập thành công.");
        }
    }
}
