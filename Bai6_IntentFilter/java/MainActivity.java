package com.example.bai6_intentfilter;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * ===================== BÀI 6: INTENT FILTER =====================
 *
 * Ứng dụng email VỚI Intent Filter - có thể nhận dữ liệu từ ứng dụng khác.
 *
 * KIẾN THỨC:
 *
 * 1. INTENT FILTER là gì?
 *    - Bộ lọc khai báo trong AndroidManifest.xml
 *    - Cho hệ thống biết Activity này có thể xử lý những loại Intent nào
 *    - Khi một app gửi Implicit Intent, Android tìm tất cả Activity có
 *      intent-filter phù hợp và hiển thị cho người dùng chọn
 *
 * 2. Cấu trúc Intent Filter:
 *    <intent-filter>
 *        <action>   : hành động Activity có thể xử lý (VD: ACTION_SEND)
 *        <category> : danh mục (DEFAULT là bắt buộc cho implicit intent)
 *        <data>     : loại dữ liệu có thể xử lý (VD: text/plain)
 *    </intent-filter>
 *
 * 3. Trong bài này, intent-filter khai báo:
 *    - action: android.intent.action.SEND → xử lý gửi dữ liệu
 *    - category: DEFAULT → bắt buộc cho implicit intent
 *    - data mimeType: text/plain → chỉ nhận dữ liệu dạng text
 *
 * 4. Cách hoạt động:
 *    - Khi app khác gửi ACTION_SEND với text/plain
 *    - App này sẽ xuất hiện trong danh sách "Chia sẻ bằng..."
 *    - Người dùng chọn app này → MainActivity mở với dữ liệu từ app khác
 *
 * 5. getIntent() trong trường hợp này:
 *    - Nếu app mở bình thường (từ launcher): Intent không có EXTRA
 *    - Nếu app mở từ chia sẻ: Intent chứa EXTRA_TEXT, EXTRA_SUBJECT, EXTRA_EMAIL
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

        // Kiểm tra Intent đã khởi động Activity
        Intent receivedIntent = getIntent();
        String action = receivedIntent.getAction();
        String type = receivedIntent.getType();

        if (Intent.ACTION_SEND.equals(action) && type != null) {
            // App được mở từ chia sẻ của app khác
            if ("text/plain".equals(type)) {
                handleReceivedText(receivedIntent);
            }
        } else {
            // App được mở bình thường từ launcher
            tvStatus.setText("Mở từ Launcher - Nhập thông tin email để gửi");
        }
    }

    /**
     * Xử lý dữ liệu nhận từ app khác qua Intent
     * Các EXTRA có thể nhận:
     * - EXTRA_EMAIL: địa chỉ email (nếu có)
     * - EXTRA_SUBJECT: tiêu đề
     * - EXTRA_TEXT: nội dung
     */
    private void handleReceivedText(Intent intent) {
        // Lấy dữ liệu từ Intent
        String[] emails = intent.getStringArrayExtra(Intent.EXTRA_EMAIL);
        String subject = intent.getStringExtra(Intent.EXTRA_SUBJECT);
        String text = intent.getStringExtra(Intent.EXTRA_TEXT);

        // Điền vào các ô nhập liệu
        if (emails != null && emails.length > 0) {
            edtTo.setText(emails[0]);
        }
        if (subject != null) {
            edtSubject.setText(subject);
        }
        if (text != null) {
            edtBody.setText(text);
        }

        tvStatus.setText("Nhận dữ liệu từ ứng dụng khác qua Intent Filter!");
    }
}
