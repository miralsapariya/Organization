package com.onlineeducationsystemorganization.network;


import com.onlineeducationsystemorganization.adapter.MyCourseList;
import com.onlineeducationsystemorganization.model.AddUser;
import com.onlineeducationsystemorganization.model.AssignCourseUserList;
import com.onlineeducationsystemorganization.model.BaseBean;
import com.onlineeducationsystemorganization.model.CartList;
import com.onlineeducationsystemorganization.model.CheckCourse;
import com.onlineeducationsystemorganization.model.CompanyUrl;
import com.onlineeducationsystemorganization.model.CourseDetail;
import com.onlineeducationsystemorganization.model.CourseList;
import com.onlineeducationsystemorganization.model.Courses;
import com.onlineeducationsystemorganization.model.DefaultCategory;
import com.onlineeducationsystemorganization.model.Exam;
import com.onlineeducationsystemorganization.model.ForgotPwd;
import com.onlineeducationsystemorganization.model.GetEditUser;
import com.onlineeducationsystemorganization.model.GetProfile;
import com.onlineeducationsystemorganization.model.GlobalSearch;
import com.onlineeducationsystemorganization.model.Home;
import com.onlineeducationsystemorganization.model.Inquirie;
import com.onlineeducationsystemorganization.model.InquiryHistory;
import com.onlineeducationsystemorganization.model.InstructorProfile;
import com.onlineeducationsystemorganization.model.MyWhishList;
import com.onlineeducationsystemorganization.model.Restart;
import com.onlineeducationsystemorganization.model.Result;
import com.onlineeducationsystemorganization.model.SectionCourse;
import com.onlineeducationsystemorganization.model.SectionSlideDetail;
import com.onlineeducationsystemorganization.model.SubCategory;
import com.onlineeducationsystemorganization.model.Subscription;
import com.onlineeducationsystemorganization.model.SubscriptionHistory;
import com.onlineeducationsystemorganization.model.User;
import com.onlineeducationsystemorganization.model.UserList;

import java.util.HashMap;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * ApiInterface.java
 * This class act as an interface between Retrofit and Classes used using Retrofit in Application
 *
 * @author Appinvetiv
 * @version 1.0
 * @since 1.0
 */

public interface ApiInterface {
    @FormUrlEncoded
    @POST("validate_url")
    Call<CompanyUrl> companyUrl(@Header("language") String lang, @FieldMap HashMap<String, String> map);

    @FormUrlEncoded
    @POST("login")
    Call<User> login(@Header("language") String lang, @FieldMap HashMap<String, String> map);

    @FormUrlEncoded
    @POST("forgotpassword")
    Call<ForgotPwd> forgotPWD(@Header("language") String lang, @FieldMap HashMap<String, String> map);

    @FormUrlEncoded
    @POST("reset_password")
    Call<BaseBean> resetPwd(@Header("language") String lang, @FieldMap HashMap<String, String> map);

    @FormUrlEncoded
    @POST("resend_otp")
    Call<BaseBean> resend(@Header("language") String lang, @FieldMap HashMap<String, String> map);

    @FormUrlEncoded
    @POST("register")
    Call<User> register(@Header("language") String lang, @FieldMap HashMap<String, String> map);

    @FormUrlEncoded
    @POST("verify_otp")
    Call<User> otp(@Header("language") String lang, @FieldMap HashMap<String, String> map);

    @FormUrlEncoded
    @POST("homescreen")
    Call<Home> getHome(@Header("language") String lang, @FieldMap HashMap<String, String> map);

    @FormUrlEncoded
    @POST("coursedetails")
    Call<CourseDetail> getCourseDetail(@Header("language") String lang, @Header("Authorization") String auth, @FieldMap HashMap<String, String> map);

    @FormUrlEncoded
    @POST("addwishlist")
    Call<BaseBean> addWhishList(@Header("language") String lang, @Header("Authorization") String auth, @FieldMap HashMap<String, String> map);


    @Multipart
    @POST("edit_profile")
    Call<GetProfile> editProfile(@Header("language") String lang,
                                 @Header("Authorization") String auth,
                                 @Part("user_id") RequestBody userId,
                                 @Part("name") RequestBody name,
                                 @Part("organization_name") RequestBody compname,
                                 @Part("email") RequestBody email,
                                 @Part("phone_no") RequestBody phone,
                                 @Part("country_name") RequestBody countryName,
                                 @Part MultipartBody.Part file);

    @Multipart
    @POST("edit_profile")
    Call<GetProfile> editProfile(@Header("language") String lang,
                                 @Header("Authorization") String auth,
                                 @Part("user_id") RequestBody userId,
                                 @Part("name") RequestBody name,
                                 @Part("organization_name") RequestBody compname,
                                 @Part("email") RequestBody email,
                                 @Part("phone_no") RequestBody phone,
                                 @Part("country_name") RequestBody countryName
    );

    @GET("get_profile")
    Call<GetProfile> getProfile(@Header("language") String lang
            , @Header("Authorization") String auth);

    @FormUrlEncoded
    @POST("courselist")
    Call<CourseList> getCourseList(@Header("language") String lang, @Header("Authorization") String auth, @FieldMap HashMap<String, String> map);

    @FormUrlEncoded
    @POST("categories_details")
    Call<SubCategory> getSubCat(@Header("language") String lang, @FieldMap HashMap<String, String> map);

    @POST("globalsearch")
    Call<DefaultCategory> getDefaultCategory(@Header("language") String lang);

    @FormUrlEncoded
    @POST("globalsearch")
    Call<GlobalSearch> getDefaultCategory(@Header("language") String lang, @Header("Authorization") String auth, @FieldMap HashMap<String, String> map);

    @POST("mycartlist")
    Call<CartList> getCartList(@Header("language") String lang, @Header("Authorization") String auth);

    @FormUrlEncoded
    @POST("addtocart")
    Call<BaseBean> addToCart(@Header("language") String lang, @Header("Authorization") String auth, @FieldMap HashMap<String, String> map);

    @FormUrlEncoded
    @POST("deletecart")
    Call<BaseBean> deleteFromCart(@Header("language") String lang, @Header("Authorization") String auth, @FieldMap HashMap<String, String> map);


    @FormUrlEncoded
    @POST("updatecart")
    Call<BaseBean> updateCart(@Header("language") String lang, @Header("Authorization") String auth, @FieldMap HashMap<String, String> map);

    @POST("add_inquiry")
    Call<BaseBean> addInquiry(@Header("language") String lang, @Header("Authorization") String auth);

    @FormUrlEncoded
    @POST("courselist")
    Call<Courses> courseList(@Header("language") String lang, @Header("Authorization") String auth, @FieldMap HashMap<String, String> map);

    @POST("userlist")
    Call<UserList> userList(@Header("language") String lang, @Header("Authorization") String auth);

    @Multipart
    @POST("adduser")
    Call<AddUser> addUser(@Header("language") String lang,
                          @Header("Authorization") String auth,
                          @Part("firstname") RequestBody userId,
                          @Part("lastname") RequestBody name,
                          @Part("email") RequestBody email,
                          @Part("user_number") RequestBody phone,
                          @Part("role") RequestBody role,
                          @Part("assign_course") RequestBody assign,
                          @Part("country_name") RequestBody countryName,
                          @Part("password") RequestBody pwd,
                          @Part MultipartBody.Part file);

    @Multipart
    @POST("adduser")
    Call<AddUser> addUser(@Header("language") String lang,
                          @Header("Authorization") String auth,
                          @Part("firstname") RequestBody userId,
                          @Part("lastname") RequestBody name,
                          @Part("email") RequestBody email,
                          @Part("user_number") RequestBody phone,
                          @Part("role") RequestBody role,
                          @Part("assign_course") RequestBody assign,
                          @Part("country_name") RequestBody countryName,
                          @Part("password") RequestBody pwd);

    @Multipart
    @POST("updateuser")
    Call<AddUser> editUser(@Header("language") String lang,
                           @Header("Authorization") String auth,
                           @Part("edit_id") RequestBody editId,
                           @Part("firstname") RequestBody userId,
                           @Part("lastname") RequestBody name,
                           @Part("email") RequestBody email,
                           @Part("user_number") RequestBody phone,
                           @Part("role") RequestBody role,
                           @Part("assign_course") RequestBody assign,
                           @Part("country_name") RequestBody countryName,
                           @Part("password") RequestBody pwd,
                           @Part MultipartBody.Part file);

    @Multipart
    @POST("updateuser")
    Call<AddUser> editUser(@Header("language") String lang,
                           @Header("Authorization") String auth,
                           @Part("edit_id") RequestBody editId,
                           @Part("firstname") RequestBody userId,
                           @Part("lastname") RequestBody name,
                           @Part("email") RequestBody email,
                           @Part("user_number") RequestBody phone,
                           @Part("role") RequestBody role,
                           @Part("assign_course") RequestBody assign,
                           @Part("country_name") RequestBody countryName,
                           @Part MultipartBody.Part file);

    @Multipart
    @POST("updateuser")
    Call<AddUser> editUser(@Header("language") String lang,
                           @Header("Authorization") String auth,
                           @Part("edit_id") RequestBody editId,
                           @Part("firstname") RequestBody userId,
                           @Part("lastname") RequestBody name,
                           @Part("email") RequestBody email,
                           @Part("user_number") RequestBody phone,
                           @Part("role") RequestBody role,
                           @Part("assign_course") RequestBody assign,
                           @Part("country_name") RequestBody countryName,
                           @Part("password") RequestBody pwd);

    @Multipart
    @POST("updateuser")
    Call<AddUser> editUser(@Header("language") String lang,
                           @Header("Authorization") String auth,
                           @Part("edit_id") RequestBody editId,
                           @Part("firstname") RequestBody userId,
                           @Part("lastname") RequestBody name,
                           @Part("email") RequestBody email,
                           @Part("user_number") RequestBody phone,
                           @Part("role") RequestBody role,
                           @Part("assign_course") RequestBody assign,
                           @Part("country_name") RequestBody countryName
    );


    @FormUrlEncoded
    @POST("get_userdata")
    Call<GetEditUser> getUser(@Header("language") String lang, @Header("Authorization") String auth, @FieldMap HashMap<String, String> map);


    @FormUrlEncoded
    @POST("delete_user")
    Call<BaseBean> deleteUser(@Header("language") String lang, @Header("Authorization") String auth, @FieldMap HashMap<String, String> map);


    @FormUrlEncoded
    @POST("courseuserlist")
    Call<AssignCourseUserList> courseuserlist(@Header("language") String lang, @Header("Authorization") String auth, @FieldMap HashMap<String, String> map);

    @FormUrlEncoded
    @POST("assigned_user")
    Call<BaseBean> assignUser(@Header("language") String lang, @Header("Authorization") String auth, @FieldMap HashMap<String, String> map);

    @FormUrlEncoded
    @POST("unassigned_user")
    Call<BaseBean> unAssignUser(@Header("language") String lang, @Header("Authorization") String auth, @FieldMap HashMap<String, String> map);

    @FormUrlEncoded
    @POST("inquirylists")
    Call<Inquirie> inquireList(@Header("language") String lang, @Header("Authorization") String auth, @FieldMap HashMap<String, String> map);

    @FormUrlEncoded
    @POST("inquiryhistory")
    Call<InquiryHistory> inquireHistory(@Header("language") String lang, @Header("Authorization") String auth, @FieldMap HashMap<String, String> map);

    @FormUrlEncoded
    @POST("inquiryadduser")
    Call<BaseBean> editInquiry(@Header("language") String lang, @Header("Authorization") String auth, @FieldMap HashMap<String, String> map);

    @FormUrlEncoded
    @POST("subscriptionuserlist")
    Call<Subscription> subscriptionList(@Header("language") String lang, @Header("Authorization") String auth, @FieldMap HashMap<String, String> map);


    @FormUrlEncoded
    @POST("subscriptionuserhistorylist")
    Call<SubscriptionHistory> subscriptionListHistory(@Header("language") String lang, @Header("Authorization") String auth, @FieldMap HashMap<String, String> map);

    @FormUrlEncoded
    @POST("resetcourse")
    Call<BaseBean> resetCourse(@Header("language") String lang, @Header("Authorization")String auth, @FieldMap HashMap<String, String> map);

    @POST("mycourselist")
    Call<MyCourseList> myCourseList(@Header("language") String lang, @Header("Authorization")String auth);

    @FormUrlEncoded
    @POST("startcourse")
    Call<SectionCourse> startCourse(@Header("language") String lang, @Header("Authorization")String auth, @FieldMap HashMap<String, String> map);

    @FormUrlEncoded
    @POST("startsectionslide")
    Call<SectionSlideDetail> getSectionSlide(@Header("language") String lang, @Header("Authorization")String auth, @FieldMap HashMap<String, String> map);

    @FormUrlEncoded
    @POST("checkcourse")
    Call<CheckCourse> checkCourse(@Header("language") String lang, @Header("Authorization")String auth, @FieldMap HashMap<String, String> map);

    @FormUrlEncoded
    @POST("quiz/start")
    Call<Exam> startQuizeApi(@Header("language") String lang, @Header("Authorization")String auth, @FieldMap HashMap<String, String> map);

    @FormUrlEncoded
    @POST("quiz/continue")
    Call<Exam> continueQuiz(@Header("language") String lang, @Header("Authorization")String auth, @FieldMap HashMap<String, String> map);

    @FormUrlEncoded
    @POST("quiz/result")
    Call<Result> resultQuiz(@Header("language") String lang, @Header("Authorization")String auth, @FieldMap HashMap<String, String> map);

    @FormUrlEncoded
    @POST("quiz/restart")
    Call<Restart> restart(@Header("language") String lang, @Header("Authorization")String auth, @FieldMap HashMap<String, String> map);

    @POST("mywishlist")
    Call<MyWhishList> getWhishList(@Header("language") String lang, @Header("Authorization")String auth);

    @FormUrlEncoded
    @POST("change_password")
    Call<BaseBean> changePwd(@Header("language") String lang,@Header("Authorization")String auth,@FieldMap HashMap<String, String> map);

    @FormUrlEncoded
    @POST("popular_instructorlist")
    Call<InstructorProfile> getInstructorProfile(@Header("language") String lang, @Header("Authorization")String auth, @FieldMap HashMap<String, String> map);

}