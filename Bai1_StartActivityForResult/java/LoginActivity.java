package com.example.bai1_startactivityforresult;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

/**
 * ===================== BÀI 1: startActivityForResult =====================
 *
 * LoginActivity - Màn hình đăng nhập chính.
 *
 * LUỒNG HOẠT ĐỘNG:
 * 1. Người dùng nhập username & password rồi nhấn "Đăng nhập"
 * 2. Nếu đúng tài khoản → chuyển sang MainActivity
 * 3. Nếu muốn đăng ký → nhấn "Đăng ký" → chuyển sang RegisterActivity
 * 4. Khi RegisterActivity trả kết quả về (username & password mới),
 *    tự động điền vào ô nhập liệu
 *
 * KIẾN THỨC SỬ DỤNG:
 * - ActivityResultLauncher: thay thế startActivityForResult() (deprecated)
 * - Intent: truyền dữ liệu giữa các Activity
 * - setResult() + RESULT_OK: trả kết quả về Activity gọi
 */
public class LoginActivity extends AppCompatActivity {

    private EditText edtUsername, edtPassword;
    private Button btnLogin, btnRegister;
    private TextView tvMessage;

    // Khai báo launcher để nhận kết quả từ RegisterActivity
    // Đây là cách mới thay thế cho startActivityForResult() đã bị deprecated
    private final ActivityResultLauncher<Intent> registerLauncher =
            registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        // Callback được gọi khi RegisterActivity trả kết quả về
                        if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                            // Lấy dữ liệu từ Intent trả về
                            String newUser = result.getData().getStringExtra("username");
                            String newPass = result.getData().getStringExtra("password");

                            // Tự động điền vào ô nhập liệu
                            edtUsername.setText(newUser);
                            edtPassword.setText(newPass);

                            tvMessage.setText("Đăng ký thành công! Hãy đăng nhập.");
                        }
                    }
            );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Ánh xạ view từ layout XML
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        tvMessage = findViewById(R.id.tvMessage);

        // Xử lý sự kiện nút Đăng nhập
        btnLogin.setOnClickListener(v -> {
            String username = edtUsername.getText().toString().trim();
            String password = edtPassword.getText().toString().trim();

            // Kiểm tra đăng nhập (tài khoản mẫu: admin/1234)
            if (username.equals("admin") && password.equals("1234")) {
                // Tạo Intent tường minh (Explicit Intent) để mở MainActivity
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("username", username); // Truyền dữ liệu sang MainActivity
                startActivity(intent);
                finish(); // Đóng LoginActivity
            } else {
                tvMessage.setText("Sai tên đăng nhập hoặc mật khẩu!");
            }
        });

        // Xử lý sự kiện nút Đăng ký
        btnRegister.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            // Sử dụng launcher thay vì startActivityForResult()
            registerLauncher.launch(intent);
        });
    }
}
