package com.kunalkirimkar.tomatodiseasedetectionapp.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.kunalkirimkar.tomatodiseasedetectionapp.R;
import com.kunalkirimkar.tomatodiseasedetectionapp.ml.TomatoDiseaseDetectionModel;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.image.ImageProcessor;
import org.tensorflow.lite.support.image.TensorImage;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;


public class DiseaseDetectionFragment extends Fragment {

    TextView output;
    ImageView selectedImage;
    Bitmap image, photo, bitmap;
    Button cameraBtn, uploadBtn, detectBtn;
    int SELECT_PICTURE = 200;
    int imageSize=299;
    private static final int pic_id = 12;
    private static final int IMAGE_MEAN = 128;
    private static final float IMAGE_STD = 128.0f;
    private final int IMG_SIZE_X = 299;
    private final int IMG_SIZE_Y = 299;
    private int PIXEL_SIZE = 3;
    private int[] intValues;
    private ByteBuffer imageData = null;

    public DiseaseDetectionFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_disease_detection, container, false);

        getCameraPermission();

        imageData = ByteBuffer.allocateDirect(4 * IMG_SIZE_X * IMG_SIZE_Y * PIXEL_SIZE);
        imageData.order(ByteOrder.nativeOrder());
        intValues = new int[IMG_SIZE_X * IMG_SIZE_Y];

        int count=0;
        String[] classes = new String[10];
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(getResources().getAssets().open("classes.txt")));
            String line = bufferedReader.readLine();
            while (line!=null) {
                classes[count] =line;
                count++;
                line = bufferedReader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        cameraBtn = view.findViewById(R.id.cameraButton);
        selectedImage = view.findViewById(R.id.selectedImage);
        uploadBtn = view.findViewById(R.id.uploadButton);
        detectBtn = view.findViewById(R.id.detectButton);
        output = view.findViewById(R.id.outputTV);

        uploadBtn.setOnClickListener(v -> ImagePicker());

        cameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(camera_intent, 12);
            }
        });

        detectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    TomatoDiseaseDetectionModel model = TomatoDiseaseDetectionModel.newInstance(requireContext());
                    TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 299, 299, 3}, DataType.FLOAT32);
                    Bitmap bitmap2 = getResizedBitmap(bitmap, IMG_SIZE_X, IMG_SIZE_Y);
                    convertBitmapToByteBuffer(bitmap2);
                    TensorImage image=new TensorImage(DataType.FLOAT32);
                    image.load(bitmap2);
                    ByteBuffer byteBuffer=image.getBuffer();
                    inputFeature0.loadBuffer(byteBuffer);

                    // Runs model inference and gets result.
                    TomatoDiseaseDetectionModel.Outputs outputs = model.process(inputFeature0);
                    TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();

                    output.setText(classes[getMax(outputFeature0.getFloatArray())] + "");

                    // Releases model resources if no longer used.
                    model.close();
                }
                catch (IOException e) {
                    // TODO Handle the exception
                }
            }
        });
//        detectBtn.setOnClickListener(view1 -> DetectDisease(image));

        return view;
    }

    void getCameraPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (getContext().checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.CAMERA}, 11);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode==11) {
            if (grantResults.length>0) {
                if (grantResults[0]!=PackageManager.PERMISSION_GRANTED) {
                    this.getCameraPermission();
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void ImagePicker() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 10);
//        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
    }

    @SuppressLint("SetTextI18n")
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==10) {
            if (data!=null) {
                Uri selectedImageUri = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.requireContext().getContentResolver(), selectedImageUri);
                    selectedImage.setImageBitmap(bitmap);
                }
                catch (IOException e) {

                }
            }
            else if (requestCode == 12) {
                bitmap = (Bitmap) data.getExtras().get("data");
                selectedImage.setImageBitmap(bitmap);
            }
        }
       /* if (requestCode == pic_id) {
            image = (Bitmap) data.getExtras().get("data");
            int dimension = Math.min(image.getWidth(), image.getHeight());
            image = ThumbnailUtils.extractThumbnail(image, dimension, dimension);
            selectedImage.setImageBitmap(image);

//            image = Bitmap.createScaledBitmap(image, imageSize, imageSize, false);
//            DetectDisease(image);
        }*/

        /*if (resultCode == RESULT_OK) {
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
            photo = Bitmap.createBitmap(photo.getWidth(), photo.getHeight(),  Bitmap.Config.ARGB_8888);
//            photo = Bitmap.createScaledBitmap(photo, imageSize, imageSize, false);
        }*/
        super.onActivityResult(requestCode, resultCode, data);
    }

    /*private void DetectDisease(Bitmap image) {
        try {
            TomatoDiseaseDetectionModel model = TomatoDiseaseDetectionModel.newInstance(requireContext());

            // Creates inputs for reference.
            *//*TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 229, 229, 3}, DataType.FLOAT32);
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
            }*//*

            TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 224, 224, 3}, DataType.FLOAT32);
            bitmap = Bitmap.createScaledBitmap(bitmap, 224, 224, true);
            inputFeature0.loadBuffer(TensorImage.fromBitmap(bitmap).getBuffer());

            // Runs model inference and gets result.
            TomatoDiseaseDetectionModel.Outputs outputs = model.process(inputFeature0);
            TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();

            output.setText(getMax(outputFeature0.getFloatArray())+"");

            *//*float[] confidence = outputFeature0.getFloatArray();
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
            output.setText(classes[maxPos]);*//*

            // Releases model resources if no longer used.
            model.close();
        }
        catch (IOException e) {
            // TODO Handle the exception
        }
    }*/
    int getMax(float[] confidence) {
        int max = 0;
        for (int i=0; i < confidence.length; i++) {
            if (confidence[i] > confidence[max]) max=i;
        }
        return max;
    }

    private void convertBitmapToByteBuffer(Bitmap bitmap)
    {
        if (imageData == null)
        {
            return;
        }
        imageData.rewind();
        bitmap.getPixels(intValues, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
        int pixel = 0;
        for (int i = 0; i < IMG_SIZE_X; ++i)
        {
            for (int j = 0; j < IMG_SIZE_Y; ++j)
            {
                final int val = intValues[pixel++];
                imageData.putFloat((((val >> 16) & 0xFF)-IMAGE_MEAN)/IMAGE_STD);
                imageData.putFloat((((val >> 8) & 0xFF)-IMAGE_MEAN)/IMAGE_STD);
                imageData.putFloat((((val) & 0xFF)-IMAGE_MEAN)/IMAGE_STD);
            }
        }
    }

    private Bitmap getResizedBitmap(Bitmap bitmapOriginal, int img_size_x, int img_size_y)
    {
        int width = bitmapOriginal.getWidth();
        int height = bitmapOriginal.getHeight();
        float scaleWidth = ((float) img_size_x)/width;
        float scaleHeight = ((float) img_size_y)/height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmapOriginal, 0, 0, width, height, matrix, false);
        return resizedBitmap;
    }

}