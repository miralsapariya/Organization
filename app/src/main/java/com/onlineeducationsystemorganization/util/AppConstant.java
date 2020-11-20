package com.onlineeducationsystemorganization.util;

import com.onlineeducationsystemorganization.model.User;

/**
 * Created  on 25/10/16.
 */
public class AppConstant {
    public static String APP_IMAGE_FOLDER = android.os.Environment.getExternalStorageDirectory() + "/";

    public static final String DEVICE = "android";
    public static final String ENG_LANG = "en";
    public static final String ARABIC_LANG = "ar";

    public static final String ABOUT_US="http://1.22.161.26:9875/online_education_system/";
    public static final String PRIVACY_POLICY="http://1.22.161.26:9875/online_education_system/";
    public static final String TERMS_CONDITION="http://1.22.161.26:9875/online_education_system/";
    public static final String BLOG="http://1.22.161.26:9875/online_education_system/";
    public static String SUB_FOLDER = "OnlineEduSystemOrg";
    public static User registerData=new User();
    //Permission Constant
    public static final int CAMERA = 1;

    public static String COURSE_STATUS_RESUME="2";
    public static String COURSE_STATUS_START="1";

    public static int USER_TYPE_ADMIN=1;
    public static String USER_TYPE_USER="2";

    public static String ASSIGN_COURSES="";
    public static String ASSIGN_COURSES_Id="";

    //
    //QUESTION_TYPE
    public static int SINGLE_CHOICE=1;
    public static int MULTIPLE_CHOICE=2;
    public static int SHORTING=3;
    public static int STATEMENT=4;
    public static int FILL_IN_THE_BLANK=5;
    public static int MATRIX=6;
    // dashboard mode
    public static  String DAILY="daily";
    public static String WEEKLY="weekly";
    public static String MONTHLY="monthly";
    public static String YEARLY="yearly";
    public static String CUSTOM="custom";

}
