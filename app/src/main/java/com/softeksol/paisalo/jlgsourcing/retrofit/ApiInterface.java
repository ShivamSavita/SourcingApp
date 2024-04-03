package com.softeksol.paisalo.jlgsourcing.retrofit;


import com.google.gson.JsonObject;
import com.softeksol.paisalo.jlgsourcing.collectionreport.CollectionReportModel;
import com.softeksol.paisalo.jlgsourcing.entities.BREResponse;
import com.softeksol.paisalo.jlgsourcing.entities.CityModelList;
import com.softeksol.paisalo.jlgsourcing.entities.CreatorModel;
import com.softeksol.paisalo.jlgsourcing.entities.DeDupeResponse;
import com.softeksol.paisalo.jlgsourcing.entities.DistrictListModel;
import com.softeksol.paisalo.jlgsourcing.entities.HomeVisitFiList;
import com.softeksol.paisalo.jlgsourcing.entities.HomeVisitListModel;
import com.softeksol.paisalo.jlgsourcing.entities.PosInstRcv;
import com.softeksol.paisalo.jlgsourcing.entities.PosInstRcvNew;
import com.softeksol.paisalo.jlgsourcing.entities.ProcessingEmiData;
import com.softeksol.paisalo.jlgsourcing.entities.QRCollStatus;
import com.softeksol.paisalo.jlgsourcing.entities.SubDistrictModel;
import com.softeksol.paisalo.jlgsourcing.entities.VillageListModel;
import com.softeksol.paisalo.jlgsourcing.entities.dto.OCRResponseModel;
import com.softeksol.paisalo.jlgsourcing.homevisit.FIDataModel;
import com.softeksol.paisalo.jlgsourcing.models.AccountDetails_Model;
import com.softeksol.paisalo.jlgsourcing.models.QrUrlData;

import java.util.Calendar;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    /*@FormUrlEncoded
    @POST("token")
    public Call<LoginResponse> login(@Field("grant_type") String grantType, @Field("username") String  userName, @Field("password") String password);
   */


    @POST("UserMobile/CreateFiVerfiedInfo")
    public  Call<JsonObject> getDocNameDate(@Body JsonObject object);

    @POST("CrifReport/CheckCrifReport")
    public  Call<CheckCrifData> checkCrifScore(@Body JsonObject object);


    @POST("CrifReport/GetCrifReport")
    public  Call<ScrifData> getCrifScore(@Body JsonObject object);

    @GET("LiveTrack/CollectionStatus")
    public Call<CollectionReportModel> getCollectionReprt(@Query("Smcode") String Smcode);

    @POST("IdentityVerification/Get")
    public Call<JsonObject> cardValidate(@Body JsonObject object);

    @POST("LiveTrack/CreateKycVerification")
    public Call<JsonObject> kycVerficationlog(@Body JsonObject object);

    @POST("LiveTrack/CreateLiveTrack")
    public Call<JsonObject> livetrack(@Body JsonObject object);

    @POST("FiWip/CheckQrCode")
    public Call<QrUrlData> getQrCode(@Body JsonObject object);

    @GET("GenerateQr/CheckQrCode")
    Call<QrUrlData> getCheckQrCode(@Query("smcode") String SmCode);

    @GET("LiveTrack/GetAppLink")
    Call<AppUpdateResponse> getAppLinkStatus(@Query("version") String version, @Query("AppName") String AppName, @Query("action") int action);



    @POST("LiveTrack/SourcingStatus")
    Call<JsonObject> updateStatus(@Query("ficode") String Ficode, @Query("creator") String Creator);

    @GET("InstCollection/GetProcessingFeeEmi")
    Call<ProcessingEmiData> processingFee(@Query("ficode") String Ficode, @Query("creator") String Creator);


    @POST("LiveTrack/CreateMorphoDeviceData")
    Call<JsonObject> sendDataForMorphoRecharge(@Body JsonObject jsonObject);

    @GET("LiveTrack/GetBreStatus")
    Call<JsonObject> getBreStatus(@Query("code") String code,@Query("creator") String creator);

    @GET("{ifsccode}")
    Call<JsonObject> getIfscCode(@Path("ifsccode") String ifsccode);

    @POST("OCR/DocVerifyByOCR")
    Call<OCRResponseModel>
    getFileValidateByOCR(@Query("imgData") String type, @Body RequestBody file);

    @GET("FI/AadharSehmatiFromPdf")
    Call<JsonObject> getFile(@Header ("Authorization") String token,@Query("creator") String creator, @Query("ficode") int ficode);
    @GET("FI/ApplicationFormPdfForVHAccOpen")
    Call<JsonObject> getFileApplicationFormPdfForVHAccOpen(@Header ("Authorization") String token,@Query("creator") String creator, @Query("ficode") int ficode);


    @POST("OCR/GetAdhardata")
    Call<JsonObject> getAdharDataByOCR(@Query("imgData") String imgData,@Query("doctype") String docType,@Body RequestBody file);

    @POST("LiveTrack/UpdateFiStatus")
    Call<JsonObject> restrictBorrower(@Query("ficode") String ficode,@Query("creator") String creator,@Query("Approved") String Approved);




    @GET("FI/FiMasterData")
    Call<FIDataModel> getDataById(@Header("Authorization") String bearerToken, @Query("creator") String param1, @Query("ficode") String param2);

    @POST("UserMobile/CreateHomeVisit")
    Call<JsonObject> saveHouseVerificationDetails(@Body RequestBody file);


    @GET("LiveTrack/GetFiListForHomeVisit")
    Call<HomeVisitListModel> getHVManagerList(@Query("creator") String creator, @Query("groupCode") String groupCode, @Query("citycode") String citycode, @Query("imei") String imei);

    @POST("PDL.UserService.API/api/User/GetToken")
    Call<JsonObject> getTokenForABF(@Body JsonObject jsonObject);


    @POST("PDL.Mobile.API/api/IMEIMapping/InsertDevicedata")
    Call<JsonObject> insertDeviceData(@Body JsonObject jsonObject);




    @GET("PDL.Userservice.api/api/DDLHelper/GetCreator")
    Call<List<CreatorModel>> getCreatorList();

    @GET("PDL.Userservice.api/api/DDLHelper/GetSBIVillageMaster")
    Call<VillageListModel> getVillageList(@Query("stcode") String stcode, @Query("discode") String discode, @Query("subdiscode") String subdiscode);


    @GET("PDL.Userservice.api/api/DDLHelper/GetSBICityMaster")
    Call<CityModelList> getCityList(@Query("ststecode") String stcode);


    @GET("PDL.Userservice.api/api/DDLHelper/GetSBIDistrictMaster")
    Call<DistrictListModel> getDistictList(@Query("ststecode") String subDistrict);


    @GET("PDL.Userservice.api/api/DDLHelper/GetSBISubDistrictMaster")
    Call<SubDistrictModel> getSubDistrictList(@Query("subDistrict") String subDistrict);



    @POST("PDL.Userservice.api/api/DDLHelper/InsertVillDistCode")
    Call<JsonObject> insertAddressCodes(@Body JsonObject jsonObject);


    @POST("PDL.Mobile.API/api/IMEIMapping/RcPromiseToPay")
    Call<JsonObject> insertRcPromiseToPay(@Body JsonObject jsonObject);

    @POST("PDL.Mobile.API/api/IMEIMapping/InsertRcDistribution")
    Call<JsonObject> insertRcDistribution(@Body JsonObject jsonObject);


    @POST("InstCollection/SaveReceipt")
    Call<JsonObject> insertRcDistributionNew(@Body PosInstRcvNew jsonObject,
                                             @Header("dbname") String dbname,
                                             @Header("userid") String userid);

    @POST("InstCollection/UpdateQrRcCollection")
    Call<JsonObject> insertQRPayment(@Body QRCollStatus jsonObject,
                                             @Header("dbname") String dbname,
                                             @Header("userid") String userid);


    @POST("PDL.Mobile.API/api/Crif/InitilizeCrif")
    Call<JsonObject> generateCrifForVehicle(@Body JsonObject jsonObject);

    @POST("PDL.Mobile.API/api/Crif/GetBREDetails")
    Call<BREResponse> getBREStatus(@Query("creator") String creator, @Query("ficode") String ficode);


    @GET("PDL.FIService.API/api/InstCollection/GetQrPaymentsBySmcode")
    Call<AccountDetails_Model> getQrPaymentsBySmcode(
            @Query("SmCode") String smCode,
            @Query("userid") String userId,
            @Query("type") String type,
            @Header("Authorization") String authorizationHeader
    );


    @POST("LiveTrack/FiForUdhan")
    Call<JsonObject> saveSchemeForVH(@Body JsonObject jsonObject);

    @POST("PDL.SMS.API/api/Sms/SendSms?MessageType=otp")
    Call<JsonObject> getOtp(@Body JsonObject jsonObject);


    @GET("PDL.SMS.API/api/Sms/SendOtp")
    Call<JsonObject> verifyOTP(@Query("MobileNo") String MobileNo,@Query("Otp") String Otp);

    @GET("PDL.Mobile.Api/api/LiveTrack/CheckLoanByAadhar")
    Call<DeDupeResponse> checkAdharDeDupe(@Query("Aadharno") String Aadharno);

    @Multipart
    @POST("PDL.SourcingApp.Api/api/InstCollection/QrPaymentSettlement")
    Call<JsonObject> saveReciptOnpayment(@Part MultipartBody.Part FileName, @Part("SmCode") RequestBody SmCode); // Other parts, if any)

/*
    @Field("ficode") String fiCode, @Field("full_name") String fullName, @Field("dob") String dob,
    @Field("co") String co, @Field("address") String address, @Field("city") String city, @Field("state") String state,
    @Field("pin") String pin, @Field("loan_amount") String loan_amount, @Field("mobile") String mobile, @Field("creator") String creator,
    @Field("pancard") String pancard, @Field("BrCode") String BrCode, @Field("GrpCode") String GrpCode, @Field("AadharID") String AadharID,
    @Field("Gender") String Gender, @Field("Bank") String Bank, @Field("Income") String Income,
    @Field("Expense") String Expense, @Field("LoanReason") String LoanReason, @Field("Duration") String Duration*/
/*

    @FormUrlEncoded
    @POST("Login")
    public Call<SignupData> login(@Field("Login_Name") String email, @Field("Login_Password") String password,@Field("SERIAL") String SERIAL);

    @FormUrlEncoded
    @POST("Login")
    public Call<SignupData> loginwithotp(@Field("Login_Mobile") String Person_Mobile);


    @FormUrlEncoded
    @POST("OnlineSessionPlay")
    public Call<List<OnlineClassData>> offlineclass(@Field("Person_Id") String person_Id,@Field("Date_From") String Date_From,@Field("Date_Till") String Date_Till);


    @FormUrlEncoded
    @POST("Assignment")
    public Call<List<Assignments>> getassignmentlist(@Field("Person_Id") String person_Id, @Field("Date_From") String Date_From, @Field("Date_Till") String Date_Till);



    @GET("OnlineSession/{userid}")
    public Call<List<OnlineClassData>>  onlineclass(@Path("userid") String userid);

  */
/*  @GET("OnlineSessionPlay/{userid}")
    public Call<List<OnlineClassData>>  offlineclass(@Path("userid") String userid);*//*


    @GET("Class/{userid}")
    public Call<JsonElement> classList(@Path("userid") String userid);


    @GET("Logout/{userid}")
    public Call<MeetingData> logout(@Path("userid") int userid);

    @GET("Profile/{userid}")
    public Call<SignupData> userProfile(@Path("userid") String userid);


    @Multipart
    @POST("AssignmentCreate")
    Call<JsonElement> uploadFile(@Part MultipartBody.Part file,@Part("Assignment_Data") RequestBody assignment_data);


    @Multipart
    @POST("AssignmentSubmission")
    Call<JsonElement> uploadAssignFile(@Part MultipartBody.Part file,@Part("Assignment_Data") RequestBody assignment_data);



    @FormUrlEncoded
    @POST("DeviceInfo")
    public Call<MeetingData> sendInformation(@Field("DeviceInfo_Id") String DeviceInfo_Id, @Field("Person_Id") String Person_Id, @Field("DEVICE") String DEVICE, @Field("MODEL") String MODEL
            , @Field("PRODUCT") String PRODUCT, @Field("BRAND") String BRAND, @Field("DISPLAY") String DISPLAY, @Field("MANUFACTURER") String MANUFACTURER
            , @Field("SERIAL") String SERIAL, @Field("Latitude") String Latitude, @Field("Longitude") String Longitude, @Field("App_Version") String App_Version, @Field("Firebase_device_token_id") String Firebase_device_token_id);



    @FormUrlEncoded
    @POST("StaffDetails")
    public Call<JsonElement> StaffDetails(@Field("UserType_Id") String UserType_Id,@Field("Organisation_Id") String Organisation_Id,@Field("BranchOffice_Id") String BranchOffice_Id
    ,@Field("Class_Id") String Class_Id);


    @FormUrlEncoded
    @POST("ProfileUpdate")
    public Call<String> UpdateProfile(@Field("Person_Id") String Person_Id,@Field("Person_Mobile") String Person_Mobile,@Field("Person_FatherMobile") String Person_FatherMobile
            ,@Field("Person_Address") String Person_Address,@Field("Person_Password") String Person_Password,@Field("Photo_Base_64") String Photo_Base_64,@Field("Photo_FileName") String Photo_FileName,@Field("Person_GoogleEmailId") String person_GoogleEmailId);


    @FormUrlEncoded
    @POST("UpdateEmail")
    public Call<String> UpdateProfileEmail(@Field("Person_Id") String Person_Id,@Field("Person_GoogleEmailId") String Person_Mobile);



    @FormUrlEncoded
    @POST("JoinMeeting")
    public Call<MeetingData> joinMetting(@Field("OnlineSession_Id") String OnlineSession_Id, @Field("Person_Id") String Person_Id, @Field("User_Type") String User_Type);

    @FormUrlEncoded
    @POST("Create")
    public Call<MeetingData> createMetting(@Field("OnlineSession_Id") String OnlineSession_Id, @Field("Person_Id") String Person_Id, @Field("User_Type") String User_Type);

    @FormUrlEncoded
    @POST("OnlineSession")
    public Call<MeetingData> createNEWMetting(@Field("OnlineSession_AddedBy") String OnlineSession_AddedBy, @Field("OnlineSession_ClassId") String OnlineSession_ClassId, @Field("OnlineSession_Date") String OnlineSession_Date
    , @Field("OnlineSession_Lead_PersonId") String OnlineSession_Lead_PersonId, @Field("OnlineSession_Topic") String OnlineSession_Topic, @Field("OnlineSession_AllowRecordingStop") String OnlineSession_AllowRecordingStop
    , @Field("OnlineSession_AutoStartRecording") String OnlineSession_AutoStartRecording, @Field("OnlineSession_EnableRecording") String OnlineSession_EnableRecording
    , @Field("OnlineSession_StartHour") String OnlineSession_StartHour, @Field("OnlineSession_StartMinute") String OnlineSession_StartMinute
    , @Field("OnlineSession_StartAM_PM") String OnlineSession_StartAM_PM, @Field("OnlineSession_EndHour") String OnlineSession_EndHour
    , @Field("OnlineSession_EndMinute") String OnlineSession_EndMinute, @Field("OnlineSession_EndAM_PM") String OnlineSession_EndAM_PM,@Field("OnlineSession_ClassId_In") String OnlineSession_ClassId_In,
    @Field("OnlineSession_MuteOnStart") String OnlineSession_MuteOnStart, @Field("OnlineSession_AllowModsToUnmuteUsers") String OnlineSession_AllowModsToUnmuteUsers,
    @Field("OnlineSession_WebCamsOnlyForModerator") String OnlineSession_WebCamsOnlyForModerator, @Field("OnlineSession_LockSettingsDisableCam") String OnlineSession_LockSettingsDisableCam,
    @Field("OnlineSession_LockSettingsDisableMic") String OnlineSession_LockSettingsDisableMic,@Field("OnlineSession_SubjectId") String OnlineSession_SubjectID,@Field("OnlineSession_Type") String OnlineSession_Type,@Field("OnlineSession_LiveURL") String OnlineSession_LiveURL);


    @GET("ImageGallery")
    public Call<List<GalleryClass>> getAlbumList();


    @FormUrlEncoded
    @POST("VideoLectures")
    public Call<List<AlbumList>> getAlbumList(@Field("Person_Id") String Person_Id,@Field("Designation_Id") String Designation_Id,@Field("UserType_Id") String UserType_Id
            ,@Field("Organisation_Id") String Organisation_Id,@Field("BranchOffice_Id") String BranchOffice_Id,@Field("Class_Id") String Class_Id,@Field("Section_Id") String Section_Id,@Field("OnlineSession_Id") String OnlineSession_Id
            ,@Field("Date_From") String Date_From,@Field("Date_Till") String Date_Till);


    @FormUrlEncoded
    @POST("Emagazine")
    public Call<List<MagazineModel>> geteBookList(@Field("Person_Id") String Person_Id, @Field("Designation_Id") String Designation_Id, @Field("UserType_Id") String UserType_Id
            , @Field("Organisation_Id") String Organisation_Id, @Field("BranchOffice_Id") String BranchOffice_Id, @Field("Class_Id") String Class_Id, @Field("Section_Id") String Section_Id, @Field("OnlineSession_Id") String OnlineSession_Id
            );



    @GET("VideoGallery")
    public Call<List<VideoGalleryData>> getgallerylist();

    @GET("attendance/monthAttendence")
    public Call<List<AttendanceData>> attendanceCalendar(@QueryMap Map<String, String> options);

    @GET("Subject")
    public Call<JsonElement> getsubjectlist();

*/



/*

    @GET("District")
    public Call<JsonElement> getdistrictlist();

    @GET("Ward")
    public Call<JsonElement> getwardlist();


    @GET("PropertyType")
    public Call<JsonElement> getpropertylist();

    @GET("SurveyStatus")
    public Call<JsonElement> getSurveyStatuslist();

    @GET("api/GrivanceCategory")
    public Call<JsonElement> getGrivanceCategory();

    @GET("api/Locality")
    public Call<JsonElement> getLocality();

    @GET("api/ConstructionType")
    public Call<JsonElement> getConstructionType();

    @GET("api/RoadWidthType")
    public Call<JsonElement> getRoadType();

    @GET("ULB/{id}")
    public Call<JsonElement> getcitylist(@Path("id") String id);

    @GET("Locality/{id}")
    public Call<JsonElement> getlocalitylist(@Path("id") String id);

    @GET("api/Questionire/1")
    public Call<JsonElement> getlist();

    @GET()
    Call<String> getStringResponse(@Url String url);


    @GET("api/Mutationtype")
    public Call<JsonElement> getMutationtype();

    @GET("api/TradeMaster")
    public Call<JsonElement> getTradetype();

    @GET("api/TradeMaster/{id}")
    public Call<JsonElement> getTradeSubtype(@Path("id") String id);

    @GET("api/UsageType")
    public Call<JsonElement> getUsagetype();*/
}
