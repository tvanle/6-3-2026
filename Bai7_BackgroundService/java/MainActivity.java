package com.example.bai7_backgroundservice;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * ===================== BÀI 7: Background Service =====================
 *
 * Ứng dụng với Service chạy nền hiển thị Toast mỗi 5 giây.
 *
 * KIẾN THỨC:
 *
 * 1. SERVICE là gì?
 *    - Component chạy nền (background), KHÔNG có giao diện
 *    - Dùng để thực hiện tác vụ dài hạn: phát nhạc, tải file, đồng bộ dữ liệu
 *    - Có 3 loại chính:
 *      a) Started Service: chạy vô thời hạn, dừng khi gọi stopSelf() hoặc stopService()
 *      b) Bound Service: gắn với Activity, dừng khi Activity unbind
 *      c) Foreground Service: hiển thị notification, ưu tiên cao hơn
 *
 * 2. CÁC PHƯƠNG THỨC QUAN TRỌNG CỦA SERVICE:
 *    - onCreate(): gọi 1 lần khi Service được tạo
 *    - onStartCommand(): gọi mỗi lần startService() được gọi
 *    - onDestroy(): gọi khi Service bị dừng
 *    - onBind(): cho Bound Service (trả null nếu là Started Service)
 *
 * 3. CÁCH ĐIỀU KHIỂN SERVICE TỪ ACTIVITY:
 *    - startService(intent): khởi động Service
 *    - stopService(intent): dừng Service
 *    - Intent tường minh chỉ định Service class cụ thể
 *
 * 4. LƯU Ý QUAN TRỌNG:
 *    - Service chạy trên MAIN THREAD → không nên làm tác vụ nặng trực tiếp
 *    - Tác vụ nặng cần chạy trong Thread/Handler riêng
 *    - Android 8.0+ hạn chế background service → nên dùng Foreground Service
 *    - Service PHẢI được khai báo trong AndroidManifest.xml
 */
public class MainActivity extends AppCompatActivity {

    private Button btnStart, btnStop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStart = findViewById(R.id.btnStart);
        btnStop = findViewById(R.id.btnStop);

        // Nút Start: khởi động MyService
        btnStart.setOnClickListener(v -> {
            // Tạo Intent tường minh trỏ đến MyService
            Intent serviceIntent = new Intent(MainActivity.this, MyService.class);

            // Khởi động Service
            startService(serviceIntent);

            Toast.makeText(this, "Service đã khởi động!", Toast.LENGTH_SHORT).show();
        });

        // Nút Stop: dừng MyService
        btnStop.setOnClickListener(v -> {
            Intent serviceIntent = new Intent(MainActivity.this, MyService.class);

            // Dừng Service
            stopService(serviceIntent);

            Toast.makeText(this, "Service đã dừng!", Toast.LENGTH_SHORT).show();
        });
    }
}
