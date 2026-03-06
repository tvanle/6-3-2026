package com.example.bai7_backgroundservice;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.widget.Toast;

/**
 * MyService - Service chạy nền hiển thị Toast mỗi 5 giây.
 *
 * GIẢI THÍCH CHI TIẾT:
 *
 * 1. extends Service:
 *    - Kế thừa từ lớp Service của Android
 *    - Bắt buộc phải override onBind() (dù không dùng Bound Service)
 *
 * 2. Handler + Runnable:
 *    - Handler: quản lý việc gửi và xử lý Message/Runnable trên một thread
 *    - Handler(Looper.getMainLooper()): gắn Handler với Main Thread
 *      → Cần thiết vì Toast phải chạy trên Main Thread (UI Thread)
 *    - postDelayed(runnable, delay): chạy runnable sau delay milliseconds
 *    - Trong runnable, gọi lại postDelayed() → tạo vòng lặp lặp lại mỗi 5s
 *
 * 3. Vòng đời Service:
 *    - startService() → onCreate() → onStartCommand() → Service chạy
 *    - stopService() → onDestroy() → Service bị hủy
 *    - Nếu gọi startService() nhiều lần: onCreate() chỉ gọi 1 lần,
 *      nhưng onStartCommand() gọi mỗi lần
 *
 * 4. START_STICKY (giá trị trả về của onStartCommand):
 *    - START_STICKY: Hệ thống tự khởi động lại Service nếu bị kill (Intent = null)
 *    - START_NOT_STICKY: Không khởi động lại
 *    - START_REDELIVER_INTENT: Khởi động lại và gửi lại Intent cuối cùng
 */
public class MyService extends Service {

    // Handler để lập lịch hiển thị Toast
    private Handler handler;

    // Runnable chứa logic lặp lại
    private Runnable toastRunnable;

    // Cờ kiểm tra Service có đang chạy không
    private boolean isRunning = false;

    @Override
    public void onCreate() {
        super.onCreate();
        // Gọi 1 lần khi Service được tạo lần đầu

        // Tạo Handler gắn với Main Thread (UI Thread)
        // Cần Main Thread vì Toast.makeText() phải chạy trên UI Thread
        handler = new Handler(Looper.getMainLooper());

        // Định nghĩa Runnable - logic sẽ lặp lại mỗi 5 giây
        toastRunnable = new Runnable() {
            @Override
            public void run() {
                if (isRunning) {
                    // Hiển thị Toast thông báo
                    Toast.makeText(MyService.this,
                            "Service đang chạy...",
                            Toast.LENGTH_SHORT).show();

                    // Lập lịch chạy lại sau 5000ms (5 giây)
                    // Đây là cách tạo vòng lặp không chặn Main Thread
                    handler.postDelayed(this, 5000);
                }
            }
        };
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Gọi mỗi lần startService() được gọi từ Activity

        isRunning = true;

        // Bắt đầu vòng lặp Toast - chạy lần đầu ngay lập tức
        handler.post(toastRunnable);

        // START_STICKY: nếu hệ thống kill Service (do thiếu RAM),
        // hệ thống sẽ tự khởi động lại Service khi có đủ tài nguyên
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Gọi khi stopService() hoặc stopSelf()

        isRunning = false;

        // Hủy tất cả Runnable đang chờ → dừng vòng lặp Toast
        handler.removeCallbacks(toastRunnable);

        Toast.makeText(this, "Service đã bị hủy!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // Bắt buộc phải override (abstract method)
        // Trả về null vì đây là Started Service, không phải Bound Service
        // Nếu là Bound Service thì trả về IBinder để Activity giao tiếp với Service
        return null;
    }
}
