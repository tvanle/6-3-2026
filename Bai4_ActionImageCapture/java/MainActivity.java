package com.example.bai4_actionimagecapture;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

/**
 * ===================== BÀI 4: Intent.ACTION_IMAGE_CAPTURE =====================
 *
 * Ứng dụng chụp ảnh bằng Camera và hiển thị ảnh vừa chụp.
 *
 * KIẾN THỨC:
 *
 * 1. Intent.ACTION_IMAGE_CAPTURE (= MediaStore.ACTION_IMAGE_CAPTURE):
 *    - Intent không tường minh mở ứng dụng Camera mặc định
 *    - Sau khi chụp, camera trả về ảnh dạng thumbnail (Bitmap nhỏ)
 *    - Ảnh thumbnail nằm trong Intent extras với key "data"
 *
 * 2. Cách lấy ảnh từ kết quả:
 *    - result.getData().getExtras().get("data") → trả về Bitmap
 *    - Đây là ảnh thumbnail (khoảng 150x150), KHÔNG phải ảnh full
 *    - Muốn ảnh full thì phải dùng FileProvider + EXTRA_OUTPUT (nâng cao)
 *
 * 3. Permission CAMERA:
 *    - Cần khai báo trong AndroidManifest.xml
 *    - Android 6.0+ cần xin permission runtime
 *    - Tuy nhiên, với ACTION_IMAGE_CAPTURE, nhiều thiết bị không yêu cầu
 *      permission vì camera app xử lý việc chụp
 *
 * 4. Bitmap:
 *    - Đối tượng lưu trữ ảnh dạng pixel trong bộ nhớ
 *    - setImageBitmap(bitmap) để hiển thị trên ImageView
 */
public class MainActivity extends AppCompatActivity {

    private ImageView imgCaptured;
    private Button btnCapture;

    // Launcher nhận kết quả từ Camera
    private final ActivityResultLauncher<Intent> cameraLauncher =
            registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        if (result.getResultCode() == Activity.RESULT_OK
                                && result.getData() != null
                                && result.getData().getExtras() != null) {

                            // Lấy ảnh thumbnail từ extras
                            // Key "data" chứa Bitmap ảnh thu nhỏ
                            Bitmap thumbnail = (Bitmap) result.getData().getExtras().get("data");

                            if (thumbnail != null) {
                                imgCaptured.setImageBitmap(thumbnail);
                            }
                        } else {
                            Toast.makeText(this, "Không chụp được ảnh!", Toast.LENGTH_SHORT).show();
                        }
                    }
            );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgCaptured = findViewById(R.id.imgCaptured);
        btnCapture = findViewById(R.id.btnCapture);

        btnCapture.setOnClickListener(v -> {
            // Tạo Intent mở camera
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            // Kiểm tra có ứng dụng camera không
            if (cameraIntent.resolveActivity(getPackageManager()) != null) {
                cameraLauncher.launch(cameraIntent);
            } else {
                Toast.makeText(this, "Không tìm thấy ứng dụng Camera!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
