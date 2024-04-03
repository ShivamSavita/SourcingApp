package com.softeksol.paisalo.jlgsourcing.activities;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.JsonObject;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.loopj.android.http.RequestParams;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.softeksol.paisalo.jlgsourcing.BuildConfig;
import com.softeksol.paisalo.jlgsourcing.Global;
import com.softeksol.paisalo.jlgsourcing.R;
import com.softeksol.paisalo.jlgsourcing.SEILIGL;
import com.softeksol.paisalo.jlgsourcing.Utilities.AadharUtils;
import com.softeksol.paisalo.jlgsourcing.Utilities.CameraUtils;
import com.softeksol.paisalo.jlgsourcing.Utilities.CustomProgressDialog;
import com.softeksol.paisalo.jlgsourcing.Utilities.DateUtils;
import com.softeksol.paisalo.jlgsourcing.Utilities.IglPreferences;
import com.softeksol.paisalo.jlgsourcing.Utilities.MyTextWatcher;
import com.softeksol.paisalo.jlgsourcing.Utilities.OTPDialog;
import com.softeksol.paisalo.jlgsourcing.Utilities.Utils;
import com.softeksol.paisalo.jlgsourcing.Utilities.Verhoeff;
import com.softeksol.paisalo.jlgsourcing.WebOperations;
import com.softeksol.paisalo.jlgsourcing.adapters.AdapterListRange;
import com.softeksol.paisalo.jlgsourcing.adapters.AdapterRecViewListDocuments;
import com.softeksol.paisalo.jlgsourcing.adapters.CityListAdapter;
import com.softeksol.paisalo.jlgsourcing.adapters.CreatorListAdapter;
import com.softeksol.paisalo.jlgsourcing.adapters.DistrictListAdapter;
import com.softeksol.paisalo.jlgsourcing.adapters.SubDistrictListAdapter;
import com.softeksol.paisalo.jlgsourcing.adapters.VillageListAdapter;
import com.softeksol.paisalo.jlgsourcing.entities.AadharData;
import com.softeksol.paisalo.jlgsourcing.entities.Borrower;
import com.softeksol.paisalo.jlgsourcing.entities.BorrowerExtra;
import com.softeksol.paisalo.jlgsourcing.entities.BorrowerExtraBank;
import com.softeksol.paisalo.jlgsourcing.entities.CityData;
import com.softeksol.paisalo.jlgsourcing.entities.CityModelList;
import com.softeksol.paisalo.jlgsourcing.entities.CreatorModel;
import com.softeksol.paisalo.jlgsourcing.entities.DeDupeData;
import com.softeksol.paisalo.jlgsourcing.entities.DeDupeResponse;
import com.softeksol.paisalo.jlgsourcing.entities.DistrictData;
import com.softeksol.paisalo.jlgsourcing.entities.DistrictListModel;
import com.softeksol.paisalo.jlgsourcing.entities.DocumentStore;
import com.softeksol.paisalo.jlgsourcing.entities.Manager;
import com.softeksol.paisalo.jlgsourcing.entities.RangeCategory;
import com.softeksol.paisalo.jlgsourcing.entities.RangeCategory_Table;
import com.softeksol.paisalo.jlgsourcing.entities.SubDistrictData;
import com.softeksol.paisalo.jlgsourcing.entities.SubDistrictModel;
import com.softeksol.paisalo.jlgsourcing.entities.VillageData;
import com.softeksol.paisalo.jlgsourcing.entities.VillageListModel;
import com.softeksol.paisalo.jlgsourcing.entities.dto.BorrowerDTO;
import com.softeksol.paisalo.jlgsourcing.entities.dto.OldFIById;
import com.softeksol.paisalo.jlgsourcing.handlers.DataAsyncResponseHandler;
import com.softeksol.paisalo.jlgsourcing.location.GpsTracker;
import com.softeksol.paisalo.jlgsourcing.retrofit.ApiInterface;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.zip.GZIPInputStream;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import cz.msebera.android.httpclient.Header;
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

import static com.softeksol.paisalo.jlgsourcing.Utilities.CameraUtils.REQUEST_TAKE_PHOTO;
import static com.softeksol.paisalo.jlgsourcing.Utilities.CameraUtils.REQUEST_TAKE_PROFILE_PHOTO;
import static com.softeksol.paisalo.jlgsourcing.Utilities.Verhoeff.validateCaseCode;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class ActivityBorrowerKyc extends AppCompatActivity  implements View.OnClickListener,AdapterRecViewListDocuments.ItemListener, CameraUtils.OnCameraCaptureUpdate ,onListCReatorInteraction,VillageChooseListner,DistrictChooseListner,CityChooseListner,SubDistChooseListner{ //, CameraUtils.OnCameraCaptureUpdate

    private final AppCompatActivity activity = this;

    CustomProgressDialog customProgressDialog;
    int otpVerified=0;
    int PAN_CARD_CAPTURE=1100;

    AdapterListRange rlaMarritalStatus;
    ImageView adharBackImg;
    ImageView adharFrontImg;
    private Borrower borrower;
    private BorrowerExtra borrowerExtra;
    private DocumentStore documentStore;
    //private BorrowerExtraBank borrowerExtraBank;
    private Uri uriPicture;
    private ImageView imgViewScanQR;
    private Manager manager;
    private long borrower_id;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private CheckBox chkTvTopup;
    private AppCompatSpinner acspGender, acspAadharState,acspRelationship;
    private TextInputEditText tietAadharId, tietName, tietAge, tietDob, tietGuardian,
            tietAddress1, tietAddress2, tietAddress3, tietCity, tietPinCode, tietMobile,
            tietDrivingLic, tietPanNo, tietVoterId, tietMotherMName,tietMotherFName,tietMotherLName, tietFatherMName,tietFatherFName,tietFatherLName
            ,tietSpouseLName,tietSpouseMName,tietSpouseFName;
    private SearchView svOldCase;
    private TextView textViewFiDetails,tilPAN_Name,tilVoterId_Name,tilDL_Name;
    private TextWatcher ageTextWatcher;
    private Calendar myCalendar;
    private DatePickerDialog.OnDateSetListener dateSetListner;
    Button voterIdCheckSign,panCheckSign,dLCheckSign,mobileNuberCheckSign;
    private MyTextWatcher aadharTextChangeListner;
    private AdapterRecViewListDocuments adapterRecViewListDocuments;
    private DocumentStore documentPic;
    private boolean showSubmitBorrowerMenuItem = true;
    private LinearLayoutCompat llTopupCode;
    AdapterListRange genderAdapter;

    protected static final byte SEPARATOR_BYTE = (byte)255;
    protected static final int VTC_INDEX = 15;
    protected int emailMobilePresent, imageStartIndex, imageEndIndex;
    protected ArrayList<String> decodedData;
    int ImageType=0;
    protected String signature,email,mobile;
    ArrayList<RangeCategory> genders;
    String loanDurationData,stateData,genderData,maritalStatus,relationShipData;
    boolean aadharNumberentry=false;
    String isAdhaarEntry ="N";
    String isNameMatched ="0";
    int isPanverify=0;
    int isDLverify=0;
    int isVoterverify=0;
    String bankName;
    String PANHolderName, VoterIdName, BankAccountHolderName;

    CardView cardView_SpouseFirstName;
    LinearLayout linearLayout433;

    TextView textView35;

    Button BtnNextOnFirstKyc,capturePanCardImage;
    ImageView imgViewCal;

    Spinner spinnerMarritalStatus;
    String requestforVerification="";
    String ResponseforVerification="";
    String lastCaseCode="";
    String lastLoanAmt="";
    String lastDuration="";
    String lastPaidEmi="";
    boolean panaadharDOBMatched=false;
    boolean isgetPanwithOCR=false;
    String schemeNameForVH;
    String AddressREGX="^[0-9A-Za-z\\s]+$";
    String choosedCreator;
    ImageView imgViewAadharPhoto;
    int profileImageClick=0;
    TextView txtVDistrictName, txtCityName,txtVillageName,txtSubDistictName;
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    List<CreatorModel> list=new ArrayList<>();
    List<VillageData> villageDataList =new ArrayList<>();
    List<CityData> cityDataList =new ArrayList<>();
    List<DistrictData> districtDataList =new ArrayList<>();
    List<SubDistrictData> subDistrictDataList =new ArrayList<>();
    onListCReatorInteraction listCReatorInteraction;
    VillageData villageData;
    DistrictData districtDat;

    SubDistrictData subDistrictData;

    CityData cityData;

    VillageChooseListner listVillageInteraction;
    DistrictChooseListner listDistictInteraction;
    SubDistChooseListner listSubDistructInteraction;

    CityChooseListner cityChooseListner;
    CreatorListAdapter adapter;

    VillageListAdapter villageListAdapter;
    DistrictListAdapter districtListAdapter;
    SubDistrictListAdapter subDistrictListAdapter;
    SubDistrictListAdapter SubDistrictListAdapter;

    CityListAdapter cityListAdapter;

    private void showCreatorSearchDialog(TextView creators) {

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


        Dialog  dialogSearch=new Dialog(ActivityBorrowerKyc.this);
        dialogSearch.setContentView(R.layout.dialog_searchable_spinner);
        dialogSearch.getWindow().setLayout(650,800);
        EditText edit_text=dialogSearch.findViewById(R.id.edit_text);
        RecyclerView recViewOfCreator=dialogSearch.findViewById(R.id.recViewOfCreator);
        dialogSearch.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                Log.d("TAG", "onDismiss: hiiitt"+choosedCreator);

                creators.setText(choosedCreator);
            }
        });
        recViewOfCreator.setLayoutManager(new LinearLayoutManager(this));

        Call<List<CreatorModel>> call=apiInterface.getCreatorList();
        call.enqueue(new Callback<List<CreatorModel>>() {
            @Override
            public void onResponse(Call<List<CreatorModel>> call, Response<List<CreatorModel>> response) {
                list.addAll(response.body());
                adapter=new CreatorListAdapter(ActivityBorrowerKyc.this,list,dialogSearch,listCReatorInteraction);
                recViewOfCreator.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<List<CreatorModel>> call, Throwable t) {

            }
        });

        edit_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,             int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

               // filter(s.toString().toUpperCase());


            }
        });


        dialogSearch.show();
    }
    public void filterVillage(String s) {
        Log.d("TAG", "filter: "+s);
        List<VillageData> villageDataModel=new ArrayList<>();
        for (int i = 0; i < villageDataList.size(); i++) {

            if (villageDataList.get(i).getVillagENAME().toUpperCase().contains(s.toString())) {
                villageDataModel.add(villageDataList.get(i));
             //   Log.d("TAG", "filter: "+list.get(i));
            }
        }


        villageListAdapter.filterList(villageDataModel);
        villageListAdapter.notifyDataSetChanged();
    }
   public void filterCity(String s) {
        Log.d("TAG", "filter: "+s);
        List<CityData> cityData1=new ArrayList<>();
        for (int i = 0; i < cityDataList.size(); i++) {

            if (cityDataList.get(i).getCitYNAME().toUpperCase().contains(s.toString())) {
                cityData1.add(cityDataList.get(i));
             //   Log.d("TAG", "filter: "+list.get(i));
            }
        }


       cityListAdapter.filterList(cityData1);
       cityListAdapter.notifyDataSetChanged();
    }

    public void filterDistrict(String s) {
        Log.d("TAG", "filter: "+s);
        List<DistrictData> districtDataModel=new ArrayList<>();
        for (int i = 0; i < districtDataList.size(); i++) {

            if (districtDataList.get(i).getDisTNAME().toUpperCase().contains(s.toString())) {
                districtDataModel.add(districtDataList.get(i));
             //   Log.d("TAG", "filter: "+list.get(i));
            }
        }


        districtListAdapter.filterList(districtDataModel);
        districtListAdapter.notifyDataSetChanged();
    }

    public void filterSubDistrict(String s) {
        Log.d("TAG", "filter: "+s);
        List<SubDistrictData> subDistrictDataModel=new ArrayList<>();
        for (int i = 0; i < subDistrictDataList.size(); i++) {

            if (subDistrictDataList.get(i).getSuBDISTNAME().toUpperCase().contains(s.toString())) {
                subDistrictDataModel.add(subDistrictDataList.get(i));
                //   Log.d("TAG", "filter: "+list.get(i));
            }
        }


        subDistrictListAdapter.filterList(subDistrictDataModel);
        subDistrictListAdapter.notifyDataSetChanged();
    }


    private void showCityDialog(TextView txtCityName, String stateCode ) {
        customProgressDialog.show();
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


        Dialog  dialogSearch=new Dialog(ActivityBorrowerKyc.this);
        dialogSearch.setContentView(R.layout.dialog_searchable_spinner);
        dialogSearch.setCancelable(false);
        dialogSearch.getWindow().setLayout(1000,1600);
        EditText edit_text=dialogSearch.findViewById(R.id.edit_text);
        TextView dialog_name=dialogSearch.findViewById(R.id.dialog_name);
        dialog_name.setText("Select City");
        RecyclerView recViewOfCreator=dialogSearch.findViewById(R.id.recViewOfCreator);
        dialogSearch.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
             //   Log.d("TAG", "onDismiss: hiiitt"+choosedCreator);

                try {
                    txtCityName.setText(cityData.getCitYNAME());
                }catch (NullPointerException e){

                }
            }
        });
        recViewOfCreator.setLayoutManager(new LinearLayoutManager(this));

        Call<CityModelList> call=apiInterface.getCityList(stateCode);
        call.enqueue(new Callback<CityModelList>() {
            @Override
            public void onResponse(Call<CityModelList> call, Response<CityModelList> response) {
                if (cityDataList.size()>1)
                    cityDataList.clear();
                cityDataList.addAll(response.body().getData());
                cityListAdapter=new CityListAdapter(ActivityBorrowerKyc.this, cityDataList,dialogSearch,cityChooseListner);
                recViewOfCreator.setAdapter(cityListAdapter);
                cityListAdapter.notifyDataSetChanged();
                if (cityDataList.size()>=1){
                    dialogSearch.show();
                }else{
                    Toast.makeText(ActivityBorrowerKyc.this, "Village List is not coming for "+stateCode, Toast.LENGTH_SHORT).show();
                }
                customProgressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<CityModelList> call, Throwable t) {
                customProgressDialog.dismiss();

            }
        });

        edit_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,  int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                filterCity(s.toString().toUpperCase());
            }
        });



    }
    private void showDistrictDialog(TextView txtVVillageName, String districtCode ) {
        customProgressDialog.show();

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


        Dialog  dialogSearch=new Dialog(ActivityBorrowerKyc.this);
        dialogSearch.setContentView(R.layout.dialog_searchable_spinner);
        dialogSearch.setCancelable(false);
        dialogSearch.getWindow().setLayout(1000,1600);
        EditText edit_text=dialogSearch.findViewById(R.id.edit_text);
        TextView dialog_name=dialogSearch.findViewById(R.id.dialog_name);
        dialog_name.setText("Select District");
        RecyclerView recViewOfCreator=dialogSearch.findViewById(R.id.recViewOfCreator);
        dialogSearch.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
             //   Log.d("TAG", "onDismiss: hiiitt"+choosedCreator);

                try {
                    txtVVillageName.setText(districtDat.getDisTNAME());
                }catch (NullPointerException e){

                }
            }
        });
        recViewOfCreator.setLayoutManager(new LinearLayoutManager(this));

        Call<DistrictListModel> call=apiInterface.getDistictList(districtCode);
        call.enqueue(new Callback<DistrictListModel>() {
            @Override
            public void onResponse(Call<DistrictListModel> call, Response<DistrictListModel> response) {
                if (districtDataList.size()>1)
                    districtDataList.clear();
                districtDataList.addAll(response.body().getData());
                districtListAdapter=new DistrictListAdapter(ActivityBorrowerKyc.this, districtDataList,dialogSearch,listDistictInteraction);
                recViewOfCreator.setAdapter(districtListAdapter);
                districtListAdapter.notifyDataSetChanged();
                if (districtDataList.size()>=1){
                    dialogSearch.show();
                }else{
                    Toast.makeText(ActivityBorrowerKyc.this, "district list is not coming for "+districtCode, Toast.LENGTH_SHORT).show();
                }
                customProgressDialog.dismiss();

            }

            @Override
            public void onFailure(Call<DistrictListModel> call, Throwable t) {
                customProgressDialog.dismiss();

            }
        });

        edit_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,  int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                filterDistrict(s.toString().toUpperCase());


            }
        });



    }
    private void showSubDistrictDialog(TextView txtVVillageName, String districtCode ) {
        customProgressDialog.show();

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


        Dialog  dialogSearch=new Dialog(ActivityBorrowerKyc.this);
        dialogSearch.setContentView(R.layout.dialog_searchable_spinner);
        dialogSearch.setCancelable(false);
        dialogSearch.getWindow().setLayout(1000,1600);
        EditText edit_text=dialogSearch.findViewById(R.id.edit_text);
        TextView dialog_name=dialogSearch.findViewById(R.id.dialog_name);
        dialog_name.setText("Select Sub-District");
        RecyclerView recViewOfCreator=dialogSearch.findViewById(R.id.recViewOfCreator);
        dialogSearch.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
             //   Log.d("TAG", "onDismiss: hiiitt"+choosedCreator);

                try {
                    txtVVillageName.setText(subDistrictData.getSuBDISTNAME());
                }catch (NullPointerException e){

                }
            }
        });
        recViewOfCreator.setLayoutManager(new LinearLayoutManager(this));

        Call<SubDistrictModel> call=apiInterface.getSubDistrictList(districtCode);
        call.enqueue(new Callback<SubDistrictModel>() {
            @Override
            public void onResponse(Call<SubDistrictModel> call, Response<SubDistrictModel> response) {
                if (subDistrictDataList.size()>1)
                    subDistrictDataList.clear();
                try {
                    subDistrictDataList.addAll(response.body().getData());
                    SubDistrictData other=   new SubDistrictData("2222222222","Other","22222");
                    subDistrictDataList.add(other);
                    subDistrictListAdapter=new SubDistrictListAdapter(ActivityBorrowerKyc.this, subDistrictDataList,dialogSearch,listSubDistructInteraction);
                    recViewOfCreator.setAdapter(subDistrictListAdapter);
                    subDistrictListAdapter.notifyDataSetChanged();
                    if (subDistrictDataList.size()>=1){
                        dialogSearch.show();
                    }else{
                        Toast.makeText(ActivityBorrowerKyc.this, "district list is not coming for "+districtCode, Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception c){
                    SubDistrictData other=   new SubDistrictData("2222222222","Other","22222");
                    subDistrictDataList.add(other);
                }

                customProgressDialog.dismiss();

            }

            @Override
            public void onFailure(Call<SubDistrictModel> call, Throwable t) {
                SubDistrictData other=   new SubDistrictData("2222222222","Other","22222");
                subDistrictDataList.add(other);
                customProgressDialog.dismiss();
            }
        });

        edit_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,  int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                filterSubDistrict(s.toString().toUpperCase());


            }
        });



    }



    private void showVillageDialog(TextView txtVDistrictName, String stateCode, String disTCODE, String suBDISTCODE) {

        customProgressDialog.show();

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


        Dialog  dialogSearch=new Dialog(ActivityBorrowerKyc.this);
        dialogSearch.setContentView(R.layout.dialog_searchable_spinner);
        dialogSearch.setCancelable(false);
        dialogSearch.getWindow().setLayout(1000,1600);
        EditText edit_text=dialogSearch.findViewById(R.id.edit_text);
        TextView dialog_name=dialogSearch.findViewById(R.id.dialog_name);
        dialog_name.setText("Select Village");
        RecyclerView recViewOfCreator=dialogSearch.findViewById(R.id.recViewOfCreator);
        dialogSearch.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                //   Log.d("TAG", "onDismiss: hiiitt"+choosedCreator);

                try {
                    txtVDistrictName.setText(villageData.getVillagENAME());
                }catch (NullPointerException e){

                }
            }
        });
        recViewOfCreator.setLayoutManager(new LinearLayoutManager(this));

        Call<VillageListModel> call=apiInterface.getVillageList(stateCode,disTCODE,suBDISTCODE);
        call.enqueue(new Callback<VillageListModel>() {
            @Override
            public void onResponse(Call<VillageListModel> call, Response<VillageListModel> response) {
                if (villageDataList.size()>1)
                    villageDataList.clear();
                try {
                    villageDataList.addAll(response.body().getData());
                    VillageData other=new VillageData("2222222222222222","Other", "000", "0000", "0000");
                    villageDataList.add(other);
                    villageListAdapter=new VillageListAdapter(ActivityBorrowerKyc.this, villageDataList,dialogSearch,listVillageInteraction);
                    recViewOfCreator.setAdapter(villageListAdapter);
                    villageListAdapter.notifyDataSetChanged();
                    if (villageDataList.size()>=1){
                        dialogSearch.show();
                    }else{
                        Toast.makeText(ActivityBorrowerKyc.this, "district list is not coming", Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    VillageData other=new VillageData("2222222222222222","Other", "000", "0000", "0000");
                    villageDataList.add(other);
                }

                customProgressDialog.dismiss();

            }

            @Override
            public void onFailure(Call<VillageListModel> call, Throwable t) {
                VillageData other=new VillageData("2222222222222222","Other", "000", "0000", "0000");
                villageDataList.add(other);
                customProgressDialog.dismiss();

            }
        });

        edit_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,  int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                filterVillage(s.toString().toUpperCase());


            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrower_entry);
        BtnNextOnFirstKyc=findViewById(R.id.BtnSaveKYCData);
        capturePanCardImage=findViewById(R.id.capturePanCardImage);
        txtVDistrictName=findViewById(R.id.txtVDistrictName);
        txtSubDistictName=findViewById(R.id.txtSubDistictName);
        txtVillageName=findViewById(R.id.txtVillageName);
        txtCityName =findViewById(R.id.txtCityName);
        listCReatorInteraction=ActivityBorrowerKyc.this;
        listDistictInteraction=ActivityBorrowerKyc.this;
        cityChooseListner=ActivityBorrowerKyc.this;
        listSubDistructInteraction=ActivityBorrowerKyc.this;
        customProgressDialog = new CustomProgressDialog(this);
        listVillageInteraction=ActivityBorrowerKyc.this;
        BtnNextOnFirstKyc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateBorrower();
            }
        });
        manager = (Manager) getIntent().getSerializableExtra(Global.MANAGER_TAG);

// Storing data into SharedPreferences
        sharedPreferences = getSharedPreferences("KYCData",MODE_PRIVATE);

// Creating an Editor object to edit(write to the file)
        editor = sharedPreferences.edit();
        //borrower = new Borrower();
        borrower = new Borrower(manager.Creator, manager.TAG, manager.FOCode, manager.AreaCd, IglPreferences.getPrefString(ActivityBorrowerKyc.this, SEILIGL.USER_ID, ""));
        borrowerExtra=new BorrowerExtra();
        borrower.setPicture("");
        //borrowerExtraBank=new BorrowerExtraBank(manager.Creator,manager.TAG);
        borrower.associateExtraBank(new BorrowerExtraBank(manager.Creator, manager.TAG));
        //borrower.fi
        borrower.isAadharVerified = "N";
        rlaMarritalStatus = new AdapterListRange(this, RangeCategory.getRangesByCatKey("marrital_status"), false);
        spinnerMarritalStatus = (Spinner) findViewById(R.id.spinLoanAppPersonalMarritalStatus);
        spinnerMarritalStatus.setAdapter(rlaMarritalStatus);
        schemeNameForVH=getIntent().getStringExtra(Global.SCHEME_TAG);

        //  Log.d("TAG", "onCreate233: "+DocumentStore.getFiData(222333));
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(" Borrower KYC"); //actionBar.getTitle();
        actionBar.setDisplayHomeAsUpEnabled(true);
//        ArrayList<RangeCategory> genders = new ArrayList<>();
//        genders.add(new RangeCategory("Female", "Gender"));

        genders = new ArrayList<>();

        genders.add(new RangeCategory("--Select--", "Gender"));
        genders.add(new RangeCategory("Female", "Gender"));
        genders.add(new RangeCategory("Male", "Gender"));
        genders.add(new RangeCategory("Transgender", "Gender"));

//        if (BuildConfig.APPLICATION_ID.equals("com.softeksol.paisalo.jlgsourcing")) {
//            genders.add(new RangeCategory("Female", "Gender"));
//            genders.add(new RangeCategory("Male", "Gender"));
//            genders.add(new RangeCategory("Transgender", "Gender"));
//        } else if (BuildConfig.APPLICATION_ID.equals("net.softeksol.seil.groupfin.sbicolending")){
//            genders.add(new RangeCategory("Female", "Gender"));
//            genders.add(new RangeCategory("Male", "Gender"));
//            genders.add(new RangeCategory("Transgender", "Gender"));
//        }else {
//            genders.add(new RangeCategory("Female", "Gender"));
//        }

        /*switch (BuildConfig.APPLICATION_ID) {
            case "net.softeksol.seil.groupfin":
            case "net.softeksol.seil.groupfin.sbi":
                genders.add(new RangeCategory("Female", "Gender"));
                break;
            default:
                genders.add(new RangeCategory("Male", "Gender"));
                genders.add(new RangeCategory("Female", "Gender"));
                genders.add(new RangeCategory("Transgender", "Gender"));
        }*/

        tietDob = findViewById(R.id.tietDob);
        tietMotherMName=findViewById(R.id.tietIncomeMonthly);
        tietMotherFName=findViewById(R.id.tietMotherFName);
        tietMotherLName=findViewById(R.id.tietMotherLName);
        tietFatherMName=findViewById(R.id.tietFatherMName);
        tietFatherFName=findViewById(R.id.tietFatherFName);
        tietFatherLName=findViewById(R.id.tietFatherLName);
        tietGuardian = findViewById(R.id.tietGuardian);
        cardView_SpouseFirstName = findViewById(R.id.cardView_SpouseFirstName);
        tietSpouseLName = findViewById(R.id.tietSpouseLName);
        tietSpouseMName = findViewById(R.id.tietSpouseMName);
        tietSpouseFName = findViewById(R.id.tietSpouseFName);
        imgViewCal = findViewById(R.id.imgViewCal);
        textView35 = findViewById(R.id.textView35);
        textView35.setVisibility(View.GONE);
        cardView_SpouseFirstName.setVisibility(View.GONE);
        linearLayout433 = findViewById(R.id.linearLayout433);
        linearLayout433.setVisibility(View.GONE);
        panCheckSign = findViewById(R.id.panCheckSign);
        dLCheckSign = findViewById(R.id.dLCheckSign);

        voterIdCheckSign = findViewById(R.id.voterIdCheckSign);
        mobileNuberCheckSign = findViewById(R.id.mobileNuberCheckSign);
        acspGender = findViewById(R.id.acspGender);
        tilPAN_Name = findViewById(R.id.tilPAN_Name);
        tilVoterId_Name = findViewById(R.id.tilVoterId_Name);
        tilDL_Name = findViewById(R.id.tilDL_Name);
        tilPAN_Name.setVisibility(View.GONE);
        tilDL_Name.setVisibility(View.GONE);
        tilVoterId_Name.setVisibility(View.GONE);
        genderAdapter=new AdapterListRange(this, genders, false);
        acspGender.setAdapter(genderAdapter);

        myCalendar = Calendar.getInstance();
        myCalendar.setTime(new Date());
        acspRelationship = findViewById(R.id.acspRelationship);
        ArrayList<RangeCategory> relationSips = new ArrayList<>();
        relationSips.add(new RangeCategory("--Select--", ""));
        relationSips.add(new RangeCategory("Husband", ""));
        relationSips.add(new RangeCategory("Father", ""));
        relationSips.add(new RangeCategory("Mother", ""));

        acspRelationship.setAdapter(new AdapterListRange(this, relationSips, false));
        //acspRelationship.setVisibility(View.GONE);
        //findViewById(R.id.llUidRelationship).setVisibility(View.GONE);

        findViewById(R.id.imgViewAadharPhoto).setVisibility(View.GONE);

        acspAadharState = findViewById(R.id.acspAadharState);
        Log.d("TAG", "onCreate: "+RangeCategory.getRangesByCatKey("state", "RangeCode", false));

        acspAadharState.setAdapter(new AdapterListRange(this, RangeCategory.getRangesByCatKey("state", "DescriptionEn", true), false));

        tietName = findViewById(R.id.tietName);
        tietName.setEnabled(false);
        tietName.addTextChangedListener(new MyTextWatcher(tietName){
            @Override
            public void validate(EditText editText, String text) {
                validateControls(editText, text);
            }
        });

        llTopupCode = findViewById(R.id.llTopupCode);
        llTopupCode.setVisibility(View.INVISIBLE);
        svOldCase = findViewById(R.id.svTopupCaseNumber);
        svOldCase.setVisibility(View.GONE);
        svOldCase.setIconified(false);
        svOldCase.setOnClickListener(this);
        TextView Capture_Aadhar=findViewById(R.id.Capture_Aadhar);
        TextView Capture_Aadharback=findViewById(R.id.Capture_Aadharback);
        capturePanCardImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageType=3;
                ImagePicker.with(ActivityBorrowerKyc.this)
                        .cameraOnly()
                        .start(PAN_CARD_CAPTURE);
//                final View dialogView = ActivityBorrowerKyc.this.getLayoutInflater().inflate(R.layout.dialog_card_pan_capture, null);
//                CardView AdharFront_acrdView=dialogView.findViewById(R.id.newPAN_acrdView);
//                CardView AdharBack_acrdView=dialogView.findViewById(R.id.olPAN_acrdView);
//                Button submitORCadharBtn=dialogView.findViewById(R.id.submitORCadharBtn);
//                TextView sampleCardOldPan=dialogView.findViewById(R.id.sampleCardOldPan);
//                TextView sampleCardNewPan=dialogView.findViewById(R.id.sampleCardNewPan);
//                adharBackImg=dialogView.findViewById(R.id.adharBackImg);
//                adharFrontImg=dialogView.findViewById(R.id.adharFrontImg);
//
//                AdharFront_acrdView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        sampleCardNewPan.setVisibility(View.GONE);
//                        ImageType=3;
//                        ImagePicker.with(ActivityBorrowerKyc.this)
//                                .cameraOnly()
//                                .start(PAN_CARD_CAPTURE);
//                    }
//                });
//                AdharBack_acrdView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        sampleCardOldPan.setVisibility(View.GONE);
//                        ImageType=4;
//                        ImagePicker.with(ActivityBorrowerKyc.this)
//                                .cameraOnly()
//                                .start(PAN_CARD_CAPTURE);
//                    }
//                });
//
//
//                AlertDialog.Builder builder = new AlertDialog.Builder(ActivityBorrowerKyc.this);
//                builder.setView(dialogView);
//                final AlertDialog dialog = builder.create();
//                dialog.setCancelable(false);
//                dialog.show();
//
//                submitORCadharBtn.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        dialog.dismiss();
//                    }
//                });
            }
        });
        Capture_Aadhar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ImageType = 1;
                ImagePicker.with(ActivityBorrowerKyc.this)
                        .cameraOnly()
                        .start(1000);

            }
        });
        Capture_Aadharback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ImageType=2;
                ImagePicker.with(ActivityBorrowerKyc.this)
                        .cameraOnly()
                        .start(1000);

            }
        });
        txtCityName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (((RangeCategory) acspAadharState.getSelectedItem()).RangeCode.equals("99")){
                    Toast.makeText(activity, "Please select Valid State", Toast.LENGTH_SHORT).show();
                }else{
                    showCityDialog(txtCityName,((RangeCategory) acspAadharState.getSelectedItem()).RangeCode);
                }
            }
        });

        txtVDistrictName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (((RangeCategory) acspAadharState.getSelectedItem()).RangeCode.equals("99")){
                    Toast.makeText(activity, "Please select Valid State", Toast.LENGTH_SHORT).show();
                }else{
                    showDistrictDialog(txtVDistrictName,((RangeCategory) acspAadharState.getSelectedItem()).RangeCode);
                }
            }
        });
        txtSubDistictName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (districtDat!=null){

                    showSubDistrictDialog(txtSubDistictName,districtDat.getDisTCODE());
                }else{
                    Toast.makeText(activity, "Please choose any District", Toast.LENGTH_SHORT).show();
                }
            }
        });

        txtVillageName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (subDistrictData!=null && districtDat!=null && !((RangeCategory) acspAadharState.getSelectedItem()).RangeCode.equals("99")){

                    showVillageDialog(txtVillageName,((RangeCategory) acspAadharState.getSelectedItem()).RangeCode,districtDat.getDisTCODE(),subDistrictData.getSuBDISTCODE());
                }else{
                    Toast.makeText(activity, "Please choose any valid state, district or sub district", Toast.LENGTH_SHORT).show();
                }
            }
        });
        /*svOldCase.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Query Click", svOldCase.getQuery().toString());
            }
        });*/
        svOldCase.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("Query Submit", query);

                if (validateCaseCode(query)) {
                    fetchTopupDetails(svOldCase.getQuery().toString());
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d("QueryTextChange", newText);
                boolean validationStatus = validateCaseCode(newText);
                svOldCase.setBackgroundResource(validationStatus ? android.R.color.transparent : R.color.colorLightRed);
                return validationStatus;
            }
        });
        spinnerMarritalStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                RangeCategory rangeCategory;
                adapterView.getId();
                rangeCategory = (RangeCategory) adapterView.getSelectedItem();
                Spinner spinnerMaritalStatus = (Spinner) findViewById(R.id.spinMARITAL_STATUS);
                    maritalStatus=rangeCategory.DescriptionEn;
                if (rangeCategory.RangeCode.equals("Unmarried")) {
                    linearLayout433.setVisibility(View.GONE);
                    cardView_SpouseFirstName.setVisibility(View.GONE);
                    textView35.setVisibility(View.GONE);
                } else {
                    linearLayout433.setVisibility(View.VISIBLE);
                    cardView_SpouseFirstName.setVisibility(View.VISIBLE);
                    textView35.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        acspRelationship.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                RangeCategory rangeCategory;
                adapterView.getId();
                rangeCategory = (RangeCategory) adapterView.getSelectedItem();
                relationShipData=rangeCategory.DescriptionEn;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        chkTvTopup = findViewById(R.id.chkTopup);
        chkTvTopup.setChecked(false);
        chkTvTopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                svOldCase.setVisibility(chkTvTopup.isChecked() ? View.VISIBLE : View.GONE);
                svOldCase.setQuery("", false);
                tietAadharId.setEnabled(true);
                borrower.OldCaseCode = null;

            }
        });

        tietAadharId = findViewById(R.id.tietAadhar);
        tietAadharId.addTextChangedListener(new MyTextWatcher(tietAadharId) {
            @Override
            public void validate(EditText editText, String text) {
                String aadharId = editText.getText().toString();

                if (aadharId.trim().length()==12 && Verhoeff.validateVerhoeff(aadharId)){
                    ProgressDialog progressBar = new ProgressDialog(ActivityBorrowerKyc.this);
                    progressBar.setCancelable(false);//you can cancel it by pressing back button.
                    progressBar.setMessage(" Please wait...");
                    progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressBar.show();

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
                    Call<DeDupeResponse> call=apiInterface.checkAdharDeDupe(aadharId.trim());
                    call.enqueue(new Callback<DeDupeResponse>() {
                        @Override
                        public void onResponse(Call<DeDupeResponse> call, Response<DeDupeResponse> response) {
                            progressBar.dismiss();
                            DeDupeResponse deDupeResponse= response.body();
                            String msg="";
                            int accountOpened=0;
                            if (deDupeResponse.getData().size()>0){
                                for (DeDupeData deDupeData:deDupeResponse.getData()){
                                    msg+=("=>Loan with ficode="+deDupeData.getCode()+" and creator="+deDupeData.getCreator()+" (CaseCode: "+deDupeData.getSmCode()+"), for a duration of "+deDupeData.getLoanDuration()+" months starting from "+deDupeData.getDtFin().split("T")[0]+", with a sanctioned amount of ₹"+deDupeData.getSanctionedAmt()+" and "+deDupeData.getCountEmi()+" EMIs have been paid.\n");
                                    if ((deDupeData.getSanctionedAmt()*0.4)>Integer.parseInt(deDupeData.getEmiAmount()))
                                    {
                                        lastCaseCode=deDupeData.getSmCode();
                                        lastLoanAmt=deDupeData.getSanctionedAmt()+"";
                                        lastDuration=deDupeData.getLoanDuration();
                                        lastPaidEmi=deDupeData.getCountEmi();
                                        accountOpened=1;
                                    }
                                }
                                if (accountOpened==1){


                                    /*msg+=("\n\nSorry!! The case will not proceed with this Aadhaar number.");
                                    AlertDialog.Builder builder = new AlertDialog.Builder(ActivityBorrowerKyc.this);
                                    builder.setTitle("Previous Loan Details");
                                    builder.setMessage(msg);
                                    builder.setCancelable(false);
                                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            finish();
                                        }
                                    });
                                    AlertDialog dialog = builder.create();*/

                                   // dialog.show();
                                }

                            }

                        }

                        @Override
                        public void onFailure(Call<DeDupeResponse> call, Throwable t) {
                            progressBar.dismiss();

                        }
                    });

                }else{
                    tietAadharId.setError("Please enter correct aadhaar id");
                }
            }
        });


        tietAge = findViewById(R.id.tietAge);
        tietAge.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    ((TextInputEditText) v).addTextChangedListener(ageTextWatcher);
                } else {
                    ((TextInputEditText) v).removeTextChangedListener(ageTextWatcher);
                }
            }
        });
        tietAge.addTextChangedListener(new MyTextWatcher(tietAge) {
            @Override
            public void validate(EditText editText, String text) {
                validateControls(editText, text);
            }
        });


        tietGuardian.addTextChangedListener(new MyTextWatcher(tietGuardian) {
            @Override
            public void validate(EditText editText, String text) {
                validateControls(editText, text);
            }
        });
        tietAddress1 = findViewById(R.id.tietAddress1);
        tietAddress1.addTextChangedListener(new MyTextWatcher(tietAddress1) {
            @Override
            public void validate(EditText editText, String text) {
                validateControls(editText, text);
            }
        });
        tietAddress2 = findViewById(R.id.tietAddress2);
        tietAddress3 = findViewById(R.id.tietAddress3);
        tietAddress2.addTextChangedListener(new MyTextWatcher(tietAddress2) {
            @Override
            public void validate(EditText editText, String text) {
                validateControls(editText, text);
            }
        });

        tietAddress3.addTextChangedListener(new MyTextWatcher(tietAddress3) {
            @Override
            public void validate(EditText editText, String text) {
                validateControls(editText, text);
            }
        });


        tietCity = findViewById(R.id.tietCity);
        tietCity.addTextChangedListener(new MyTextWatcher(tietCity) {
            @Override
            public void validate(EditText editText, String text) {
                validateControls(editText, text);
            }
        });
        tietPinCode = findViewById(R.id.tietPinCode);
        tietPinCode.addTextChangedListener(new MyTextWatcher(tietPinCode) {
            @Override
            public void validate(EditText editText, String text) {
                validateControls(editText, text);
            }
        });
        imgViewAadharPhoto= ((ImageView) findViewById(R.id.imgViewAadharPhoto));

        imgViewScanQR = (ImageView) findViewById(R.id.imgViewScanQR);
        imgViewScanQR.setOnClickListener(this);
        imgViewScanQR.setVisibility(View.VISIBLE);
        imgViewAadharPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                profileImageClick=1;

                ImagePicker.with(ActivityBorrowerKyc.this)
                        .cameraOnly()
                        .start(REQUEST_TAKE_PROFILE_PHOTO);
            }
        });
        ageTextWatcher = new TextWatcher() {
            String dtString;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() > 0)
                    dtString = DateUtils.getDobFrmAge(Integer.parseInt(s.toString()));
                myCalendar.setTime(DateUtils.getParsedDate(dtString, "dd-MMM-yyyy"));
                tietDob.setText(dtString);
            }
        };

        dateSetListner = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                tietAge.clearFocus();
                myCalendar.set(year, monthOfYear, dayOfMonth);
                tietAge.setText(String.valueOf(DateUtils.getAge(myCalendar)));
                tietDob.setText(DateUtils.getFormatedDate(myCalendar.getTime(), "dd-MMM-yyyy"));
            }
        };
        imgViewCal.setOnClickListener(this);
        tietMobile = findViewById(R.id.tietMobile);
        tietMobile.addTextChangedListener(new MyTextWatcher(tietMobile) {
            @Override
            public void validate(EditText editText, String text) {
                mobileNuberCheckSign.setBackground(getResources().getDrawable(R.drawable.check_sign_ic));
                mobileNuberCheckSign.setEnabled(true);
                otpVerified=0;
            }
        });

        tietPanNo = findViewById(R.id.tietPAN);

        tietVoterId = findViewById(R.id.tietVoter);

        tietDrivingLic = findViewById(R.id.tietDrivingLlicense);
        tietDrivingLic.addTextChangedListener(new MyTextWatcher(tietDrivingLic) {
            @Override
            public void validate(EditText editText, String text) {
                validateControls(editText, text);
            }
        });

        textViewFiDetails = ((TextView) findViewById(R.id.tv_fi_details));
        textViewFiDetails.setVisibility(View.GONE);


        acspAadharState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                RangeCategory rangeCategory= (RangeCategory) adapterView.getSelectedItem();
                Log.d("TAG", "onItemSelected: "+rangeCategory.DescriptionEn);
                stateData=rangeCategory.DescriptionEn;
                villageData=null;
                districtDat=null;
                subDistrictData=null;
                cityData=null;
                txtVDistrictName.setText("");
                txtVillageName.setText("");
                txtSubDistictName.setText("");
                txtCityName.setText("");
                villageDataList.clear();
                districtDataList.clear();
                cityDataList.clear();
                subDistrictDataList.clear();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        acspGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                RangeCategory rangeCategory= (RangeCategory) adapterView.getSelectedItem();
                Log.d("TAG", "onItemSelected: "+rangeCategory.DescriptionEn);
                genderData=rangeCategory.DescriptionEn;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        panCheckSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tietPanNo.getText().toString().trim().length() == 10) {
                    cardValidate(tietPanNo.getText().toString().trim(),"pancard","","");
                } else {
                    tilPAN_Name.setVisibility(View.GONE);

                    tietPanNo.setError("Enter PAN");
                }
            }
        });
        dLCheckSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tietDob.getText().toString().trim().length()>9){
                    try {
                        String Dob=formatDate(tietDob.getText().toString().trim(),"dd-MMM-yyyy","yyyy-MM-dd");
                        if (tietDrivingLic.getText().toString().trim().length() >5) {
                            Log.d("TAG", "onClick: "+tietDob.getText().toString()+"/////"+Dob);
                            cardValidate(tietDrivingLic.getText().toString().trim(),"drivinglicense","",Dob);
                        } else {
                            tilDL_Name.setVisibility(View.GONE);
                            tietDrivingLic.setError("Enter Driving License");
                        }
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }

                }else {
                    Toast.makeText(activity, "Please enter Date of Birth", Toast.LENGTH_SHORT).show();
                }

            }
        });
        voterIdCheckSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tietVoterId.getText().toString().trim().length() > 5) {
                    cardValidate(tietVoterId.getText().toString().trim(),"voterid","","");
                }else {
                    tilVoterId_Name.setVisibility(View.GONE);
                    tietVoterId.setError("Enter Voter Id");
                }
            }
        });
        mobileNuberCheckSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tietMobile.getText().toString().trim().length()!=10){
                    tietMobile.setError("Please enter correct mobile number!!");
                }else{
                    getMobileOTP(tietMobile.getText().toString().trim());
                }
            }
        });

        tietPanNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                panCheckSign.setEnabled(true);
                panCheckSign.setBackground(getResources().getDrawable(R.drawable.check_sign_ic));
                tilPAN_Name.setText("");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        tietDrivingLic.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                dLCheckSign.setEnabled(true);
                dLCheckSign.setBackground(getResources().getDrawable(R.drawable.check_sign_ic));
                tilDL_Name.setText("");

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });




        tietVoterId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                voterIdCheckSign.setEnabled(true);
                voterIdCheckSign.setBackground(getResources().getDrawable(R.drawable.check_sign_ic));
                tilVoterId_Name.setText("");

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    private void getMobileOTP(String mobileNumber) {

        ProgressDialog progressBar = new ProgressDialog(this);
        progressBar.setCancelable(false);//you can cancel it by pressing back button.
        progressBar.setMessage(" Please wait...");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.show();

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

        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityBorrowerKyc.this);
        View dialogView = getLayoutInflater().inflate(R.layout.otp_dialog_layout, null);
        builder.setView(dialogView);

        AlertDialog dialogs = builder.create();
        dialogs.setCanceledOnTouchOutside(false);
        dialogs.setCancelable(false);
        EditText otpEditText = dialogView.findViewById(R.id.editTextOTP);
        Button submitButton = dialogView.findViewById(R.id.buttonSubmit);
        ImageButton crossButtonDialog = dialogView.findViewById(R.id.crossButtonDialog);
        crossButtonDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.dismiss();
                dialogs.dismiss();
            }
        });
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (otpEditText.getText().toString().trim().length()!=6){
                    otpEditText.setError("Wrong OTP");
                }else{
                    Call<JsonObject> call=apiInterface.verifyOTP(mobileNumber,otpEditText.getText().toString().trim());
                    call.enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                            if (response.isSuccessful()){
                                if (response.body().get("message").getAsString().equals("verified OTP")){
                                    mobileNuberCheckSign.setBackground(getResources().getDrawable(R.drawable.check_sign_ic_green));
                                    mobileNuberCheckSign.setEnabled(false);
                                    Toast.makeText(ActivityBorrowerKyc.this, "OTP verified", Toast.LENGTH_SHORT).show();
                                    otpVerified=1;
                                    progressBar.dismiss();
                                    dialogs.dismiss();

                                }else{
                                    otpEditText.setError("Wrong OTP");
                                    Toast.makeText(ActivityBorrowerKyc.this, "OTP Not verified", Toast.LENGTH_SHORT).show();
                                    mobileNuberCheckSign.setBackground(getResources().getDrawable(R.drawable.check_sign_ic));
                                    mobileNuberCheckSign.setEnabled(true);
                                   // dialogs.dismiss();
                                }
                            }else{
                                otpEditText.setError("Wrong OTP");
                                Toast.makeText(ActivityBorrowerKyc.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                                mobileNuberCheckSign.setBackground(getResources().getDrawable(R.drawable.check_sign_ic));
                                mobileNuberCheckSign.setEnabled(true);
                            }
                        }

                        @Override
                        public void onFailure(Call<JsonObject> call, Throwable t) {

                            Toast.makeText(ActivityBorrowerKyc.this, "Please try again!!", Toast.LENGTH_SHORT).show();
                            mobileNuberCheckSign.setBackground(getResources().getDrawable(R.drawable.check_sign_ic));
                            mobileNuberCheckSign.setEnabled(true);
                        }
                    });

                }
            }
        });




            JsonObject jsonObject=new JsonObject();
            jsonObject.addProperty("contentId","1007458689942092806");
            jsonObject.addProperty("username","paisalo.trans");
            jsonObject.addProperty("password","oDqLM");
            jsonObject.addProperty("unicode",false);
            jsonObject.addProperty("from","PAISAL");
            jsonObject.addProperty("to",mobileNumber);
            jsonObject.addProperty("text","Your OTP for validation of mobile number with Paisalo Digital Limited is {#otp#} https://www.paisalo.in");

        Call<JsonObject> call=apiInterface.getOtp(jsonObject);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()){
                    if(response.body().get("message").getAsString().contains("Successfully")){
                        dialogs.show();
                    }else{
                        Toast.makeText(ActivityBorrowerKyc.this, "Something went wrong, API Failure", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(ActivityBorrowerKyc.this, "Something went wrong "+t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });



    }


    public static String formatDate (String date, String initDateFormat, String endDateFormat) throws ParseException {

        Date initDate = new SimpleDateFormat(initDateFormat).parse(date);
        SimpleDateFormat formatter = new SimpleDateFormat(endDateFormat);
        String parsedDate = formatter.format(initDate);

        return parsedDate;
    }
    private void setDataToView(View v) {
        if (borrower.Gender != null) {
            if (Utils.setSpinnerPosition((AppCompatSpinner) v.findViewById(R.id.acspGender), borrower.Gender.charAt(0), true) < 0) {
                Utils.alert(this, "Please check Gender, Cannot accept this Aadhar for Loan Application");
                return;
            }
        }
//        tietAadharId.removeTextChangedListener(aadharTextChangeListner);
        tietAadharId.setText(borrower.aadharid);
//        tietAadharId.addTextChangedListener(aadharTextChangeListner);


        tietName.setText(borrower.getBorrowerName());

        tietAge.setText(String.valueOf(borrower.Age));
        Utils.setOnFocuseSelect(tietAge, "0");
        if (borrower.DOB == null) {
            tietDob.setText(DateUtils.getDobFrmAge(Integer.parseInt(tietAge.getText().toString())));
        } else {
            myCalendar.setTime(borrower.DOB);
            tietDob.setText(DateUtils.getFormatedDate(borrower.DOB, "dd-MMM-yyyy"));
        }

        tietGuardian.setText(borrower.getGurName());
        tietAddress1.setText(borrower.P_Add1);
        tietAddress2.setText(borrower.P_add2);
        tietAddress3.setText(borrower.P_add3);
        tietCity.setText(borrower.P_city);
        tietPinCode.setText(String.valueOf(borrower.p_pin));
        Utils.setOnFocuseSelect(tietPinCode, "0");



        if (borrower.RelationWBorrower != null) {
            Utils.setSpinnerPosition(acspRelationship, borrower.RelationWBorrower, false);
        }
        if (borrower.p_state != null) {
            Utils.setSpinnerPosition(acspAadharState, borrower.p_state);
        }


        if (borrower.isAadharVerified.equals("Q")) {
            // imgViewScanQR.setVisibility(View.GONE);
            //tietAadharId.setEnabled(false);
            tietName.setEnabled(false);
            if (Utils.NullIf(borrower.getGurName(), "").trim().length() > 0)
                tietGuardian.setEnabled(false);
            if (Utils.NullIf(borrower.Age, 0) > 0) {
                tietAge.setEnabled(false);
                tietDob.setEnabled(false);
            }
            acspGender.setEnabled(false);
//            acspAadharState.setEnabled(false);
            if (borrower.P_Add1.trim().length() > 0) tietAddress1.setEnabled(false);
            if (Utils.NullIf(borrower.P_add2.trim(), "").trim().length() > 2)
                tietAddress2.setEnabled(false);
            if (Utils.NullIf(borrower.P_add3.trim(), "").trim().length() > 2)
                tietAddress3.setEnabled(false);
            if (Utils.NullIf(borrower.P_city, "").trim().length() > 0) tietCity.setEnabled(false);
            if (Utils.NullIf(borrower.p_pin, 0) > 0) tietPinCode.setEnabled(false);
        }else{
            tietName.setEnabled(false);
            if (Utils.NullIf(borrower.getGurName(), "").trim().length() > 0)
                tietGuardian.setEnabled(false);
            if (Utils.NullIf(borrower.Age, 0) > 0) {
                tietAge.setEnabled(false);
                tietDob.setEnabled(false);
            }
            acspGender.setEnabled(false);
//          acspAadharState.setEnabled(false);
            if (borrower.P_Add1.trim().length() > 0) tietAddress1.setEnabled(false);
            if (Utils.NullIf(borrower.P_add2, "").trim().length() > 2)
                tietAddress2.setEnabled(false);
            if (Utils.NullIf(borrower.P_add3, "").trim().length() > 2)
                tietAddress3.setEnabled(false);
            if (Utils.NullIf(borrower.P_city, "").trim().length() > 0) tietCity.setEnabled(false);
            if (Utils.NullIf(borrower.p_pin, 0) > 0) tietPinCode.setEnabled(false);

        }  //sir call pick karo
        //showPicture();
        tietMobile.setText(borrower.P_ph3);
        tietPanNo.setText(borrower.PanNO);
        tietVoterId.setText(borrower.voterid);
        tietDrivingLic.setText(borrower.drivinglic);


        if (borrower.Code > 0) {
            textViewFiDetails.setVisibility(View.VISIBLE);
            textViewFiDetails.setText(borrower.Creator + " / " + borrower.Code);
            tietAadharId.setEnabled(false);
            imgViewScanQR.setVisibility(View.GONE);
            showSubmitBorrowerMenuItem = false;
            invalidateOptionsMenu();
//            showScanDocs();
        }
    }

    private void getDataFromView(View v) {
        GpsTracker gpsTracker=new GpsTracker(ActivityBorrowerKyc.this);
        borrower.aadharid = Utils.getNotNullText(tietAadharId);
        borrower.setNames(Utils.getNotNullText(tietName));
        borrower.Age = Utils.getNotNullInt(tietAge);
        borrower.DOB = myCalendar.getTime();
        borrower.setGuardianNames(Utils.getNotNullText(tietGuardian).replace("S/O:","").replace("D/O:","").replace("W/O:","").replace("C/O:","").replace("S/O","").replace("D/O","").replace("W/O","").replace("C/O","").replace("\"",""));
        borrower.P_Add1 = Utils.getNotNullText(tietAddress1);
        borrower.P_add2 = Utils.getNotNullText(tietAddress2);
        borrower.P_add3 = Utils.getNotNullText(tietAddress3);
        borrower.P_city = Utils.getNotNullText(tietCity);
        borrower.p_pin = Utils.getNotNullInt(tietPinCode);
        Log.d("TAG", "getDataFromView: "+gpsTracker.getLongitude()+"////"+gpsTracker.getLatitude());
        borrower.Latitude= (float) gpsTracker.getLatitude();
        borrower.Longitude= (float) gpsTracker.getLongitude();
        borrower.Gender = ((RangeCategory) acspGender.getSelectedItem()).RangeCode.substring(0, 1);
        borrower.p_state = ((RangeCategory) acspAadharState.getSelectedItem()).RangeCode;
        borrower.P_ph3 = Utils.getNotNullText(tietMobile);
        borrower.PanNO = Utils.getNotNullText(tietPanNo);
        borrower.drivinglic = Utils.getNotNullText(tietDrivingLic);
        borrower.voterid = Utils.getNotNullText(tietVoterId);
        borrower.IsNameVerify=isNameMatched;
        borrower.isAdhaarEntry=isAdhaarEntry;
        borrower.isMarried = Utils.getSpinnerStringValue((Spinner) v.findViewById(R.id.spinLoanAppPersonalMarritalStatus));
        borrowerExtra.save();
        borrower.fiExtra=null;
        borrower.BankName= bankName;
        borrower.save();

        editor.clear();
        editor.apply();
        editor.putString("Adhaar",tietAadharId.getText().toString());
        editor.putString("Name", Utils.getNotNullText(tietName));
        editor.putString("Age", String.valueOf(Utils.getNotNullInt(tietAge)));
        editor.putString("Gur", Utils.getNotNullText(tietGuardian));
        editor.putString("Address", Utils.getNotNullText(tietAddress1)+Utils.getNotNullText(tietAddress2)+Utils.getNotNullText(tietAddress3));
        editor.putString("City", Utils.getNotNullText(tietCity));
        editor.putString("PIN", tietPinCode.getText().toString());
        editor.putString("LoanAmount", String.valueOf(borrower.Loan_Amt));
        editor.putString("State",stateData);
        editor.putString("Mobile",tietMobile.getText().toString());
        editor.putString("PAN",tietPanNo.getText().toString());
        editor.putString("Gender", genderData);
        editor.putString("Bank", "");
        editor.putString("Income", "");
        editor.putString("Expense", "");
        editor.putString("Duration", loanDurationData);
        editor.putString("VoterId", Utils.getNotNullText(tietVoterId));
        editor.putString("DOB", parseDateToddMMyyyy(tietDob.getText().toString()));
        editor.putString("LoanReason", borrower.Loan_Reason);
        editor.apply();

    }
    public String parseDateToddMMyyyy(String time) {
        String inputPattern = "dd-MMM-yyyy";
        String outputPattern = "dd-MM-yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }




    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgViewScanQR:
                IntentIntegrator scanIntegrator = new IntentIntegrator(this);
                scanIntegrator.setOrientationLocked(false);
                scanIntegrator.initiateScan(Collections.singleton("QR_CODE"));
                break;
            /*
            case R.id.btnSubmitKyc:
                updateBorrower();
                break;
            case R.id.btnCancel:
                finish();
                break;
            case R.id.btnCapturePhoto:
                launchScanning();
                break;
                */
            case R.id.imgViewCal:
                Date dob = DateUtils.getParsedDate(tietDob.getText().toString(), "dd-MMM-yyyy");
                try{
                    if (!dob.equals(null)){
                        myCalendar.setTime(dob);
                    }
                }catch (Exception e){

                }

                new DatePickerDialog(this, dateSetListner,
                        myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)
                ).show();
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("TAG", "onActivityResult: "+data+""+ requestCode);
        if (requestCode == IntentIntegrator.REQUEST_CODE) {
            IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            //Log.d("QR Scan","Executed");
            if (scanningResult != null) {
                //we have a result
                String scanContent = scanningResult.getContents();
                String scanFormat = scanningResult.getFormatName();
                Log.d("CheckXMLDATA3","AadharData:->" + scanContent);
                if (scanFormat != null) {
                    try {
                        setAadharContent(scanContent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            if (requestCode == REQUEST_TAKE_PROFILE_PHOTO && resultCode == RESULT_OK) {


                if (data != null) {
                    uriPicture = data.getData();
                    CropImage.activity(uriPicture)
                            .setGuidelines(CropImageView.Guidelines.ON)
                            .setAspectRatio(45, 52)
                            .start(  ActivityBorrowerKyc.this);
                } else {
                    Log.e("ImageData","Null");
                    Toast.makeText(this, "Image Data Null", Toast.LENGTH_SHORT).show();
                }

            }
            else if (requestCode == REQUEST_TAKE_PHOTO) {
                if (resultCode == RESULT_OK) {
                    if (documentPic.checklistid == 0) {
                        CropImage.activity(this.uriPicture)
                                .setGuidelines(CropImageView.Guidelines.ON)
                                .setAspectRatio(45, 52)
                                .start(this);
                    } else {
                        CropImage.activity(this.uriPicture)
                                .setGuidelines(CropImageView.Guidelines.ON)
                                .start(this);
                    }
                } else {
                    Utils.alert(this, "Could not take Picture");
                }
            }else if(requestCode == 1000){
                uriPicture = data.getData();
                if(uriPicture!=null){
                    CropImage.activity(uriPicture)
                            .setGuidelines(CropImageView.Guidelines.ON)
                            .setAllowFlipping(true)
                            .setMultiTouchEnabled(true)
                            .start(  ActivityBorrowerKyc.this);
                }
            }else if(requestCode == PAN_CARD_CAPTURE){
                uriPicture = data.getData();
                if(uriPicture!=null){
                    CropImage.activity(uriPicture)
                            .setGuidelines(CropImageView.Guidelines.ON)
                            .setAllowFlipping(true)
                            .setMultiTouchEnabled(true)
                            .start(  ActivityBorrowerKyc.this);
                }
            }
            if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && profileImageClick==0) {

                Exception error = null;
                if (documentPic!=null){
                    int maxDimentions = (documentPic.checklistid == 0 ? 300 : 1000);
                    Uri imageUri = CameraUtils.finaliseImageCropUri(resultCode, data, maxDimentions, error, false);
                    File tempCroppedImage = new File(imageUri.getPath());
                    Toast.makeText(activity, "document pic is not null", Toast.LENGTH_SHORT).show();
                    if (tempCroppedImage.length() > 100) {
                        if (borrower != null) {
                            (new File(this.uriPicture.getPath())).delete();
                            try {
                                File croppedImage = CameraUtils.moveCachedImage2Storage(this, tempCroppedImage, true,1);
                                documentPic.imagePath = croppedImage.getPath();
                                documentPic.save();
                                if (documentPic.checklistid == 1) putExifData(documentPic);
                                adapterRecViewListDocuments.updateList(getDocumentStore(borrower));
                                //borrower.setPicture(croppedImage.getPath());
                                //borrower.save();
                                //borrower_id = borrower.FiID;
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                }
                else{

                    Uri imageUri1 = CameraUtils.finaliseImageCropUri(resultCode, data, 1000, error, false);
                    File tempCroppedImage1 = new File(imageUri1.getPath());

                    try {
                        File croppedImage = CameraUtils.moveCachedImage2Storage(this, tempCroppedImage1, true,0);
                        Bitmap myBitmap = BitmapFactory.decodeFile(croppedImage.getAbsolutePath());
                        //ImageView myImage = (ImageView) findViewById(R.id.imageviewTest);
                        if (myBitmap!=null) {
                            // Toast.makeText(activity, "Bitmap: "+myBitmap+"", Toast.LENGTH_SHORT).show();
                            if (ImageType==1){
                                //adharFrontImg.setImageBitmap(myBitmap);
                                setDataOfAdhar(croppedImage,"aadharfront","aadhar");
                            }else if (ImageType==2){
                                //adharBackImg.setImageBitmap(myBitmap);
                                setDataOfAdhar(croppedImage,"aadharback","aadhar");
                            }else if (ImageType==3){
                                setDataOfAdhar(croppedImage,"pan","pan");
                            }

                        }else{
                            Toast.makeText(activity, "Image adata null", Toast.LENGTH_SHORT).show();
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }
            }
            else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && profileImageClick==1){

                Toast.makeText(activity, "Image in processing..", Toast.LENGTH_SHORT).show();
                Exception error = null;
                    Uri imageUri = CameraUtils.finaliseImageCropUri(resultCode, data, 300, error, false);
                    File tempCroppedImage = new File(imageUri.getPath());
                    Log.d("TAG", "onActivityResult: "+tempCroppedImage.length());
                if (tempCroppedImage.length() > 100) {
                    if (borrower != null) {
                        (new File(this.uriPicture.getPath())).delete();
                        try {
                            File croppedImage = CameraUtils.moveCachedImage2Storage(this, tempCroppedImage, true,0);
                            borrower.setPicture(croppedImage.getPath());
                            borrower.Oth_Prop_Det = null;
                            borrower.save();
                            if (borrower.getPicture() != null && (new File(borrower.getPicture().getPath())).length() > 100) {
                                if (new File(borrower.getPicture().getPath()).length() != 0) {

                                    Bitmap myBitmap = BitmapFactory.decodeFile(new File(borrower.getPicture().getPath()).getAbsolutePath());

                                    //ImageView myImage = (ImageView) findViewById(R.id.imageviewTest);

                                    if (myBitmap != null) {
                                        imgViewAadharPhoto.setImageBitmap(myBitmap);
                                        Log.e("CHeckingmyBitmap22",myBitmap+"");
                                    } else {
                                        Toast.makeText(this, "Bitmap Null", Toast.LENGTH_SHORT).show();
                                        Log.e("BitmapImage", "Null");
                                    }
                                } else {
                                    Toast.makeText(this, "Filepath Empty", Toast.LENGTH_SHORT).show();

                                }
                                  }


                        } catch (IOException e) {
                            Log.d("TAG", "onActivityResult: "+e.getMessage());
                            e.printStackTrace();
                        }
                    }else {
                        Toast.makeText(this, "Borrower is null", Toast.LENGTH_SHORT).show();
                    }
                }
                    profileImageClick=0;

            }

        }
    }

    private void showPicture(Borrower borrower) {


//        Log.e("CHeckingNewCondition",borrower.getPictureborrower()+"");
        if (borrower != null) {

            Log.d("TAG", "showPicture: "+borrower.toString());
//            if (borrower.getPictureborrower()!=null){
//                imageView.setImageBitmap(StringToBitmap(borrower.getPictureborrower()));
//            }

            //Log.d("BorrowerImagePath",borrower.getPicture().getPath());
            if (borrower.getPicture() != null && (new File(borrower.getPicture().getPath())).length() > 100) {
                Log.d("BorrowerImagePath1",borrower.getPicture().getPath());
                Toast.makeText(this, "BorrowerPicture: " + borrower.getPicture().getPath() + "", Toast.LENGTH_SHORT).show();

                setImagepath(new File(borrower.getPicture().getPath()));
                //imageView.setImageBitmap(StringToBitmap(borrower.getPictureborrower()));
                //Glide.with(activity).load(borrower.getPicture().getPath()).override(Target.SIZE_ORIGINAL, 300).into(imageView);
            }
        }
    }
    private void setImagepath(File file) {

//        File imgFile = new  File("/sdcard/Images/test_image.jpg");

//        customProgress.hideProgress();
        // Toast.makeText(this, "Checking File: "+file.getAbsolutePath()+"", Toast.LENGTH_SHORT).show();

        if (file.length() != 0) {

            Bitmap myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());

            //ImageView myImage = (ImageView) findViewById(R.id.imageviewTest);

            if (myBitmap != null) {
                imgViewAadharPhoto.setImageBitmap(myBitmap);
                Log.e("CHeckingmyBitmap22",myBitmap+"");
            } else {
                Toast.makeText(this, "Bitmap Null", Toast.LENGTH_SHORT).show();
                Log.e("BitmapImage", "Null");
            }
        } else {
            Toast.makeText(this, "Filepath Empty", Toast.LENGTH_SHORT).show();

        }
    }


    private void setDataOfAdhar(File croppedImage,String imageData,String type) {
        ProgressDialog progressBar = new ProgressDialog(this);
        progressBar.setCancelable(false);//you can cancel it by pressing back button.
        progressBar.setMessage("Data Fetching from "+type.toUpperCase()+" Please wait...");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.show();

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.connectTimeout(1, TimeUnit.MINUTES);
        httpClient.readTimeout(1,TimeUnit.MINUTES);
        httpClient.addInterceptor(logging);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SEILIGL.NEW_SERVERAPI)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);

        RequestBody surveyBody = RequestBody.create(MediaType.parse("*/*"), croppedImage);
        builder.addFormDataPart("file",croppedImage.getName(),surveyBody);
        RequestBody requestBody = builder.build();
        ApiInterface apiInterface=retrofit.create(ApiInterface.class);
        Call<JsonObject> call=apiInterface.getAdharDataByOCR(imageData,type,requestBody);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.d("TAG", "onResponsews: "+response.body());

                if (response.body()!=null){
                    if (response.body().get("data")!=null){
                        if (imageData.equals("aadharfront")){
                            if (response.body().get("data").getAsJsonArray().size()>0){
                                if (response.body().get("data").getAsJsonArray().get(0).getAsJsonObject().get("name").getAsString().trim().length()>2){

                                    String[] borrowerNames=response.body().get("data").getAsJsonArray().get(0).getAsJsonObject().get("name").getAsString().split(" ");
                                    switch (borrowerNames.length){
                                        case 1:
                                            borrower.Fname=borrowerNames[0];
                                            break;
                                        case 2:
                                            borrower.Fname=borrowerNames[0];
                                            borrower.Lname=borrowerNames[1];
                                            break;
                                        case 3:
                                            borrower.Fname=borrowerNames[0];
                                            borrower.Mname=borrowerNames[1];
                                            borrower.Lname=borrowerNames[2];
                                            break;
                                    }



                                    borrower.Gender=response.body().get("data").getAsJsonArray().get(0).getAsJsonObject().get("gender").getAsString().charAt(0)+"";
                                    borrower.aadharid=response.body().get("data").getAsJsonArray().get(0).getAsJsonObject().get("aadharno").getAsString().replace(" ","");

                                    Date date;
                                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                                    try {
                                        date = formatter.parse(response.body().get("data").getAsJsonArray().get(0).getAsJsonObject().get("dob").getAsString());
                                    } catch (ParseException e) {
                                        throw new RuntimeException(e);
                                    }
                                    Instant instant = null;
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                        instant = date.toInstant();
                                        ZonedDateTime zone = instant.atZone(ZoneId.systemDefault());
                                        LocalDate givenDate = zone.toLocalDate();

                                        Period period = Period.between(givenDate, LocalDate.now());

                                        borrower.Age = period.getYears();
                                        System.out.print(period.getYears()+" years "+period.getMonths()+" and "+period.getDays()+" days");
                                    }
                                    borrower.DOB=date;
                                    borrower.isAadharVerified="O";
                                    borrower.save();
                                    setDataToView(activity.findViewById(android.R.id.content).getRootView());

                                }else{
                                    Utils.alert(ActivityBorrowerKyc.this,"Please capture aadhaar front image!!");

                                    Toast.makeText(ActivityBorrowerKyc.this, "Please capture aadhaar front image!!", Toast.LENGTH_SHORT).show();
                                }
                            }else {
                                Utils.alert(ActivityBorrowerKyc.this,"Please capture aadhaar front image!!");

                            }

                            progressBar.dismiss();
                        }else if(imageData.equals("aadharback")){
                            if (response.body().get("data").getAsJsonArray().size()>0){
                                if (response.body().get("data").getAsJsonArray().get(0).getAsJsonObject().get("address1").getAsString().trim().length()>2) {

                                    borrower.p_pin = Integer.parseInt(response.body().get("data").getAsJsonArray().get(0).getAsJsonObject().get("pincode").getAsString().trim());
                                    JsonObject jsonObject=response.body().get("data").getAsJsonArray().get(0).getAsJsonObject();
                                    String fullAddress=jsonObject.get("address1").getAsString().trim()+jsonObject.get("address2").getAsString().trim()+jsonObject.get("address3").getAsString().trim()+jsonObject.get("address4").getAsString().trim();
                                    String[] arrOfAdd=fullAddress.split(",");
                                    String city=arrOfAdd[arrOfAdd.length-2];
                                    borrower.P_city=city;
//                                try {
//
//                                    borrower.P_city = response.body().get("data").getAsJsonArray().get(0).getAsJsonObject().get("address2").getAsString().split(",")[2].trim();
//                                } catch (Exception e) {
//                                    borrower.P_city = response.body().get("data").getAsJsonArray().get(0).getAsJsonObject().get("address3").getAsString().split(",")[2].trim();
//
//                                }
                                    if (response.body().get("data").getAsJsonArray().get(0).getAsJsonObject().get("address4").getAsString().length() > 1) {

                                        borrower.P_add3 = response.body().get("data").getAsJsonArray().get(0).getAsJsonObject().get("address3").getAsString().trim();
                                    }
                                    borrower.P_add2 = response.body().get("data").getAsJsonArray().get(0).getAsJsonObject().get("address2").getAsString();

                                    String[] address1 = response.body().get("data").getAsJsonArray().get(0).getAsJsonObject().get("address1").getAsString().split(",");
                                    for (int i = 0; i < address1.length; i++) {
                                        if (address1[i].toUpperCase().contains("S/O") || address1[i].toUpperCase().contains("D/O") || address1[i].toUpperCase().contains("W/O")){
                                            borrower.setGuardianNames(address1[i].replace("S/O:","").replace("D/O:","").replace("W/O:","").replace("C/O:","").replace("S/O","").replace("D/O","").replace("W/O","").replace("C/O","").replace("\"",""));
                                            if (address1[i].toUpperCase().contains("S/O") || address1[i].toUpperCase().contains("D/O")){
                                                String[] fatherName=address1[i].split(" ");
                                                switch (fatherName.length){
                                                    case 2:
                                                        tietFatherFName.setText(fatherName[1]);
                                                        break;
                                                    case 3:
                                                        tietFatherFName.setText(fatherName[1]);
                                                        tietFatherLName.setText(fatherName[2]);

                                                        break;
                                                    case 4:
                                                        tietFatherFName.setText(fatherName[1]);
                                                        tietFatherMName.setText(fatherName[2]);
                                                        tietFatherLName.setText(fatherName[3]);
                                                        break;
                                                    default:
                                                        tietFatherFName.setText(response.body().get("data").getAsJsonArray().get(0).getAsJsonObject().get("address1").getAsString().split(",")[0].toUpperCase().replace("S/O","").replace("S/O:","").replace("D/O","").replace("D/O:","").replace("'",""));
                                                        break;
                                                }


                                            }else{
                                                if (address1[i].startsWith("W/O")){
                                                    String[] spouseName=address1[i].split(" ");
                                                    switch (spouseName.length){
                                                        case 2:
                                                            tietSpouseFName.setText(spouseName[1]);
                                                            break;
                                                        case 3:
                                                            tietSpouseFName.setText(spouseName[1]);
                                                            tietSpouseLName.setText(spouseName[2]);

                                                            break;
                                                        case 4:
                                                            tietSpouseFName.setText(spouseName[1]);
                                                            tietSpouseMName.setText(spouseName[2]);
                                                            tietSpouseLName.setText(spouseName[3]);
                                                            break;
                                                        default:
                                                            tietSpouseFName.setText(address1[i].toUpperCase().replace("W/O","").replace("W/O:","").replace("C/O:","").replace("\"",""));
                                                            break;
                                                    }

                                                }
                                            }
                                            continue;
                                        }
                                        borrower.P_Add1 = borrower.P_Add1 + address1[i];

//                                        if (containsOnlyAllowedCharacters(borrower.P_Add1)){
//                                            borrower.P_Add1="";
//                                            tietAddress1.setEnabled(true);
//                                        }
//
//
//
//                                        if (containsOnlyAllowedCharacters(borrower.P_add2)){
//                                            borrower.P_add2="";
//                                            tietAddress2.setEnabled(true);
//                                        }
//
//                                        if (containsOnlyAllowedCharacters(borrower.P_add3)){
//                                            borrower.P_add3="";
//                                            tietAddress3.setEnabled(true);
//                                        }
                                        /* ----------Add 15-12-23----------------------------------*/
                                        if (address1[i].startsWith("W/O")){
                                            Utils.setSpinnerPosition(spinnerMarritalStatus, "Married", false);
                                            String[] spouseName=address1[i].split(" ");
                                            switch (spouseName.length){
                                                case 2:
                                                    tietSpouseFName.setText(spouseName[1]);
                                                    break;
                                                case 3:
                                                    tietSpouseFName.setText(spouseName[1]);
                                                    tietSpouseLName.setText(spouseName[2]);

                                                    break;
                                                case 4:
                                                    tietSpouseFName.setText(spouseName[1]);
                                                    tietSpouseMName.setText(spouseName[2]);
                                                    tietSpouseLName.setText(spouseName[3]);
                                                    break;
                                                default:
                                                    tietSpouseFName.setText(address1[i].toUpperCase().replace("S/O","").replace("D/O","").replace("W/O","").replace("C/O","").replace("\"",""));
                                                    break;
                                            }

                                        }if (address1[i].startsWith("S/O") || address1[i].startsWith("D/O")){
                                            String[] fatherName=address1[i].split(" ");
                                            switch (fatherName.length){
                                                case 2:
                                                    tietFatherFName.setText(fatherName[1]);
                                                    break;
                                                case 3:
                                                    tietFatherFName.setText(fatherName[1]);
                                                    tietFatherLName.setText(fatherName[2]);

                                                    break;
                                                case 4:
                                                    tietFatherFName.setText(fatherName[1]);
                                                    tietFatherMName.setText(fatherName[2]);
                                                    tietFatherLName.setText(fatherName[3]);
                                                    break;
                                                default:
                                                    tietFatherFName.setText(response.body().get("data").getAsJsonArray().get(0).getAsJsonObject().get("address1").getAsString().split(",")[0].toUpperCase().replace("S/O","").replace("D/O","").replace("W/O","").replace("C/O","").replace("\"",""));
                                                    break;
                                            }
                                        }


                                    }
                                    borrower.p_state = AadharUtils.getStateCode(response.body().get("data").getAsJsonArray().get(0).getAsJsonObject().get("state").getAsString().trim());
                                    borrower.isAadharVerified="O";
                                    borrower.save();
                                    setDataToViewForAdharBack(activity.findViewById(android.R.id.content).getRootView());
                                }else{
                                    Utils.alert(ActivityBorrowerKyc.this,"Please capture aadhaar back image!!");
                                    //   Toast.makeText(ActivityBorrowerKyc.this, "Please capture aadhaar back image!!", Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                Utils.alert(ActivityBorrowerKyc.this,"Please capture aadhaar back image again!!");

                                Toast.makeText(ActivityBorrowerKyc.this, "Please capture aadhaar back image again!!", Toast.LENGTH_SHORT).show();
                            }

                            progressBar.dismiss();
                        }else if(imageData.equals("pan")){
                            if (response.body().get("data").getAsJsonArray().size()>0){
                                if (response.body().get("data").getAsJsonArray().get(0).getAsJsonObject().get("panno").getAsString().length()>1 && !response.body().get("data").getAsJsonArray().get(0).getAsJsonObject().get("panno").getAsString().equals("NA")) {
                                    isgetPanwithOCR=true;
                                    borrower.PanNO = response.body().get("data").getAsJsonArray().get(0).getAsJsonObject().get("panno").getAsString();
                                    borrower.isAadharVerified="O";
                                    panaadharDOBMatched=true;
                                    borrower.save();
                                    setDataToView(activity.findViewById(android.R.id.content).getRootView());

                                }else {
                                    Toast.makeText(ActivityBorrowerKyc.this, "Please capture PAN Card on behalf sample", Toast.LENGTH_SHORT).show();
                                    Utils.alert(ActivityBorrowerKyc.this,"Please capture PAN image again!!");

                                }
                            }else{
                                Utils.alert(ActivityBorrowerKyc.this,"Please capture PAN image again!!");
                                Toast.makeText(ActivityBorrowerKyc.this, "Please capture PAN image again!!", Toast.LENGTH_SHORT).show();
                            }
                            progressBar.dismiss();
                        }
                        //   borrower.setNames(response.body().get("data").getAsJsonArray().get(0).getAsJsonObject().get("name").getAsString());
                    }
                }
            }
            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d("TAG", "onFailure: "+t.getMessage());
            }
        });
    }

    public static boolean containsOnlyAllowedCharacters(String input) {
        String A = "[,:./()]";
        String allowedCharactersRegex = "[a-zA-Z0-9 ,:./()\\-]+";

        return ( input.matches(allowedCharactersRegex) && !(input.startsWith(".") ||input.startsWith(":") || input.startsWith("/") ||input.startsWith("-") ||input.startsWith(",")));

    }
    private boolean containsNumbers(String text) {
        return text != null && text.matches("^[a-zA-Z ]+\\.?[a-zA-Z ]*$");

    }

    private void setAadharContent(String aadharDataString) throws Exception {
//        try {
        Log.d("CheckXMLDATA2", "AadharData:->" + aadharDataString);
        if (aadharDataString.toUpperCase().contains("XML")) {

            Log.d("XML printing", "AadharData:->" + aadharDataString);
            //AadharData aadharData = AadharUtils.getAadhar(aadharDataString);
            AadharData aadharData = AadharUtils.getAadhar(AadharUtils.ParseAadhar(aadharDataString));

            Log.d("TAG", "setAadharContent: "+aadharData.isAadharVerified);
            if (aadharData.AadharId != null) {

                Borrower borrower1 = Borrower.getBorrower(aadharData.AadharId);
                if (borrower1 != null) {
                    borrower = borrower1;
                    setDataToView(activity.findViewById(android.R.id.content).getRootView());
                    tietAadharId.setEnabled(false);
                    return;
                }
                Log.d("TAG", "setAadharContent: done1");
                if (chkTvTopup.isChecked()) {
                    if (tietAadharId.getText().toString().equals(aadharData.AadharId)) {
                        Utils.alert(this, "Aadhar ID did not match with Topup Case");
                        return;
                    }
                }
                borrower.aadharid = aadharData.AadharId;
                Log.d("TAG", "setAadharContent: done2");

            }

            if (aadharData.Address2 == null) {
                aadharData.Address2 = aadharData.Address3;
                aadharData.Address3 = null;
            } else if (aadharData.Address2.trim().equals("")) {
                aadharData.Address2 = aadharData.Address3;
                aadharData.Address3 = null;
            }
            if (aadharData.Address1 == null) {
                aadharData.Address1 = aadharData.Address2;
                aadharData.Address2 = aadharData.Address3;
                aadharData.Address3 = null;
            } else if (aadharData.Address1.trim().equals("")) {
                aadharData.Address1 = aadharData.Address2;
                aadharData.Address2 = aadharData.Address3;
                aadharData.Address3 = null;
            }                    Log.d("TAG", "setAadharContent: done3");

            borrower.isAadharVerified = aadharData.isAadharVerified;
            borrower.setNames(aadharData.Name);
            borrower.DOB = aadharData.DOB;
            borrower.Age = aadharData.Age;
            borrower.Gender = aadharData.Gender;
            borrower.setGuardianNames(aadharData.GurName==null?"":aadharData.GurName.replace("S/O:","").replace("D/O:","").replace("W/O:","").replace("C/O:","").replace("S/O","").replace("D/O","").replace("W/O","").replace("C/O","").replace("\"",""));
            borrower.P_city = aadharData.City;
            borrower.p_pin = aadharData.Pin;
            borrower.P_Add1 = aadharData.Address1;
            borrower.P_add2 = aadharData.Address2;
            borrower.P_add3 = aadharData.Address3;
            borrower.p_state = AadharUtils.getStateCode(aadharData.State);
            setDataToView(this.findViewById(android.R.id.content).getRootView());
            validateBorrower();
            tietAge.setEnabled(false);
            tietDob.setEnabled(false);
            aadharNumberentry=true;

        }  else {

            final BigInteger bigIntScanData = new BigInteger(aadharDataString, 10);
            Log.e("testbigin======", "AadharData:->" + bigIntScanData);
            // 2. Convert BigInt to Byte Array
            final byte byteScanData[] = bigIntScanData.toByteArray();

            // 3. Decompress Byte Array
            final byte[] decompByteScanData = decompressData(byteScanData);

            // 4. Split the byte array using delimiter
            List<byte[]> parts = separateData(decompByteScanData);
            // Throw error if there are no parts
            Log.e("Parts======11======> ", "part data =====> " + parts.toString());
            decodeData(parts);
            decodeSignature(decompByteScanData);
            decodeMobileEmail(decompByteScanData);
            aadharNumberentry=true;
        }
//            } catch(Exception ex) {
//            Utils.alert(this, ex.getMessage());
//        }

    }

    private void setDataToViewForAdharBack(View v) {
        if (borrower.Gender != null) {
            if (Utils.setSpinnerPosition((AppCompatSpinner) v.findViewById(R.id.acspGender), borrower.Gender.charAt(0), true) < 0) {
                Utils.alert(this, "Please check Gender, Cannot accept this Aadhar for Loan Application");
                return;
            }
        }
//        tietAadharId.removeTextChangedListener(aadharTextChangeListner);
        tietAadharId.setText(borrower.aadharid);
//        tietAadharId.addTextChangedListener(aadharTextChangeListner);


        tietName.setText(borrower.getBorrowerName());

        tietAge.setText(String.valueOf(borrower.Age));
        Utils.setOnFocuseSelect(tietAge, "0");
        if (borrower.DOB == null) {
            tietDob.setText(DateUtils.getDobFrmAge(Integer.parseInt(tietAge.getText().toString())));
        } else {
            myCalendar.setTime(borrower.DOB);
            tietDob.setText(DateUtils.getFormatedDate(borrower.DOB, "dd-MMM-yyyy"));
        }

        tietGuardian.setText(borrower.getGurName());
        tietAddress1.setText(borrower.P_Add1);
        tietAddress2.setText(borrower.P_add2);
        tietAddress3.setText(borrower.P_add3);
        tietCity.setText(borrower.P_city);
        tietPinCode.setText(String.valueOf(borrower.p_pin));
        Utils.setOnFocuseSelect(tietPinCode, "0");



        if (borrower.RelationWBorrower != null) {
            Utils.setSpinnerPosition(acspRelationship, borrower.RelationWBorrower, false);
        }
        if (borrower.p_state != null) {
            Utils.setSpinnerPosition(acspAadharState, borrower.p_state);
        }


        if (borrower.isAadharVerified.equals("Q")) {
            // imgViewScanQR.setVisibility(View.GONE);
            //tietAadharId.setEnabled(false);
            //  tietName.setEnabled(false);
            if (Utils.NullIf(borrower.getGurName(), "").trim().length() > 0)
                //  tietGuardian.setEnabled(false);
                if (Utils.NullIf(borrower.Age, 0) > 0) {
                    //tietAge.setEnabled(false);
                    //tietDob.setEnabled(false);
                }
            //acspGender.setEnabled(false);
//            acspAadharState.setEnabled(false);
            if (borrower.P_Add1.trim().length() > 0) tietAddress1.setEnabled(false);
            if (Utils.NullIf(borrower.P_add2.trim(), "").trim().length() > 2)
                // tietAddress2.setEnabled(false);
                if (Utils.NullIf(borrower.P_add3.trim(), "").trim().length() > 2)
                    // tietAddress3.setEnabled(false);
                    if (Utils.NullIf(borrower.P_city, "").trim().length() > 0) tietCity.setEnabled(false);
            if (Utils.NullIf(borrower.p_pin, 0) > 0) tietPinCode.setEnabled(false);
        }else{
            // tietName.setEnabled(false);
            if (Utils.NullIf(borrower.getGurName(), "").trim().length() > 0)
                // tietGuardian.setEnabled(false);
                if (Utils.NullIf(borrower.Age, 0) > 0) {
                    //  tietAge.setEnabled(false);
                    // tietDob.setEnabled(false);
                }
            //acspGender.setEnabled(false);
//          acspAadharState.setEnabled(false);
            // if (borrower.P_Add1.trim().length() > 0) //tietAddress1.setEnabled(false);
            // if (Utils.NullIf(borrower.P_add2, "").trim().length() > 2)
            // tietAddress2.setEnabled(false);
            // if (Utils.NullIf(borrower.P_add3, "").trim().length() > 2)
            // tietAddress3.setEnabled(false);
            // if (Utils.NullIf(borrower.P_city, "").trim().length() > 0) //tietCity.setEnabled(false);
            // if (Utils.NullIf(borrower.p_pin, 0) > 0)
            //tietPinCode.setEnabled(false);

        }  //sir call pick karo
        //showPicture();
        tietMobile.setText(borrower.P_ph3);
        tietPanNo.setText(borrower.PanNO);
        tietVoterId.setText(borrower.voterid);
        tietDrivingLic.setText(borrower.drivinglic);


        if (borrower.Code > 0) {
            textViewFiDetails.setVisibility(View.VISIBLE);
            textViewFiDetails.setText(borrower.Creator + " / " + borrower.Code);
            // tietAadharId.setEnabled(false);
            imgViewScanQR.setVisibility(View.GONE);
            showSubmitBorrowerMenuItem = false;
            invalidateOptionsMenu();
//            showScanDocs();
        }
    }


    // 20/11/2022 ========================================
    protected byte[] decompressData(byte[] byteScanData) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream(byteScanData.length);
        ByteArrayInputStream bin = new ByteArrayInputStream(byteScanData);
        GZIPInputStream gis = null;

        try {
            gis = new GZIPInputStream(bin);
        } catch (IOException e) {
            Log.e("Exception", "Decompressing QRcode, Opening byte stream failed: " + e.toString());
            // throw new QrCodeException("Error in opening Gzip byte stream while decompressing QRcode",e);
        }

        int size = 0;
        byte[] buf = new byte[1024];
        while (size >= 0) {
            try {
                size = gis.read(buf,0,buf.length);
                if(size > 0){
                    bos.write(buf,0,size);
                }
            } catch (IOException e) {
                Log.e("Exception", "Decompressing QRcode, writing byte stream failed: " + e.toString());
                // throw new QrCodeException("Error in writing byte stream while decompressing QRcode",e);
            }
        }

        try {
            gis.close();
            bin.close();
        } catch (IOException e) {
            Log.e("Exception", "Decompressing QRcode, closing byte stream failed: " + e.toString());
            // throw new QrCodeException("Error in closing byte stream while decompressing QRcode",e);
        }

        return bos.toByteArray();
    }

    protected List<byte[]> separateData(byte[] source) {
        List<byte[]> separatedParts = new LinkedList<>();
        int begin = 0;

        for (int i = 0; i < source.length; i++) {
            if(source[i] == SEPARATOR_BYTE){
                // skip if first or last byte is separator
                if(i != 0 && i != (source.length -1)){
                    separatedParts.add(Arrays.copyOfRange(source, begin, i));
                }
                begin = i + 1;
                // check if we have got all the parts of text data
                if(separatedParts.size() == (VTC_INDEX + 1)){
                    // this is required to extract image data
                    imageStartIndex = begin;
                    break;
                }
            }
        }
        return separatedParts;
    }

    protected void decodeSignature(byte[] decompressedData){
        // extract 256 bytes from the end of the byte array
        int startIndex = decompressedData.length - 257,
                noOfBytes = 256;
        try {
            signature = new String (decompressedData,startIndex,noOfBytes,"ISO-8859-1");
            Log.e("signature======>","signature===> "+signature);
        } catch (UnsupportedEncodingException e) {
            Log.e("Exception", "Decoding Signature of QRcode, ISO-8859-1 not supported: " + e.toString());
            // throw new QrCodeException("Decoding Signature of QRcode, ISO-8859-1 not supported",e);
        }

    }


    protected void decodeData(List<byte[]> encodedData) throws ParseException{
        Iterator<byte[]> i = encodedData.iterator();
        decodedData = new ArrayList<String>();
        while(i.hasNext()){
            decodedData.add(new String(i.next(), StandardCharsets.ISO_8859_1));
        }
        // set the value of email/mobile present flag
        Log.e("Parts======2======> ","part data =====> "+decodedData.toString());
        //emailMobilePresent = Integer.parseInt(decodedData[0]);

        Log.d("part data =====> ","Parts======2======> "+decodedData.get(1));
        Log.d("part data =====> ","Parts======3======> "+decodedData.get(2));
        Log.d("part data =====> ","Parts======4======> "+decodedData.get(3));
        Log.d("part data =====> ","Parts======5======> "+decodedData.get(4));
        Log.d("part data =====> ","Parts======6======> "+decodedData.get(5));
        Log.d("part data =====> ","Parts======7======> "+decodedData.get(6));
        Log.d("part data =====> ","Parts======8======> "+decodedData.get(7));
        Log.d("part data =====> ","Parts======9======> "+decodedData.get(8));
        Log.d("part data =====> ","Parts======10=====> "+decodedData.get(9));
        Log.d("part data =====> ","Parts======11=====> "+decodedData.get(10));
        Log.d("part data =====> ","Parts======12=====> "+decodedData.get(11));
        Log.d("part data =====> ","Parts======13=====> "+decodedData.get(12));
        Log.d("part data =====> ","Parts======14=====> "+decodedData.get(13));
        Log.d("part data =====> ","Parts======15=====> "+decodedData.get(14));
        Log.d("part data =====> ","Parts======16=====> "+decodedData.get(15));

        int inc=0;
        Log.d("TAG", "decodeData: "+decodedData.get(0).startsWith("V")+"/////"+decodedData.get(0));
        if (decodedData.get(0).startsWith("V")){
            inc=0;
        }else {
            inc=1;
        }
        // populate decoded data
        SimpleDateFormat sdt = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            sdt = new SimpleDateFormat("dd-MM-YYYY");

        }
        Date result = null;
        try {
            result = sdt.parse(decodedData.get(4));


        } catch (ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Date date = formatter.parse(decodedData.get(4-inc));

        Instant instant = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            instant = date.toInstant();
            ZonedDateTime zone = instant.atZone(ZoneId.systemDefault());
            LocalDate givenDate = zone.toLocalDate();

            Period period = Period.between(givenDate, LocalDate.now());

            borrower.Age = period.getYears();
            System.out.print(period.getYears()+" years "+period.getMonths()+" and "+period.getDays()+" days");
        }
        borrower.isAadharVerified="Q";
        borrower.aadharid=decodedData.get(2-inc);
        borrower.Gender = decodedData.get(5-inc);
        if (decodedData.get(13-inc).equals("")||decodedData.get(13-inc).equals(null)){
        }else{
            borrower.p_state = AadharUtils.getStateCode(decodedData.get(13-inc));
        }
        if (decodedData.get(3-inc).equals("")||decodedData.get(3-inc).equals(null)){
            //tietName.setEnabled(true);
        }else{
            borrower.setNames(decodedData.get(3-inc));
        }
        if (decodedData.get(4-inc).equals("")||decodedData.get(4-inc).equals(null)){
            //tietDob.setEnabled(true);
        }else{
            borrower.DOB = date;
        }
        if (decodedData.get(6-inc).equals("")||decodedData.get(6-inc).equals(null)){

        }else{
            if (decodedData.get(6-inc).startsWith("S/O:") ||decodedData.get(6-inc).startsWith("D/O:") ||decodedData.get(6-inc).startsWith("W/O:")){
                borrower.setGuardianNames(decodedData.get(6-inc).split(":")[1].replace("S/O:","").replace("D/O:","").replace("W/O:","").replace("C/O:","").replace("S/O","").replace("D/O","").replace("\"","").replace("W/O","").replace("C/O","").trim());
                if (decodedData.get(6-inc).toUpperCase().startsWith("W/O:")){
                    Utils.setSpinnerPosition(spinnerMarritalStatus, "Married", false);
                    String[] spouseName=decodedData.get(6-inc).split(" ");
                    switch (spouseName.length){
                        case 2:
                            tietSpouseFName.setText(spouseName[1]);
                            break;
                        case 3:
                            tietSpouseFName.setText(spouseName[1]);
                            tietSpouseLName.setText(spouseName[2]);
                            break;
                        case 4:
                            tietSpouseFName.setText(spouseName[1]);
                            tietSpouseMName.setText(spouseName[2]);
                            tietSpouseLName.setText(spouseName[3]);
                            break;
                        default:
                            tietSpouseFName.setText(decodedData.get(6-inc));
                            break;
                    }

                }
            }else if (decodedData.get(6-inc).startsWith("S/O,") ||decodedData.get(6-inc).startsWith("D/O,") ||decodedData.get(6-inc).startsWith("W/O,")){
                borrower.setGuardianNames(decodedData.get(6-inc).split(",")[1].trim());
            }else{
                borrower.setGuardianNames(decodedData.get(6-inc).replace("S/O:","").replace("D/O:","").replace("W/O:","").replace("C/O:","").replace("C/O:","").replace("S/O","").replace("D/O","").replace("W/O","").replace("C/O","").replace("\"",""));

            }
            if (decodedData.get(6-inc).startsWith("S/O:") ||decodedData.get(6-inc).startsWith("D/O:") ||decodedData.get(6-inc).startsWith("W/O:")){
                borrower.setGuardianNames(decodedData.get(6-inc).split(":")[1].replace("S/O:","").replace("D/O:","").replace("W/O:","").replace("C/O:","").replace("S/O","").replace("D/O","").replace("\"","").replace("W/O","").replace("C/O","").trim());
                if (decodedData.get(6-inc).toUpperCase().startsWith("W/O:")){
                    Utils.setSpinnerPosition(spinnerMarritalStatus, "Married", false);
                    String[] spouseName=decodedData.get(6-inc).split(" ");
                    switch (spouseName.length){
                        case 2:
                            tietSpouseFName.setText(spouseName[1]);
                            break;
                        case 3:
                            tietSpouseFName.setText(spouseName[1]);
                            tietSpouseLName.setText(spouseName[2]);
                            break;
                        case 4:
                            tietSpouseFName.setText(spouseName[1]);
                            tietSpouseMName.setText(spouseName[2]);
                            tietSpouseLName.setText(spouseName[3]);
                            break;
                        default:
                            tietSpouseFName.setText(decodedData.get(6-inc));
                            break;
                    }

                }
            }else if (decodedData.get(6-inc).startsWith("S/O,") ||decodedData.get(6-inc).startsWith("D/O,") ||decodedData.get(6-inc).startsWith("W/O,")){
                borrower.setGuardianNames(decodedData.get(6-inc).split(",")[1].trim());
            }else{
                borrower.setGuardianNames(decodedData.get(6-inc).replace("S/O:","").replace("D/O:","").replace("W/O:","").replace("C/O:","").replace("S/O","").replace("D/O","").replace("W/O","").replace("C/O","").replace("\"",""));

            }
            if (decodedData.get(6-inc).startsWith("S/O") ||decodedData.get(6-inc).startsWith("D/O")){
                Utils.setSpinnerPosition(acspRelationship, "Father", false);
                acspRelationship.setEnabled(false);
                String[] fatherNames =decodedData.get(6-inc).contains(":")?decodedData.get(6-inc).split(":"):decodedData.get(6-inc).split("/O");
                String[] newFatherName=fatherNames[1].split(" ");
                if (newFatherName.length>2){
                    String fatherFirstName="";
                    for (int a=1;a<newFatherName.length-1;a++){
                        fatherFirstName= fatherFirstName+" "+newFatherName[a];
                    }
                    tietFatherFName.setText(fatherFirstName.trim());
                    tietFatherFName.setEnabled(false);
                    tietFatherLName.setText(newFatherName[newFatherName.length-1].trim());
                    tietFatherLName.setEnabled(false);

                }else{
                    if (decodedData.get(6-inc).contains(":")){

                        tietFatherFName.setText(decodedData.get(6-inc).split(":")[1].trim());
                        tietFatherFName.setEnabled(false);
                    }else{
                        tietFatherFName.setText(decodedData.get(6-inc).split("/O")[1].trim());
                        tietFatherFName.setEnabled(false);
                    }

                }
            }else if (decodedData.get(6-inc).startsWith("W/O")){
                Utils.setSpinnerPosition(acspRelationship, "Husband", false);
                acspRelationship.setEnabled(false);
                String[] spouseNames =decodedData.get(6-inc).contains(":")?decodedData.get(6-inc).split(":"):decodedData.get(6-inc).split("/O");
                String[] newSpouseName=spouseNames[1].split(" ");
                if (newSpouseName.length>2){
                    String spouseFirstName="";
                    for (int a=1;a<newSpouseName.length-1;a++){
                        spouseFirstName= spouseFirstName+" "+newSpouseName[a];
                    }
                    tietSpouseFName.setText(spouseFirstName.trim());
                    tietSpouseFName.setEnabled(false);
                    tietSpouseLName.setText(newSpouseName[newSpouseName.length-1].trim());
                    tietSpouseLName.setEnabled(false);

                }else{

                    if (decodedData.get(6-inc).contains(":")){
                        tietSpouseFName.setText(decodedData.get(6-inc).split(":")[1].trim());
                        tietSpouseFName.setEnabled(false);
                    }else{
                        tietSpouseFName.setText(decodedData.get(6-inc).split("/O")[1].trim());
                        tietSpouseFName.setEnabled(false);
                    }

                }

            }
        }

        if (decodedData.get(7-inc).equals("")||decodedData.get(7-inc).equals(null)){
            // tietCity.setEnabled(true);
        }else{
            borrower.P_city = decodedData.get(7-inc);
        }

        if (decodedData.get(11-inc).equals("")||decodedData.get(11-inc).equals(null)){
        }else{
            borrower.p_pin = Integer.parseInt(decodedData.get(11-inc));
        }


   /* if (decodedData.get(10-inc).equals("")||decodedData.get(10-inc).equals(null)){
        //  tietAddress3.setEnabled(true);
    }else{
        borrower.P_add3 = decodedData.get(10-inc);
    }

    try{
        if (decodedData.get(9-inc).equals("")||decodedData.get(9-inc).equals(null)){
            tietAddress2.setEnabled(true);
        }else{
            borrower.P_Add1 = decodedData.get(9-inc);
            borrower.P_add2 = decodedData.get(14-inc);
        }
    }catch (Exception e){
        tietAddress2.setEnabled(true);

        9,10,11,13,15,16
    }*/

        Log.e("part data =====> ","Add2 "+decodedData.get(8-inc));
        Log.e("part data =====> ","Add3 "+decodedData.get(9-inc));
        Log.e("part data =====> ","Add3 "+decodedData.get(10-inc));
        Log.e("part data =====> ","Add3 "+decodedData.get(12-inc));
        Log.e("part data =====> ","Add5 "+decodedData.get(14-inc));
        Log.e("part data =====> ","Add5 "+decodedData.get(15-inc));



        StringBuilder joinedStringBuilder = new StringBuilder();
        appendIfNotNullOrEmpty(joinedStringBuilder, decodedData.get(8-inc));
        appendIfNotNullOrEmpty(joinedStringBuilder, decodedData.get(9-inc));
        appendIfNotNullOrEmpty(joinedStringBuilder, decodedData.get(10-inc));
        appendIfNotNullOrEmpty(joinedStringBuilder, decodedData.get(12-inc));
        appendIfNotNullOrEmpty(joinedStringBuilder, decodedData.get(14-inc));
        appendIfNotNullOrEmpty(joinedStringBuilder, decodedData.get(15-inc));

        String joinedString = joinedStringBuilder.toString();
        Log.e("part data =====> ","joinedString "+joinedString);

        // Loop to remove dots before commas and consecutive commas until condition is true
        boolean changesMade;
        do {
            String previousString = joinedString;

            // Remove dots before commas and consecutive commas
            joinedString = joinedString.replaceAll(",+", ",");

            // Check if any changes were made in this iteration
            changesMade = !previousString.equals(joinedString);
        } while (changesMade);

        Log.d("aAadhaar", "joined   " + joinedString);


        if(!joinedString.contains(",")){
            borrower.P_Add1= joinedString;
        }else {
            // Separate the string by commas
            String[] separatedStrings = joinedString.split(",");
            Log.d("aAadhaar", "separated   " + Arrays.toString(separatedStrings));

            // Divide the strings into A, B, and C
            int totalStrings = separatedStrings.length;

            if(totalStrings < 3) {
                borrower.P_Add1 = concatenateStrings(separatedStrings, 0, 1);
                borrower.P_add2 = concatenateStrings(separatedStrings, 1, 2);
                Log.e("part data =====> ", "P_Add1: " + borrower.P_Add1);
                Log.e("part data =====> ", "P_add2: " + borrower.P_add2);
                Log.e("part data =====> ", "P_add3: " + borrower.P_add3);
            } else {
                int stringsInA = totalStrings / 3;
                int stringsInB = (totalStrings - stringsInA) / 2;
                int stringsInC = totalStrings - stringsInA - stringsInB;

                borrower.P_Add1 = concatenateStrings(separatedStrings, 0, stringsInA);
                borrower.P_add2 = concatenateStrings(separatedStrings, stringsInA, stringsInA + stringsInB);
                borrower.P_add3 = concatenateStrings(separatedStrings, stringsInA + stringsInB, totalStrings);

                Log.e("part data =====> ", "P_Add1: " + borrower.P_Add1);
                Log.e("part data =====> ", "P_add2: " + borrower.P_add2);
                Log.e("part data =====> ", "P_add3: " + borrower.P_add3);
            }
        }

        //borrower.P_city = decodedData.get(7);

        // borrower.P_Add1 = decodedData.get(9);
        // borrower.P_add2 = decodedData.get(8);
        // borrower.P_add3 = decodedData.get(10);

        setDataToView(this.findViewById(android.R.id.content).getRootView());
        validateBorrower();
        tietAge.setEnabled(false);
        tietDob.setEnabled(false);
        acspAadharState.setEnabled(false);
        acspGender.setEnabled(false);


    }

    private String concatenateStrings(String[] strings, int startIndex, int endIndex) {
        StringBuilder result = new StringBuilder();
        for (int i = startIndex; i < endIndex; i++) {
            if (i > startIndex) {
                result.append(",");
            }
            result.append(strings[i]);
        }
        return result.toString();
    }

    private void appendIfNotNullOrEmpty(StringBuilder stringBuilder, String str) {
        if (str != null && !str.isEmpty()) {
            if (stringBuilder.length() > 0) {
                stringBuilder.append(",");
            }
            stringBuilder.append(str);
        }
    }

    protected void decodeMobileEmail(byte[] decompressedData){
        int mobileStartIndex = 0,mobileEndIndex = 0,emailStartIndex = 0,emailEndIndex = 0;
        switch (emailMobilePresent){
            case 3:
                // both email mobile present
                mobileStartIndex = decompressedData.length - 289; // length -1 -256 -32
                mobileEndIndex = decompressedData.length - 257; // length -1 -256
                emailStartIndex = decompressedData.length - 322;// length -1 -256 -32 -1 -32
                emailEndIndex = decompressedData.length - 290;// length -1 -256 -32 -1

                mobile = bytesToHex (Arrays.copyOfRange(decompressedData,mobileStartIndex,mobileEndIndex+1));
                email = bytesToHex (Arrays.copyOfRange(decompressedData,emailStartIndex,emailEndIndex+1));
                // set image end index, it will be used to extract image data
                imageEndIndex = decompressedData.length - 323;
                break;

            case 2:
                // only mobile
                email = "";
                mobileStartIndex = decompressedData.length - 289; // length -1 -256 -32
                mobileEndIndex = decompressedData.length - 257; // length -1 -256

                mobile = bytesToHex (Arrays.copyOfRange(decompressedData,mobileStartIndex,mobileEndIndex+1));
                // set image end index, it will be used to extract image data
                imageEndIndex = decompressedData.length - 290;

                break;

            case 1:
                // only email
                mobile = "";
                emailStartIndex = decompressedData.length - 289; // length -1 -256 -32
                emailEndIndex = decompressedData.length - 257; // length -1 -256

                email = bytesToHex (Arrays.copyOfRange(decompressedData,emailStartIndex,emailEndIndex+1));
                // set image end index, it will be used to extract image data
                imageEndIndex = decompressedData.length - 290;
                break;

            default:
                // no mobile or email
                mobile = "";
                email = "";
                // set image end index, it will be used to extract image data
                imageEndIndex = decompressedData.length - 257;
        }

        Log.e("email mobile======> ","Data=====>"+email +"   "+mobile);

    }

    public static String bytesToHex(byte[] bytes) {
        final char[] hexArray = "0123456789ABCDEF".toCharArray();

        char[] hexChars = new char[bytes.length * 2];
        for ( int j = 0; j < bytes.length; j++ ) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }


    //=================================================


    @Override
    public void cameraCaptureUpdate(Uri uriImage) {
        this.uriPicture = uriImage;
    }

    private void  updateBorrower() {

//        else if( otpVerified==0){
//            Toast.makeText(activity, "Please verify mobile number vis OTP", Toast.LENGTH_SHORT).show();
//        }
        Log.d("TAG", "updateBorrower: "+borrower.getPicture());
        if(stateData.equalsIgnoreCase("APO Address") || stateData.equalsIgnoreCase("--Select--") ){
            Toast.makeText(activity, "Select State Name", Toast.LENGTH_SHORT).show();
        }else if(genderData.equalsIgnoreCase("--Select--") ){
            Toast.makeText(activity, "Select Gender", Toast.LENGTH_SHORT).show();
        }else if(maritalStatus.equalsIgnoreCase("--Select--") ){
            Toast.makeText(activity, "Select Marital Status", Toast.LENGTH_SHORT).show();
        }else if( relationShipData.equalsIgnoreCase("--Select--") ){
            Toast.makeText(activity, "Select Relationship", Toast.LENGTH_SHORT).show();
        }else{
            if (borrower != null) {
                getDataFromView(this.findViewById(android.R.id.content).getRootView());

                if ((tietPanNo.getText().toString().equals("") || tietPanNo.getText().toString().equals(null)) && (tietDrivingLic.getText().toString().equals("") || tietDrivingLic.getText().toString().equals(null)) &&(tietVoterId.getText().toString().equals("") || tietVoterId.getText().toString().equals(null))){
                    Utils.showSnakbar(findViewById(android.R.id.content),"Please enter anyone in PAN number, Driving License or Voter ID");
                }else{
                    if (validateBorrower()) {
                        Map<String, String> messages = borrower.validateKyc(this);
                        if (messages.size() > 0) {
                            String combineMessage = Arrays.toString(messages.values().toArray());
                            combineMessage = combineMessage.replace("[", "->").replace(", ", "\n->").replace("]", "");
                            Log.e("combineMessage",combineMessage);
                            Utils.alert(this, combineMessage);
                        } else {
                            invalidateOptionsMenu();
                            if (!chkTvTopup.isChecked()) borrower.OldCaseCode = null;
                            borrower.Oth_Prop_Det = null;
                            borrower.save();
                            borrower.associateExtraBank(borrower.fiExtraBank);
                            borrower.fiExtraBank.save();

                            BorrowerDTO borrowerDTO = new BorrowerDTO(borrower);
                            borrowerDTO.fiFamExpenses = null;
                            borrowerDTO.fiExtra = null;
                            //Log.d("Borrower Json",WebOperations.convertToJson(borrower));
                            String borrowerJsonString = WebOperations.convertToJson(borrowerDTO);
                            //Log.d("Borrower Json", borrowerJsonString);
                            Log.d("TAG", "updateBorrower: "+borrowerJsonString);

                            if (!tietPanNo.getText().toString().equals("") && !tietVoterId.getText().toString().equals("") && !tietDrivingLic.getText().toString().equals("")){
                                if (tilPAN_Name.getText().toString().trim().equals("") && tilVoterId_Name.getText().toString().trim().equals("") && tilDL_Name.getText().toString().trim().equals("")){
                                    Toast.makeText(activity, "Please Verify PAN Card, Voter ID and Driving License", Toast.LENGTH_SHORT).show();
                                }else{
                                    sendingDataToNewPage();
                                }
                            }else{
                                if(!tilPAN_Name.getText().toString().trim().equals("") ||!tilVoterId_Name.getText().toString().trim().equals("") ||!tilDL_Name.getText().toString().trim().equals("") ){
                                    sendingDataToNewPage();
                                }else if (!tietPanNo.getText().toString().equals("")){
                                    if (tilPAN_Name.getText().toString().trim().equals("")){
                                        Toast.makeText(activity, "Please Verify the PAN Card", Toast.LENGTH_SHORT).show();
                                    }else {
                                        sendingDataToNewPage();
                                    }
                                }
                                else if(!tietVoterId.getText().toString().equals("")){
                                    sendingDataToNewPage();

                                }
                                else if(!tietDrivingLic.getText().toString().equals("")){
                                    if (tilDL_Name.getText().toString().trim().equals("")){
                                        Toast.makeText(activity, "Please Verify the Driving License ", Toast.LENGTH_SHORT).show();
                                    }else{
                                        sendingDataToNewPage();
                                    }



                                }

                            }

                        }
                    } else {
                        Utils.alert(this, "There is at least one errors in the Aadhar Data");
                    }
                }

            }
        }
    }

    private void sendingDataToNewPage() {
        if(isgetPanwithOCR){
            if(isPanverify==1 || isDLverify==1 || isVoterverify==1){
                Intent intent = new Intent(ActivityBorrowerKyc.this, KYC_Form_New.class);
                intent.putExtra("FatherFName", tietFatherFName.getText().toString());
                intent.putExtra("FatherLName", tietFatherLName.getText().toString());
                intent.putExtra("FatherMName", tietFatherMName.getText().toString());
                intent.putExtra("MotherFName", tietMotherFName.getText().toString());
                intent.putExtra("MotherLName", tietMotherLName.getText().toString());
                intent.putExtra("MotherMName", tietMotherMName.getText().toString());
                intent.putExtra("SpouseLName", tietSpouseLName.getText().toString());
                intent.putExtra("SpouseMName", tietSpouseMName.getText().toString());
                intent.putExtra("SpouseFName", tietSpouseFName.getText().toString());
                intent.putExtra("VoterIdName", tilVoterId_Name.getText().toString());
                intent.putExtra("PANName", tilPAN_Name.getText().toString());
                intent.putExtra("DLName", tilDL_Name.getText().toString());
                intent.putExtra("AadharName", tietName.getText().toString());
                intent.putExtra("manager", manager);
                intent.putExtra("borrower", borrower);
                intent.putExtra(Global.SCHEME_TAG, schemeNameForVH);
                intent.putExtra("lastCaseCode",  lastCaseCode);
                intent.putExtra("lastLoanAmt",   lastLoanAmt);
                intent.putExtra("lastDuration",   lastDuration);
                intent.putExtra("lastPaidEmi",   lastPaidEmi);
                intent.putExtra("AddressCodes",cityData.getCitYCODE()+"_"+districtDat.getDisTCODE()+"_"+subDistrictData.getSuBDISTCODE()+"_"+villageData.getVillagECODE());
                startActivity(intent);
            }else{
                Utils.alert(ActivityBorrowerKyc.this,"Verify any one ID from PAN|DL|Voter ID");
            }

        }else{
            if(isPanverify==1 || isDLverify==1 || isVoterverify==1){
                Intent intent = new Intent(ActivityBorrowerKyc.this, KYC_Form_New.class);
                intent.putExtra("FatherFName", tietFatherFName.getText().toString());
                intent.putExtra("FatherLName", tietFatherLName.getText().toString());
                intent.putExtra("FatherMName", tietFatherMName.getText().toString());
                intent.putExtra("MotherFName", tietMotherFName.getText().toString());
                intent.putExtra("MotherLName", tietMotherLName.getText().toString());
                intent.putExtra("MotherMName", tietMotherMName.getText().toString());
                intent.putExtra("SpouseLName", tietSpouseLName.getText().toString());
                intent.putExtra("SpouseMName", tietSpouseMName.getText().toString());
                intent.putExtra("SpouseFName", tietSpouseFName.getText().toString());
                intent.putExtra("VoterIdName", tilVoterId_Name.getText().toString());
                intent.putExtra("PANName", tilPAN_Name.getText().toString());
                intent.putExtra("DLName", tilDL_Name.getText().toString());
                intent.putExtra("AadharName", tietName.getText().toString());
                intent.putExtra("manager", manager);
                intent.putExtra("borrower", borrower);
                intent.putExtra(Global.SCHEME_TAG, schemeNameForVH);
                intent.putExtra("lastCaseCode",  lastCaseCode);
                intent.putExtra("lastLoanAmt",   lastLoanAmt);
                intent.putExtra("lastDuration",   lastDuration);
                intent.putExtra("lastPaidEmi",   lastPaidEmi);
                intent.putExtra("AddressCodes",cityData.getCitYCODE()+"_"+districtDat.getDisTCODE()+"_"+subDistrictData.getSuBDISTCODE()+"_"+villageData.getVillagECODE());

                startActivity(intent);

            }else{
                Utils.alert(ActivityBorrowerKyc.this,"Verify any one ID from PAN|DL|Voter ID");
            }
        }

    }

    private void fetchTopupDetails(final String oldCaseCode) {
        DataAsyncResponseHandler dataAsyncResponseHandler = new DataAsyncResponseHandler(this, "Borrower KYC", "Fetching Topup Details") {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    String jsonString = new String(responseBody);
                    Log.d("Response", jsonString);
                    JSONObject jo = null;
                    try {
                        jo = (new JSONArray(jsonString)).getJSONObject(0);
                        int noInst = jo.getInt("Total_Installments");
                        int lastInstR = jo.getInt("LastInst_Recd");
                        String aadharId = jo.getString("aadharid");

                        if (aadharId.length() == 12) {
                            if (tietAadharId.getText().length() == 12) {
                                if (!tietAadharId.getText().toString().equals(aadharId)) {
                                    Utils.alert(ActivityBorrowerKyc.this, "Aadhar IDs did not match");
                                    return;
                                }
                            }
                                /*if (tietAadharId.getText().length() == 0 || tietAadharId.getText().length() != 12) {
                                    tietAadharId.setText(aadharId);
                                    tietAadharId.setEnabled(false);
                                }*/
                            borrower.OldCaseCode = jo.getString("CODE");
                            //tietName.setText(jo.getString("SUBS_NAME"));
                            if (lastInstR >= ((noInst / 2) - 1)) {

                            } else {
                                Utils.alert(ActivityBorrowerKyc.this, "Minimum installment not received for topup");
                            }
                        } else {
                            Utils.alert(ActivityBorrowerKyc.this, "Please get Aadhar ID updated for Topup Case");
                        }


                    } catch (JSONException je) {
                        if (("[]").equals(jsonString))
                            Utils.alert(ActivityBorrowerKyc.this, "No record found for Loan A/c " + oldCaseCode);
                        else {

                        }
                    }
                }
            }
        };
        RequestParams params = new RequestParams();
        params.add("SmCode", oldCaseCode);
        (new WebOperations()).getEntity(this, "posfi", "gettopupcasedtls", params, dataAsyncResponseHandler);

    }

    private void fetchAadharDetails(String aadharId) {

        DataAsyncResponseHandler dataAsyncResponseHandler = new DataAsyncResponseHandler(this, "Borrower KYC", "Fetching Previous Applications") {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    String jsonString = new String(responseBody);
                    Log.d("Response", jsonString);
                    //JSONObject jo = null;
                    //Type listType = new TypeToken<List<OldFIById>>() {}.getType();
                    List<OldFIById> oldFIByIds = WebOperations.convertToObjectArray(jsonString);


                    /*try {
                        jo = (new JSONArray(jsonString)).getJSONObject(0);
                        int noInst = jo.getInt("Total_Installments");
                        int lastInstR = jo.getInt("LastInst_Recd");
                        String aadharId = jo.getString("AadharID");
                        if (lastInstR >= ((noInst / 2) - 1)) {
                            if (aadharId.length() == 12) {
                                if (tietAadharId.getText().length() == 12) {
                                    if (!tietAadharId.getText().toString().equals(aadharId)) {
                                        Utils.alert(ActivityBorrowerKyc.this, "Aadhar IDs did not match");
                                        return;
                                    }
                                }
                                if (tietAadharId.getText().length() == 0 || tietAadharId.getText().length() != 12) {
                                    tietAadharId.setText(aadharId);
                                    tietAadharId.setEnabled(false);
                                }
                                borrower.OldCaseCode = jo.getString("CODE");
                                tietName.setText(jo.getString("SUBS_NAME"));

                            } else {
                                Utils.alert(ActivityBorrowerKyc.this, "Please get Aadhar ID updated for Topup Case");
                            }

                        } else {
                            Utils.alert(ActivityBorrowerKyc.this, "Minimum installment not received for topup");
                        }

                    } catch (JSONException je) {

                    }*/
                }
            }
        };
        RequestParams params = new RequestParams();
        params.add("Aadharid", aadharId);
        (new WebOperations()).getEntity(this, "posdb", "getaadharstatus", params, dataAsyncResponseHandler);

    }

    private boolean validateControls(EditText editText, String text) {
        boolean retVal = true;
        editText.setError(null);
        switch (editText.getId()) {
            case R.id.tietAadhar:
                if (editText.length() != 12) {
                    editText.setError("Should be of 12 Characters");
                    Utils.alert(this,"Should be of 12 Characters");
                    retVal = false;
                } else if (!Verhoeff.validateVerhoeff(editText.getText().toString())) {
                    editText.setError("Aadhar is not Valid");
                    Utils.alert(this,"Aadhar is not Valid");

                    retVal = false;
                }
                break;
            case R.id.tietDob:
                if (editText.length() < 10) {
                    editText.setError("Should be more than 3 Characters");
                    Utils.alert(this,"Age should be more than 3 Characters");

                    retVal = false;
                }
                break;
            case R.id.tietGuardian:
                if (!containsNumbers(tietGuardian.getText().toString())) {
                    // Show error: Invalid name (contains numbers)
                    tietGuardian.setError("Invalid name. Please remove numbers.");
                    editText.setEnabled(true);
                    retVal = false;

                }
                break;

            case R.id.tietName:
                Log.d("naam","naam"+tietName.getText().toString());
                Log.d("naam","naam"+editText.length());
                if (editText.length() < 3 || !containsNumbers(tietName.getText().toString())) {
                    editText.setError("Invalid Name");
                    retVal = false;
                }
                break;

            case R.id.tietAge:
                try {
                    if (text.length() == 0) text = "0";
                    int age = Integer.parseInt(text);
                    if (age < 21) {
                        editText.setError("Age should be greater than 17");
                        retVal = false;
                    } else if (age > 57) {
                        editText.setError("Age should be less than 66");
                        retVal = false;
                    }
                    tietDob.setEnabled(retVal);
                }catch (Exception e){
                    editText.setError("");
                }
                break;
            case R.id.tietAddress1:
                String character=editText.getText().toString().trim();
                if (editText.getText().toString().trim().length() < 3 || !containsOnlyAllowedCharacters(character)) {
                    editText.setError("Should be more than 2 Characters or correct");
                    editText.setEnabled(true);
                    retVal = false;
                }else{
                    /*if (!Pattern.matches(AddressREGX, character)) {
                        editText.setError("Special Char. not allowed.");
                        retVal = false;
                    } else {

                    }*/
                    try {
                        int intValue = Integer.parseInt(character);
                        tietAddress1.setEnabled(true);
                        editText.setError("Only number not allowed.");
                        Utils.alert(this,"Only number not allowed.");
                        retVal = false;
                    } catch (NumberFormatException e) {
                        retVal = true;
                        System.out.println("Input String cannot be parsed to Integer.");
                    }

                }
                break;
            case R.id.tietAddress2:
                String character1=editText.getText().toString().trim();
                Log.d("TAG", "validateControls: "+character1);
                if (editText.getText().toString().trim().length() < 1 || !containsOnlyAllowedCharacters(character1)) {
                    editText.setError("Should be more than 2 Characters or Address correct");
                    editText.setEnabled(true);

                    retVal = false;
                }/*else{
                    if (!Pattern.matches(AddressREGX, character1)) {
                        editText.setError("Special Char. not allowed.");
                        retVal = false;
                    } else {
                        retVal = true;
                    }
                }*/
                break;

            case R.id.tietAddress3:
                String character3=editText.getText().toString().trim();
                if (!containsOnlyAllowedCharacters(character3)) {
                    editText.setError("Address should be correct");
                    editText.setEnabled(true);

                    retVal = false;
                }/*else{
                    if (!Pattern.matches(AddressREGX, character1)) {
                        editText.setError("Special Char. not allowed.");
                        retVal = false;
                    } else {
                        retVal = true;
                    }
                }*/
                break;
            case R.id.tietCity:
                String city=editText.getText().toString();
                if (city.trim().length() < 1 || !containsOnlyAllowedCharacters(city)) {
                    editText.setError("Should be more than 2 Characters");
                    editText.setEnabled(true);

                    retVal = false;
                }
                break;
            case R.id.tietPinCode:
                if (editText.getText().toString().trim().length() != 6) {
                    editText.setError("Should be of 6 digits");
                    editText.setEnabled(true);

                    retVal = false;
                }
                break;

            case R.id.tietDrivingLlicense:
                if (editText.getText().toString().trim().length() > 0) {
                    if (editText.getText().toString().trim().length() < 1) {
                        editText.setError("Enter Driving License");
                        editText.setEnabled(true);

                        retVal = false;
                    }
                } else {
                    retVal = true;
                    editText.setError(null);
                }
                break;
            case R.id.tietBankCIF:
                if (editText.getText().toString().trim().length() > 0) {
                    if (editText.getText().toString().trim().replace(" ","").length() < 10) {
                        editText.setError("Should be more than 9 Characters");
                        editText.setEnabled(true);

                        retVal = false;
                    }
                } else {
                    retVal = true;
                    editText.setError(null);
                }
                break;
            case R.id.tietMobile:
                if (editText.getText().toString().trim().length() > 0) {
                    if (editText.getText().toString().trim().replace(" ","").length() != 10) {
                        editText.setError("Should be of 10 digits");
                        editText.setEnabled(true);
                        retVal = false;
                    }
                } else {
                    retVal = true;
                    editText.setError(null);
                }
                break;
            case R.id.tietMotherFName:
                if (editText.getText().toString().trim().length() < 1 ||!containsNumbers(tietMotherFName.getText().toString())) {
                    editText.setError("Please Enter Correct Mother Name");
                    editText.setEnabled(true);

                    retVal = false;

                } else {
                    retVal = true;
                    editText.setError(null);
                }
                break;

            case R.id.tietFatherFName:
                if (editText.getText().toString().trim().length() < 1|| !containsNumbers(tietFatherFName.getText().toString())) {
                    editText.setError("Please Enter Correct Father Name");
                    editText.setEnabled(true);

                    retVal = false;

                } else {
                    retVal = true;
                    editText.setError(null);
                }
                break;

            case R.id.tietSpouseFName:
                if (Utils.getSpinnerStringValue((Spinner)findViewById(R.id.spinLoanAppPersonalMarritalStatus)).equals("Married"))
                {
                    if (editText.getText().toString().trim().length() < 1 ||!containsNumbers(tietSpouseFName.getText().toString())) {
                        editText.setError("Please Enter Correct Spouse Name");
                        editText.setEnabled(true);

                        retVal = false;

                    } else {
                        retVal = true;
                        editText.setError(null);
                    }

                }
                break;

        }
        return retVal;
    }

  private boolean validateControls(TextView editText, String text) {
        boolean retVal = true;
        editText.setError(null);
        switch (editText.getId()) {

            case R.id.txtCityName:
                    if (editText.getText().toString().trim().length() < 1) {
                        editText.setError("Please Choose City");
                        retVal = false;
                    }
                break;   case R.id.txtSubDistictName:
                    if (editText.getText().toString().trim().length() < 1) {
                        editText.setError("Please Choose Sub-District");
                        retVal = false;
                    }
                break;   case R.id.txtVDistrictName:
                    if (editText.getText().toString().trim().length() < 1) {
                        editText.setError("Please Choose District");
                        retVal = false;
                    }
                break;   case R.id.txtVillageName:
                    if (editText.getText().toString().trim().length() < 1) {
                        editText.setError("Please Choose Village");
                        retVal = false;
                    }
                break;

        }
        return retVal;
    }



    private void cardValidate(String id,String type,String bankIfsc,String dob) {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setIndeterminate(false);
        progressDialog.setTitle("Fetching Details");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        ApiInterface apiInterface= getClientPan(SEILIGL.NEW_SERVERAPIAGARA).create(ApiInterface.class);
        Log.d("TAG", "checkCrifScore: "+getJsonOfString(id,type,bankIfsc,dob));
        requestforVerification= String.valueOf(getJsonOfString(id,type,bankIfsc,dob));
        Call<JsonObject> call=apiInterface.cardValidate(getJsonOfString(id,type,bankIfsc,dob));
        call.enqueue(new Callback<JsonObject>() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.d("TAG", "onResponse: this called");
                if(response.body() != null){
                    Log.d("TAG", "onResponse: this called");

                    ResponseforVerification= String.valueOf(response.body().get("data"));
                    saveVerficationLogs(IglPreferences.getPrefString(getApplicationContext(), SEILIGL.USER_ID, ""),type,requestforVerification,ResponseforVerification);
                    if (type.equals("pancard")){
                        try {
                            tilPAN_Name.setVisibility(View.VISIBLE);
                            tilPAN_Name.setText(response.body().get("data").getAsJsonObject().get("name").getAsString());
                            panCheckSign.setBackground(getResources().getDrawable(R.drawable.check_sign_ic_green));
                            panCheckSign.setEnabled(false);
                            isNameMatched="1";
                            isPanverify=1;
                        }catch (Exception e){
                            tilPAN_Name.setVisibility(View.VISIBLE);
                            tilPAN_Name.setText("Card Holder Name Not Found");
                            tilPAN_Name.setTextColor(getResources().getColor(R.color.red));
                            panCheckSign.setBackground(getResources().getDrawable(R.drawable.check_sign_ic));
                            panCheckSign.setEnabled(true);
                            isPanverify=0;
                        }
                        progressDialog.cancel();
                    }else if(type.equals("voterid")){
                        try {
                            tilVoterId_Name.setVisibility(View.VISIBLE);
                            tilVoterId_Name.setText(response.body().get("data").getAsJsonObject().get("name").getAsString());
                            voterIdCheckSign.setBackground(getResources().getDrawable(R.drawable.check_sign_ic_green));
                            voterIdCheckSign.setEnabled(false);
                            isNameMatched="1";
                            isVoterverify=1;
                        }catch (Exception e){
                            tilVoterId_Name.setVisibility(View.VISIBLE);
                            tilVoterId_Name.setText("Card Holder Name Not Found");
                            tilVoterId_Name.setTextColor(getResources().getColor(R.color.red));
                            voterIdCheckSign.setBackground(getResources().getDrawable(R.drawable.check_sign_ic));
                            voterIdCheckSign.setEnabled(true);
                            isVoterverify=0;

                        }
                        progressDialog.cancel();

                    }else if(type.equals("drivinglicense")){
                        try {
                            tilDL_Name.setVisibility(View.VISIBLE);
                            tilDL_Name.setText(response.body().get("data").getAsJsonObject().get("name").getAsString());
                            dLCheckSign.setBackground(getResources().getDrawable(R.drawable.check_sign_ic_green));
                            dLCheckSign.setEnabled(false);
                            isNameMatched="1";
                            isDLverify=1;
                        }catch (Exception e){
                            tilDL_Name.setVisibility(View.VISIBLE);
                            tilDL_Name.setText("Card Holder Name Not Found");
                            tilDL_Name.setTextColor(getResources().getColor(R.color.red));
                            dLCheckSign.setBackground(getResources().getDrawable(R.drawable.check_sign_ic));
                            dLCheckSign.setEnabled(true);
                            isDLverify=0;
                        }
                        progressDialog.cancel();

                    }
                }else{
                    progressDialog.cancel();
                    if(type.equals("pancard")){
                        tilPAN_Name.setVisibility(View.VISIBLE);
                        tilPAN_Name.setText("Not found");
                        tilDL_Name.setTextColor(getResources().getColor(R.color.red));
                        panCheckSign.setBackground(getResources().getDrawable(R.drawable.check_sign_ic));
                    }else if(type.equals("voterid")){
                        tilVoterId_Name.setVisibility(View.VISIBLE);

                        tilVoterId_Name.setText("Not found");
                        tilDL_Name.setTextColor(getResources().getColor(R.color.red));
                        voterIdCheckSign.setBackground(getResources().getDrawable(R.drawable.check_sign_ic));
                        isVoterverify=1;
                    }else{
                        tilDL_Name.setVisibility(View.VISIBLE);

                        tilDL_Name.setText("Not found");
                        tilDL_Name.setTextColor(getResources().getColor(R.color.red));
                        dLCheckSign.setBackground(getResources().getDrawable(R.drawable.check_sign_ic));
                    }

                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d("TAG", "onFailure: this called"+t.getMessage());

                progressDialog.cancel();
                if (type.equals("pancard")){
                    tilPAN_Name.setText(t.getMessage().length()<1?"Not Found":t.getMessage());
                    tilPAN_Name.setTextColor(getResources().getColor(R.color.red));
                    panCheckSign.setBackground(getResources().getDrawable(R.drawable.check_sign_ic));

                }if(type.equals("voterid")){
                    tilVoterId_Name.setText(t.getMessage().length()<1?"Not Found":t.getMessage());
                    tilVoterId_Name.setTextColor(getResources().getColor(R.color.red));
                    voterIdCheckSign.setBackground(getResources().getDrawable(R.drawable.check_sign_ic));

                }else{
                    tilDL_Name.setText(t.getMessage().length()<1?"Not Found":t.getMessage());
                    tilDL_Name.setTextColor(getResources().getColor(R.color.red));
                    dLCheckSign.setBackground(getResources().getDrawable(R.drawable.check_sign_ic));

                }
            }
        });
    }

    private void saveVerficationLogs(String id,String type,String request,String response) {
        ApiInterface apiInterface= getClientPan(SEILIGL.NEW_SERVERAPI).create(ApiInterface.class);
        Call<JsonObject> call=apiInterface.kycVerficationlog(getJsonOfKyCLogs(id,type,request,response));
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.d("TAG", "checkCrifScore: "+response.body());
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }
    private JsonObject getJsonOfKyCLogs(String id, String type,String bankIfsc,String userDOB) {
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("Type",type);
        jsonObject.addProperty("Userid",id);
        jsonObject.addProperty("Request",bankIfsc);
        jsonObject.addProperty("Response",userDOB);
        return  jsonObject;
    }

    private JsonObject getJsonOfString(String id, String type,String bankIfsc,String userDOB) {
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("type",type);
        jsonObject.addProperty("txtnumber",id);
        jsonObject.addProperty("ifsc",bankIfsc);
        jsonObject.addProperty("userdob",userDOB);
        jsonObject.addProperty("key","1");
        return  jsonObject;
    }

    private boolean validateBorrower() {
        boolean retVal = true;
        retVal &= validateControls(tietAadharId, tietAadharId.getText().toString());
        retVal &= validateControls(tietName, tietName.getText().toString());
        retVal &= validateControls(tietGuardian, tietGuardian.getText().toString());
        retVal &= validateControls(tietAge, tietAge.getText().toString());
        retVal &= validateControls(tietDob, tietDob.getText().toString());
        retVal &= validateControls(tietAddress1, tietAddress1.getText().toString());
        retVal &= validateControls(tietAddress2, tietAddress2.getText().toString());
        retVal &= validateControls(tietAddress3, tietAddress3.getText().toString());
        retVal &= validateControls(tietCity, tietCity.getText().toString());
        retVal &= validateControls(tietPinCode, tietPinCode.getText().toString());
        retVal &= validateControls(tietMobile, tietMobile.getText().toString());
//      retVal &= validateControls(tietVoterId, tietVoterId.getText().toString()) || validateControls(tietPanNo, tietPanNo.getText().toString());
        retVal &= validateControls(tietDrivingLic, tietDrivingLic.getText().toString());
        retVal &= validateControls(tietMotherFName, tietMotherFName.getText().toString());
        retVal &= validateControls(tietFatherFName, tietFatherFName.getText().toString());
        retVal &= validateControls(tietSpouseFName, tietSpouseFName.getText().toString());
        retVal &= validateControls(txtCityName, txtCityName.getText().toString());
        retVal &= validateControls(txtSubDistictName, txtSubDistictName.getText().toString());
        retVal &= validateControls(txtVDistrictName, txtVDistrictName.getText().toString());
        retVal &= validateControls(txtVillageName, txtVillageName.getText().toString());
        return retVal;
    }

    private List<DocumentStore> getDocumentStore(Borrower borrower) {
        List<DocumentStore> documentStores = new ArrayList<>();
        DocumentStore documentStore = new DocumentStore();
        documentStores.add(documentStore.getDocumentStore(borrower, 0, "Picture"));
        if (!Utils.NullIf(borrower.isAadharVerified, "N").equals("Y")) {
            documentStores.add(documentStore.getDocumentStore(borrower, 1, "AadharFront"));
            documentStores.add(documentStore.getDocumentStore(borrower, 1, "AadharBack"));
        }

        if (BuildConfig.APPLICATION_ID == "com.softeksol.paisalo.jlgsourcing") {
            documentStores.add(documentStore.getDocumentStore(borrower, 5, "VoterFront"));
            documentStores.add(documentStore.getDocumentStore(borrower, 5, "VoterBack"));

            if (borrower.PanNO.length() == 10) {
                documentStores.add(documentStore.getDocumentStore(borrower, 6, "PANCard"));
            }
        } else {
            if (borrower.voterid.length() > 9) {
                documentStores.add(documentStore.getDocumentStore(borrower, 3, "VoterFront"));
                documentStores.add(documentStore.getDocumentStore(borrower, 3, "VoterBack"));
            }
            if (borrower.PanNO.length() == 10) {
                documentStores.add(documentStore.getDocumentStore(borrower, 4, "PANCard"));
            }
        }
        if (borrower.drivinglic.length() > 9) {
            documentStores.add(documentStore.getDocumentStore(borrower, 15, "DrivingLicFront"));
            documentStores.add(documentStore.getDocumentStore(borrower, 15, "DrivingLicBack"));
        }

        //documentStores.add(documentStore.getDocumentStore(borrower,2,"PassbookFirst"));
        //documentStores.add(documentStore.getDocumentStore(borrower,2,"PassbookLast"));
        //documentStores.add(documentStore.getDocumentStore(borrower,4,"Mandate"));
        Collections.sort(documentStores, DocumentStore.sortByUploadStatus);
        return documentStores;
    }

    @Override
    public void onKycCapture(DocumentStore item) {
        documentPic = item;
        Boolean cropState = true;
        try {
            CameraUtils.dispatchTakePictureIntent(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onKycUpload(final DocumentStore documentStore, final View view) {
        DataAsyncResponseHandler responseHandler = new DataAsyncResponseHandler(this, "Loan Financing", DocumentStore.getDocumentName(documentStore.checklistid)) {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String responseString = new String(responseBody);
                Utils.showSnakbar(activity.findViewById(android.R.id.content).getRootView(), responseString);
                //if(responseString.equals("")) {
                documentStore.updateStatus = true;
                documentStore.update();
                (new File(documentStore.imagePath)).delete();
//                showScanDocs();
            }
        };

        String jsonString = WebOperations.convertToJson(documentStore.getDocumentDTO());
        String apiPath = documentStore.checklistid == 0 ? "/api/uploaddocs/savefipicjson" : "/api/uploaddocs/savefidocsjson";
        (new WebOperations()).postEntity(activity, BuildConfig.BASE_URL + apiPath, jsonString, responseHandler);
    }

    public void showScanDocs() {
        List<DocumentStore> documentStoreList = getDocumentStore(borrower);
        adapterRecViewListDocuments.updateList(documentStoreList);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void putExifData(DocumentStore documentStore) {
        try {
            ExifInterface newExif = new ExifInterface(documentStore.imagePath);
            newExif.setAttribute(ExifInterface.TAG_USER_COMMENT, documentStore.Creator + "_" + documentStore.ficode);
            SecretKey secretKey = Utils.generateKey(documentStore.Creator + "#" + documentStore.ficode);
            String id = new String(Utils.encryptMsg(borrower.aadharid, secretKey));
            newExif.setAttribute(ExifInterface.TAG_IMAGE_UNIQUE_ID, id);
            newExif.saveAttributes();
        } catch (IOException | NoSuchAlgorithmException | InvalidKeyException | NoSuchPaddingException | BadPaddingException | InvalidKeySpecException | IllegalBlockSizeException | InvalidParameterSpecException e) {
            e.printStackTrace();
        }
    }



    public static Retrofit getClientPan(String BASE_URL) {
        Retrofit retrofit = null;
        if (retrofit==null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder(

            );
            httpClient.connectTimeout(1, TimeUnit.MINUTES);
            httpClient.readTimeout(1,TimeUnit.MINUTES);
            httpClient.addInterceptor(logging);
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();
        }
        return retrofit;
    }


    @Override
    public void onListCReatorInteraction(String choosedCreatora) {
        Log.d("TAG", "onListCReatorInteraction: "+choosedCreatora);
        choosedCreator=choosedCreatora;
    }

    @Override
    public void DistrictChooseListner(DistrictData districtDatas) {
        districtDat=districtDatas;
    }

    @Override
    public void VillageChooseListner(VillageData villageDatas) {
        villageData=villageDatas;


    }

    @Override
    public void CityChooseListner(CityData cityDatas) {
        cityData=cityDatas;
    }

    @Override
    public void SubDistChooseListner(SubDistrictData subDistrictDatas) {
        subDistrictData=subDistrictDatas;

    }
}




