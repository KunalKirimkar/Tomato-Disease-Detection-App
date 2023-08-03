package com.kunalkirimkar.stegapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class DecryptionActivity extends AppCompatActivity {
    TextView statusTV;
    ImageView selectedImage;
    Button selectImageBtn, decryptBtn;
    int SELECT_PICTURE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decryption);

        selectedImage = findViewById(R.id.selectedImage);
        statusTV = findViewById(R.id.statusText);
        selectImageBtn = findViewById(R.id.selectImageButton);
        decryptBtn = findViewById(R.id.decryptButton);

        selectImageBtn.setOnClickListener(v -> ImagePicker());

        decryptBtn.setOnClickListener(v -> DecryptImage());
    }

    private void ImagePicker() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    selectedImage.setImageURI(selectedImageUri);
                }
            }
        }
    }
    private void DecryptImage() {

    }
}