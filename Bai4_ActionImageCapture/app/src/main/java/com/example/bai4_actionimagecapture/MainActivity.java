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
 * ===================== BAI 4: Intent.ACTION_IMAGE_CAPTURE =====================
 *
 * Ung dung chup anh bang Camera va hien thi anh vua chup.
 *
 * KIEN THUC:
 *
 * 1. Intent.ACTION_IMAGE_CAPTURE (= MediaStore.ACTION_IMAGE_CAPTURE):
 *    - Intent khong tuong minh mo ung dung Camera mac dinh
 *    - Sau khi chup, camera tra ve anh dang thumbnail (Bitmap nho)
 *    - Anh thumbnail nam trong Intent extras voi key "data"
 *
 * 2. Cach lay anh tu ket qua:
 *    - result.getData().getExtras().get("data") -> tra ve Bitmap
 *    - Day la anh thumbnail (khoang 150x150), KHONG phai anh full
 *    - Muon anh full thi phai dung FileProvider + EXTRA_OUTPUT (nang cao)
 *
 * 3. Permission CAMERA:
 *    - Can khai bao trong AndroidManifest.xml
 *
 * 4. Bitmap:
 *    - Doi tuong luu tru anh dang pixel trong bo nho
 *    - setImageBitmap(bitmap) de hien thi tren ImageView
 */
public class MainActivity extends AppCompatActivity {

    private ImageView imgCaptured;
    private Button btnCapture;

    private final ActivityResultLauncher<Intent> cameraLauncher =
            registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        if (result.getResultCode() == Activity.RESULT_OK
                                && result.getData() != null
                                && result.getData().getExtras() != null) {
                            Bitmap thumbnail = (Bitmap) result.getData().getExtras().get("data");
                            if (thumbnail != null) {
                                imgCaptured.setImageBitmap(thumbnail);
                            }
                        } else {
                            Toast.makeText(this, "Khong chup duoc anh!", Toast.LENGTH_SHORT).show();
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
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (cameraIntent.resolveActivity(getPackageManager()) != null) {
                cameraLauncher.launch(cameraIntent);
            } else {
                Toast.makeText(this, "Khong tim thay ung dung Camera!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
