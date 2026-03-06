package com.example.bai7_backgroundservice;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.widget.Toast;

/**
 * MyService - Service chay nen hien thi Toast moi 5 giay.
 *
 * GIAI THICH CHI TIET:
 *
 * 1. extends Service:
 *    - Ke thua tu lop Service cua Android
 *    - Bat buoc phai override onBind() (du khong dung Bound Service)
 *
 * 2. Handler + Runnable:
 *    - Handler: quan ly viec gui va xu ly Message/Runnable tren mot thread
 *    - Handler(Looper.getMainLooper()): gan Handler voi Main Thread
 *      -> Can thiet vi Toast phai chay tren Main Thread (UI Thread)
 *    - postDelayed(runnable, delay): chay runnable sau delay milliseconds
 *    - Trong runnable, goi lai postDelayed() -> tao vong lap lap lai moi 5s
 *
 * 3. Vong doi Service:
 *    - startService() -> onCreate() -> onStartCommand() -> Service chay
 *    - stopService() -> onDestroy() -> Service bi huy
 *    - Neu goi startService() nhieu lan: onCreate() chi goi 1 lan,
 *      nhung onStartCommand() goi moi lan
 *
 * 4. START_STICKY (gia tri tra ve cua onStartCommand):
 *    - START_STICKY: He thong tu khoi dong lai Service neu bi kill
 *    - START_NOT_STICKY: Khong khoi dong lai
 *    - START_REDELIVER_INTENT: Khoi dong lai va gui lai Intent cuoi cung
 */
public class MyService extends Service {

    private Handler handler;
    private Runnable toastRunnable;
    private boolean isRunning = false;

    @Override
    public void onCreate() {
        super.onCreate();

        // Tao Handler gan voi Main Thread (UI Thread)
        handler = new Handler(Looper.getMainLooper());

        // Dinh nghia Runnable - logic se lap lai moi 5 giay
        toastRunnable = new Runnable() {
            @Override
            public void run() {
                if (isRunning) {
                    Toast.makeText(MyService.this,
                            "Service dang chay...",
                            Toast.LENGTH_SHORT).show();
                    // Lap lich chay lai sau 5000ms (5 giay)
                    handler.postDelayed(this, 5000);
                }
            }
        };
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        isRunning = true;
        handler.post(toastRunnable);

        // START_STICKY: neu he thong kill Service, tu khoi dong lai
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isRunning = false;
        handler.removeCallbacks(toastRunnable);
        Toast.makeText(this, "Service da bi huy!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // Tra ve null vi day la Started Service, khong phai Bound Service
        return null;
    }
}
