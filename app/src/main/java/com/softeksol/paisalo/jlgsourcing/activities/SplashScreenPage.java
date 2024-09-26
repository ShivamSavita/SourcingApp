package com.softeksol.paisalo.jlgsourcing.activities;

import static android.os.storage.StorageManager.ACTION_CLEAR_APP_CACHE;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.gson.JsonObject;
import com.softeksol.paisalo.jlgsourcing.BuildConfig;
import com.softeksol.paisalo.jlgsourcing.R;
import com.softeksol.paisalo.jlgsourcing.SEILIGL;
import com.softeksol.paisalo.jlgsourcing.retrofit.ApiClient;
import com.softeksol.paisalo.jlgsourcing.retrofit.ApiInterface;
import com.softeksol.paisalo.jlgsourcing.retrofit.AppUpdateResponse;

import java.io.File;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SplashScreenPage extends AppCompatActivity {
    Button buttonGettignStarted;
    boolean h;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen_page);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        getAppUpdate();

      /*new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreenPage.this, ActivityLogin.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                finish();
            }
        }, 1700);*/

    }
    private void getAppUpdate(){
        ApiInterface apiInterface= ApiClient.getClient(SEILIGL.NEW_SERVERAPI).create(ApiInterface.class);
        Call<AppUpdateResponse> call=apiInterface.getAppLinkStatus(BuildConfig.VERSION_NAME,"S",1);
        call.enqueue(new Callback<AppUpdateResponse>() {
            @Override
            public void onResponse(Call<AppUpdateResponse> call, Response<AppUpdateResponse> response) {
                Log.d("TAG", "onResponse: "+response.body());
                AppUpdateResponse brandResponse=response.body();
                if (brandResponse.getData().length()<5){
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            /*Context context = getApplicationContext();
                            // context.getCacheDir().delete();
                            File cacheDir = context.getCacheDir();
                            if (cacheDir != null && cacheDir.isDirectory()) {
                                deleteFilesInDir(cacheDir);
                            }*/
                            Intent intent = new Intent(SplashScreenPage.this, ActivityLogin.class);
                            startActivity(intent);
                            overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                            finish();
                        }
                    }, 1200);
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(SplashScreenPage.this);
                    builder.setTitle("Need Update");
                    builder.setCancelable(false);
                    builder.setMessage("You are using older version of this app kindly update this app");
                    builder.setPositiveButton("Update Now", (dialog, which) -> {
                        dialog.cancel();
                        String url = brandResponse.getData();
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(url));
                        startActivity(i);
                        finish();
                    });
                    builder.setNegativeButton("Cancel", (dialog, which) -> {
                        // this method is called when user click on negative button.
                        dialog.cancel();
                        finish();
                    });
                    // below line is used to display our dialog
                    builder.show();
                }
            }
            @Override
            public void onFailure(Call<AppUpdateResponse> call, Throwable t) {
                Log.d("TAG", "onFailure: "+t.getMessage());
                AlertDialog.Builder builder = new AlertDialog.Builder(SplashScreenPage.this);
                builder.setTitle("No Internet Connection");
                builder.setCancelable(false);
                builder.setMessage("No Internet Connection, Try again");
                builder.setPositiveButton("Try again", (dialog, which) -> {
                    dialog.cancel();
                    finish();
                    Intent i = new Intent(getApplicationContext(),SplashScreenPage.class);
                    startActivity(i);

                });
                builder.setNegativeButton("Cancel", (dialog, which) -> {
                    // this method is called when user click on negative button.
                    dialog.cancel();
                    finish();


                });
                // below line is used to display our dialog
                builder.show();
            }
        });
    }


    private static void deleteFilesInDir(File dir) {
        if (dir != null) {
            File[] files = dir.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        deleteFilesInDir(file);
                    } else {
                        file.delete();
                    }
                }
            }
        }
    }
}