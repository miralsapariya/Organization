package com.onlineeducationsystemorganization.network;


import com.onlineeducationsystemorganization.model.BaseBean;
import com.onlineeducationsystemorganization.model.CartList;
import com.onlineeducationsystemorganization.model.CompanyUrl;
import com.onlineeducationsystemorganization.model.CourseDetail;
import com.onlineeducationsystemorganization.model.CourseList;
import com.onlineeducationsystemorganization.model.Courses;
import com.onlineeducationsystemorganization.model.DefaultCategory;
import com.onlineeducationsystemorganization.model.ForgotPwd;
import com.onlineeducationsystemorganization.model.GetProfile;
import com.onlineeducationsystemorganization.model.GlobalSearch;
import com.onlineeducationsystemorganization.model.Home;
import com.onlineeducationsystemorganization.model.SubCategory;
import com.onlineeducationsystemorganization.model.User;

import java.util.HashMap;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
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
    Call<BaseBean> resend(@Header("language") String lang,@FieldMap HashMap<String, String> map);

    @FormUrlEncoded
    @POST("register")
    Call<User> register(@Header("language") String lang,@FieldMap HashMap<String, String> map);

    @FormUrlEncoded
    @POST("verify_otp")
    Call<User> otp(@Header("language") String lang,@FieldMap HashMap<String, String> map);

    @FormUrlEncoded
    @POST("homescreen")
    Call<Home> getHome(@Header("language") String lang, @FieldMap HashMap<String, String> map);

    @FormUrlEncoded
    @POST("coursedetails")
    Call<CourseDetail> getCourseDetail(@Header("language") String lang, @Header("Authorization")String auth, @FieldMap HashMap<String, String> map);

    @FormUrlEncoded
    @POST("addwishlist")
    Call<BaseBean> addWhishList(@Header("language") String lang,@Header("Authorization")String auth,@FieldMap HashMap<String, String> map);


    @Multipart
    @POST("edit_profile")
    Call<GetProfile> editProfile(@Header("language") String lang,
                                 @Header("Authorization")String auth,
                                 @Part("user_id") RequestBody userId,
                                 @Part("name") RequestBody name,
                                 @Part("email") RequestBody email,
                                 @Part("phone_no") RequestBody phone,
                                 @Part MultipartBody.Part file);

    @Multipart
    @POST("edit_profile")
    Call<GetProfile> editProfile(@Header("language") String lang,
                                 @Header("Authorization")String auth,
                                 @Part("user_id") RequestBody userId,
                                 @Part("name") RequestBody name,
                                 @Part("email") RequestBody email,
                                 @Part("phone_no") RequestBody phone
    );
    @FormUrlEncoded
    @POST("get_profile")
    Call<GetProfile> getProfile(@Header("language") String lang
            ,@Header("Authorization")String auth,
                                @FieldMap HashMap<String, String> map);

    @FormUrlEncoded
    @POST("courselist")
    Call<CourseList> getCourseList(@Header("language") String lang, @Header("Authorization")String auth, @FieldMap HashMap<String, String> map);

    @FormUrlEncoded
    @POST("categories_details")
    Call<SubCategory> getSubCat(@Header("language") String lang, @FieldMap HashMap<String, String> map);

    @POST("globalsearch")
    Call<DefaultCategory> getDefaultCategory(@Header("language") String lang);

    @FormUrlEncoded
    @POST("globalsearch")
    Call<GlobalSearch> getDefaultCategory(@Header("language") String lang, @Header("Authorization")String auth, @FieldMap HashMap<String, String> map);

    @POST("mycartlist")
    Call<CartList> getCartList(@Header("language") String lang, @Header("Authorization")String auth);

    @FormUrlEncoded
    @POST("addtocart")
    Call<BaseBean> addToCart(@Header("language") String lang,@Header("Authorization")String auth,@FieldMap HashMap<String, String> map);

    @FormUrlEncoded
    @POST("deletecart")
    Call<BaseBean> deleteFromCart(@Header("language") String lang,@Header("Authorization")String auth,@FieldMap HashMap<String, String> map);


    @FormUrlEncoded
    @POST("updatecart")
    Call<BaseBean> updateCart(@Header("language") String lang,@Header("Authorization")String auth,@FieldMap HashMap<String, String> map);

    @POST("add_inquiry")
    Call<BaseBean> addInquiry(@Header("language") String lang,@Header("Authorization")String auth);

    @FormUrlEncoded
    @POST("courselist")
    Call<Courses> courseList(@Header("language") String lang, @Header("Authorization") String auth, @FieldMap HashMap<String, String> map);
}