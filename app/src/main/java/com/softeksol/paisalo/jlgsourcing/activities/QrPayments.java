package com.softeksol.paisalo.jlgsourcing.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.softeksol.paisalo.jlgsourcing.R;
import com.softeksol.paisalo.jlgsourcing.SEILIGL;
import com.softeksol.paisalo.jlgsourcing.Utilities.CustomProgressDialog;
import com.softeksol.paisalo.jlgsourcing.Utilities.IglPreferences;
import com.softeksol.paisalo.jlgsourcing.Utilities.Utils;
import com.softeksol.paisalo.jlgsourcing.adapters.AccountDataListAdapter;
import com.softeksol.paisalo.jlgsourcing.adapters.QrDataListAdapter;
import com.softeksol.paisalo.jlgsourcing.entities.AccountDetails_Model;
import com.softeksol.paisalo.jlgsourcing.entities.MyDataModel;
import com.softeksol.paisalo.jlgsourcing.retrofit.ApiClient;
import com.softeksol.paisalo.jlgsourcing.retrofit.ApiInterface;

import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class QrPayments extends AppCompatActivity {

    EditText accountNo;
    Button searchButton;
    ListView accountListView;

    QrDataListAdapter adapter;
    AccountDataListAdapter accountDataListAdapter;
    CustomProgressDialog customProgressDialog;
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_payments);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getToken();


        accountListView = findViewById(R.id.accountListView);
        accountNo = findViewById(R.id.accountNo);
        searchButton = findViewById(R.id.searchButton);

        customProgressDialog = new CustomProgressDialog(this);


        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customProgressDialog.show();


//                String smCode = accountNo.getText().toString();
                if (accountNo.getText().length() < 2) {
                    customProgressDialog.dismiss();
                    accountNo.setError("Please Enter Correct Account Number");
                    Utils.alert(QrPayments.this,"Please Enter Correct Account Number");

                } else {
                    String smCode = accountNo.getText().toString().trim();
                    String userId = IglPreferences.getPrefString(getApplicationContext(), SEILIGL.USER_ID, "");
                    String type = "Mobile";

                    fetchDataFromApi(smCode, userId, type );

                }
            }
        });
    }
    private void getToken() {
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("emailId", "dotnetdev2@paisalo.in");
        jsonObject.addProperty("password", "admin@123");
        jsonObject.addProperty("location", "string");

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.connectTimeout(1, TimeUnit.MINUTES);
        httpClient.readTimeout(1,TimeUnit.MINUTES);
        httpClient.addInterceptor(logging);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://erpservice.paisalo.in:980/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
        ApiInterface apiInterface=retrofit.create(ApiInterface.class);
        Call<JsonObject> call=apiInterface.getTokenForABF(jsonObject);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.d("TAG", "onResponse: "+response.body());
                SEILIGL.NEW_TOKEN="Bearer "+ response.body().get("token").getAsString();
                Log.d("TAGs", "onCreate: "+SEILIGL.NEW_TOKEN);

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d("TAGs", "onFailure: "+t.getMessage());
            }
        });
    }
    private void fetchDataFromApi(String smCode, String userId, String type) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.connectTimeout(1, TimeUnit.MINUTES);
        httpClient.readTimeout(1,TimeUnit.MINUTES);
        httpClient.addInterceptor(logging);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://erpservice.paisalo.in:980/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
        ApiInterface apiInterface=retrofit.create(ApiInterface.class);

        Call<AccountDetails_Model> call = apiInterface.getQrPaymentsBySmcode(smCode, userId, type, SEILIGL.NEW_TOKEN);

        call.enqueue(new Callback<AccountDetails_Model>() {
            @Override
            public void onResponse(Call<AccountDetails_Model> call, Response<AccountDetails_Model> response) {
                Log.d("TAG", "onResponse: "+response.body());
                if (response.isSuccessful()) {
                    AccountDetails_Model accountDetailsModel = response.body();
                    Log.d("TAG", "onResponse: "+response.body());

                    if (accountDetailsModel != null && accountDetailsModel.getData() != null) {
                        if (accountDetailsModel.getData().replace(" ","").toUpperCase().contains("SMCODENOTMAPPEDGIVENUSERID"))
                        {
                            Utils.alert(QrPayments.this,"Account No. not belongs to you. \n Please check and enter correct account number");
                        }else{
                            Gson gson = new Gson();
                            Log.d("TAG", "onResponse: +"+accountDetailsModel.getData());
                            MyDataModel[] nameList = gson.fromJson(accountDetailsModel.getData(), MyDataModel[].class);
                            accountDataListAdapter = new AccountDataListAdapter(QrPayments.this, nameList);
                            accountListView.setAdapter(accountDataListAdapter);
                            accountDataListAdapter.notifyDataSetChanged();
                        }

                    }
                } else {
                    Toast.makeText(QrPayments.this, "API call failed", Toast.LENGTH_SHORT).show();
                }
                customProgressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<AccountDetails_Model> call, Throwable t) {
                Log.d("TAG", "onFailure: "+t.getMessage());
                customProgressDialog.dismiss();
                Toast.makeText(QrPayments.this, "failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private List<AccountDetails_Model> parseMyDataList(String jsonArrayString) {
        Gson gson = new Gson();
        Type listType = new TypeToken<List<AccountDetails_Model>>() {}.getType();
        return gson.fromJson(jsonArrayString, listType);
    }




}