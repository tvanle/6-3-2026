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
 * ===================== BÀI 5: Intent.ACTION_SEND =====================
 *
 * Ứng dụng gửi email với tính năng đính kèm file.
 *
 * KIẾN THỨC:
 *
 * 1. Intent.ACTION_SEND:
 *    - Intent không tường minh để chia sẻ/gửi dữ liệu
 *    - Hệ thống hiển thị danh sách ứng dụng có thể xử lý (email, tin nhắn, ...)
 *    - setType("message/rfc822") để giới hạn chỉ hiện ứng dụng email
 *
 * 2. Các EXTRA cho email:
 *    - Intent.EXTRA_EMAIL: mảng String[] chứa địa chỉ email người nhận
 *    - Intent.EXTRA_SUBJECT: tiêu đề email
 *    - Intent.EXTRA_TEXT: nội dung email
 *    - Intent.EXTRA_STREAM: URI của file đính kèm
 *
 * 3. Intent.createChooser():
 *    - Luôn hiển thị dialog chọn ứng dụng (không nhớ lựa chọn mặc định)
 *    - Tham số thứ 2 là tiêu đề của dialog
 *    - Best practice: luôn dùng createChooser() khi gửi dữ liệu
 *
 * 4. File đính kèm:
 *    - Dùng ACTION_PICK để chọn file từ thiết bị
 *    - URI của file được đặt vào EXTRA_STREAM
 *    - Cần addFlags(FLAG_GRANT_READ_URI_PERMISSION) để app email có quyền đọc file
 */
public class MainActivity extends AppCompatActivity {

    private EditText edtTo, edtSubject, edtBody;
    private Button btnAttach, btnSend;
    private TextView tvAttachment;

    // Lưu URI của file đính kèm (null nếu không đính kèm)
    private Uri attachmentUri = null;

    // Launcher để chọn file đính kèm
    private final ActivityResultLauncher<Intent> attachLauncher =
            registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                            attachmentUri = result.getData().getData();
                            tvAttachment.setText("File đính kèm: " + attachmentUri.getLastPathSegment());
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

        // Chọn file đính kèm
        btnAttach.setOnClickListener(v -> {
            // Dùng ACTION_PICK để chọn file bất kỳ
            Intent pickIntent = new Intent(Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            pickIntent.setType("*/*"); // Cho phép chọn mọi loại file
            attachLauncher.launch(pickIntent);
        });

        // Gửi email
        btnSend.setOnClickListener(v -> {
            String to = edtTo.getText().toString().trim();
            String subject = edtSubject.getText().toString().trim();
            String body = edtBody.getText().toString().trim();

            if (to.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập email người nhận!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Tạo Intent gửi email
            Intent emailIntent = new Intent(Intent.ACTION_SEND);

            // MIME type cho email
            emailIntent.setType("message/rfc822");

            // Đặt các thông tin email
            emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{to}); // Mảng người nhận
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);        // Tiêu đề
            emailIntent.putExtra(Intent.EXTRA_TEXT, body);              // Nội dung

            // Nếu có file đính kèm
            if (attachmentUri != null) {
                emailIntent.putExtra(Intent.EXTRA_STREAM, attachmentUri);
                // Cấp quyền đọc file cho ứng dụng email nhận
                emailIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }

            // Hiển thị dialog chọn ứng dụng email
            Intent chooser = Intent.createChooser(emailIntent, "Gửi email bằng...");

            if (chooser.resolveActivity(getPackageManager()) != null) {
                startActivity(chooser);
            } else {
                Toast.makeText(this, "Không tìm thấy ứng dụng email!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
