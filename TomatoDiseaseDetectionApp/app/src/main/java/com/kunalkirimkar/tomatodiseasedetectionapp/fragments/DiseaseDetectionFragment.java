package com.kunalkirimkar.tomatodiseasedetectionapp.fragments;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.kunalkirimkar.tomatodiseasedetectionapp.R;
import com.kunalkirimkar.tomatodiseasedetectionapp.ml.TomatoDiseaseDetectionModel;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;


public class DiseaseDetectionFragment extends Fragment {

    TextView output;
    ImageView selectedImage;
    Bitmap image, photo;
    Button cameraBtn, uploadBtn, detectBtn;
    int SELECT_PICTURE = 200;
    int imageSize=224;
    private static final int pic_id = 123;

    public DiseaseDetectionFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_disease_detection, container, false);
        cameraBtn = view.findViewById(R.id.cameraButton);
        selectedImage = view.findViewById(R.id.selectedImage);
        uploadBtn = view.findViewById(R.id.uploadButton);
        detectBtn = view.findViewById(R.id.detectButton);
        output = view.findViewById(R.id.outputTV);

        cameraBtn.setOnClickListener(view1 -> {
            Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(camera_intent, pic_id);
        });
        uploadBtn.setOnClickListener(v -> ImagePicker());
        detectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                DetectDisease(image);
            }
        });
//        detectBtn.setOnClickListener(view1 -> DetectDisease(image));

        return view;
    }

    private void ImagePicker() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
    }

    @SuppressLint("SetTextI18n")
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
       /* if (requestCode == pic_id) {
            image = (Bitmap) data.getExtras().get("data");
            int dimension = Math.min(image.getWidth(), image.getHeight());
            image = ThumbnailUtils.extractThumbnail(image, dimension, dimension);
            selectedImage.setImageBitmap(image);

//            image = Bitmap.createScaledBitmap(image, imageSize, imageSize, false);
//            DetectDisease(image);
        }*/

        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    selectedImage.setImageURI(selectedImageUri);
                }
            }
        }
        if (requestCode == pic_id) {
            photo = (Bitmap) data.getExtras().get("data");
            int dimension = Math.min(photo.getWidth(), photo.getHeight());
            photo = ThumbnailUtils.extractThumbnail(photo, dimension, dimension);
            selectedImage.setImageBitmap(photo);
            photo = Bitmap.createScaledBitmap(photo, imageSize, imageSize, false);
        }
    }

    private void DetectDisease(Bitmap image) {
        try {
            TomatoDiseaseDetectionModel model = TomatoDiseaseDetectionModel.newInstance(requireContext());

            // Creates inputs for reference.
            TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 299, 299, 3}, DataType.FLOAT32);
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4 * imageSize * imageSize * 3);
            byteBuffer.order(ByteOrder.nativeOrder());

            // get 1D array of 224 *224 pixels in image
            int[] value = new int[imageSize * imageSize];
            image.getPixels(value, 0, image.getWidth(), 0,0, image.getWidth(), image.getHeight());

            int pixel=0;
            for (int i=0; i < imageSize; i++) {
                for (int j=0; j < imageSize; j++) {
                    int val = value[pixel++];
                    byteBuffer.putFloat(((val >> 16) & 0xFF) * (1.f / 255.f));
                    byteBuffer.putFloat(((val >> 8) & 0xFF) * (1.f / 255.f));
                    byteBuffer.putFloat(((val & 0xFF) * (1.f / 255.f)));
                }
            }

            inputFeature0.loadBuffer(byteBuffer);

            // Runs model inference and gets result.
            TomatoDiseaseDetectionModel.Outputs outputs = model.process(inputFeature0);
            TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();

            float[] confidence = outputFeature0.getFloatArray();
            int maxPos = 0;
            float maxConfidence = 0;
            for (int i=0; i < confidence.length; i++) {
                if (confidence[i] > maxConfidence) {
                    maxConfidence = confidence[i];
                    maxPos = i;
                }
            }

            String[] classes = {
                "Tomato___Bacterial_spot",
                "Tomato___Early_blight",
                "Tomato___Late_blight",
                "Tomato___Leaf_Mold",
                "Tomato___Septoria_leaf_spot",
                "Tomato___Spider_mites",
                "Tomato___Target_Spot",
                "Tomato___Tomato_Yellow_Leaf_Curl_Virus",
                "Tomato___Tomato_mosaic_virus",
                "Tomato___healthy"
            };
            output.setText(classes[maxPos]);

            // Releases model resources if no longer used.
            model.close();
        }
        catch (IOException e) {
            // TODO Handle the exception
        }
    }

}