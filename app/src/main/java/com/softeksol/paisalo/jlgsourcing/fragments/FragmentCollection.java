package com.softeksol.paisalo.jlgsourcing.fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonObject;
import com.softeksol.paisalo.jlgsourcing.BuildConfig;
import com.softeksol.paisalo.jlgsourcing.R;
import com.softeksol.paisalo.jlgsourcing.SEILIGL;
import com.softeksol.paisalo.jlgsourcing.Utilities.IglPreferences;
import com.softeksol.paisalo.jlgsourcing.Utilities.MyTextWatcher;
import com.softeksol.paisalo.jlgsourcing.Utilities.Utils;
import com.softeksol.paisalo.jlgsourcing.WebOperations;
import com.softeksol.paisalo.jlgsourcing.activities.ActivityCollection;
import com.softeksol.paisalo.jlgsourcing.activities.OnlinePaymentActivity;
import com.softeksol.paisalo.jlgsourcing.adapters.AdapterDueData;
import com.softeksol.paisalo.jlgsourcing.adapters.AdapterInstallment;
import com.softeksol.paisalo.jlgsourcing.adapters.AdapterInstallmentTemp;
import com.softeksol.paisalo.jlgsourcing.entities.DueData;
import com.softeksol.paisalo.jlgsourcing.entities.Installment;
import com.softeksol.paisalo.jlgsourcing.entities.InstallmentTemp;
import com.softeksol.paisalo.jlgsourcing.entities.PosInstRcv;
import com.softeksol.paisalo.jlgsourcing.handlers.DataAsyncResponseHandler;
import com.softeksol.paisalo.jlgsourcing.location.GpsTracker;
import com.softeksol.paisalo.jlgsourcing.retrofit.ApiClient;
import com.softeksol.paisalo.jlgsourcing.retrofit.ApiInterface;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import cz.msebera.android.httpclient.Header;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentCollection#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentCollection extends AbsCollectionFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_DB_NAME = "param1";
    private static final String ARG_DB_DESC = "param2";

    // TODO: Rename and change types of parameters
    private String mDbName;
    private String mDbDesc;
    private ListView lv;
    private boolean isDialogActive;
    private int collectionAmount;
    private int latePmtIntAmt;
    private boolean isProcessingEMI=false;
    String userid;
    private String SchmCode;
    private String SMCode;
    GpsTracker gpsTracker;
    Dialog dialogConfirm;
    ArrayList<InstallmentTemp> insttemplist;
    public FragmentCollection() {
        // Required empty public constructor
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param dbName Parameter 1.
     * @return A new instance of fragment FragmentCollection.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentCollection newInstance(String dbName, String dbDesc) {
        FragmentCollection fragment = new FragmentCollection();

        Bundle args = new Bundle();
        args.putString(ARG_DB_NAME, dbName);
        args.putString(ARG_DB_DESC, dbDesc);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mDbName = getArguments().getString(ARG_DB_NAME);
            mDbDesc = getArguments().getString(ARG_DB_DESC);
        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_collection, container, false);
        userid=IglPreferences.getPrefString(getContext(), SEILIGL.USER_ID, "");
        lv = (ListView) view.findViewById(R.id.lvDueData);
        gpsTracker=new GpsTracker(getContext());
        dialogConfirm = new Dialog(getContext());

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ArrayList<String> menuOptions = new ArrayList<>();
                menuOptions.add("EMI Paying");
                menuOptions.add("EMI Not Paying");

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                String[] mOptions = new String[menuOptions.size()];
                mOptions = menuOptions.toArray(mOptions);
                builder.setItems(mOptions, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (which==0){
                            showPaymentDialog(parent,position);
                        }else{
                            DialogForEMINotPaying(getContext(),parent,position);
                        }

                    }
                });
                builder.create().show();


            }
        });

        Log.e("MDatabaseName",mDbName+"");
        Log.e("MDB_DESC",mDbDesc+"");
        lv.setAdapter(new AdapterDueData(getContext(), R.layout.layout_item_collection, ((ActivityCollection) getActivity()).getDueDataByDbName(mDbName)));
        SearchView searchView = (SearchView) view.findViewById(R.id.searchViewDueData);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ((AdapterDueData) lv.getAdapter()).getFilter().filter(newText);
                return false;
            }
        });
        return view;
    }
    public  void DialogForEMINotPaying(Context context,AdapterView<?> parent,int position) {
        AdapterDueData adapterDueData = (AdapterDueData) parent.getAdapter();
        final DueData dueData = (DueData) adapterDueData.getItem(position);
        // Create a dialog view by inflating the layout XML
        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_layout_notpay_emi, null);

        // Initialize views from the layout
        Spinner spinEMIReasons=dialogView.findViewById(R.id.spinEMIReasons);
        ImageView imgViewCal = dialogView.findViewById(R.id.imgViewCal);
        TextInputEditText tietPromisingDate = dialogView.findViewById(R.id.tietPromisingDate);
        Button saveDataForEMINOTPAY=dialogView.findViewById(R.id.saveDataForEMINOTPAY);

        final DatePicker datePicker = dialogView.findViewById(R.id.datePicker);
        datePicker.setMinDate(System.currentTimeMillis());
        // Create a DatePickerDialog and set its date set listener
        final DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String selectedDate =( dayOfMonth<=9?"0"+dayOfMonth:dayOfMonth) + "-" + (monthOfYear<9?"0"+(monthOfYear + 1):(monthOfYear + 1) )+ "-" + year;
                tietPromisingDate.setText(selectedDate);
            }
        };

        imgViewCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show the DatePickerDialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(context, dateSetListener, 2023, 0, 1);
                datePickerDialog.show();
            }
        });

        // Create and configure the custom dialog
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );

        final androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(context);
        builder.setView(dialogView);
        builder.setCancelable(true);

        final androidx.appcompat.app.AlertDialog dialog = builder.create();
        saveDataForEMINOTPAY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tietPromisingDate.getText().length()<1){
                    Toast.makeText(context, "Please Choose date on Calender Icon Click", Toast.LENGTH_SHORT).show();
                    tietPromisingDate.setError("Please Choose date on Calender Icon Click");
                }else{
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
                    Log.d("TAG", "onClick: "+getRCPromisToPayJson(Utils.getNotNullText(tietPromisingDate),spinEMIReasons.getSelectedItem().toString(),dueData.getCreator(),dueData.getCustName(),dueData.getCaseCode(),dueData.getMobile(),dueData.getAadhar()));
                    Call<JsonObject> call=apiInterface.insertRcPromiseToPay(getRCPromisToPayJson(Utils.getNotNullText(tietPromisingDate),spinEMIReasons.getSelectedItem().toString(),dueData.getCreator(),dueData.getCustName(),dueData.getCaseCode(),dueData.getMobile(),dueData.getAadhar()));
                    call.enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                            Log.d("TAG", "onResponse: "+response.body());
                            Toast.makeText(context, "Data is valid. Saving...", Toast.LENGTH_SHORT).show();

                            if (response.body()!=null){
                                if (response.body().get("statusCode").getAsInt()==200){
                                    Utils.alert(context,response.body().get("message").getAsString());
                                    dialog.dismiss();
                                }
                            }else{
                                Utils.alert(context,response.body().get("message").getAsString());
                                dialog.dismiss();
                            }

                        }

                        @Override
                        public void onFailure(Call<JsonObject> call, Throwable t) {
                            Utils.alert(context,"Something went wrong!!");
                            dialog.dismiss();
                        }
                    });

                }
            }
        });
        dialog.show();
    }







    private void showPaymentDialog(AdapterView<?> parent,int position) {
        AdapterDueData adapterDueData = (AdapterDueData) parent.getAdapter();
        final DueData dueData = (DueData) adapterDueData.getItem(position);
        Log.d("DueData",dueData.toString());
        Log.d("DueDataSchmCode",dueData.getSchmCode());
        insttemplist=new ArrayList<>();
        SchmCode=dueData.getSchmCode();
        SMCode=dueData.getCaseCode();
        latePmtIntAmt=0;
        adapterDueData.notifyDataSetChanged();
        final int maxDue = dueData.getMaxDueAmount();
        final int latePaymentInterest = dueData.getInterestAmt();
        final List<Installment> installments = dueData.getInstData();

        final View dialogView = getActivity().getLayoutInflater().inflate(R.layout.layout_dialog_collect, null);
        final RadioGroup radioGroup = (RadioGroup) dialogView.findViewById(R.id.rgCollectionType);
        dialogView.setBackgroundResource(R.color.colorLightGreen);
        AppCompatButton cancel = (AppCompatButton) dialogView.findViewById(R.id.btnCollectRight);
        cancel.setText("Cancel");

        final AppCompatButton collect = (AppCompatButton) dialogView.findViewById(R.id.btnCollectLeft);
        collect.setText("Collect");
        collect.setEnabled(false);

        final LinearLayout llLatePayment = dialogView.findViewById(R.id.llLatePmtInterest);
        final Button onlinepayment = dialogView.findViewById(R.id.onlinepayment);
        final Button prossingFees = dialogView.findViewById(R.id.prossingFees);
        final CheckBox chkLatePayment = dialogView.findViewById(R.id.chkLatePmtInterest);
        final TextView tvLatePayAmount = dialogView.findViewById(R.id.tvLatePmtInterestAmt);
        if (latePaymentInterest > 0) {
            llLatePayment.setVisibility(View.VISIBLE);
            tvLatePayAmount.setText(String.valueOf(latePaymentInterest));
        }
        onlinepayment.setEnabled(false);
        onlinepayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int totCollectAmt;
                if(radioGroup.getCheckedRadioButtonId()==R.id.rbLumpSumAmount){
                    totCollectAmt=collectionAmount;
                }else{
                    totCollectAmt=collectionAmount+latePmtIntAmt;
                }

                Intent intent=new Intent(getContext(), OnlinePaymentActivity.class);
                intent.putExtra("Price",totCollectAmt);
                getActivity().startActivity(intent);
            }
        });
        if(isProcessingEMI==false){
            prossingFees.setVisibility(View.INVISIBLE);
        }
        prossingFees.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        final ToggleButton tglBtnPaidBy = (ToggleButton) dialogView.findViewById(R.id.tglPaidBy);
        tglBtnPaidBy.setVisibility(BuildConfig.APPLICATION_ID.equals("com.softeksol.paisalo.jlgsourcing") ? View.VISIBLE : View.GONE);
        tglBtnPaidBy.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                dialogView.setBackgroundResource(isChecked ? R.color.colorLightRed : R.color.colorLightGreen);
            }
        });

        final TextView tvSelectedCount = (TextView) dialogView.findViewById(R.id.tvCollectSelected);
        final TextView tvTotDue = (TextView) dialogView.findViewById(R.id.tvCollectTotalAmt);
        TextView tvSundary = (TextView) dialogView.findViewById(R.id.tvCollectSundry);
        //int total=dueData.getInstallmentSum(false);
        //final int dif =total-  dueData.getInstsAmtDue();
        tvSundary.setText(String.valueOf(dueData.getSundryBalance()));

        final TextInputEditText teitLumpSumAccount = (TextInputEditText) dialogView.findViewById(R.id.tietLumSumAmount);
        final TextInputEditText tietOtherEMIAmount = (TextInputEditText) dialogView.findViewById(R.id.tietOtherEMIAmount);
        final TextInputEditText tietPFAmount = (TextInputEditText) dialogView.findViewById(R.id.tietPFAmount);
        final TextInputEditText tietEMIAmount = (TextInputEditText) dialogView.findViewById(R.id.tietEMIAmount);
        final TextInputLayout tilLumpsumAccount = (TextInputLayout) dialogView.findViewById(R.id.tilLumSumAmount);
        final LinearLayout linearLayout16 = (LinearLayout) dialogView.findViewById(R.id.linearLayout16);

        tietEMIAmount.addTextChangedListener(new MyTextWatcher(tietEMIAmount) {
            @Override
            public void validate(EditText editText, String text) {

                teitLumpSumAccount.setText(String.valueOf(Utils.getNotNullInt(tietOtherEMIAmount)+Utils.getNotNullInt(tietPFAmount)+Utils.getNotNullInt(tietEMIAmount)));

            }
        });
        tietPFAmount.addTextChangedListener(new MyTextWatcher(tietEMIAmount) {
            @Override
            public void validate(EditText editText, String text) {

                teitLumpSumAccount.setText(String.valueOf(Utils.getNotNullInt(tietOtherEMIAmount)+Utils.getNotNullInt(tietPFAmount)+Utils.getNotNullInt(tietEMIAmount)));

            }
        });
        tietOtherEMIAmount.addTextChangedListener(new MyTextWatcher(tietEMIAmount) {
            @Override
            public void validate(EditText editText, String text) {

                teitLumpSumAccount.setText(String.valueOf(Utils.getNotNullInt(tietOtherEMIAmount)+Utils.getNotNullInt(tietPFAmount)+Utils.getNotNullInt(tietEMIAmount)));

            }
        });



        teitLumpSumAccount.addTextChangedListener(new MyTextWatcher(teitLumpSumAccount) {
            @Override
            public void validate(EditText editText, String text) {
                int amt = Utils.getNotNullInt(editText);
                collectionAmount = amt;
                tvTotDue.setText(text);
                collect.setEnabled(false);
                onlinepayment.setEnabled(false);

                if (amt < 1) {
                    editText.setError("Amount should be greater than or equals to 1");
                    return;
                }
                if (amt > maxDue) {
                    editText.setError("Amount should be less than or equals to " + maxDue);
                    return;
                }
                //collectEnableDisable(latePaymentInterest, collect, tvTotDue);
                collect.setEnabled(true);
                onlinepayment.setEnabled(true);

                editText.setError(null);
            }
        });

        tilLumpsumAccount.setVisibility(View.INVISIBLE);
        linearLayout16.setVisibility(View.GONE);
        final ListView lvc = (ListView) dialogView.findViewById(R.id.lvcCollectInstallments);
        lvc.setItemsCanFocus(false);
        lvc.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        lvc.setAdapter(new AdapterInstallment(getContext(), R.layout.layout_item_installment, installments));
        lvc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //System.out.println("clicked" + position);
                Installment installment = (Installment) parent.getItemAtPosition(position);
                installment.setSelected(!installment.isSelected());
                CheckBox chb = (CheckBox) view.findViewById(R.id.cbItemInstallmentSelected);
                chb.setChecked(installment.isSelected());
                int selectedCount = DueData.getSelectedCount(installments);
                int selectedAmount = DueData.getInstallmentSum(installments, true);
                collectionAmount = selectedAmount;
                tvSelectedCount.setText(String.valueOf(selectedCount));

                collect.setEnabled(collectionAmount + latePmtIntAmt> (latePmtIntAmt>0?latePmtIntAmt-1:0));
                onlinepayment.setEnabled(collectionAmount + latePmtIntAmt> (latePmtIntAmt>0?latePmtIntAmt-1:0));

                tvTotDue.setText(String.valueOf(collectionAmount+ latePmtIntAmt));
                if(installment.isSelected()==true){
                    InstallmentTemp intemp=new InstallmentTemp();
                    intemp.setAmount(installment.getAmount());
                    intemp.setDueDate(installment.getDueDate());
                    insttemplist.add(intemp);
                }else{
                    insttemplist.remove(position);
                }

            }
        });

        chkLatePayment.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    latePmtIntAmt =latePaymentInterest;
                }else{
                    latePmtIntAmt=0;
                }
                if(radioGroup.getCheckedRadioButtonId()==R.id.rbLumpSumAmount) {
                    tvTotDue.setText(String.valueOf(collectionAmount));
                }else {
                    tvTotDue.setText(String.valueOf(collectionAmount+latePmtIntAmt));
                }
                collect.setEnabled(collectionAmount> (latePmtIntAmt>0?latePmtIntAmt-1:0));
                onlinepayment.setEnabled(collectionAmount> (latePmtIntAmt>0?latePmtIntAmt-1:0));

            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Select on or more Installments");
        builder.setView(dialogView);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                chkLatePayment.setEnabled(true);
                chkLatePayment.setChecked(false);
                switch (checkedId) {
                    case R.id.rbFixedAmount:
                        lvc.setEnabled(true);
                        tilLumpsumAccount.setVisibility(View.INVISIBLE);
                        linearLayout16.setVisibility(View.GONE);
                        teitLumpSumAccount.setText("0");
                        break;
                    case R.id.rbLumpSumAmount:
                        dueData.clearSection(installments);
                        tvSelectedCount.setText("0");
                        lvc.setEnabled(false);
                        ((AdapterInstallment) lvc.getAdapter()).notifyDataSetChanged();
                        tilLumpsumAccount.setVisibility(View.VISIBLE);
                        linearLayout16.setVisibility(View.VISIBLE);
                        tilLumpsumAccount.setHint(getResources().getString(R.string.lump_sum_amount) + " (Max " + maxDue + ")");
                        collect.setEnabled(false);
                        onlinepayment.setEnabled(false);
                        teitLumpSumAccount.setText("0");
                        break;
                }
            }
        });

        final AlertDialog dialog = builder.create();
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        collect.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                dueData.setEnabled(false);
                collect.setEnabled(false);
                onlinepayment.setEnabled(false);
                int totCollectAmt;
                if(radioGroup.getCheckedRadioButtonId()==R.id.rbLumpSumAmount){
                    totCollectAmt=collectionAmount;
                }else{
                    totCollectAmt=collectionAmount+latePmtIntAmt;
                }
                if(radioGroup.getCheckedRadioButtonId()==R.id.rbLumpSumAmount) {
                    saveDepositeWithPFAndEmiAmount(dueData.getCreator(), dueData.getCustName(), dueData.getCaseCode(), dueData.getMobile(), totCollectAmt, Utils.getNotNullInt(tietEMIAmount), Utils.getNotNullInt(tietPFAmount), Utils.getNotNullInt(tietOtherEMIAmount));
                }


               // ConfirmationDialog(totCollectAmt,latePmtIntAmt);

                ////////////////////////////////////////////////////////// ALERT /////////////////////////////////////////////////////

                dialogConfirm.setContentView(R.layout.dialogconfirmation_layout);
                dialogConfirm.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialogConfirm.setCancelable(false);
                // dialogConfirm.getWindow().getAttributes().windowAnimations = R.style.animation;

                ListView  list_dialog=dialogConfirm.findViewById(R.id.list_dialog);
                TextView text_instamt = dialogConfirm.findViewById(R.id.text_instamt);
                TextView text_totalamt = dialogConfirm.findViewById(R.id.text_totalamt);
                list_dialog.setAdapter(new AdapterInstallmentTemp(getContext(), R.layout.layout_item_installmenttemp, insttemplist));
                text_totalamt.setText(totCollectAmt+"");
                text_instamt.setText(latePmtIntAmt+"");

                Button btnSave = dialogConfirm.findViewById(R.id.button_saveEmi);
                Button btncancel = dialogConfirm.findViewById(R.id.button_Closedialog);

                btnSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        dialogConfirm.dismiss();
                        saveDeposit(SchmCode,dueData, totCollectAmt,latePmtIntAmt,tglBtnPaidBy.isChecked() ? "F" : "B");
                        //Toast.makeText(MainActivity.this, "okay clicked", Toast.LENGTH_SHORT).show();
                    }
                });

                btncancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogConfirm.dismiss();
                        // Toast.makeText(MainActivity.this, "Cancel clicked", Toast.LENGTH_SHORT).show();
                    }
                });

                dialogConfirm.show();





            }
        });
        dialog.show();
    }

    private void saveDepositeWithPFAndEmiAmount(String creator, String custName, String caseCode, String mobile, int totCollectAmt, int EmiAmt, int pfAmt, int otherAmt) {
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
        Log.d("TAG", "saveDepositeWithPFAndEmiAmount: "+getJsonOfRcDist(creator,custName,caseCode,mobile,totCollectAmt,EmiAmt,pfAmt,otherAmt));
        Call<JsonObject> call=apiInterface.insertRcDistribution(getJsonOfRcDist(creator,custName,caseCode,mobile,totCollectAmt,EmiAmt,pfAmt,otherAmt));
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.d("TAG", "onResponse: "+response.body());
                Toast.makeText(getContext(), "Data is valid. Saving...", Toast.LENGTH_SHORT).show();

                if (response.body()!=null){
                    if (response.body().get("statusCode").getAsInt()==200){
                        Utils.alert(getContext(),response.body().get("message").getAsString());

                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d("TAG", "onFailure: "+t.getMessage());

            }
        });
    }
    public  JsonObject getRCPromisToPayJson(String date, String reason, String creator, String custName, String caseCode, String mobile, String adhar) {
        SimpleDateFormat inputDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date gotDate = null;
        try {
            gotDate = inputDateFormat.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        SimpleDateFormat outputDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = outputDateFormat.format(gotDate);


        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String formattedDateCurrent = df.format(c);
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty( "id", 0);
        jsonObject.addProperty( "reason", reason);
        jsonObject.addProperty( "creator", creator);
        jsonObject.addProperty( "caseCode", caseCode);
        jsonObject.addProperty( "customerName", custName);
        jsonObject.addProperty( "aadhar", adhar);
        jsonObject.addProperty( "dateToPay", formattedDate);
        jsonObject.addProperty( "createdOn",  formattedDateCurrent);
        jsonObject.addProperty( "createdBy", userid);
        jsonObject.addProperty( "modifiedOn",formattedDateCurrent);
        jsonObject.addProperty( "modifiedBy", userid);

        return  jsonObject;
    }



    private JsonObject getJsonOfRcDist(String creator, String custName, String caseCode, String mobile, int totCollectAmt, int emiAmt, int pfAmt, int otherAmt) {
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("id", 0);
        jsonObject.addProperty("creator", creator);
        jsonObject.addProperty("foCode", "");
        jsonObject.addProperty("caseCode", caseCode);
        jsonObject.addProperty("customerName", custName);
        jsonObject.addProperty("mobile", mobile);
        jsonObject.addProperty("totalDue",String.valueOf( totCollectAmt));
        jsonObject.addProperty("userID", IglPreferences.getPrefString(getContext(), SEILIGL.USER_ID, ""));
        jsonObject.addProperty("emi", String.valueOf(emiAmt));
        jsonObject.addProperty("pf", String.valueOf(pfAmt));
        jsonObject.addProperty("others", String.valueOf(otherAmt));
        jsonObject.addProperty("createdBy", IglPreferences.getPrefString(getContext(), SEILIGL.USER_ID, ""));
        return  jsonObject;
    }

    public String getName() {
        return getArguments().getString(ARG_DB_DESC);
    }

    private void collectEnableDisable(int latePaymentInterest, AppCompatButton collect, TextView tvTotDue) {
        collect.setEnabled(collectionAmount > (latePaymentInterest > 0 ? latePaymentInterest - 1 : 0));
        //collect.setEnabled(collectionAmount > 0);
        tvTotDue.setText(String.valueOf(collectionAmount));
        //collect.setEnabled(latePaymentInterest > 0);
    }

    private void saveDeposit(String SchmCode,DueData dueData, int collectedAmount, int latePmtAmount, String depBy) {
        DataAsyncResponseHandler asyncResponseHandler = new DataAsyncResponseHandler(getContext(), "Loan Collection", "Saving Collection Entry") {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                   /* if(SchmCode.substring(0,2).equalsIgnoreCase("SD") && SchmCode.substring(4).equalsIgnoreCase("A")){
                        saveDepositOwn(SchmCode,dueData, collectedAmount,latePmtAmount,depBy);
                    }*/
                    ((ActivityCollection) getActivity()).refreshData(FragmentCollection.this);
                    getLoginLocation("Collection","");
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(getContext(), error.getMessage() + "\n" + (new String(responseBody)), Toast.LENGTH_LONG).show();
                Log.d("eKYC Response",error.getLocalizedMessage());
            }
        };

        PosInstRcv instRcv = new PosInstRcv();
        instRcv.setCaseCode(dueData.getCaseCode());
        instRcv.setCreator(dueData.getCreator());
        instRcv.setDataBaseName(dueData.getDb());
        instRcv.setIMEI(IglPreferences.getPrefString(getContext(), SEILIGL.DEVICE_IMEI, "0"));
        instRcv.setInstRcvAmt(collectedAmount - latePmtAmount);
        instRcv.setInstRcvDateTimeUTC(new Date());
        instRcv.setFoCode(dueData.getFoCode());
        instRcv.setCustName(dueData.getCustName());
        instRcv.setPartyCd(dueData.getPartyCd());
        instRcv.setInterestAmt(latePmtAmount);
        instRcv.setPayFlag(depBy);
        //Log.d("Json", String.valueOf(instRcv.getInstRcvDateTimeUTC()));
        Log.d("JsonInstRcv", String.valueOf(WebOperations.convertToJson(instRcv)));
        (new WebOperations()).postEntity(getContext(), "POSDATA", "instcollection", "savereceipt", WebOperations.convertToJson(instRcv),asyncResponseHandler);
    }


    private void saveDepositOwn(String SchmCode,DueData dueData, int collectedAmount, int latePmtAmount, String depBy) {
        DataAsyncResponseHandler asyncResponseHandler = new DataAsyncResponseHandler(getContext(), "Loan Collection", "Saving Collection Entry") {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    // ((ActivityCollection) getActivity()).refreshData(FragmentCollection.this);
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(getContext(), error.getMessage() + "\n" + (new String(responseBody)), Toast.LENGTH_LONG).show();
                Log.d("eKYC Response",error.getLocalizedMessage());
            }
        };

        PosInstRcv instRcv = new PosInstRcv();
        instRcv.setCaseCode(dueData.getCaseCode());
        instRcv.setCreator(dueData.getCreator());
        instRcv.setDataBaseName("PDL_OWN");
        instRcv.setIMEI(IglPreferences.getPrefString(getContext(), SEILIGL.DEVICE_IMEI, "0"));
        instRcv.setInstRcvAmt(collectedAmount - latePmtAmount);
        instRcv.setInstRcvDateTimeUTC(new Date());
        instRcv.setFoCode(dueData.getFoCode());
        instRcv.setCustName(dueData.getCustName());
        instRcv.setPartyCd(dueData.getPartyCd());
        instRcv.setInterestAmt(latePmtAmount);
        instRcv.setPayFlag(depBy);
        //Log.d("Json", String.valueOf(instRcv.getInstRcvDateTimeUTC()));
        Log.d("JsonInstRcv", String.valueOf(WebOperations.convertToJson(instRcv)));
        (new WebOperations()).postEntity(getContext(), "POSDATA", "instcollection", "savereceipt", WebOperations.convertToJson(instRcv),asyncResponseHandler);
    }



    public void refreshData() {
        AdapterDueData adapterDueData = (AdapterDueData) lv.getAdapter();
        adapterDueData.clear();
        adapterDueData.addAll(((ActivityCollection) getActivity()).getDueDataByDbName(mDbName));
        adapterDueData.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        refreshData();
        super.onResume();
    }

    @Override
    public void onStart() {
        refreshData();
        super.onStart();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(ARG_DB_NAME, mDbName);
        outState.putString(ARG_DB_DESC, mDbDesc);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            mDbName = savedInstanceState.getString(ARG_DB_NAME);
            mDbDesc = savedInstanceState.getString(ARG_DB_DESC);
        }
    }


    private void ConfirmationDialog(int totCollectAmt, int latePmtIntAmt){
        dialogConfirm.setContentView(R.layout.dialogconfirmation_layout);
        dialogConfirm.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialogConfirm.setCancelable(false);
       // dialogConfirm.getWindow().getAttributes().windowAnimations = R.style.animation;

        ListView  list_dialog=dialogConfirm.findViewById(R.id.list_dialog);
        TextView text_instamt = dialogConfirm.findViewById(R.id.text_instamt);
        TextView text_totalamt = dialogConfirm.findViewById(R.id.text_totalamt);
        list_dialog.setAdapter(new AdapterInstallmentTemp(getContext(), R.layout.layout_item_installmenttemp, insttemplist));
        text_totalamt.setText(totCollectAmt+"");
        text_instamt.setText(latePmtIntAmt+"");

        Button btnSave = dialogConfirm.findViewById(R.id.button_saveEmi);
        Button btncancel = dialogConfirm.findViewById(R.id.button_Closedialog);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialogConfirm.dismiss();
                //Toast.makeText(MainActivity.this, "okay clicked", Toast.LENGTH_SHORT).show();
            }
        });

        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogConfirm.dismiss();
               // Toast.makeText(MainActivity.this, "Cancel clicked", Toast.LENGTH_SHORT).show();
            }
        });

        dialogConfirm.show();

    }

    private void getLoginLocation(String login,String address){
        ApiInterface apiInterface= ApiClient.getClient(SEILIGL.NEW_SERVERAPI).create(ApiInterface.class);
        Log.d("TAG", "checkCrifScore: "+getdatalocation(login, address));
        Call<JsonObject> call=apiInterface.livetrack(getdatalocation(login,address));
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.d("TAG", "onResponse: "+response.body());
            }
            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d("TAG", "onFailure: "+t.getMessage());

            }
        });
    }

    private JsonObject getdatalocation(String login, String address) {
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("userId", IglPreferences.getPrefString(getContext(), SEILIGL.USER_ID, ""));
        jsonObject.addProperty("deviceId", IglPreferences.getPrefString(getContext(), SEILIGL.DEVICE_ID, ""));
        jsonObject.addProperty("Creator", IglPreferences.getPrefString(getContext(), SEILIGL.CREATOR, ""));
        jsonObject.addProperty("trackAppVersion", BuildConfig.VERSION_NAME);
        jsonObject.addProperty("latitude",gpsTracker.getLatitude()+"");
        jsonObject.addProperty("longitude", gpsTracker.getLongitude()+"");
        jsonObject.addProperty("appInBackground",login);
        jsonObject.addProperty("Activity",SMCode);
        jsonObject.addProperty("Address",Utils.getAddress(gpsTracker.getLatitude(),gpsTracker.getLongitude(),getContext()));
        return jsonObject;
    }

}
