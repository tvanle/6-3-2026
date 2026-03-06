package com.example.bai1_startactivityforresult;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * RegisterActivity - Màn hình đăng ký tài khoản mới.
 *
 * CHỨC NĂNG:
 * - Nhập username, password, confirm password
 * - Validate dữ liệu nhập
 * - Trả kết quả (username, password) về LoginActivity thông qua setResult()
 *
 * GIẢI THÍCH setResult():
 * - setResult(RESULT_OK, intent): báo cho Activity gọi biết thao tác thành công
 * - setResult(RESULT_CANCELED): báo cho Activity gọi biết thao tác bị hủy
 * - Dữ liệu được đặt trong Intent và gửi về qua ActivityResultLauncher
 */
public class RegisterActivity extends AppCompatActivity {

    private EditText edtNewUsername, edtNewPassword, edtConfirmPassword;
    private Button btnDoRegister, btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Ánh xạ view
        edtNewUsername = findViewById(R.id.edtNewUsername);
        edtNewPassword = findViewById(R.id.edtNewPassword);
        edtConfirmPassword = findViewById(R.id.edtConfirmPassword);
        btnDoRegister = findViewById(R.id.btnDoRegister);
        btnCancel = findViewById(R.id.btnCancel);

        // Xử lý nút Đăng ký
        btnDoRegister.setOnClickListener(v -> {
            String username = edtNewUsername.getText().toString().trim();
            String password = edtNewPassword.getText().toString().trim();
            String confirm = edtConfirmPassword.getText().toString().trim();

            // Validate dữ liệu
            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!password.equals(confirm)) {
                Toast.makeText(this, "Mật khẩu xác nhận không khớp!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Tạo Intent chứa dữ liệu trả về
            Intent resultIntent = new Intent();
            resultIntent.putExtra("username", username);
            resultIntent.putExtra("password", password);

            // Đặt kết quả RESULT_OK và gửi dữ liệu về LoginActivity
            setResult(Activity.RESULT_OK, resultIntent);

            // Đóng RegisterActivity, quay về LoginActivity
            finish();
        });

        // Xử lý nút Hủy
        btnCancel.setOnClickListener(v -> {
            setResult(Activity.RESULT_CANCELED); // Báo hủy thao tác
            finish();
        });
    }
}
