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
 * ===================== BAI 3: Intent.ACTION_PICK =====================
 *
 * Ung dung chon hinh anh tu thu vien va hien thi.
 *
 * KIEN THUC:
 *
 * 1. Intent.ACTION_PICK:
 *    - Intent khong tuong minh de chon du lieu tu mot nguon
 *    - Ket hop voi MediaStore.Images.Media.EXTERNAL_CONTENT_URI de chon anh
 *    - Tra ve URI cua anh da chon
 *
 * 2. MediaStore.Images.Media.EXTERNAL_CONTENT_URI:
 *    - URI tro den bo suu tap anh tren bo nho ngoai
 *    - Gia tri: content://media/external/images/media
 *
 * 3. ActivityResultLauncher:
 *    - Cach moi de nhan ket qua tu Activity khac
 *    - Thay the onActivityResult() da deprecated
 *
 * 4. ImageView.setImageURI(uri):
 *    - Hien thi anh tu URI truc tiep trong ImageView
 */
public class MainActivity extends AppCompatActivity {

    private ImageView imgSelected;
    private Button btnPickImage;

    // Launcher nhan ket qua tu thu vien anh
    private final ActivityResultLauncher<Intent> pickImageLauncher =
            registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                            Uri imageUri = result.getData().getData();
                            if (imageUri != null) {
                                imgSelected.setImageURI(imageUri);
                            }
                        } else {
                            Toast.makeText(this, "Khong chon anh nao!", Toast.LENGTH_SHORT).show();
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
            Intent pickIntent = new Intent(Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            pickIntent.setType("image/*");
            pickImageLauncher.launch(pickIntent);
        });
    }
}
