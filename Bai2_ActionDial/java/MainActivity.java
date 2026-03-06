package com.example.bai2_actiondial;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * ===================== BÀI 2: Intent.ACTION_DIAL =====================
 *
 * Ứng dụng quay số điện thoại sử dụng Intent không tường minh (Implicit Intent).
 *
 * KIẾN THỨC:
 *
 * 1. IMPLICIT INTENT (Intent không tường minh):
 *    - Không chỉ định Activity cụ thể sẽ xử lý
 *    - Hệ thống Android tự tìm ứng dụng phù hợp dựa trên action + data
 *    - Ví dụ: ACTION_DIAL → mở trình quay số mặc định
 *
 * 2. Intent.ACTION_DIAL:
 *    - Mở trình quay số với số điện thoại đã điền sẵn
 *    - KHÔNG tự động gọi → người dùng phải nhấn nút gọi
 *    - Khác với ACTION_CALL (tự động gọi, cần permission CALL_PHONE)
 *    - ACTION_DIAL không cần permission nào
 *
 * 3. Uri.parse("tel:" + phoneNumber):
 *    - Tạo URI với scheme "tel:" để hệ thống nhận diện đây là số điện thoại
 *    - Ví dụ: Uri.parse("tel:0901234567")
 */
public class MainActivity extends AppCompatActivity {

    private EditText edtPhoneNumber;
    private ImageButton btnDial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtPhoneNumber = findViewById(R.id.edtPhoneNumber);
        btnDial = findViewById(R.id.btnDial);

        btnDial.setOnClickListener(v -> {
            String phoneNumber = edtPhoneNumber.getText().toString().trim();

            if (phoneNumber.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập số điện thoại!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Tạo Implicit Intent với ACTION_DIAL
            // Uri.parse("tel:...") tạo URI dạng tel:0901234567
            Intent dialIntent = new Intent(Intent.ACTION_DIAL);
            dialIntent.setData(Uri.parse("tel:" + phoneNumber));

            // Kiểm tra xem có ứng dụng nào có thể xử lý Intent này không
            // resolveActivity() trả về null nếu không có app nào phù hợp
            if (dialIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(dialIntent);
            } else {
                Toast.makeText(this, "Không tìm thấy ứng dụng quay số!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
