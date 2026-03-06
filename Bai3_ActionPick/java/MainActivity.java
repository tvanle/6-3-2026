package com.example.bai3_actionpick;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

/**
 * ===================== BÀI 3: Intent.ACTION_PICK =====================
 *
 * Ứng dụng chọn hình ảnh từ thư viện và hiển thị.
 *
 * KIẾN THỨC:
 *
 * 1. Intent.ACTION_PICK:
 *    - Intent không tường minh để chọn dữ liệu từ một nguồn
 *    - Kết hợp với MediaStore.Images.Media.EXTERNAL_CONTENT_URI để chọn ảnh
 *    - Trả về URI của ảnh đã chọn
 *
 * 2. MediaStore.Images.Media.EXTERNAL_CONTENT_URI:
 *    - URI trỏ đến bộ sưu tập ảnh trên bộ nhớ ngoài
 *    - Giá trị: content://media/external/images/media
 *
 * 3. ActivityResultLauncher:
 *    - Cách mới để nhận kết quả từ Activity khác
 *    - Thay thế onActivityResult() đã deprecated
 *    - result.getData().getData() trả về URI của ảnh đã chọn
 *
 * 4. ImageView.setImageURI(uri):
 *    - Hiển thị ảnh từ URI trực tiếp trong ImageView
 *    - Phù hợp cho ảnh nhỏ, ảnh lớn nên dùng thư viện như Glide/Picasso
 *
 * 5. Permission READ_MEDIA_IMAGES (Android 13+) / READ_EXTERNAL_STORAGE (cũ):
 *    - Cần để đọc ảnh từ bộ nhớ ngoài
 */
public class MainActivity extends AppCompatActivity {

    private ImageView imgSelected;
    private Button btnPickImage;

    // Launcher nhận kết quả từ thư viện ảnh
    private final ActivityResultLauncher<Intent> pickImageLauncher =
            registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                            // Lấy URI của ảnh đã chọn
                            Uri imageUri = result.getData().getData();

                            if (imageUri != null) {
                                // Hiển thị ảnh trong ImageView
                                imgSelected.setImageURI(imageUri);
                            }
                        } else {
                            Toast.makeText(this, "Không chọn ảnh nào!", Toast.LENGTH_SHORT).show();
                        }
                    }
            );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgSelected = findViewById(R.id.imgSelected);
        btnPickImage = findViewById(R.id.btnPickImage);

        btnPickImage.setOnClickListener(v -> {
            // Tạo Intent chọn ảnh từ thư viện
            Intent pickIntent = new Intent(Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

            // Giới hạn chỉ chọn file ảnh
            pickIntent.setType("image/*");

            pickImageLauncher.launch(pickIntent);
        });
    }
}
