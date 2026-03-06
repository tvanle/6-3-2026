package com.example.bai2_actiondial;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * ===================== BAI 2: Intent.ACTION_DIAL =====================
 *
 * Ung dung quay so dien thoai su dung Intent khong tuong minh (Implicit Intent).
 *
 * KIEN THUC:
 *
 * 1. IMPLICIT INTENT (Intent khong tuong minh):
 *    - Khong chi dinh Activity cu the se xu ly
 *    - He thong Android tu tim ung dung phu hop dua tren action + data
 *    - Vi du: ACTION_DIAL -> mo trinh quay so mac dinh
 *
 * 2. Intent.ACTION_DIAL:
 *    - Mo trinh quay so voi so dien thoai da dien san
 *    - KHONG tu dong goi -> nguoi dung phai nhan nut goi
 *    - Khac voi ACTION_CALL (tu dong goi, can permission CALL_PHONE)
 *    - ACTION_DIAL khong can permission nao
 *
 * 3. Uri.parse("tel:" + phoneNumber):
 *    - Tao URI voi scheme "tel:" de he thong nhan dien day la so dien thoai
 */
public class MainActivity extends AppCompatActivity {

    private EditText edtPhoneNumber;
    private Button btnDial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtPhoneNumber = findViewById(R.id.edtPhoneNumber);
        btnDial = findViewById(R.id.btnDial);

        btnDial.setOnClickListener(v -> {
            String phoneNumber = edtPhoneNumber.getText().toString().trim();

            if (phoneNumber.isEmpty()) {
                Toast.makeText(this, "Vui long nhap so dien thoai!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Tao Implicit Intent voi ACTION_DIAL
            Intent dialIntent = new Intent(Intent.ACTION_DIAL);
            dialIntent.setData(Uri.parse("tel:" + phoneNumber));

            // Kiem tra co ung dung nao xu ly duoc khong
            if (dialIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(dialIntent);
            } else {
                Toast.makeText(this, "Khong tim thay ung dung quay so!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
