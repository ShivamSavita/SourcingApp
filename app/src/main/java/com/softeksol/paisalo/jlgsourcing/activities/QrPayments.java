package com.softeksol.paisalo.jlgsourcing.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.softeksol.paisalo.jlgsourcing.R;
import com.softeksol.paisalo.jlgsourcing.adapters.QrDataListAdapter;
import com.softeksol.paisalo.jlgsourcing.retrofit.ApiClient;
import com.softeksol.paisalo.jlgsourcing.retrofit.ApiInterface;

public class QrPayments extends AppCompatActivity {

    EditText accountNo;
    Button searchButton;
    ListView accountListView;
    private static final String JSON_DATA = "{\"emailId\": \"dotnetdev2@paisalo.in\", \"password\": \"admin@123\", \"location\": \"string\"}";

    QrDataListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_payments);

        fetchToken(JSON_DATA);

        accountListView = findViewById(R.id.accountListView);
        accountNo = findViewById(R.id.accountNo);
        searchButton = findViewById(R.id.searchButton);



        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String smCode = accountNo.getText().toString();

                String smCode = "UCMB023348";
                String userId = "gmst000435";
                String type = "Mobile";

                fetchDataFromApi(smCode, userId, type, "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJJZCI6IjE1MzkiLCJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9uYW1lIjoiZG90bmV0ZGV2MkBwYWlzYWxvLmluIiwiaHR0cDovL3NjaGVtYXMueG1sc29hcC5vcmcvd3MvMjAwNS8wNS9pZGVudGl0eS9jbGFpbXMvZW1haWxhZGRyZXNzIjoiZG90bmV0ZGV2MkBwYWlzYWxvLmluIiwiaHR0cDovL3NjaGVtYXMueG1sc29hcC5vcmcvd3MvMjAwNS8wNS9pZGVudGl0eS9jbGFpbXMvbmFtZWlkZW50aWZpZXIiOiIxNTM5IiwiUm9sZSI6WyJBRE1JTiIsIkFETUlOIl0sIkJyYW5jaENvZGUiOiIwMDQiLCJDcmVhdG9yIjoiYWdyYSIsIlVOYW1lIjoiUkFKQU4gS1VNQVIiLCJodHRwOi8vc2NoZW1hcy5taWNyb3NvZnQuY29tL3dzLzIwMDgvMDYvaWRlbnRpdHkvY2xhaW1zL2V4cGlyYXRpb24iOiJEZWMgU2F0IDE2IDIwMjMgMDY6NDc6MTMgQU0iLCJuYmYiOjE3MDI2MjI4MzMsImV4cCI6MTcwMjY4OTQzMywiaXNzIjoiaHR0cHM6Ly9sb2NhbGhvc3Q6NzE4OCIsImF1ZCI6Imh0dHBzOi8vbG9jYWxob3N0OjcxODgifQ.cjB6wdNuiZmkIj8GK4qSyXSlAgJRX9MaEAOQgdkTwCs");
            }
        });
    }

    private void fetchDataFromApi(String smCode, String userId, String type, String token) {
        ApiInterface apiService = ApiClient.getClient().create(ApiService.class);

        String authorizationHeader = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJJZCI6IjE1MzkiLCJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9uYW1lIjoiZG90bmV0ZGV2MkBwYWlzYWxvLmluIiwiaHR0cDovL3NjaGVtYXMueG1sc29hcC5vcmcvd3MvMjAwNS8wNS9pZGVudGl0eS9jbGFpbXMvZW1haWxhZGRyZXNzIjoiZG90bmV0ZGV2MkBwYWlzYWxvLmluIiwiaHR0cDovL3NjaGVtYXMueG1sc29hcC5vcmcvd3MvMjAwNS8wNS9pZGVudGl0eS9jbGFpbXMvbmFtZWlkZW50aWZpZXIiOiIxNTM5IiwiUm9sZSI6WyJBRE1JTiIsIkFETUlOIl0sIkJyYW5jaENvZGUiOiIwMDQiLCJDcmVhdG9yIjoiYWdyYSIsIlVOYW1lIjoiUkFKQU4gS1VNQVIiLCJodHRwOi8vc2NoZW1hcy5taWNyb3NvZnQuY29tL3dzLzIwMDgvMDYvaWRlbnRpdHkvY2xhaW1zL2V4cGlyYXRpb24iOiJEZWMgU2F0IDE2IDIwMjMgMDY6NDc6MTMgQU0iLCJuYmYiOjE3MDI2MjI4MzMsImV4cCI6MTcwMjY4OTQzMywiaXNzIjoiaHR0cHM6Ly9sb2NhbGhvc3Q6NzE4OCIsImF1ZCI6Imh0dHBzOi8vbG9jYWxob3N0OjcxODgifQ.cjB6wdNuiZmkIj8GK4qSyXSlAgJRX9MaEAOQgdkTwCs";

        Call<AccountDetails_Model> call = apiService.getQrPaymentsBySmcode(smCode, userId, type, authorizationHeader);

        call.enqueue(new Callback<AccountDetails_Model>() {
            @Override
            public void onResponse(Call<AccountDetails_Model> call, Response<AccountDetails_Model> response) {
                Log.d("TAG", "onResponse: "+response.body());
                if (response.isSuccessful()) {
                    AccountDetails_Model accountDetailsModel = response.body();
                    Log.d("TAG", "onResponse: "+response.body());
                    if (accountDetailsModel != null && accountDetailsModel.getData() != null) {
                        Gson gson = new Gson();
                        Log.d("TAG", "onResponse: +"+accountDetailsModel.getData());
                        MyDataModel[] nameList = gson.fromJson(accountDetailsModel.getData(), MyDataModel[].class);
                        //   List<AccountDetails_Model> dataList = parseMyDataList(accountDetailsModel.getData());


                        adapter = new AccountDataListAdapter(MainActivity.this, nameList);
                        accountListView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "API call failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AccountDetails_Model> call, Throwable t) {
                Log.d("TAG", "onFailure: "+t.getMessage());
                Toast.makeText(MainActivity.this, "failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private List<AccountDetails_Model> parseMyDataList(String jsonArrayString) {
        Gson gson = new Gson();
        Type listType = new TypeToken<List<AccountDetails_Model>>() {}.getType();
        return gson.fromJson(jsonArrayString, listType);
    }

    private void fetchToken(String jsonData) {
        Token_Model tokenRequest = new Gson().fromJson(jsonData, Token_Model.class);

        Call<Token_Model> call = ApiService.getToken(Token_Model);

        call.enqueue(new Callback<Token_Model>() {
            @Override
            public void onResponse(Call<Token_Model> call, Response<Token_Model> response) {
                if (response.isSuccessful()) {
                    Token_Model tokenModel = response.body();
                    if (tokenModel != null && tokenModel.getToken() != null) {
                        // Now you have the token, you can use it for other API calls
                        String token = tokenModel.getToken();
                        Log.d("TAG", "Token: " + token);

                        // Call another function to fetch data using the obtained token
                        fetchDataFromApi("UCMB023348", "gmst000435", "Mobile", "Bearer " + token);
                    } else {
                        Log.e("TAG", "Token not received in the response");
                    }
                } else {
                    Log.e("TAG", "Token request failed");
                }
            }

            @Override
            public void onFailure(Call<Token_Model> call, Throwable t) {
                Log.e("TAG", "Token request failed: " + t.getMessage());
            }
        });
    }


}