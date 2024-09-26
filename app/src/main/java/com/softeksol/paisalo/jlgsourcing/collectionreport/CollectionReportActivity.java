package com.softeksol.paisalo.jlgsourcing.collectionreport;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.softeksol.paisalo.jlgsourcing.R;
import com.softeksol.paisalo.jlgsourcing.SEILIGL;
import com.softeksol.paisalo.jlgsourcing.retrofit.ApiClient;
import com.softeksol.paisalo.jlgsourcing.retrofit.ApiInterface;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CollectionReportActivity extends AppCompatActivity {

    EditText search;
    Button getdata;
    private List<InstallmentColl> installmentList;


    private RecyclerView recyclerView1, recyclerView2;
    private InstallmentAdapter installmentAdapter;
    private PaidInstallmentAdapter paidInstallmentAdapter;
    private List<PaidInstallment> paidInstallmentList;

    private ProgressDialog progressDialog;
    TextView itemcount, paiditemcount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_report);

        search = findViewById(R.id.search);
        getdata = findViewById(R.id.getdata);
        recyclerView1 = findViewById(R.id.recycleview1);
        recyclerView2 = findViewById(R.id.recycleview2);
        installmentList = new ArrayList<>();
        paidInstallmentList = new ArrayList<>();
        recyclerView1.setLayoutManager(new LinearLayoutManager(this));
        recyclerView2.setLayoutManager(new LinearLayoutManager(this));

        itemcount = findViewById(R.id.itemcount);
        paiditemcount = findViewById(R.id.paiditemcount);



        getdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (search.getText()!=null && search.getText().toString().length()>0) {
                    installmentList = new ArrayList<>();
                    paidInstallmentList = new ArrayList<>();
                    getInstallData(search.getText().toString());
                    //filterPaidEMI(search.getText().toString().toUpperCase());
                   // filterEMI(search.getText().toString().toUpperCase());

                }else {
                   // filterPaidEMI("");
                   // filterEMI("");
                }

            }
        });


    }

    public void filterPaidEMI(String s) {
        Log.d("TAG", "filter: "+s);
        List<PaidInstallment> installmentColls=new ArrayList<>();
        for (int i = 0; i < paidInstallmentList.size(); i++) {

            if (paidInstallmentList.get(i).getPAmt().toUpperCase().contains(s.toString())) {
                installmentColls.add(paidInstallmentList.get(i));

            }
        }


        paidInstallmentAdapter.filterList(installmentColls);
        paidInstallmentAdapter.notifyDataSetChanged();
    }

    public void filterEMI(String s) {
        Log.d("TAG", "filter: "+s);
        List<InstallmentColl> installmentColls=new ArrayList<>();
        for (int i = 0; i < installmentList.size(); i++) {
            if (installmentList.get(i).getAmt().toUpperCase().contains(s.toString())) {
                installmentColls.add(installmentList.get(i));

            }
        }


        installmentAdapter.filterList(installmentColls);
        installmentAdapter.notifyDataSetChanged();
    }


    private void getInstallData(String smcode){
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        ApiInterface apiInterface= ApiClient.getClient(SEILIGL.NEW_SERVERAPI).create(ApiInterface.class);
        Call<CollectionReportModel> call = apiInterface.getCollectionReprt(smcode);

        call.enqueue(new Callback<CollectionReportModel>() {
            @Override
            public void onResponse(Call<CollectionReportModel> call, Response<CollectionReportModel> response) {
                if (response.isSuccessful()) {

                    int emiListSize, EmiCollectionSize;
                    Log.d("TAG", "onResponse: " + response.body());
                    CollectionReportModel collectionReportModel = response.body();
                    Data data = collectionReportModel.getData();

                    List<Emi> emiList = data.getEmis();
                    for (Emi emi : emiList) {

                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
                        SimpleDateFormat desiredDateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());


                        Date date = null;
                        try {
                            date = simpleDateFormat.parse(emi.getPvNRCPDT());

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        String formattedDate = desiredDateFormat.format(date);

                        InstallmentColl installmentColl = new InstallmentColl(emi.getAmt().toString(), formattedDate);
                        installmentList.add(installmentColl);
                    }

                    Log.d("TAG_list", "onResponse: " + installmentList);
                    installmentAdapter = new InstallmentAdapter(installmentList);

                    List<EmiCollection> emiCollections = data.getEmiCollections();
                    for (EmiCollection emiCollection : emiCollections) {

                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
                        SimpleDateFormat desiredDateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());


                        Date date = null;
                        try {
                            date = simpleDateFormat.parse(emiCollection.getVdate());

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        String formattedDate = desiredDateFormat.format(date);

                        PaidInstallment paidInstallment = new PaidInstallment(emiCollection.getCr().toString(), formattedDate);
                        paidInstallmentList.add(paidInstallment);
                    }
                    Log.d("TAG_list", "onResponse: " + paidInstallmentList);
                    paidInstallmentAdapter = new PaidInstallmentAdapter(paidInstallmentList);


                    recyclerView1.setAdapter(installmentAdapter);


                    recyclerView2.setAdapter(paidInstallmentAdapter);
                    installmentAdapter.notifyDataSetChanged();
                    paidInstallmentAdapter.notifyDataSetChanged();

                    //itemcount
                    emiListSize = emiList.size();
                    EmiCollectionSize = emiCollections.size();
                    itemcount.setText("(" + emiListSize + ")");
                    paiditemcount.setText("(" + EmiCollectionSize + ")");



                } else {
                    Log.d("TAG_response", "onResponse:5 " + response.code());
                }
                progressDialog.dismiss();

            }


            @Override
            public void onFailure(Call<CollectionReportModel> call, Throwable t) {
                Log.d("TAG_response", "onResponse: 4" + "fail");
                progressDialog.dismiss();

            }
        });

    }
}



