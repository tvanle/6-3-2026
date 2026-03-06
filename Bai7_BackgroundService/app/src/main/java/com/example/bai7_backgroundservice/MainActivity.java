package com.example.bai7_backgroundservice;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * ===================== BAI 7: Background Service =====================
 *
 * Ung dung voi Service chay nen hien thi Toast moi 5 giay.
 *
 * KIEN THUC:
 *
 * 1. SERVICE la gi?
 *    - Component chay nen (background), KHONG co giao dien
 *    - Dung de thuc hien tac vu dai han: phat nhac, tai file, dong bo du lieu
 *    - Co 3 loai chinh:
 *      a) Started Service: chay vo thoi han, dung khi goi stopSelf() hoac stopService()
 *      b) Bound Service: gan voi Activity, dung khi Activity unbind
 *      c) Foreground Service: hien thi notification, uu tien cao hon
 *
 * 2. CACH DIEU KHIEN SERVICE TU ACTIVITY:
 *    - startService(intent): khoi dong Service
 *    - stopService(intent): dung Service
 *    - Intent tuong minh chi dinh Service class cu the
 *
 * 3. LUU Y:
 *    - Service chay tren MAIN THREAD -> khong nen lam tac vu nang truc tiep
 *    - Service PHAI duoc khai bao trong AndroidManifest.xml
 */
public class MainActivity extends AppCompatActivity {

    private Button btnStart, btnStop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStart = findViewById(R.id.btnStart);
        btnStop = findViewById(R.id.btnStop);

        // Nut Start: khoi dong MyService
        btnStart.setOnClickListener(v -> {
            Intent serviceIntent = new Intent(MainActivity.this, MyService.class);
            startService(serviceIntent);
            Toast.makeText(this, "Service da khoi dong!", Toast.LENGTH_SHORT).show();
        });

        // Nut Stop: dung MyService
        btnStop.setOnClickListener(v -> {
            Intent serviceIntent = new Intent(MainActivity.this, MyService.class);
            stopService(serviceIntent);
            Toast.makeText(this, "Service da dung!", Toast.LENGTH_SHORT).show();
        });
    }
}
