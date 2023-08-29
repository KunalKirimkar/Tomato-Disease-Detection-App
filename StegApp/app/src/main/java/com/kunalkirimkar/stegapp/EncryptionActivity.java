package com.kunalkirimkar.stegapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class EncryptionActivity extends AppCompatActivity {


    TextView statusTV;
    EditText messageText;
    ImageView selectedImage;
    Button selectImageBtn, encryptBtn;
    File coverImageFile;
    int SELECT_PICTURE = 200;
    static final int PICKFILE_RESULT_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encryption);

        selectedImage = findViewById(R.id.selectedImage);
        messageText = findViewById(R.id.inputText);
        statusTV = findViewById(R.id.statusText);
        selectImageBtn = findViewById(R.id.selectImageButton);
        encryptBtn = findViewById(R.id.encryptButton);
        coverImageFile = new File(String.valueOf(selectedImage));
        Bitmap coverBitmap = BitmapFactory.decodeFile(coverImageFile.getPath());
        selectedImage.setImageBitmap(coverBitmap);

        selectImageBtn.setOnClickListener(v -> ImagePicker());

        encryptBtn.setOnClickListener(v -> HideMessage());

    }

    public void HideMessage() {
        String message = messageText.getText().toString();

        if(!message.isEmpty()) {
            try {
                File outputImageFile = new File("/root/sdcard/Pictures/OutputFile.jpg");
                // Jsteg.hide(coverImageFile, outputImageFile, message);
                statusTV.setText("Message encrypted...");
            }
            catch (StringIndexOutOfBoundsException e) {
                e.printStackTrace();
                Toast.makeText(this, "Error hiding message!", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            statusTV.setText("Please enter message!");
        }
    }
    public void ImagePicker() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
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
    }
}