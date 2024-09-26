package com.softeksol.paisalo.jlgsourcing.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.softeksol.paisalo.jlgsourcing.BuildConfig;
import com.softeksol.paisalo.jlgsourcing.R;
import com.softeksol.paisalo.jlgsourcing.SEILIGL;
import com.softeksol.paisalo.jlgsourcing.TermAndPolicyPage;
import com.softeksol.paisalo.jlgsourcing.Utilities.IglPreferences;
import com.softeksol.paisalo.jlgsourcing.Utilities.MyTextWatcher;
import com.softeksol.paisalo.jlgsourcing.Utilities.Utils;
import com.softeksol.paisalo.jlgsourcing.WebOperations;
import com.softeksol.paisalo.jlgsourcing.adapters.AdapterListRange;
import com.softeksol.paisalo.jlgsourcing.adapters.CreatorListAdapter;
import com.softeksol.paisalo.jlgsourcing.entities.CreatorModel;
import com.softeksol.paisalo.jlgsourcing.entities.Manager;
import com.softeksol.paisalo.jlgsourcing.entities.RangeCategory;
import com.softeksol.paisalo.jlgsourcing.handlers.DataAsyncResponseHandler;
import com.softeksol.paisalo.jlgsourcing.location.GpsTracker;
import com.softeksol.paisalo.jlgsourcing.retrofit.ApiInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import cz.msebera.android.httpclient.Header;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ActivityLogin extends AppCompatActivity implements View.OnClickListener, onListCReatorInteraction {
    final static int CALLBACK_NUMBER = 1001;

    DataAsyncResponseHandler dataAsyncResponseHandler;
    DataAsyncResponseHandler dataAsyncResponseHandler2;
    onListCReatorInteraction listCReatorInteraction;
    String choosedCreator;
    File storageDir;

    String apkPath;
    private EditText userName;
    private EditText password;
    List<CreatorModel> list=new ArrayList<>();
    private TextView btnShareDeviceID;
    private Button btnSignIn;
    private Spinner database;
    CreatorListAdapter adapter;
    private String deviceId;
    private long deviceImei;
    String UserName;
    TextView versionText;
    String Password;
    Dialog dialogSearch;

    public static final String BASE_URL = BuildConfig.APPLICATION_ID + ".BASE_URL";
    public static final String DEVICE_IMEI = BuildConfig.APPLICATION_ID + ".IMEI";
    public static final String DEVICE_ID = BuildConfig.APPLICATION_ID + ".DEV_ID";
    public static final String DATABASE_NAME = BuildConfig.APPLICATION_ID + ".DBNAME";
    public static final String LANGUAGE = BuildConfig.APPLICATION_ID + ".LANGUAGE";
    public static final String USER_ID = BuildConfig.APPLICATION_ID + ".USER_ID";
    public static final String LOGIN_TOKEN = BuildConfig.APPLICATION_ID + ".LOGIN_TOKEN";
    public static final String ALLOW_COLLECTION = BuildConfig.APPLICATION_ID + ".ALLOW_COLLECTION";
    public static final String IS_ACTUAL = BuildConfig.APPLICATION_ID + ".IS_ACTUAL";
    public static final String APP_UPDATE_URL = BuildConfig.APPLICATION_ID + ".APP_UPDATE_URL";
    public static boolean appInstalledOrNot(@NonNull final Context context, @NonNull final String targetPackage) {
        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> pkgAppsList =
                context.getPackageManager().queryIntentActivities( mainIntent, 0);
        for (ResolveInfo resolveInfo : pkgAppsList) {
            Log.d("TAG", "PACKAGENAME"+resolveInfo.activityInfo.packageName);
            if (resolveInfo.activityInfo.packageName != null
                    && resolveInfo.activityInfo.packageName.equals(targetPackage)) {
                return true;
            }
        }
        return false;
    }
    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setTitle("Paisalo Digital (" + BuildConfig.VERSION_NAME + ")");

        listCReatorInteraction=ActivityLogin.this;
//        boolean isAppInstalled = appInstalledOrNot(this,"com.plcoding.backgroundlocationtracking");
//        if(isAppInstalled) {
//
//            Toast.makeText(this, "Installed", Toast.LENGTH_SHORT).show();
//            Log.i("SampleLog", "Application is already installed.");
//        } else {
//
//            Setup setup=new Setup(ActivityLogin.this);
//            setup.run();
//            Log.i("SampleLog", "Application is not currently installed.");
//        }
        if (requestPermissions()){
            getDeviceID();
            GpsTracker gpsTracker=new GpsTracker(getApplicationContext());
        }else {
            permissionCheck();
        }

        btnShareDeviceID = findViewById(R.id.btnLoginShareDeviceId);
        versionText = findViewById(R.id.versionText);
        btnShareDeviceID.setEnabled(false);
        checkBtnShareIDStatus(false);

        versionText.setText("Ver: "+BuildConfig.VERSION_NAME);

        btnShareDeviceID.setTextColor(getResources().getColor(R.color.defaultTextColor));
        btnShareDeviceID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                UserName = ((EditText) findViewById(R.id.til_login_username)).getText().toString();

                if ( requestPermissions()){
                    getDeviceID();
                }else {
                    permissionCheck();
                }

                Log.e("DEVICEID_NEW", IglPreferences.getPrefString(ActivityLogin.this, DEVICE_ID, "") + "\nUserName = " + userName.getText().toString().toUpperCase() + "");
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "For Mobile Registration\nDevice ID = " + IglPreferences.getPrefString(ActivityLogin.this, DEVICE_ID, "") + "\nUserName = " + userName.getText().toString().toUpperCase());
                sendIntent.setType("text/plain");

//                Intent shareIntent = Intent.createChooser(sendIntent, null);
//                startActivity(shareIntent);
                DeviceMappingRequests(IglPreferences.getPrefString(ActivityLogin.this, DEVICE_ID, ""),userName.getText().toString().toUpperCase());

            }
        });

        btnSignIn = findViewById(R.id.BtnSaveKYCData);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                UserName = ((EditText) findViewById(R.id.til_login_username)).getText().toString().trim();
                Password = ((EditText) findViewById(R.id.etLoginPassword)).getText().toString().trim();

                if (requestPermissions()){
                    getDeviceID();
                }else {
                    permissionCheck();
                }

                Log.d("TAG",IglPreferences.getPrefString(ActivityLogin.this, SEILIGL.DATABASE_NAME, ""));
                Log.d("TAG",IglPreferences.getPrefString(ActivityLogin.this, SEILIGL.DEVICE_ID, ""));
                Log.d("TAG",SEILIGL.USER_ID+"");


                dataAsyncResponseHandler2 = new DataAsyncResponseHandler(ActivityLogin.this, "Logging in ...") {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        String jsonString = new String(responseBody);
                        Log.d("Login Resp",jsonString);
                        JSONObject jo;
                        JSONArray ja;
                        //AlertDialog alertDialog = new AlertDialog.Builder(ActivityLogin.this).create();
                        try {
                            jo = new JSONObject(jsonString);

                            JSONObject loginData = new JSONObject(jo.getString("LoginData"));
                            jo.remove("LoginData");

                            Type listType = new TypeToken<List<Manager>>() {
                            }.getType();
                            List<Manager> managers = WebOperations.convertToObjectArray(loginData.getString("folist"), listType);
                            loginData.remove("folist");
                            //Log.d("Login Resp",loginData.toString());
                            

                        /*IglPreferences.removePref(getBaseContext(), SEILESign.APP_SETTINGS);
                        IglPreferences.removePref(getBaseContext(), SEILESign.IS_ACTUAL);
                        IglPreferences.removePref(getBaseContext(), SEILESign.DEVICE_IMEI);
                        IglPreferences.removePref(getBaseContext(), SEILESign.LOGIN_TOKEN);
                        IglPreferences.removePref(getBaseContext(), SEILESign.USER_ID);
                        IglPreferences.removePref(getBaseContext(), SEILESign.APP_UPDATE_URL);*/

                            IglPreferences.setSharedPref(getBaseContext(), "tokenEsign", jo.toString());

                            dataAsyncResponseHandler = new DataAsyncResponseHandler(ActivityLogin.this, "Logging in ...") {
                                @Override
                                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                                    String jsonString = new String(responseBody);
                                    Log.e("TAG", "onSuccess: "+"++++++++++++++++++++++++++++++==============="+getRequestURI());
                                    Log.e("TAG", "onSuccess: "+responseBody+headers);
                                    Log.d("Login Resp",jsonString);
                                    JSONObject jo;
                                    JSONArray ja;
                                    try {
                                        jo = new JSONObject(jsonString);

                                        Type listType = new TypeToken<List<Manager>>() {
                                        }.getType();

                                        JSONObject loginData = new JSONObject(jo.getString("LoginData"));
                                        jo.remove("LoginData");

                                        //JSONObject json = new JSONObject(String.valueOf(loginData.getJSONArray("folist")));

                                        JSONArray jArray = loginData.getJSONArray("folist");

                                        JSONObject jsonObject = (JSONObject) jArray.get(0);
                                        Log.d("CHeckingLoginValue", jsonObject.getString("FOCode"));
                                        IglPreferences.setSharedPref(getBaseContext(), SEILIGL.LOGIN_TOKEN, jo.toString());

                                        //Log.d("Token", jo.toString());
                                        JSONObject foImei = loginData.getJSONObject("foImei");
                                        String branchCode = jsonObject.getString("FOCode");
                                        String Creator = jsonObject.getString("Creator");
                                        String IMEI_NO = jsonObject.getString("IMEINO");
                                        loginData.remove("foImei");
                                        Log.d("TAG", "onSuccess allow: "+foImei.getString("SIMNO"));

                                        IglPreferences.setSharedPref(getBaseContext(), SEILIGL.USER_ID, UserName);
                                        IglPreferences.setSharedPref(getBaseContext(), SEILIGL.CREATOR, Creator);
                                        IglPreferences.setSharedPref(getBaseContext(), SEILIGL.IMEI, IMEI_NO+"");
                                        IglPreferences.setSharedPref(getBaseContext(), SEILIGL.BRANCH_CODE, branchCode);
                                        IglPreferences.setSharedPref(getBaseContext(), SEILIGL.ALLOW_COLLECTION, foImei.getString("SIMNO"));
                                        //IglPreferences.setSharedPref(getBaseContext(),SEILIGL.DATABASE_NAME,foImei.getString("actualYN").equals("N")?"IGLDIG_TEST_PL":"IGLDIG_PL");
                                        IglPreferences.setSharedPref(getBaseContext(), SEILIGL.IS_ACTUAL, foImei.getString("actualYN"));
                                        IglPreferences.setSharedPref(getBaseContext(), SEILIGL.DEVICE_IMEI, foImei.getString("IMEINO"));

                                        List<Manager> managers = WebOperations.convertToObjectArray(loginData.getString("folist"), listType);

                                        int version = foImei.getInt("NewAppVerison");
                                        Log.e("NewVersionCheck",version+"");
                                        Log.e("NewVersionCheck",BuildConfig.VERSION_CODE+"");
                                        if (version > BuildConfig.VERSION_CODE) {
//                            String appPath = foImei.getString("AppDownPath");
//                            if (appPath.toUpperCase().startsWith("ID")) {
//                                //https://docs.google.com/uc?export=download&id=
//                                //
//                                appPath = "https://drive.google.com/uc?export=download&" + appPath;
//                                IglPreferences.setSharedPref(getBaseContext(), SEILIGL.APP_UPDATE_URL, appPath);
//                            }
//                            else {
//                                IglPreferences.setSharedPref(getBaseContext(), SEILIGL.APP_UPDATE_URL, appPath);
//                            }
//
//
//                            final AlertDialog alertDialog = new AlertDialog.Builder(ActivityLogin.this)
//                                    .setTitle("Loan Financing")
//                                    .setMessage("New version of this app is available\nWant to install it now?")
//                                    .setPositiveButton("Yes", null)
//                                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                                        public void onClick(DialogInterface dialog, int which) {
//                                            dialog.dismiss();
//                                            finish();
//                                        }
//                                    }).create();
//                            alertDialog.show();
//                            final Button btnPositive = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
//                            btnPositive.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View view) {
//                                    view.setEnabled(false);
//                                    alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setEnabled(false);
//                                    alertDialog.setMessage("Downloading update, please wait.");
//                                    updateApplication(alertDialog);
//                                }
//                            });
                                            if (foImei.getString("RequestUrl").length() > 4) {
                                                //IglPreferences.setSharedPref(getBaseContext(), SEILIGL.BASE_URL, foImei.getString("RequestUrl"));
                                                //(new WebOperations()).getAccessToken(context,UserName, Password, reloginResponseHandler);
                                                updateReloginToken(foImei.getString("RequestUrl"), UserName, Password);
                                            }
                                            //updateManager( managers);
                                            Intent intent = new Intent(ActivityLogin.this, ActivityOperationSelect.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                        else {
                                            if (foImei.getString("RequestUrl").length() > 4) {
                                                //IglPreferences.setSharedPref(getBaseContext(), SEILIGL.BASE_URL, foImei.getString("RequestUrl"));
                                                //(new WebOperations()).getAccessToken(context,UserName, Password, reloginResponseHandler);
                                                updateReloginToken(foImei.getString("RequestUrl"), UserName, Password);
                                            }
                                            //updateManager( managers);
                                            Intent intent = new Intent(ActivityLogin.this, ActivityOperationSelect.class);
                                            //Intent intent = new Intent(ActivityLogin.this, AttendenceActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    } catch (JSONException e) {
                                        Toast.makeText(ActivityLogin.this, "Manager List Not Mapped Kindly Contact to Your Area Head", Toast.LENGTH_LONG).show();
                                        e.printStackTrace();
                                    }
                                }
                                @Override
                                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                                    super.onFailure(statusCode, headers, responseBody, error);
                                    Log.e("TAG", "onFailure: "+"++++++++++++++++++++++++++++++==============="+getRequestURI()+getRequestHeaders());
                                    Log.e("TAG", "onFailure: "+responseBody+error.getMessage());
                                }
                            };
                            (new WebOperations()).getAccessToken(ActivityLogin.this, UserName, Password, dataAsyncResponseHandler);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                (new WebOperations()).getAccessTokenEsign(ActivityLogin.this, UserName, Password, dataAsyncResponseHandler2);
            }
        });
        btnSignIn.setEnabled(false);

        //Displaying TextInputLayout Error
        userName = (EditText) findViewById(R.id.til_login_username);
        //tilUserName.setErrorEnabled(true);
        userName.setError("Must be 10 Characters to share Device ID");

        password = (EditText) findViewById(R.id.etLoginPassword);
        //tilPassowrd.setErrorEnabled(true);
        password.setError("Min 5 chars required");
        //Displaying EditText Error
        database = (Spinner) findViewById(R.id.database);
        userName.setError("Required");
        userName.addTextChangedListener(new MyTextWatcher(userName) {
            @Override
            public void validate(EditText editText, String text) {
                btnShareDeviceID.setEnabled(false);
                checkBtnShareIDStatus(false);
                editText.setError(null);

                switch (text.length()) {
                    case 0:
                        editText.setError("Required");
                        break;
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                        editText.setError("Min 5 chars required");
                        break;
                    case 10:
                        btnShareDeviceID.setEnabled(true);
                        checkBtnShareIDStatus(true);
                        break;
                }
                if (validateInputs()) {
                    btnSignIn.setEnabled(true);
                } else {
                    btnSignIn.setEnabled(false);
                }
            }
        });

        password.setError("Required");
        password.addTextChangedListener(new MyTextWatcher(password) {
            @Override
            public void validate(EditText editText, String text) {
                switch (text.trim().length()) {
                    case 0:
                        editText.setError("Required");
                        break;
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                        editText.setError("Min 5 chars required");
                        break;
                }
                if (validateInputs()) {
                    btnSignIn.setEnabled(true);
                } else {
                    btnSignIn.setEnabled(false);
                }
            }
        });

        TextView btnBackup = (TextView) findViewById(R.id.btnLoginBackup);
        btnBackup.setOnClickListener(this);
        btnBackup.setVisibility(BuildConfig.DEBUG ? View.VISIBLE : View.GONE);


        ArrayList<RangeCategory> DatabaseName = new ArrayList<>();
        DatabaseName.add(new RangeCategory("SBI COLENDING", "Database"));
        DatabaseName.add(new RangeCategory("PDL OWN", "Database"));
        DatabaseName.add(new RangeCategory("GROUP FINANCE", "Database"));
        DatabaseName.add(new RangeCategory("SBI PDL", "Database"));
        // DatabaseName.add(new RangeCategory("PNB COLENDING", "Database"));

        database.setAdapter(new AdapterListRange(this, R.layout.spinner_card_orange,DatabaseName));
        storageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "");
        apkPath = storageDir.getPath() + "/sourcing.apk";
        File destination = new File(apkPath);
        if (destination.exists()) destination.delete();


        database.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("SpinnerStrng",DatabaseName.get(i)+"");

                IglPreferences.removePref(getBaseContext(), DATABASE_NAME);


                if (DatabaseName.get(i).DescriptionEn.equals("IGL DIGITAL")) {
                    IglPreferences.setSharedPref(getBaseContext(), DATABASE_NAME, "IGLDIG_PL");
                }
                else  if (DatabaseName.get(i).DescriptionEn.equals("GROUP FINANCE")) {
                    IglPreferences.setSharedPref(getBaseContext(), DATABASE_NAME, "GROUPFIN_MFI");
                }
                else  if (DatabaseName.get(i).DescriptionEn.equals("SBI PDL")) {
                    IglPreferences.setSharedPref(getBaseContext(), DATABASE_NAME, "SBIPDL");
                }
                else if(DatabaseName.get(i).DescriptionEn.equals("SBI COLENDING")) {
                    IglPreferences.setSharedPref(getBaseContext(), DATABASE_NAME, "SBIPDLCOL");
                }
                else {
                   IglPreferences.setSharedPref(getBaseContext(), DATABASE_NAME, "PDL_OWN");
                }

                Log.e("CHECK_DB_DOC_NAME0",IglPreferences.getPrefString(ActivityLogin.this, SEILIGL.DATABASE_NAME, "")+"");

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    private void checkBtnShareIDStatus(boolean b) {
        if (b){
            btnShareDeviceID.setTextColor(getResources().getColor(R.color.blueTextColor));

        }else {
            btnShareDeviceID.setTextColor(getResources().getColor(R.color.defaultTextColor));

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_language, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Boolean retVal;
        switch (item.getItemId()) {
            case R.id.action_language_english:
                IglPreferences.setLanguage(getApplicationContext(), "en");
                reStartApplication();
                retVal = true;
                break;
            case R.id.action_language_hindi:
                retVal = true;
                IglPreferences.setLanguage(getApplicationContext(), "hi");
                reStartApplication();
                break;
            case R.id.action_sourcing_update:
                retVal = true;
                String url = "https://erpservice.paisalo.in:980/PDL.Mobile.Api/api/ApkApp/paisaloSourcingApp";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
                break;
            case R.id.action_nsdl_update:
                retVal = true;
                String url1 = "https://erpservice.paisalo.in:980/PDL.Mobile.Api/api/ApkApp/AndroidNSDL";
                Intent i1 = new Intent(Intent.ACTION_VIEW);
                i1.setData(Uri.parse(url1));
                startActivity(i1);
                break;

            case R.id.action_rdSer_update:
                retVal = true;
                String url2 = "https://erpservice.paisalo.in:980/PDL.Mobile.Api/api/ApkApp/AndroidRDService";
                Intent i2 = new Intent(Intent.ACTION_VIEW);
                i2.setData(Uri.parse(url2));
                startActivity(i2);
                break;
            default:
                retVal = super.onOptionsItemSelected(item);
        }
        return retVal;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.BtnSaveKYCData) {

        }
        if (view.getId() == R.id.btnLoginBackup) {
            startActivity(new Intent(this, TermAndPolicyPage.class));
            /*String aadharString="<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                    "<PrintLetterBarcodeData uid=\"344347946174\" name=\"Anoop Agarwal\" gender=\"M\" dateOfBirth=\"02-02-1979\" careOf=\"S/O Bal Kishan Agarwal\" building=\"7\" street=\"new avdhesh puri\" landmark=\"karmyogi\" locality=\"kamla nagar\" vtcName=\"Agra\" poName=\"Dayal Bagh\" districtName=\"Agra\" subDistrictName=\"Kiraoli\" stateName=\"Uttar Pradesh\" pincode=\"282005\"/>";
            Document doc= null;
            try {
                doc = Utils.parseXmlString(aadharString);
                NamedNodeMap nodeMap= AadharUtils.getAadharFields(doc);
                List<String> uidLIst=new ArrayList<>(Arrays.asList("u","uid"));
                String name=AadharUtils.getAadharFieldValue(nodeMap,uidLIst);
                Log.d("FieldName",name);

            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            }*/
//
//            File newFIle = new File(Environment.getExternalStorageDirectory().getPath() + "/" + "PAISALO_SRC_DB" + "_bkp.db");
//            //File newFIle = new File(Environment.getExternalStorageDirectory().getPath() + "/test/mobilelending.db");
//            //Log.d("Backup File",newFIle.getAbsolutePath());
//            File DbFile = new File(FlowManager.getContext().getDatabasePath("PAISALO_SRC_DB" + ".db").getPath());
//            //Log.d("Database File",DbFile.getPath());
//            try {
//                Utils.copyFile(DbFile, newFIle);
//                Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
//                intent.setData(Uri.fromFile(newFIle));
//                getApplicationContext().sendBroadcast(intent);
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }
        if (view.getId() == R.id.btnLoginShareDeviceId) {
            /*TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            if (ActivityCompat.checkSelfPermission(ActivityLogin.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(ActivityLogin.this, new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_CODE);
                return;
            }
            String IMEINumber = telephonyManager.getImei();*/

            Log.e("DEVICEID_NEW", IglPreferences.getPrefString(this, DEVICE_ID, "") + "\nUserName = " + userName.getText().toString().toUpperCase() + "");
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "For Mobile Registration\nDevice ID = " + IglPreferences.getPrefString(this, DEVICE_ID, "") + "\nUserName = " + userName.getText().toString().toUpperCase());
            sendIntent.setType("text/plain");
            Intent shareIntent = Intent.createChooser(sendIntent, null);
            startActivity(shareIntent);
        }
    }

    private void updateReloginToken(String newUrl, String UserName, String Password) {
        IglPreferences.setSharedPref(getBaseContext(), SEILIGL.BASE_URL, newUrl);

        DataAsyncResponseHandler reloginResponseHandler = new DataAsyncResponseHandler(this) {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String jsonString = new String(responseBody);
                //Log.d("Login Resp",jsonString);
                JSONObject jo;
                try {
                    jo = new JSONObject(jsonString);
                    jo.remove("LoginData");
                    IglPreferences.setSharedPref(getBaseContext(), SEILIGL.LOGIN_TOKEN, jo.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        (new WebOperations()).getAccessToken(this, UserName, Password, reloginResponseHandler);

    }

    private void reStartApplication() {
        Intent i = getBaseContext().getPackageManager()
                .getLaunchIntentForPackage(getBaseContext().getPackageName());
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
    }

    private void updateManager(List<Manager> managers) {
        SQLite.delete().from(Manager.class).query();
        for (Manager manager : managers) {
            manager.insert();
        }
        Snackbar.make(findViewById(android.R.id.content), "Managers List Updated", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
        Intent intent = new Intent(ActivityLogin.this, ActivityManagerSelect.class);
        startActivity(intent);
        finish();
    }

    private boolean validateInputs() {
        if (userName.getText().length() < 5) {
            return false;
        }
        if (password.getText().length() < 5) {
            return false;
        }
        return true;
    }

    private void updateApplication(final AlertDialog dialog) {

        final String url = IglPreferences.getPrefString(getBaseContext(), SEILIGL.APP_UPDATE_URL, "");

        if (!url.equals("")) {
            if (!storageDir.exists()) {
                if (!storageDir.mkdirs()) {
                    Log.d("Dir", "Cannot make directory");
                }
            }
            final File destination = new File(apkPath);
            if (destination.exists()) destination.delete();
            final Uri uri = Uri.parse("file://" + destination);

            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
            request.setDescription("Downloading Application Update ...");
            request.setTitle(ActivityLogin.this.getString(R.string.app_name));

            //set destination
            request.setDestinationUri(uri);

            // get download service and enqueue file
            final DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
            final long downloadId = manager.enqueue(request);
            showDownloadProgress(downloadId, dialog);
            //set BroadcastReceiver to install app when .apk is downloaded
            BroadcastReceiver onComplete = new BroadcastReceiver() {
                public void onReceive(Context ctxt, Intent intent) {
                    Intent install = new Intent(Intent.ACTION_VIEW);
                    install.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    install.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    install.putExtra(Intent.EXTRA_NOT_UNKNOWN_SOURCE, true);
                    install.setDataAndType(FileProvider.getUriForFile(ActivityLogin.this,
                                    ActivityLogin.this.getApplicationContext().getPackageName() + ".provider",
                                    destination),
                            manager.getMimeTypeForDownloadedFile(downloadId));
                    startActivity(install);

                    unregisterReceiver(this);
                    finish();
                }
            };
            //register receiver for when .apk download is compete
            registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        }
    }

    @SuppressLint("Range")
    private void showDownloadProgress(final long downloadId, final AlertDialog alertDialog) {
        final DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean downloading = true;
                while (downloading) {
                    DownloadManager.Query q = new DownloadManager.Query();
                    q.setFilterById(downloadId);
                    Cursor cursor = manager.query(q);
                    cursor.moveToFirst();

                    int bytes_downloaded = cursor.getInt(cursor
                            .getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                    int bytes_total = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));

                    if (cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)) == DownloadManager.STATUS_SUCCESSFUL) {
                        downloading = false;
                        alertDialog.dismiss();
                    }
                    if (bytes_total > 0) {
                        final int dl_progress = (int) ((bytes_downloaded * 100L) / bytes_total);
                        alertDialog.setMessage(dl_progress + " % downloaded");
                    } else {
                        alertDialog.setMessage(bytes_downloaded / 1024 + " KB downloaded");
                    }
                    cursor.close();
                }
            }
        }).start();
    }

    private  boolean checkAndRequestPermissions() {
        int permissionSendMessage = ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA);
        int locationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        int readPhoneState = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (locationPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (permissionSendMessage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);

        } if (readPhoneState != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),1111);
            return false;
        }
        return true;
    }

    private boolean requestPermissions() {
        final boolean[] perMissionFlag = {false};
        // below line is use to request permission in the current activity.
        // this method is use to handle error in runtime permissions
        Dexter.withActivity(this)
                // below line is use to request the number of permissions which are required in our app.
                .withPermissions(Manifest.permission.CAMERA,
                        // below is the list of permissions
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.READ_PHONE_STATE)
                // after adding permissions we are calling an with listener method.
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                        // this method is called when all permissions are granted
                        if (multiplePermissionsReport.areAllPermissionsGranted()) {
                            // do you work now
                            permissionCheck();
                            perMissionFlag[0] =true;

                        }
                        // check for permanent denial of any permission
                        if (multiplePermissionsReport.isAnyPermissionPermanentlyDenied()) {
                            // permission is denied permanently, we will show user a dialog message.
                            showSettingsDialog();
                            perMissionFlag[0] =false;

                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                        // this method is called when user grants some permission and denies some of them.
                        permissionToken.continuePermissionRequest();
                    }
                }).withErrorListener(error -> {
                    // we are displaying a toast message for error message.
                    Toast.makeText(getApplicationContext(), "Error occurred! ", Toast.LENGTH_SHORT).show();
                    handlePermissionException();
                    perMissionFlag[0] =false;

                })
                // below line is use to run the permissions on same thread and to check the permissions
                .onSameThread().check();
        return perMissionFlag[0];


    }

    // below is the shoe setting dialog method which is use to display a dialogue message.
    private void showSettingsDialog() {
        // we are displaying an alert dialog for permissions
        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityLogin.this);
        // below line is the title for our alert dialog.
        builder.setTitle("Need Permissions");
        // below line is our message for our dialog
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", (dialog, which) -> {
            // this method is called on click on positive button and on clicking shit button
            // we are redirecting our user from our app to the settings page of our app.
            dialog.cancel();
            // below is the intent from which we are redirecting our user.
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", getPackageName(), null);
            intent.setData(uri);
            startActivityForResult(intent, 101);
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> {
            // this method is called when user click on negative button.
            dialog.cancel();
            handlePermissionException();
        });
        // below line is used to display our dialog
        builder.show();
    }


    private void permissionCheck() {
        String[] permissions = {
                Manifest.permission.CAMERA,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION};
        String rationale = "Please provide permission so that app can work smoothly ...";
        Permissions.Options options = new Permissions.Options()
                .setRationaleDialogTitle("Info")
                .setSettingsDialogTitle("Warning");

        Permissions.check(this/*context*/, permissions, rationale, options, new PermissionHandler() {
            @Override
            public void onGranted() {
                // do your task.

//                if (ActivityCompat.checkSelfPermission(ActivityLogin.this,
//                        Manifest.permission.READ_PHONE_STATE)
//                        != PackageManager.PERMISSION_GRANTED) {
//                    handlePermissionException();
//                }
//                else {
                getDeviceID();
//                }

                // If you have access to the external storage, do whatever you nee
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//                    if (Environment.isExternalStorageManager()){
//
//                       getDeviceID();
//
//    // If you don't have access, launch a new activity to show the user the system's dialog
//    // to allow access to the external storage
//                    }else{
//                      handlePermissionException();
//                    }
//                }

            }

            @Override
            public void onDenied(Context context, ArrayList<String> deniedPermissions) {
                // permission denied, block the feature.
                //permissionCheck();
                handlePermissionException();
            }
        });
    }

    private void getDeviceID() {
        deviceImei = 0;
        getDeviceIMEI();

        Log.d("checkIDDD", deviceId + "");
        if (deviceId.length() > 8) {

            if (deviceImei > 0) {
                IglPreferences.setSharedPref(getBaseContext(), DEVICE_IMEI, deviceImei);
            }
            IglPreferences.setSharedPref(getBaseContext(), BASE_URL, BuildConfig.BASE_URL);

        }
    }


    private void handlePermissionException() {
        InstalledAppDetailsActivity(this);
        System.exit(0);
    }


    private void InstalledAppDetailsActivity(ActivityLogin activityLogin) {

        if (activityLogin == null) {
            return;
        }

        Intent intent = new Intent();
        //intent.setAction(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getApplicationContext().getPackageName(), null);
        intent.setData(uri);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        startActivity(intent);

    }

    @SuppressLint("HardwareIds")
    public void getDeviceIMEI() {

//        deviceId = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        String lastThreeChars = "";

        try {
            if (UserName.trim().length() > 3) {
                lastThreeChars = UserName.trim().substring(UserName.trim().length() - 3);
            } else {
                lastThreeChars = UserName;
            }
        }catch (Exception e)
        {
            //  Toast.makeText(this, "Please Enter User Id for getting device Id", Toast.LENGTH_SHORT).show();

        }
        deviceId = lastThreeChars + //we make this look like a valid IMEI
                Build.BOARD.length()%10+ Build.BRAND.length()%10 +
                Build.CPU_ABI.length()%10 + Build.DEVICE.length()%10 +
                Build.DISPLAY.length()%10 + Build.HOST.length()%10 +
                Build.ID.length()%10 + Build.MANUFACTURER.length()%10 +
                Build.MODEL.length()%10 + Build.PRODUCT.length()%10 +
                Build.TAGS.length()%10 + Build.TYPE.length()%10 +
                Build.USER.length()%10 ; //13 digits

        Log.e("DirectoryDeviceID", deviceId + "");

//        try {
//            File dir = Utils.getFilePath("/", false);
//
//            Log.e("DirectoryDeviceID", dir + "");
//            File file = new File(dir, "." + BuildConfig.ESIGN_APP + ".info");
//            Log.e("FileLocation", file + "");
//            OutputStream out = new FileOutputStream(file);
//            out.write(deviceId.getBytes());
//
//            Log.e("DeviceIDWrite", Arrays.toString(deviceId.getBytes()) + "");
//            Log.e("DeviceIDWrite0", deviceId + "");
//            out.flush();
//            out.close();
//
//            //Utils.writeBytesToFile(deviceId.getBytes(),file);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        IglPreferences.setSharedPref(getBaseContext(), DEVICE_ID, deviceId);


    }

    private void DeviceMappingRequests(String deviceID, String UserID){


        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityLogin.this);
        View dialogView = getLayoutInflater().inflate(R.layout.mappingpopup_layout, null);
        builder.setView(dialogView);

        AlertDialog dialogs = builder.create();
        dialogs.setCanceledOnTouchOutside(false);
        dialogs.setCancelable(false);

        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

        dialogs.show();

        ArrayList<String> arrayList;
        final Dialog[] dialog = new Dialog[1];
        EditText name = dialogView.findViewById(R.id.nameET);
        EditText mobile = dialogView.findViewById(R.id.mobileET);
        EditText imei = dialogView.findViewById(R.id.imeiET);
        EditText imeiET2 = dialogView.findViewById(R.id.imeiET2);
        TextView deviceId = dialogView.findViewById(R.id.deviceIdET);
        TextView userId = dialogView.findViewById(R.id.userIDET);
        TextView creators = dialogView.findViewById(R.id.selectcreator);
        TextView errorTextView = dialogView.findViewById(R.id.errorTextView);
        EditText branchcodes = dialogView.findViewById(R.id.branchcodes);
        Spinner spinnerReq = dialogView.findViewById(R.id.spinner);

        deviceId.setText(deviceID);
        userId.setText(UserID);

        creators.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                dialogSearch=new Dialog(ActivityLogin.this);
//                dialogSearch.setContentView(R.layout.dialog_searchable_spinner);
//                dialogSearch.getWindow().setLayout(650,800);
//                // dialogSearch.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                dialogSearch.show();

                showCreatorSearchDialog(creators);
                //startActivity(new Intent(ActivityLogin.this,CreatorActivity.class));




            }
        });


        Button save = dialogView.findViewById(R.id.saveButton);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GpsTracker gpsTracker=new GpsTracker(ActivityLogin.this);
                String Name = name.getText().toString();
                String Mobile = mobile.getText().toString();
                String Imei = imei.getText().toString();
                String Imei2 = imeiET2.getText().toString();
                String DeviceID = deviceId.getText().toString();
                String UserID = userId.getText().toString();
                String Creator = creators.getText().toString();
                String branchcode = branchcodes.getText().toString();

                String error = validateData(Name, Mobile, Imei, DeviceID, UserID,Creator,branchcode);
                if (error == null) {
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
                    JsonObject jsonObject=new JsonObject();
                    jsonObject.addProperty( "name", Name);
                    jsonObject.addProperty( "mobile", Mobile);
                    jsonObject.addProperty( "creator", choosedCreator);
                    jsonObject.addProperty( "compType", spinnerReq.getSelectedItem().toString());
                    jsonObject.addProperty( "deviceId", DeviceID);
                    jsonObject.addProperty( "imeI_no1", Imei);
                    jsonObject.addProperty( "imeI_no2", Imei2);
                    jsonObject.addProperty( "userId", UserID);
                    jsonObject.addProperty( "mapBranch", branchcodes.getText().toString());
                    jsonObject.addProperty( "latitude", String.valueOf(gpsTracker.getLatitude()));
                    jsonObject.addProperty( "longitude", String.valueOf(gpsTracker.getLongitude()));
                    Log.d("TAG", "onClick: "+jsonObject);

                    Call<JsonObject> call=apiInterface.insertDeviceData(jsonObject);
                    call.enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                            Log.d("TAG", "onResponse: "+response.body());
                            Toast.makeText(ActivityLogin.this, "Data is valid. Saving...", Toast.LENGTH_SHORT).show();

                            if (response.body()!=null){
                                if (response.body().get("statusCode").getAsInt()==200){
                                    Utils.alert(ActivityLogin.this,response.body().get("message").getAsString());
                                    dialogs.dismiss();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<JsonObject> call, Throwable t) {

                            Utils.alert(ActivityLogin.this,t.getMessage());


                        }
                    });


                } else {
                    Log.d("Error","MSG" + error);
                    errorTextView.setText(error);
                }

                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        });

        Button cancelButton =dialogView.findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogs.dismiss();
            }
        });
    }

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


        dialogSearch=new Dialog(ActivityLogin.this);
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
                adapter=new CreatorListAdapter(ActivityLogin.this,list,dialogSearch,listCReatorInteraction);
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

                filter(s.toString().toUpperCase());


            }
        });


        dialogSearch.show();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void filter(String s) {
        Log.d("TAG", "filter: "+s);
        List<CreatorModel> creatorModels=new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {

            if (list.get(i).getCreator().toUpperCase().contains(s.toString())) {
                creatorModels.add(list.get(i));
                Log.d("TAG", "filter: "+list.get(i));
            }
        }


        adapter.filterList(creatorModels);
        adapter.notifyDataSetChanged();
    }

    public String validateData(String name, String mobile, String imei, String deviceId, String userId,String creator,String branchcode) {
        if (name.isEmpty()) {
            return "Name is required.";
        }

        if (mobile.length() != 10) {
            return "Mobile number must be 10 digits long.";
        }

        if (imei.length() != 15) {
            return "IMEI must be 15 digits.";
        }

        if (deviceId.length() != 16) {
            return "Device ID must be 16 digit.";
        }

        if (!userId.matches("[A-Za-z]{4}\\d{6}")) {
            return "User ID should be in the format: ABCD123456";
        }

        if (creator.isEmpty()) {
            return "Creator is required.";
        }
        if (branchcode.isEmpty()) {
            return "Branch Code is required.";
        }
        return null; // No error
    }


    @Override
    public void onListCReatorInteraction(String choosedCreatora) {
        Log.d("TAG", "onListCReatorInteraction: "+choosedCreatora);
        choosedCreator=choosedCreatora;
    }
}
