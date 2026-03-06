package com.example.bai6_intentfilter;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * ===================== BAI 6: INTENT FILTER =====================
 *
 * Ung dung email VOI Intent Filter - co the nhan du lieu tu ung dung khac.
 *
 * KIEN THUC:
 *
 * 1. INTENT FILTER la gi?
 *    - Bo loc khai bao trong AndroidManifest.xml
 *    - Cho he thong biet Activity nay co the xu ly nhung loai Intent nao
 *    - Khi mot app gui Implicit Intent, Android tim tat ca Activity co
 *      intent-filter phu hop va hien thi cho nguoi dung chon
 *
 * 2. Cau truc Intent Filter:
 *    <intent-filter>
 *        <action>   : hanh dong Activity co the xu ly (VD: ACTION_SEND)
 *        <category> : danh muc (DEFAULT la bat buoc cho implicit intent)
 *        <data>     : loai du lieu co the xu ly (VD: text/plain)
 *    </intent-filter>
 *
 * 3. Cach hoat dong:
 *    - Khi app khac gui ACTION_SEND voi text/plain
 *    - App nay se xuat hien trong danh sach "Chia se bang..."
 *    - Nguoi dung chon app nay -> MainActivity mo voi du lieu tu app khac
 *
 * 4. getIntent() trong truong hop nay:
 *    - Neu app mo binh thuong (tu launcher): Intent khong co EXTRA
 *    - Neu app mo tu chia se: Intent chua EXTRA_TEXT, EXTRA_SUBJECT, EXTRA_EMAIL
 */
public class MainActivity extends AppCompatActivity {

    private EditText edtTo, edtSubject, edtBody;
    private TextView tvStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtTo = findViewById(R.id.edtTo);
        edtSubject = findViewById(R.id.edtSubject);
        edtBody = findViewById(R.id.edtBody);
        tvStatus = findViewById(R.id.tvStatus);

        // Kiem tra Intent da khoi dong Activity
        Intent receivedIntent = getIntent();
        String action = receivedIntent.getAction();
        String type = receivedIntent.getType();

        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(type)) {
                handleReceivedText(receivedIntent);
            }
        } else {
            tvStatus.setText("Mo tu Launcher - Nhap thong tin email de gui");
        }
    }

    /**
     * Xu ly du lieu nhan tu app khac qua Intent
     */
    private void handleReceivedText(Intent intent) {
        String[] emails = intent.getStringArrayExtra(Intent.EXTRA_EMAIL);
        String subject = intent.getStringExtra(Intent.EXTRA_SUBJECT);
        String text = intent.getStringExtra(Intent.EXTRA_TEXT);

        if (emails != null && emails.length > 0) {
            edtTo.setText(emails[0]);
        }
        if (subject != null) {
            edtSubject.setText(subject);
        }
        if (text != null) {
            edtBody.setText(text);
        }

        tvStatus.setText("Nhan du lieu tu ung dung khac qua Intent Filter!");
    }
}
