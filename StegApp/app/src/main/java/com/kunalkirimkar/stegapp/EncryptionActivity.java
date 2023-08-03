package com.kunalkirimkar.stegapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class EncryptionActivity extends AppCompatActivity {


    TextView selectedFileTV, statusTV;
    ImageView selectedImage;
    Button selectImageBtn, selectFileBtn, encryptBtn;
    int SELECT_PICTURE = 200;
    static final int PICKFILE_RESULT_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encryption);

        selectedImage = findViewById(R.id.selectedImage);
        selectedFileTV = findViewById(R.id.selectedFileTV);
        statusTV = findViewById(R.id.statusText);
        selectImageBtn = findViewById(R.id.selectImageButton);
        selectFileBtn = findViewById(R.id.selectFileButton);
        encryptBtn = findViewById(R.id.encryptButton);

        selectImageBtn.setOnClickListener(v -> ImagePicker());

        selectFileBtn.setOnClickListener(v -> FilePicker());

        encryptBtn.setOnClickListener(v -> EncryptData());

    }

    private void ImagePicker() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
    }

    private void FilePicker() {
        Intent chooseFile;
        Intent intent;
        chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
        chooseFile.setType("*/*");
        intent = Intent.createChooser(chooseFile, "Choose a file");
        startActivityForResult(intent, PICKFILE_RESULT_CODE);
    }

    @SuppressLint("SetTextI18n")
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
        if (requestCode == PICKFILE_RESULT_CODE) {
            if (resultCode == -1) {
                Uri uri = data.getData();
                String filePath = uri.getPath();
                selectedFileTV.setText("Selected File: " + filePath);
            }
        }
    }

    private void EncryptData() {

    }
}