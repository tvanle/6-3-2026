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
 * ===================== BAI 1: startActivityForResult =====================
 *
 * LoginActivity - Man hinh dang nhap chinh.
 *
 * LUONG HOAT DONG:
 * 1. Nguoi dung nhap username & password roi nhan "Dang nhap"
 * 2. Neu dung tai khoan -> chuyen sang MainActivity
 * 3. Neu muon dang ky -> nhan "Dang ky" -> chuyen sang RegisterActivity
 * 4. Khi RegisterActivity tra ket qua ve (username & password moi),
 *    tu dong dien vao o nhap lieu
 *
 * KIEN THUC SU DUNG:
 * - ActivityResultLauncher: thay the startActivityForResult() (deprecated)
 * - Intent: truyen du lieu giua cac Activity
 * - setResult() + RESULT_OK: tra ket qua ve Activity goi
 */
public class LoginActivity extends AppCompatActivity {

    private EditText edtUsername, edtPassword;
    private Button btnLogin, btnRegister;
    private TextView tvMessage;

    // Khai bao launcher de nhan ket qua tu RegisterActivity
    // Day la cach moi thay the cho startActivityForResult() da bi deprecated
    private final ActivityResultLauncher<Intent> registerLauncher =
            registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        // Callback duoc goi khi RegisterActivity tra ket qua ve
                        if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                            String newUser = result.getData().getStringExtra("username");
                            String newPass = result.getData().getStringExtra("password");

                            edtUsername.setText(newUser);
                            edtPassword.setText(newPass);

                            tvMessage.setText("Dang ky thanh cong! Hay dang nhap.");
                        }
                    }
            );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        tvMessage = findViewById(R.id.tvMessage);

        // Xu ly su kien nut Dang nhap
        btnLogin.setOnClickListener(v -> {
            String username = edtUsername.getText().toString().trim();
            String password = edtPassword.getText().toString().trim();

            // Kiem tra dang nhap (tai khoan mau: admin/1234)
            if (username.equals("admin") && password.equals("1234")) {
                // Tao Intent tuong minh (Explicit Intent) de mo MainActivity
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);
                finish();
            } else {
                tvMessage.setText("Sai ten dang nhap hoac mat khau!");
            }
        });

        // Xu ly su kien nut Dang ky
        btnRegister.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            registerLauncher.launch(intent);
        });
    }
}
