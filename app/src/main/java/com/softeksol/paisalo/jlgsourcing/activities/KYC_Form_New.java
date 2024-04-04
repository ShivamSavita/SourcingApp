package com.softeksol.paisalo.jlgsourcing.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.JsonObject;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.softeksol.paisalo.jlgsourcing.BuildConfig;
import com.softeksol.paisalo.jlgsourcing.Global;
import com.softeksol.paisalo.jlgsourcing.R;
import com.softeksol.paisalo.jlgsourcing.SEILIGL;
import com.softeksol.paisalo.jlgsourcing.Utilities.Utils;
import com.softeksol.paisalo.jlgsourcing.WebOperations;
import com.softeksol.paisalo.jlgsourcing.adapters.AdapterListRange;
import com.softeksol.paisalo.jlgsourcing.entities.Borrower;
import com.softeksol.paisalo.jlgsourcing.entities.BorrowerExtra;
import com.softeksol.paisalo.jlgsourcing.entities.DocumentStore;
import com.softeksol.paisalo.jlgsourcing.entities.Manager;
import com.softeksol.paisalo.jlgsourcing.entities.RangeCategory;
import com.softeksol.paisalo.jlgsourcing.entities.RangeCategory_Table;
import com.softeksol.paisalo.jlgsourcing.entities.dto.BorrowerDTO;
import com.softeksol.paisalo.jlgsourcing.entities.dto.DocumentStoreDTO;
import com.softeksol.paisalo.jlgsourcing.entities.dto.OperationItem;
import com.softeksol.paisalo.jlgsourcing.enums.EnumApiPath;
import com.softeksol.paisalo.jlgsourcing.enums.EnumFieldName;
import com.softeksol.paisalo.jlgsourcing.enums.EnumImageTags;
import com.softeksol.paisalo.jlgsourcing.handlers.AsyncResponseHandler;
import com.softeksol.paisalo.jlgsourcing.handlers.DataAsyncResponseHandler;
import com.softeksol.paisalo.jlgsourcing.retrofit.ApiClient;
import com.softeksol.paisalo.jlgsourcing.retrofit.ApiInterface;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import cz.msebera.android.httpclient.Header;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class KYC_Form_New extends AppCompatActivity {
TextInputEditText tietAgricultureIncome,tietFutureIncome,tietExpenseMonthly,tietIncomeMonthly,tietOtherIncome,EditEarningMemberIncome,tietPensionIncome,tietInterestIncome;
Spinner loanbanktype,loanDuration,acspOccupation,acspLoanReason,acspBusinessDetail,earningMemberTypeSpin;
EditText acspLoanAppFinanceLoanAmount;
    private Manager manager;
Button BtnSaveKYCData;
Borrower borrower;
    String borrowerImagePath;
private AdapterListRange rlaBankType, rlaPurposeType, rlaLoanAmount, rlaEarningMember, rlaSchemeType ,rlsOccupation,rlaBussiness;
Intent i;
String FatherFName, FatherLName,FatherMName, MotherFName,MotherLName, MotherMName,SpouseLName,SpouseMName,SpouseFName;
String VoterIdName="",tilPAN_Name="",tilDL_Name="",tietName="",AddressCodes="";
TextView textViewTotalAnnualIncome;
    String schemeNameForVH;
    String lastCaseCode="";
    String lastLoanAmt="";
    String lastDuration="";
    String lastPaidEmi="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kyc_form_new);
        i=getIntent();
        FatherFName=i.getStringExtra("FatherFName").trim().length()<1?null:i.getStringExtra("FatherFName").trim();
        FatherLName=i.getStringExtra("FatherLName").trim().length()<1?null:i.getStringExtra("FatherLName").trim();
        FatherMName=i.getStringExtra("FatherMName").trim().length()<1?null:i.getStringExtra("FatherMName").trim();
        MotherFName=i.getStringExtra("MotherFName").trim().length()<1?null:i.getStringExtra("MotherFName").trim();
        MotherLName=i.getStringExtra("MotherLName").trim().length()<1?null:i.getStringExtra("MotherLName").trim();
        MotherMName=i.getStringExtra("MotherMName").trim().length()<1?null:i.getStringExtra("MotherMName").trim();
        SpouseLName=i.getStringExtra("SpouseLName").trim().length()<1?null:i.getStringExtra("SpouseLName").trim();
        SpouseMName=i.getStringExtra("SpouseMName").trim().length()<1?null:i.getStringExtra("SpouseMName").trim();
        SpouseFName=i.getStringExtra("SpouseFName").trim().length()<1?null:i.getStringExtra("SpouseFName").trim();
        VoterIdName=i.getStringExtra("VoterIdName").length()<1?"":i.getStringExtra("VoterIdName");
        tilPAN_Name=i.getStringExtra("PANName").length()<1?"":i.getStringExtra("PANName");
        tilDL_Name=i.getStringExtra("DLName").length()<1?"":i.getStringExtra("DLName");
        tietName=i.getStringExtra("AadharName").length()<1?"":i.getStringExtra("AadharName");
        AddressCodes=i.getStringExtra("AddressCodes").length()<1?"":i.getStringExtra("AddressCodes");
        lastCaseCode=i.getStringExtra("lastCaseCode");
        lastLoanAmt=i.getStringExtra("lastLoanAmt");
        lastDuration=i.getStringExtra("lastDuration");
        lastPaidEmi=i.getStringExtra("lastPaidEmi");

        Log.d("TAG", "onCreate: "+AddressCodes);

        schemeNameForVH=i.getStringExtra(Global.SCHEME_TAG);


        manager = (Manager) i.getSerializableExtra("manager");
        borrower = (Borrower) i.getSerializableExtra("borrower");
      //  borrowerImagePath=borrower.getPicture().getPath();

        tietAgricultureIncome=findViewById(R.id.tietAgricultureIncome);
        tietFutureIncome=findViewById(R.id.tietFutureIncome);
        tietExpenseMonthly=findViewById(R.id.tietExpenseMonthly);
        tietIncomeMonthly=findViewById(R.id.tietIncomeMonthly);
        tietOtherIncome=findViewById(R.id.tietOtherIncome);
        tietInterestIncome=findViewById(R.id.tietInterestIncome);
        tietPensionIncome=findViewById(R.id.tietPensionIncome);
        EditEarningMemberIncome=findViewById(R.id.EditEarningMemberIncome);
        loanbanktype=findViewById(R.id.loanbanktype);
        loanDuration=findViewById(R.id.loanDuration);
        acspOccupation=findViewById(R.id.acspOccupation);
        acspLoanReason=findViewById(R.id.acspLoanReason);
        acspBusinessDetail=findViewById(R.id.acspBusinessDetail);
        earningMemberTypeSpin=findViewById(R.id.earningMemberTypeSpin);
        acspLoanAppFinanceLoanAmount=findViewById(R.id.acspLoanAppFinanceLoanAmount);
        textViewTotalAnnualIncome=findViewById(R.id.textViewTotalAnnualIncome);
        BtnSaveKYCData=findViewById(R.id.BtnFinalSaveKYCData);

        rlaSchemeType = new AdapterListRange(this,RangeCategory.getRangesByCatKey("DISBSCH"), false);

        rlaPurposeType = new AdapterListRange(this,RangeCategory.getRangesByCatKey("loan_purpose"), true);

        rlaBankType = new AdapterListRange(this,RangeCategory.getRangesByCatKey("banks"), false);

        rlaLoanAmount= new AdapterListRange(this,RangeCategory.getRangesByCatKey("loan_amt"), false);

        rlsOccupation= new AdapterListRange(this,RangeCategory.getRangesByCatKey("occupation-type"), false);

        rlaBussiness= new AdapterListRange(this,RangeCategory.getRangesByCatKey("other_employment"), false);

        rlaEarningMember = new AdapterListRange(this,RangeCategory.getRangesByCatKey("other_income"), false);


        loanbanktype.setAdapter(rlaBankType);
        acspLoanReason.setAdapter(rlaPurposeType);
        acspBusinessDetail.setAdapter(rlaBussiness);
        acspOccupation.setAdapter(rlsOccupation);
        earningMemberTypeSpin.setAdapter(rlaEarningMember);

            tietIncomeMonthly.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    try {

                        int totalAnnualIncome=Integer.parseInt(EditEarningMemberIncome.getText().toString())+Integer.parseInt(tietAgricultureIncome.getText().toString())+Integer.parseInt(tietFutureIncome.getText().toString())+Integer.parseInt(tietOtherIncome.getText().toString())+(12*Integer.parseInt(tietIncomeMonthly.getText().toString())+Integer.parseInt(tietPensionIncome.getText().toString())+Integer.parseInt(tietInterestIncome.getText().toString()));
                        textViewTotalAnnualIncome.setText("Your Total Annual Income: "+String.valueOf(totalAnnualIncome)+" /-");
                        textViewTotalAnnualIncome.setVisibility(View.VISIBLE);
                    }catch (Exception e){

                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
            tietAgricultureIncome.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    try {

                        int totalAnnualIncome=Integer.parseInt(EditEarningMemberIncome.getText().toString())+Integer.parseInt(tietAgricultureIncome.getText().toString())+Integer.parseInt(tietFutureIncome.getText().toString())+Integer.parseInt(tietOtherIncome.getText().toString())+(12*Integer.parseInt(tietIncomeMonthly.getText().toString())+Integer.parseInt(tietPensionIncome.getText().toString())+Integer.parseInt(tietInterestIncome.getText().toString()));
                        textViewTotalAnnualIncome.setText("Your Total Annual Income: "+String.valueOf(totalAnnualIncome)+" /-");
                        textViewTotalAnnualIncome.setVisibility(View.VISIBLE);
                    }catch (Exception e){

                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
            tietFutureIncome.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    try {

                        int totalAnnualIncome=Integer.parseInt(EditEarningMemberIncome.getText().toString())+Integer.parseInt(tietAgricultureIncome.getText().toString())+Integer.parseInt(tietFutureIncome.getText().toString())+Integer.parseInt(tietOtherIncome.getText().toString())+(12*Integer.parseInt(tietIncomeMonthly.getText().toString())+Integer.parseInt(tietPensionIncome.getText().toString())+Integer.parseInt(tietInterestIncome.getText().toString()));
                        textViewTotalAnnualIncome.setText("Your Total Annual Income: "+String.valueOf(totalAnnualIncome)+" /-");
                        textViewTotalAnnualIncome.setVisibility(View.VISIBLE);
                    }catch (Exception e){

                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
            tietOtherIncome.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    try {

                        int totalAnnualIncome=Integer.parseInt(EditEarningMemberIncome.getText().toString())+Integer.parseInt(tietAgricultureIncome.getText().toString())+Integer.parseInt(tietFutureIncome.getText().toString())+Integer.parseInt(tietOtherIncome.getText().toString())+(12*Integer.parseInt(tietIncomeMonthly.getText().toString())+Integer.parseInt(tietPensionIncome.getText().toString())+Integer.parseInt(tietInterestIncome.getText().toString()));
                        textViewTotalAnnualIncome.setText("Your Total Annual Income: "+String.valueOf(totalAnnualIncome)+" /-");
                        textViewTotalAnnualIncome.setVisibility(View.VISIBLE);
                    }catch (Exception e){

                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
            EditEarningMemberIncome.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    try {

                        int totalAnnualIncome=Integer.parseInt(EditEarningMemberIncome.getText().toString())+Integer.parseInt(tietAgricultureIncome.getText().toString())+Integer.parseInt(tietFutureIncome.getText().toString())+Integer.parseInt(tietOtherIncome.getText().toString())+(12*Integer.parseInt(tietIncomeMonthly.getText().toString())+Integer.parseInt(tietPensionIncome.getText().toString())+Integer.parseInt(tietInterestIncome.getText().toString()));
                        textViewTotalAnnualIncome.setText("Your Total Annual Income: "+String.valueOf(totalAnnualIncome)+" /-");
                        textViewTotalAnnualIncome.setVisibility(View.VISIBLE);
                    }catch (Exception e){

                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        tietPensionIncome.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    try {

                        int totalAnnualIncome=Integer.parseInt(EditEarningMemberIncome.getText().toString())+Integer.parseInt(tietAgricultureIncome.getText().toString())+Integer.parseInt(tietFutureIncome.getText().toString())+Integer.parseInt(tietOtherIncome.getText().toString())+(12*Integer.parseInt(tietIncomeMonthly.getText().toString())+Integer.parseInt(tietPensionIncome.getText().toString())+Integer.parseInt(tietInterestIncome.getText().toString()));
                        textViewTotalAnnualIncome.setText("Your Total Annual Income: "+String.valueOf(totalAnnualIncome)+" /-");
                        textViewTotalAnnualIncome.setVisibility(View.VISIBLE);
                    }catch (Exception e){

                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        tietInterestIncome.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    try {

                        int totalAnnualIncome=Integer.parseInt(EditEarningMemberIncome.getText().toString())+Integer.parseInt(tietAgricultureIncome.getText().toString())+Integer.parseInt(tietFutureIncome.getText().toString())+Integer.parseInt(tietOtherIncome.getText().toString())+(12*Integer.parseInt(tietIncomeMonthly.getText().toString())+Integer.parseInt(tietPensionIncome.getText().toString())+Integer.parseInt(tietInterestIncome.getText().toString()));
                        textViewTotalAnnualIncome.setText("Your Total Annual Income: "+String.valueOf(totalAnnualIncome)+" /-");
                        textViewTotalAnnualIncome.setVisibility(View.VISIBLE);
                    }catch (Exception e){

                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

        BtnSaveKYCData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                updateBorrower();

            }
        });


    }

    private void  updateBorrower() {

        int maxLoanAmt=300000;
        String maxLoanAmtStr="Three lacks";
        Log.d("TAG", "updateBorrower: "+manager.Creator);
        if (manager.Creator.toLowerCase().startsWith("vh")){
            maxLoanAmt=1000000;
             maxLoanAmtStr="Ten lacks";
        }
        if (borrower != null) {
            getDataFromView(this.findViewById(android.R.id.content).getRootView());

            double totalincode=Double.parseDouble(tietIncomeMonthly.getText().toString())+Double.parseDouble(tietFutureIncome.getText().toString())+Double.parseDouble(tietAgricultureIncome.getText().toString())+Double.parseDouble(tietOtherIncome.getText().toString())+Double.parseDouble(EditEarningMemberIncome.getText().toString())+Double.parseDouble(tietPensionIncome.getText().toString())+Double.parseDouble(tietInterestIncome.getText().toString());

            if (checkSpinnerData()){
                Utils.showSnakbar(findViewById(android.R.id.content),"Please check all fields");
            }else if (tietIncomeMonthly.getText().toString().trim().equals("")){
                tietIncomeMonthly.setError("Please Enter Income");
                Utils.showSnakbar(findViewById(android.R.id.content),"Please enter Income");
            }else if(tietExpenseMonthly.getText().toString().trim().equals("")){
                tietExpenseMonthly.setError("Please Enter Expense");
                Utils.showSnakbar(findViewById(android.R.id.content),"Please enter Expense");
            }else if(loanbanktype.getSelectedItem() =="SBI" && totalincode>25000){
                Utils.showSnakbar(findViewById(android.R.id.content),"Income should be less than 25000 for SBI");
            }else if(tietFutureIncome.getText().toString().trim().equals("")){
                tietFutureIncome.setError("Please Enter Future Income");
                Utils.showSnakbar(findViewById(android.R.id.content),"Please enter Future Income");
            }else if(tietAgricultureIncome.getText().toString().trim().equals("")){
                tietAgricultureIncome.setError("Please Enter Agriculture Income");
                Utils.showSnakbar(findViewById(android.R.id.content),"Please enter Agriculture Income");
            }else if(tietOtherIncome.getText().toString().trim().equals("")){
                tietOtherIncome.setError("Please Enter Other Income");
                Utils.showSnakbar(findViewById(android.R.id.content),"Please enter Other Income");
            }else if(EditEarningMemberIncome.getText().toString().trim().equals("") && !Utils.getSpinnerStringValue(earningMemberTypeSpin).equals("None")){
                EditEarningMemberIncome.setError("Please Enter "+Utils.getSpinnerStringValue(earningMemberTypeSpin)+"'s Income");
                Utils.showSnakbar(findViewById(android.R.id.content),"Please Enter "+Utils.getSpinnerStringValue(earningMemberTypeSpin)+"'s Income");
            } else if(tietPensionIncome.getText().toString().trim().equals("")){
                tietPensionIncome.setError("Please Enter Pension Income");
                Utils.showSnakbar(findViewById(android.R.id.content),"Please enter Pension Income");
            }
             else if(tietInterestIncome.getText().toString().trim().equals("")){
                tietInterestIncome.setError("Please Enter Interest Income");
                Utils.showSnakbar(findViewById(android.R.id.content),"Please enter Interest Income");
            } else if(acspLoanAppFinanceLoanAmount.getText().toString().trim().equals("")){
                acspLoanAppFinanceLoanAmount.setError("Please Enter Loan Amount");
                Utils.showSnakbar(findViewById(android.R.id.content),"Please enter Loan Amount");
            } else if(Integer.parseInt(acspLoanAppFinanceLoanAmount.getText().toString().trim())>maxLoanAmt ||Integer.parseInt(acspLoanAppFinanceLoanAmount.getText().toString().trim())<5000){
                acspLoanAppFinanceLoanAmount.setError("Please Enter Loan Amount Less than "+maxLoanAmtStr+" and Greater than 5 thousand");
                Utils.showSnakbar(findViewById(android.R.id.content),"Please enter Loan Amount Less than "+maxLoanAmtStr+" and Greater than 5 thousand");
            }else if(!manager.Creator.startsWith("VH") && ((Double.parseDouble(tietIncomeMonthly.getText().toString().trim())))<(0.15*Double.parseDouble(acspLoanAppFinanceLoanAmount.getText().toString().trim()))){
                tietIncomeMonthly.setError("Income should be greater than 15% of Loan Amount");
                Utils.showSnakbar(findViewById(android.R.id.content),"Income should be greater than 15% of Loan Amount");
            }else if(!manager.Creator.startsWith("VH") && ((Double.parseDouble(tietIncomeMonthly.getText().toString().trim()))* 0.25)>Double.parseDouble(tietExpenseMonthly.getText().toString().trim())){
                tietExpenseMonthly.setError("Expense should be greater than 25 % of Income");
                Utils.showSnakbar(findViewById(android.R.id.content),"Expense should be greater than 25 % of Income");
            }
            else{

                borrower.Oth_Prop_Det = null;
                borrower.save();
                borrower.fiExtraBank.setMotherName(MotherFName);
                borrower.fiExtraBank.setFatherName(FatherFName);
                String occCode = Utils.getSpinnerStringValue(acspOccupation);
                borrower.fiExtraBank.setCkycOccupationCode(occCode);
                borrower.associateExtraBank(borrower.fiExtraBank);
                borrower.fiExtraBank.save();
                BorrowerDTO borrowerDTO = new BorrowerDTO(borrower);
                borrowerDTO.fiFamExpenses = null;
                borrowerDTO.fiExtra = null;
                Log.d("TAG", "onSuccess1: "+borrower.fiExtraBank.getActivityType());
                Log.d("TAG", "onSuccess1: "+borrower.fiExtraBank.getCode());
                Log.d("TAG", "onSuccess1: "+borrower.fiExtraBank);
                Log.d("TAG", "onSuccess1: "+WebOperations.convertToJson(borrower.fiExtraBank));

                        AsyncResponseHandler dataAsyncResponseHandler = new AsyncResponseHandler(this, "Loan Financing\nSubmittiong Loan Application", "Submitting Borrower Information") {
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                                String jsonString = new String(responseBody);
                                Log.d("Response Data",jsonString);
                                try {

                                    JSONObject jo = new JSONObject(jsonString);
                                    long FiCode = jo.getLong("FiCode");
                                    borrower.updateFiCode(FiCode, borrower.Tag);
                                    borrower.Oth_Prop_Det = null;
                                    borrower.save();

//                                    fiDocGeoLoc=new FiDocGeoLoc(FiCode,borrower.Creator,isAdhaarEntry,isNameMatched);
//                                    fiDocGeoLoc.save();
                                    BorrowerExtra borrowerExtra=new BorrowerExtra(FiCode,manager.Creator,Utils.getNotNullInt(tietIncomeMonthly),Utils.getNotNullInt(tietFutureIncome),Utils.getNotNullText(tietAgricultureIncome),Utils.getNotNullText(tietOtherIncome),Utils.getSpinnerStringValue(earningMemberTypeSpin),Utils.getNotNullInt(EditEarningMemberIncome),MotherFName,MotherLName,MotherMName, FatherFName,FatherLName, FatherMName,borrower.Tag,SpouseLName,SpouseMName,SpouseFName,Utils.getNotNullInt(tietPensionIncome),Utils.getNotNullInt(tietInterestIncome));
                                    Log.d("TAG", "onCreate: "+FatherFName);
                                    Log.d("TAG", "onCreate: "+FatherLName);
                                    Log.d("TAG", "onCreate: "+FatherMName);
                                    Log.d("TAG", "onCreate: "+MotherFName);
                                    Log.d("TAG", "onCreate: "+MotherLName);
                                    Log.d("TAG", "onCreate: "+MotherMName);
                                    Log.d("TAG", "onCreate: "+SpouseLName);
                                    Log.d("TAG", "onCreate: "+SpouseMName);
                                    Log.d("TAG", "onCreate: "+SpouseFName);
//                                    BorrowerExtraBank borrowerExtraBank=new BorrowerExtraBank(manager.Creator,manager.TAG,FiCode);

//                                    borrower.associateExtraBank(borrowerExtraBank);

                                    borrower.fiExtra=borrowerExtra;
                                    borrower.fiExtra.save();
//                                    borrower.associateExtraBank(borrower.fiExtraBank);
//                                    borrower.fiExtraBank.save();
                                    borrower.save();
                                    Log.d("TAG", "onSuccess: "+borrower.fiExtra);
                                    Log.d("TAG", "onSuccess: "+WebOperations.convertToJson(borrower.fiExtraBank));
                                    Log.d("TAG", "onSuccess: "+WebOperations.convertToJson(borrower));

                                    AsyncResponseHandler dataAsyncResponseHandlerUpdateFI = new AsyncResponseHandler(KYC_Form_New.this, "Loan Financing\nSubmitting Loan Application","Submitting Borrower Information") {
                                        @Override
                                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                                            String jsonString = new String(responseBody);
                                            //Log.d("Response Data",jsonString);
                                            Utils.showSnakbar(findViewById(android.R.id.content), "Borrower Loan Application Saved");

                                            try {
                                                JSONObject jo = new JSONObject(jsonString);


                                                Log.d("CHeckJsonFinancing",jo+"");
                                                Log.d("CHeckJsonFinancing1",jsonString+"");


                                                borrower.Code = jo.getLong("FiCode");
                                                borrower.Oth_Prop_Det = "U";

                                                borrower.save();
                                              //  saveDataOfImages(borrower,borrowerImagePath,"B");

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }

                                        @Override
                                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                                            super.onFailure(statusCode, headers, responseBody, error);
                                        }
                                    };
                                    Log.d("TAG", "onSuccess: "+WebOperations.convertToJson(borrower));
                                    (new WebOperations()).postEntity(KYC_Form_New.this, "posfi", "updatefi", WebOperations.convertToJson(borrower), dataAsyncResponseHandlerUpdateFI);
                                    if (schemeNameForVH.length()>1){
                                        saveFIWithSchemeName(manager.Creator,FiCode);
                                    }
                                    if (AddressCodes.length()>1){
                                       //saveAddressCodeOfFi();
                                        UpdatefiVerificationDocName(FiCode,manager.Creator,AddressCodes);
                                    }
                                    AlertDialog.Builder builder = new AlertDialog.Builder(KYC_Form_New.this);
                                    builder.setTitle("Borrower KYC");
                                    builder.setCancelable(false);
                                    builder.setMessage("KYC Saved with " + manager.Creator + " / " + FiCode + "\nPlease capture / scan documents");
                                    builder.setPositiveButton("Want to E-Sign", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            OperationItem operationItem=new OperationItem(6, "E-Sign", R.color.colorMenuPremature, "POSDB", "Getmappedfo");

                                            Intent intent = new Intent(KYC_Form_New.this, ActivityManagerSelect.class);
                                            intent.putExtra(Global.OPTION_ITEM, operationItem);
                                            intent.putExtra("Title", operationItem.getOprationName());
                                            startActivity(intent);
                                            finish();
                                        }
                                    });
                                    builder.setNegativeButton("Done", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            Intent intent=new Intent(KYC_Form_New.this,ActivityOperationSelect.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            startActivity(intent);
                                            finish();
                                        }
                                    });
                                    builder.create().show();



                                } catch (JSONException jo) {
                                    Log.d("TAG", "onSuccess: "+jo.getMessage());
                                    Utils.showSnakbar(findViewById(android.R.id.content), jo.getMessage());
                                }
                            }
                            @Override
                            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                                super.onFailure(statusCode, headers, responseBody, error);
                                Log.d("TAG", "onFailure: "+error.getMessage());
                                //btnSubmit.setEnabled(true);
                                invalidateOptionsMenu();
                            }
                        };


                        //Log.d("Borrower Json",WebOperations.convertToJson(borrower));
                        String borrowerJsonString = WebOperations.convertToJson(borrowerDTO);
                        //Log.d("Borrower Json", borrowerJsonString);
                        Log.d("TAG", "updateBorrower: "+borrowerJsonString);
                (new WebOperations()).postEntity(getApplicationContext(), "posfi", "savefi", borrowerJsonString, dataAsyncResponseHandler);
            }

        }
    }

    private boolean checkSpinnerData() {
//        loanbanktype
//                acspLoanReason
//        acspBusinessDetail
//                acspOccupation
//        earningMemberTypeSpin
        if (((RangeCategory)earningMemberTypeSpin.getSelectedItem()).DescriptionEn.equalsIgnoreCase("--Select--"))
        {
            Toast.makeText(this, "Please select earning member type", Toast.LENGTH_SHORT).show();
            return true;
        }else if (((RangeCategory)acspBusinessDetail.getSelectedItem()).DescriptionEn.equalsIgnoreCase("--Select--"))
        {
            Toast.makeText(this, "Please select business type", Toast.LENGTH_SHORT).show();
            return true;
        }else  if (((RangeCategory)acspLoanReason.getSelectedItem()).DescriptionEn.equalsIgnoreCase("--Select--"))
        {
            Toast.makeText(this, "Please select loan reason type", Toast.LENGTH_SHORT).show();
            return true;
        }else  if (((RangeCategory)acspOccupation.getSelectedItem()).DescriptionEn.equalsIgnoreCase("--Select--"))
        {
            Toast.makeText(this, "Please select occupation type", Toast.LENGTH_SHORT).show();
            return true;
        }else if (loanDuration.getSelectedItem().toString().equalsIgnoreCase("--Select--"))
        {
            Toast.makeText(this, "Please select loan duration", Toast.LENGTH_SHORT).show();
            return true;
        }else if (((RangeCategory)loanbanktype.getSelectedItem()).DescriptionEn.equalsIgnoreCase("--Select--"))
        {
            Toast.makeText(this, "Please select bank type", Toast.LENGTH_SHORT).show();
            return true;
        }else{
            return false;
        }


    }

    private void saveDataOfImages(Borrower borrower, String borrowerProfilePic,String imgTag) {
        DocumentStore documentStore = new DocumentStore();
//        {"ChecklistID":0,"Creator":"AGRA","DocRemark":"Picture","Document":"",
//                "FICode":272664,"GrNo":0,"ImageTag":"CUSTIMG",
//                "Tag":"CLAG","UserID":"GRST000223","latitude":0.0,
//                "longitude":0.0,"timestamp":"01-Jul-1996"}

//        {"ChecklistID":0,"Creator":"AGRA","DocRemark":"Picture","Document":"","FICode":272678,
//                "GrNo":1,"ImageTag":"GUARPIC","Tag":"CLAG",
//                "UserID":"GRST000223","latitude":0.0,"longitude":0.0,"timestamp":"01-Jul-1996"}

//        {"ChecklistID":0,"Creator":"AGRA","DocRemark":"Picture","Document":"",
//                "FICode":266173,"GrNo":1,"ImageTag":"GUARPIC",
//                "Tag":"CLAG","UserID":"","latitude":0.0,"longitude":0.0,"timestamp":"01-Jul-1996"}

        documentStore.Creator = borrower.Creator;
        documentStore.ficode = borrower.Code;
        documentStore.fitag = borrower.Tag;

        documentStore.remarks = "Picture";
        documentStore.checklistid = 0;
        documentStore.userid = borrower.UserID;
        documentStore.latitude = 0;
        documentStore.longitude = 0;
        documentStore.DocId = 0;
        documentStore.FiID =0;
        documentStore.updateStatus = false;
        //documentStore.imagePath = mDocumentStore.imagePath;
        //documentStore.imagePath = "file:" + mDocumentStore.imagePath;
        if (imgTag.equals("B")){
            documentStore.GuarantorSerial = 0;
            documentStore.imageTag = EnumImageTags.Borrower.getImageTag();
            documentStore.fieldname = EnumFieldName.Borrower.getFieldName();
            documentStore.apiRelativePath = EnumApiPath.BorrowerApiJson.getApiPath();
        }else{
            documentStore.GuarantorSerial = 1;
            documentStore.imageTag = EnumImageTags.Guarantor.getImageTag();
            documentStore.fieldname = EnumFieldName.Guarantor.getFieldName();
            documentStore.apiRelativePath = EnumApiPath.GuarantorApi.getApiPath();
        }


        documentStore.imagePath = borrowerProfilePic;
        Toast.makeText(this, documentStore.imagePath+"", Toast.LENGTH_SHORT).show();
        Log.d("TAG", "saveDataOfImages: "+documentStore.imagePath);



        DataAsyncResponseHandler responseHandler = new DataAsyncResponseHandler(KYC_Form_New.this, "Loan Financing", "Uploading " + DocumentStore.getDocumentName(documentStore.checklistid)) {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String responseString = new String(responseBody);
                //Utils.showSnakbar( findViewById(android.R.id.content).getRootView(), responseString);
                //if(responseString.equals("")) {


                Log.d("TAG", "onSuccess: "+responseString);

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                super.onFailure(statusCode, headers, responseBody, error);

            }
        };
        DocumentStoreDTO documentStore1=documentStore.getDocumentDTO();
        documentStore1.Document="";
        Log.d("TAG", "uploadKycJson: "+WebOperations.convertToJson(documentStore1));

        String jsonString = WebOperations.convertToJson(documentStore.getDocumentDTO());
        // Log.d("Document Json",jsonString);
        String apiPath = documentStore.checklistid == 0 ? "/api/uploaddocs/savefipicjson" : "/api/uploaddocs/savefidocsjson";
        (new WebOperations()).postEntity(KYC_Form_New.this, BuildConfig.BASE_URL + apiPath, jsonString, responseHandler);










    }

    private void saveAddressCodeOfFi(String creator, long fiCode,String addressCodes) {

        String[] codes=addressCodes.split("_");
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

        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("FiCode",String.valueOf(fiCode));
        jsonObject.addProperty("Creator",creator);
        jsonObject.addProperty("villagE_CODE",codes[3]);
        jsonObject.addProperty("suB_DIST_CODE",codes[2]);
        jsonObject.addProperty("DIST_CODE",codes[1]);
        jsonObject.addProperty("STATE_CODE",codes[0]);
        ApiInterface apiInterface=retrofit.create(ApiInterface.class);
        Call<JsonObject> call=apiInterface.insertAddressCodes(jsonObject);
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

    private void getDataFromView(View view) {
        borrower.Income= Utils.getNotNullInt(tietIncomeMonthly);
        borrower.Expense=Utils.getNotNullInt(tietExpenseMonthly);
        borrower.Business_Detail=Utils.getSpinnerStringValue(acspBusinessDetail);
        borrower.Loan_Duration=loanDuration.getSelectedItem().toString().trim();
        borrower.Loan_Reason=Utils.getSpinnerStringValue(acspLoanReason);
        borrower.Loan_Amt=Utils.getNotNullInt(acspLoanAppFinanceLoanAmount);
        borrower.BankName=Utils.getSpinnerStringValueDesc(loanbanktype);
        borrower.T_ph3=Utils.getSpinnerStringValueDesc(loanbanktype);
        borrower.Approved=null;



    try{
    Log.d("TAG", "getDataFromView: "+ borrower.Latitude);
    Log.d("TAG", "getDataFromView: "+ borrower.Longitude);
    Log.d("TAG", "getDataFromView: "+ borrower.Gender);
    Log.d("TAG", "getDataFromView: "+ borrower.p_state);
    Log.d("TAG", "getDataFromView: "+ borrower.P_ph3);
    Log.d("TAG", "getDataFromView: "+ borrower.PanNO);
    Log.d("TAG", "getDataFromView: "+ borrower.drivinglic);
    Log.d("TAG", "getDataFromView: "+ borrower.voterid);
    Log.d("TAG", "getDataFromView: "+ borrower.Income);
    Log.d("TAG", "getDataFromView: "+ borrower.Income);
    Log.d("TAG", "getDataFromView: "+ borrower.Expense);
    Log.d("TAG", "getDataFromView: "+ borrower.Business_Detail);
    Log.d("TAG", "getDataFromView: "+ borrower.Loan_Duration);
    Log.d("TAG", "getDataFromView: "+ borrower.Loan_Reason);
    Log.d("TAG", "getDataFromView: "+ borrower.Loan_Amt);

    }catch (Exception e){

      }
    }

    private void UpdatefiVerificationDocName(long fiCode, String creator,String addressCodes) {

        ApiInterface apiInterface= ApiClient.getClient(SEILIGL.NEW_SERVERAPI).create(ApiInterface.class);
        //Log.d("TAG", "checkCrifScore: "+getJsonOfDocName(fiCode, creator,addressCodes));
        Call<JsonObject> call=apiInterface.getDocNameDate(getJsonOfDocName(fiCode,creator,addressCodes));
        call.enqueue(new Callback<JsonObject>(){
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response){
                Log.d("TAG", "onResponse: "+response.body());
                if(response.body() != null){

                }
            }
            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d("TAG", "onFailure: "+t.getMessage());

            }
        });
    }
    private JsonObject getJsonOfDocName(long fiCode, String creator,String addressCodes) {
        String[] codes=addressCodes.split("_");
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("pan_Name",tilPAN_Name);
        jsonObject.addProperty("voterId_Name",VoterIdName);
        jsonObject.addProperty("aadhar_Name",tietName);
        jsonObject.addProperty("drivingLic_Name",tilDL_Name);
        jsonObject.addProperty("bankAcc_Name","");
        jsonObject.addProperty("bank_Name","");
        jsonObject.addProperty("villagE_CODE",codes[3]);
        jsonObject.addProperty("suB_DIST_CODE",codes[2]);
        jsonObject.addProperty("disT_CODE",codes[1]);
        jsonObject.addProperty("statE_CODE",codes[0]);
        jsonObject.addProperty("fiCode",fiCode+"");
        jsonObject.addProperty("creator",creator);
        return jsonObject;
    }

    private void saveFIWithSchemeName(String creator, long fiCode) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.connectTimeout(1, TimeUnit.MINUTES);
        httpClient.readTimeout(1,TimeUnit.MINUTES);
        httpClient.addInterceptor(logging);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://erpservice.paisalo.in:980/PDL.Mobile.API/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();

        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("FiCode",String.valueOf(fiCode));
        jsonObject.addProperty("Creator",creator);
        jsonObject.addProperty("SchemeCode",schemeNameForVH);
        ApiInterface apiInterface=retrofit.create(ApiInterface.class);
        Call<JsonObject> call=apiInterface.saveSchemeForVH(jsonObject);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()){
                    if (response.body().get("statusCode").getAsInt()==200){
                        Toast.makeText(KYC_Form_New.this, response.body().get("message").getAsString(), Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(KYC_Form_New.this, "Something went wrong!!\nScheme VH", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(KYC_Form_New.this, "Failure!!\nScheme VH", Toast.LENGTH_SHORT).show();

            }
        });



    }
}