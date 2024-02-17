package com.softeksol.paisalo.jlgsourcing.activities;

import static com.softeksol.paisalo.jlgsourcing.Utilities.CameraUtils.REQUEST_TAKE_PHOTO;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.gson.JsonObject;
import com.softeksol.paisalo.jlgsourcing.R;
import com.softeksol.paisalo.jlgsourcing.Utilities.CameraUtils;
import com.softeksol.paisalo.jlgsourcing.retrofit.ApiClient;
import com.softeksol.paisalo.jlgsourcing.retrofit.ApiInterface;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Upload_Payslip_page extends AppCompatActivity {
    Button btnUploadImage,btnSubmit;
    private ImageView imageViewSelectedImage;
    private Uri selectedImageUri;
    EditText editTextCaseCode;
    File pickedFile;
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private static final int PICK_IMAGE_REQUEST = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_payslip_page);

        btnUploadImage=findViewById(R.id.btnUploadImage);
        editTextCaseCode=findViewById(R.id.editTextCaseCode);
        btnSubmit=findViewById(R.id.btnSubmit);
        imageViewSelectedImage=findViewById(R.id.imageViewSelectedImage);

        btnUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showChoiceDialog();
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             //   if (pickedFile!=null && editTextCaseCode.getText().toString().trim().length()<8)
                saveReciept(pickedFile,editTextCaseCode.getText().toString().trim());
            }
        });
    }

    private void saveReciept(File pickedFile, String smCode) {
        Log.d("TAG", "saveReciept: api hitt");
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.connectTimeout(1, TimeUnit.MINUTES);
        httpClient.readTimeout(1,TimeUnit.MINUTES);
        httpClient.addInterceptor(logging);
        Retrofit retrofit = ApiClient.getClientdynamic("yfMerfC6mRvfr0AOoHmOJ8Et9Q9MPwNEKzFdLsfEs1A=","GRST000223");
        ApiInterface apiInterface=retrofit.create(ApiInterface.class);


            RequestBody Body = RequestBody.create(MediaType.parse("*/*"),pickedFile);
        MultipartBody.Part ImagesParts = MultipartBody.Part.createFormData("FileName",pickedFile.getName(),Body);

        final RequestBody smcode = RequestBody.create(MediaType.parse("text/plain"), smCode);

        Log.d("TAG", "saveReciept: "+pickedFile.getPath());
        Log.d("TAG", "saveReciept: "+smCode);
        Log.d("TAG", "saveReciept: "+smCode);
        Call<JsonObject> call=apiInterface.saveReciptOnpayment(ImagesParts,smcode);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.d("TAG", "onResponse: "+response.body());
                Log.d("TAG", "onResponse: "+response.headers());
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d("TAG", "onFailure: "+t.getMessage());
            }
        });
    }

    private void showChoiceDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose an option")
                .setItems(R.array.choice_array, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // The 'which' argument contains the index position
                        // of the selected item
                        switch (which) {
                            case 0:
                                // Option 1 selected
                                openGallery();
                                break;
                            case 1:
                                // Option 2 selected
                                takePhoto();
                                break;
                            default:
                                break;
                        }
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void takePhoto() {


        ImagePicker.with(this)
                .cameraOnly()
                .start(REQUEST_TAKE_PHOTO);
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);
    }
    private void setImagepath(File file) {

//        File imgFile = new  File("/sdcard/Images/test_image.jpg");

//        customProgress.hideProgress();
        Toast.makeText(this, "Checking File: "+file.getAbsolutePath()+"", Toast.LENGTH_SHORT).show();

        if (file.length() != 0) {

            Bitmap myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());

            //ImageView myImage = (ImageView) findViewById(R.id.imageviewTest);

            if (myBitmap != null) {
                imageViewSelectedImage.setVisibility(View.VISIBLE);
                imageViewSelectedImage.setImageBitmap(myBitmap);
                Log.e("CHeckingmyBitmap22",myBitmap+"");
                pickedFile=file;
            } else {
                Toast.makeText(this, "Bitmap Null", Toast.LENGTH_SHORT).show();
                Log.e("BitmapImage", "Null");
            }
        } else {
            Toast.makeText(this, "Filepath Empty", Toast.LENGTH_SHORT).show();

        }
    }

    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            // Get the selected image URI
            selectedImageUri = data.getData();

            // Update the ImageView with the selected image
            if (imageViewSelectedImage != null) {
                imageViewSelectedImage.setVisibility(View.VISIBLE);
                imageViewSelectedImage.setImageURI(selectedImageUri);

                pickedFile=new File(getRealPathFromURI(selectedImageUri));
            }
        } else if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {

            if (data != null) {
                selectedImageUri = data.getData();
                CropImage.activity(selectedImageUri)
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setMultiTouchEnabled(true)
                        .start(this);
            } else {
                Log.e("ImageData", "Null");
                Toast.makeText(Upload_Payslip_page.this, "Image Data Null", Toast.LENGTH_SHORT).show();
            }

        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            Exception error = null;
            if (data != null) {
                Uri imageUri = CameraUtils.finaliseImageCropUri(resultCode, data, 300, error, false);
                //Toast.makeText(activity, imageUri.toString(), Toast.LENGTH_SHORT).show();
                File tempCroppedImage = new File(imageUri.getPath());
                Log.e("tempCroppedImage", tempCroppedImage.getPath() + "");


                if (error == null) {

                    if (imageUri != null) {

                        if (tempCroppedImage.length() > 100) {
                            (new File(selectedImageUri.getPath())).delete();
                            try {


//                                    if (android.os.Build.VERSION.SDK_INT >= 29) {
//                                        bitmap = ImageDecoder.decodeBitmap(ImageDecoder.createSource(this.getActivity().getContentResolver(), imageUri));
//                                    } else {
//                                        bitmap = MediaStore.Images.Media.getBitmap(this.getActivity().getContentResolver(), imageUri);
//                                    }
//
//
//                                    ImageString = bitmapToBase64(bitmap);

                                File croppedImage = CameraUtils.moveCachedImage2Storage(this, tempCroppedImage, true, 1);
//                                    if (android.os.Build.VERSION.SDK_INT >= 29) {
//                                        bitmap = ImageDecoder.decodeBitmap(ImageDecoder.createSource(this.getActivity().getContentResolver(), imageUri));
//                                    } else {
//                                        bitmap = MediaStore.Images.Media.getBitmap(this.getActivity().getContentResolver(), imageUri);
//                                    }
//                                    //Log.e("CroppedImageMyBitmap", bitmap+ "");
                                Log.e("CroppedImageFile1", croppedImage.getPath() + "");
                                Log.e("CroppedImageFile2", croppedImage.getAbsolutePath() + "");
                                Log.e("CroppedImageFile3", croppedImage.getCanonicalPath() + "");
                                Log.e("CroppedImageFile4", croppedImage.getParent() + "");
                                Log.e("CroppedImageFile5", croppedImage.getParentFile().getCanonicalPath() + "");
                                Log.e("CroppedImageFile6", croppedImage.getParentFile().getName() + "");
//
//                                    ImageString = bitmapToBase64(bitmap);
//                                if (imageViewSelectedImage != null) {
//                                    imageViewSelectedImage.setVisibility(View.VISIBLE);
//                                    imageViewSelectedImage.setImageURI(selectedImageUri);
//                                }
                                setImagepath(croppedImage);
//                                    borrower.setPicture(croppedImage.getPath(),ImageString);


                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        } else {
                            Toast.makeText(this, "CroppedImage FIle Length:" + tempCroppedImage.length() + "", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, imageUri.toString() + "", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, error.toString() + "", Toast.LENGTH_SHORT).show();
                }
            } else {
//                Log.e("Error",data.getData()+"");
                Toast.makeText(this, "CropImage data: NULL", Toast.LENGTH_SHORT).show();
            }

        }
    }
}