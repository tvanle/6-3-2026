package com.example.bai1_startactivityforresult;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * RegisterActivity - Man hinh dang ky tai khoan moi.
 *
 * CHUC NANG:
 * - Nhap username, password, confirm password
 * - Validate du lieu nhap
 * - Tra ket qua (username, password) ve LoginActivity thong qua setResult()
 *
 * GIAI THICH setResult():
 * - setResult(RESULT_OK, intent): bao cho Activity goi biet thao tac thanh cong
 * - setResult(RESULT_CANCELED): bao cho Activity goi biet thao tac bi huy
 * - Du lieu duoc dat trong Intent va gui ve qua ActivityResultLauncher
 */
public class RegisterActivity extends AppCompatActivity {

    private EditText edtNewUsername, edtNewPassword, edtConfirmPassword;
    private Button btnDoRegister, btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edtNewUsername = findViewById(R.id.edtNewUsername);
        edtNewPassword = findViewById(R.id.edtNewPassword);
        edtConfirmPassword = findViewById(R.id.edtConfirmPassword);
        btnDoRegister = findViewById(R.id.btnDoRegister);
        btnCancel = findViewById(R.id.btnCancel);

        btnDoRegister.setOnClickListener(v -> {
            String username = edtNewUsername.getText().toString().trim();
            String password = edtNewPassword.getText().toString().trim();
            String confirm = edtConfirmPassword.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Vui long nhap day du thong tin!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!password.equals(confirm)) {
                Toast.makeText(this, "Mat khau xac nhan khong khop!", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent resultIntent = new Intent();
            resultIntent.putExtra("username", username);
            resultIntent.putExtra("password", password);

            setResult(Activity.RESULT_OK, resultIntent);
            finish();
        });

        btnCancel.setOnClickListener(v -> {
            setResult(Activity.RESULT_CANCELED);
            finish();
        });
    }
}
