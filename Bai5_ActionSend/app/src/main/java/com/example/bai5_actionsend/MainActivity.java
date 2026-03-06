package com.example.bai5_actionsend;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

/**
 * ===================== BAI 5: Intent.ACTION_SEND =====================
 *
 * Ung dung gui email voi tinh nang dinh kem file.
 *
 * KIEN THUC:
 *
 * 1. Intent.ACTION_SEND:
 *    - Intent khong tuong minh de chia se/gui du lieu
 *    - He thong hien thi danh sach ung dung co the xu ly
 *    - setType("message/rfc822") de gioi han chi hien ung dung email
 *
 * 2. Cac EXTRA cho email:
 *    - Intent.EXTRA_EMAIL: mang String[] chua dia chi email nguoi nhan
 *    - Intent.EXTRA_SUBJECT: tieu de email
 *    - Intent.EXTRA_TEXT: noi dung email
 *    - Intent.EXTRA_STREAM: URI cua file dinh kem
 *
 * 3. Intent.createChooser():
 *    - Luon hien thi dialog chon ung dung (khong nho lua chon mac dinh)
 *
 * 4. File dinh kem:
 *    - Dung ACTION_PICK de chon file tu thiet bi
 *    - URI cua file duoc dat vao EXTRA_STREAM
 *    - Can addFlags(FLAG_GRANT_READ_URI_PERMISSION) de app email co quyen doc file
 */
public class MainActivity extends AppCompatActivity {

    private EditText edtTo, edtSubject, edtBody;
    private Button btnAttach, btnSend;
    private TextView tvAttachment;
    private Uri attachmentUri = null;

    private final ActivityResultLauncher<Intent> attachLauncher =
            registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                            attachmentUri = result.getData().getData();
                            tvAttachment.setText("File dinh kem: " + attachmentUri.getLastPathSegment());
                        }
                    }
            );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtTo = findViewById(R.id.edtTo);
        edtSubject = findViewById(R.id.edtSubject);
        edtBody = findViewById(R.id.edtBody);
        btnAttach = findViewById(R.id.btnAttach);
        btnSend = findViewById(R.id.btnSend);
        tvAttachment = findViewById(R.id.tvAttachment);

        // Chon file dinh kem
        btnAttach.setOnClickListener(v -> {
            Intent pickIntent = new Intent(Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            pickIntent.setType("*/*");
            attachLauncher.launch(pickIntent);
        });

        // Gui email
        btnSend.setOnClickListener(v -> {
            String to = edtTo.getText().toString().trim();
            String subject = edtSubject.getText().toString().trim();
            String body = edtBody.getText().toString().trim();

            if (to.isEmpty()) {
                Toast.makeText(this, "Vui long nhap email nguoi nhan!", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent emailIntent = new Intent(Intent.ACTION_SEND);
            emailIntent.setType("message/rfc822");
            emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{to});
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
            emailIntent.putExtra(Intent.EXTRA_TEXT, body);

            if (attachmentUri != null) {
                emailIntent.putExtra(Intent.EXTRA_STREAM, attachmentUri);
                emailIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }

            Intent chooser = Intent.createChooser(emailIntent, "Gui email bang...");
            if (chooser.resolveActivity(getPackageManager()) != null) {
                startActivity(chooser);
            } else {
                Toast.makeText(this, "Khong tim thay ung dung email!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
