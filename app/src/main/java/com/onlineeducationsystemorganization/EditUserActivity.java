package com.onlineeducationsystemorganization;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.hbb20.CountryCodePicker;
import com.onlineeducationsystemorganization.interfaces.NetworkListener;
import com.onlineeducationsystemorganization.model.AddUser;
import com.onlineeducationsystemorganization.model.Courses;
import com.onlineeducationsystemorganization.model.GetEditUser;
import com.onlineeducationsystemorganization.network.ApiCall;
import com.onlineeducationsystemorganization.network.ApiInterface;
import com.onlineeducationsystemorganization.network.RestApi;
import com.onlineeducationsystemorganization.network.ServerConstents;
import com.onlineeducationsystemorganization.util.AppConstant;
import com.onlineeducationsystemorganization.util.AppSharedPreference;
import com.onlineeducationsystemorganization.util.AppUtils;
import com.onlineeducationsystemorganization.util.ImageHelper;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;

public class EditUserActivity extends BaseActivity implements NetworkListener {

    //
    private static final int PERMISSION_PHOTO = 1;
    private static final int TAKE_PHOTO = 2;
    private static final int SELECT_IMAGE = 3;
    private static Uri cameraURI;
    private static String mCurrentPhotoPath;
    CountryCodePicker ccp;
    private ImageView imgBack;
    private BottomSheetDialog mBottomSheetDialog;
    private LinearLayout llAssignment;
    private TextView tvRole, tvAssignCourse, tvTitle;
    private Button btnSubmit, btnReset;
    private EditText etName, etLName, etEmail, etPhone, etPwd, etConfirmPwd;
    private LinearLayout llMain;
    private String role = "";
    private String selectedCountryCode = "", selectedCountry = "";
    private ImageView imgUser;
    private String filePath = "", user_id = "";
    private int rotationAngle;
    private File myFile;
    private Uri mCurrentPhotoPathUri;
    private GetEditUser data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initUI();
    }

    private void initUI() {
        user_id = getIntent().getExtras().getString("user_id");
        imgUser = findViewById(R.id.imgUser);
        imgUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(EditUserActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                        || ContextCompat.checkSelfPermission(EditUserActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(EditUserActivity.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_PHOTO);
                } else {
                    chosePicture();
                }
            }
        });
        imgBack = findViewById(R.id.imgBack);
        tvRole = findViewById(R.id.tvRole);
        tvAssignCourse = findViewById(R.id.tvAssignCourse);
        tvRole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showRoleDialog();
            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        llAssignment = findViewById(R.id.llAssignment);
        llAssignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditUserActivity.this, SelectCoursesEditUserActivity.class);
                intent.putExtra("user_id", user_id);
                startActivity(intent);
            }
        });
        tvTitle = findViewById(R.id.tvTitle);
        tvTitle.setText(getString(R.string.edit_user));
        btnSubmit = findViewById(R.id.btnSubmit);
        btnSubmit.setText(getString(R.string.update));
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AppUtils.isInternetAvailable(EditUserActivity.this)) {
                    if (isValid()) {
                        hintAddUSer();
                    }
                }
            }
        });
        btnReset = findViewById(R.id.btnReset);
        btnReset.setVisibility(View.GONE);
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgUser.setImageResource(R.mipmap.placeholder);
                etName.setText("");
                etName.requestFocus();
                etLName.setText("");
                etEmail.setText("");
                etPhone.setText("");
                etPwd.setText("");
                etConfirmPwd.setText("");
                tvRole.setText(getString(R.string.role));
                tvAssignCourse.setText(R.string.assign_courses);
            }
        });
        etName = findViewById(R.id.etName);
        etLName = findViewById(R.id.etLName);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        etPwd = findViewById(R.id.etPwd);
        etConfirmPwd = findViewById(R.id.etConfirmPwd);
        tvTitle = findViewById(R.id.tvTitle);

        llMain = findViewById(R.id.llMain);
        ccp = findViewById(R.id.ccp);
        if (AppSharedPreference.getInstance().getString(EditUserActivity.this, AppSharedPreference.LANGUAGE_SELECTED) != null &&
                AppSharedPreference.getInstance().getString(EditUserActivity.this, AppSharedPreference.LANGUAGE_SELECTED).equalsIgnoreCase(AppConstant.ARABIC_LANG)) {
            ccp.setTextDirection(View.TEXT_DIRECTION_RTL);
        }
        selectedCountryCode = ccp.getSelectedCountryCodeWithPlus();
        selectedCountry = ccp.getSelectedCountryName();
        ccp.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
                selectedCountryCode = ccp.getSelectedCountryCodeWithPlus();
                selectedCountry = ccp.getSelectedCountryName();
            }
        });

        if (AppUtils.isInternetAvailable(EditUserActivity.this)) {
            hintGetUser();
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppConstant.ASSIGN_COURSES="";
    }

    private void hintGetUser() {
        String lang = "";
        AppUtils.showDialog(EditUserActivity.this, getString(R.string.pls_wait));
        ApiInterface apiInterface = RestApi.getConnection(ApiInterface.class, ServerConstents.API_URL);
        final HashMap params = new HashMap<>();
        params.put("user_id", user_id);
        if (AppSharedPreference.getInstance().getString(EditUserActivity.this, AppSharedPreference.LANGUAGE_SELECTED) == null ||
                AppSharedPreference.getInstance().getString(EditUserActivity.this, AppSharedPreference.LANGUAGE_SELECTED).equalsIgnoreCase(AppConstant.ENG_LANG)) {
            lang = AppConstant.ENG_LANG;
        } else {
            lang = AppConstant.ARABIC_LANG;
        }
        Call<GetEditUser> call = apiInterface.getUser(lang, AppSharedPreference.getInstance().
                getString(EditUserActivity.this, AppSharedPreference.ACCESS_TOKEN), params);
        ApiCall.getInstance().hitService(EditUserActivity.this, call, this, ServerConstents.GET_USER);
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (AppConstant.ASSIGN_COURSES.length() > 0) {
            tvAssignCourse.setText(AppConstant.ASSIGN_COURSES);
        }else
        {
            AppConstant.ASSIGN_COURSES="";
            tvAssignCourse.setText(getString(R.string.assign_courses));
        }
    }

    private void hintAddUSer() {
        String lang = "";

        AppUtils.showDialog(this, getString(R.string.pls_wait));
        ApiInterface apiInterface = RestApi.getConnection(ApiInterface.class, ServerConstents.API_URL);

        MultipartBody.Part body = null;
        if (myFile != null && !TextUtils.isEmpty(myFile.getAbsolutePath())) {
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), myFile);
            body = MultipartBody.Part.createFormData("image", myFile.getName(), requestFile);

            Log.d("file :::: ", myFile.getName());
        }
        RequestBody name =
                RequestBody.create(
                        MultipartBody.FORM, etName.getText().toString());
        RequestBody lname =
                RequestBody.create(
                        MultipartBody.FORM, etLName.getText().toString());
        RequestBody email =
                RequestBody.create(
                        MultipartBody.FORM, etEmail.getText().toString());

        RequestBody phone =
                RequestBody.create(
                        MultipartBody.FORM, selectedCountryCode + "-" + etPhone.getText().toString());

        RequestBody roleReq =
                RequestBody.create(
                        MultipartBody.FORM, role);

        RequestBody assignReq =
                RequestBody.create(
                        MultipartBody.FORM, AppConstant.ASSIGN_COURSES_Id);

        RequestBody countryName = RequestBody.create(
                MultipartBody.FORM, selectedCountry);

        RequestBody pwd = RequestBody.create(
                MultipartBody.FORM, etPwd.getText().toString());

        RequestBody userId=RequestBody.create(MultipartBody.FORM, user_id);

        if (AppSharedPreference.getInstance().getString(EditUserActivity.this, AppSharedPreference.LANGUAGE_SELECTED) == null ||
                AppSharedPreference.getInstance().getString(EditUserActivity.this, AppSharedPreference.LANGUAGE_SELECTED).equalsIgnoreCase(AppConstant.ENG_LANG)) {
            lang = AppConstant.ENG_LANG;
        } else {
            lang = AppConstant.ARABIC_LANG;
        }

        if (body != null) {
            Call<AddUser> call;
            if(!TextUtils.isEmpty(etPwd.getText().toString())) {
                call = apiInterface.editUser(lang,
                        AppSharedPreference.getInstance().getString(EditUserActivity.this, AppSharedPreference.ACCESS_TOKEN),
                        userId,name, lname, email, phone, roleReq, assignReq, countryName, pwd,
                        body
                );
            }else {
                 call = apiInterface.editUser(lang,
                        AppSharedPreference.getInstance().getString(EditUserActivity.this, AppSharedPreference.ACCESS_TOKEN),
                        userId,name, lname, email, phone, roleReq, assignReq, countryName,
                        body
                );
            }
            ApiCall.getInstance().hitService(EditUserActivity.this, call, this, ServerConstents.EDIT_PROFILE);
        } else {
            Call<AddUser> call;
            if(!TextUtils.isEmpty(etPwd.getText().toString())) {
                 call = apiInterface.editUser(lang,
                        AppSharedPreference.getInstance().getString(EditUserActivity.this, AppSharedPreference.ACCESS_TOKEN),
                        userId,name, lname, email, phone, roleReq, assignReq, countryName, pwd);
            }else{
                 call= apiInterface.editUser(lang,
                        AppSharedPreference.getInstance().getString(EditUserActivity.this, AppSharedPreference.ACCESS_TOKEN),
                       userId, name, lname, email, phone, roleReq, assignReq, countryName);
            }
            ApiCall.getInstance().hitService(EditUserActivity.this, call, this, ServerConstents.EDIT_USER);
        }
    }

    @Override
    public void onSuccess(int responseCode, Object response, int requestCode) {

        if (requestCode == ServerConstents.GET_USER) {
            data = (GetEditUser) response;
            if (data.getStatus() == ServerConstents.CODE_SUCCESS) {

                etName.setText(data.getData().get(0).getFirstName());
                etLName.setText(data.getData().get(0).getLastName());
                if(!TextUtils.isEmpty(data.getData().get(0).getCountry_code())) {
                    String sPhone = data.getData().get(0).getCountry_code();
                    selectedCountryCode = sPhone;

                    String s = sPhone.replaceAll("\\+", "");
                    Log.d("______________ ", s);

                    ccp.setCountryForPhoneCode(Integer.parseInt(s));
                    selectedCountry = ccp.getSelectedCountryName();

                    etPhone.setText(data.getData().get(0).getPhoneNo());
                }
                etEmail.setText(data.getData().get(0).getEmail());
                if (data.getData().get(0).getRole() == 1) {
                    role=1+"";
                    tvRole.setText(getString(R.string.admin));
                } else {
                    role=2+"";
                    tvRole.setText(getString(R.string.user));
                }


                if(data.getData().get(0).
                        getProfilePicture().length()>0) {
                    Picasso.with(this).load(data.getData().get(0).
                            getProfilePicture()).error(getResources().getDrawable(R.mipmap.placeholder)).into(imgUser);
                }

                if (AppUtils.isInternetAvailable(EditUserActivity.this)) {
                    getCourses();
                }else {
                    AppUtils.showAlertDialog(EditUserActivity.this,getString(R.string.no_internet),getString(R.string.alter_net));
                }
            }
        } else if (requestCode == ServerConstents.COURSE_LIST) {
            Courses data = (Courses) response;
            if (data.getStatus() == ServerConstents.CODE_SUCCESS) {

                int cnt=0;
                StringBuilder sb=new StringBuilder();
                StringBuilder sb1=new StringBuilder();
                for(int i=0;i<data.getData().get(0).getCourseslist().size();i++)
                {
                    if(data.getData().get(0).getCourseslist().get(i).getIs_assigned() == 1)
                    {
                        cnt=cnt+ (cnt+1);
                        Log.d("IS SELCTED :::: ", ""+data.getData().get(0).getCourseslist().get(i).getIs_assigned()+" "+cnt);
                        sb=sb.append(data.getData().get(0).getCourseslist().get(i).getId()).append(",");
                        sb1=sb1.append(data.getData().get(0).getCourseslist().get(i).getCourseName()).append(",");

                    }
                }
                if(cnt >0) {
                    AppConstant.ASSIGN_COURSES_Id = sb.deleteCharAt(sb.length() - 1).toString();
                    AppConstant.ASSIGN_COURSES = sb1.deleteCharAt(sb1.length() - 1).toString();

                    tvAssignCourse.setText(AppConstant.ASSIGN_COURSES);
                    Log.d(";;;;;;;;;;;;;;;;;;;; ",  AppConstant.ASSIGN_COURSES);
                }else
                {
                    tvAssignCourse.setText(getString(R.string.assign_courses));
                }
            }

        }else if(requestCode == ServerConstents.EDIT_USER){
            AddUser data=(AddUser) response;
            if(data.getStatus()==ServerConstents.CODE_SUCCESS)
            {
                Toast.makeText(EditUserActivity.this, data.getMessage(), Toast.LENGTH_SHORT).show();
                finish();
            }
        }

    }

    @Override
    public void onError (String response,int requestCode, int errorCode){
        Toast.makeText(EditUserActivity.this, response, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onFailure () {
        }

    private void getCourses () {
            String lang = "";
            AppUtils.showDialog(EditUserActivity.this, getString(R.string.pls_wait));
            ApiInterface apiInterface = RestApi.getConnection(ApiInterface.class, ServerConstents.API_URL);
            final HashMap params = new HashMap<>();
            params.put("user_id", user_id);
            if (AppSharedPreference.getInstance().getString(EditUserActivity.this, AppSharedPreference.LANGUAGE_SELECTED) == null ||
                    AppSharedPreference.getInstance().getString(EditUserActivity.this, AppSharedPreference.LANGUAGE_SELECTED).equalsIgnoreCase(AppConstant.ENG_LANG)) {
                lang = AppConstant.ENG_LANG;
            } else {
                lang = AppConstant.ARABIC_LANG;
            }
            Call<Courses> call = apiInterface.courseListUser(lang, AppSharedPreference.getInstance().
                    getString(EditUserActivity.this, AppSharedPreference.ACCESS_TOKEN), params);
            ApiCall.getInstance().hitService(EditUserActivity.this, call, this, ServerConstents.COURSE_LIST);

        }
        @Override
        public void onRequestPermissionsResult (int requestCode, @NonNull String[] permissions,
        @NonNull int[] grantResults){
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            switch (requestCode) {
                case PERMISSION_PHOTO:

                    if (grantResults.length > 0) {
                        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                            chosePicture();
                        } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                            Log.d("DENIE ", "=====>");
                            Intent i = new Intent();
                            i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            i.addCategory(Intent.CATEGORY_DEFAULT);
                            i.setData(Uri.parse("package:" + getPackageName()));
                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                            i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                            startActivity(i);
                        }
                    }
                    break;
            }
        }

        private boolean isValid ()
        {
            boolean bool = true;
            if (TextUtils.isEmpty(etName.getText().toString())) {
                bool = false;
                hideKeyboard();
                Toast.makeText(EditUserActivity.this, getString(R.string.toast_name), Toast.LENGTH_SHORT).show();

            } else if (TextUtils.isEmpty(etLName.getText().toString())) {
                bool = false;
                hideKeyboard();
                Toast.makeText(EditUserActivity.this, getString(R.string.toast_lname), Toast.LENGTH_SHORT).show();

            } else if (TextUtils.isEmpty(etEmail.getText().toString())) {
                bool = false;
                hideKeyboard();
                Toast.makeText(EditUserActivity.this, getString(R.string.toast_email), Toast.LENGTH_SHORT).show();

            } else if (TextUtils.isEmpty(etPhone.getText().toString())) {
                bool = false;
                hideKeyboard();
                Toast.makeText(EditUserActivity.this, getString(R.string.toast_phone), Toast.LENGTH_SHORT).show();

            } else if (!TextUtils.isEmpty(etPwd.getText().toString()) &&!isValidPassword(etPwd.getText().toString())) {
                bool = false;
                hideKeyboard();
                Toast.makeText(EditUserActivity.this, getString(R.string.toast_pwd_match), Toast.LENGTH_SHORT).show();
            } else if (!etPwd.getText().toString().equals(etConfirmPwd.getText().toString())) {
                bool = false;
                hideKeyboard();
                Toast.makeText(EditUserActivity.this, getString(R.string.toast_pwd_retype_pwd_same), Toast.LENGTH_SHORT).show();

            } else if (TextUtils.isEmpty(role)) {
                bool = false;
                hideKeyboard();
                Toast.makeText(EditUserActivity.this, getString(R.string.toast_role), Toast.LENGTH_SHORT).show();
            }/* else if (TextUtils.isEmpty(AppConstant.ASSIGN_COURSES_Id)) {
                bool = false;
                hideKeyboard();
                Toast.makeText(EditUserActivity.this, getString(R.string.toast_slect_course), Toast.LENGTH_SHORT).show();
            }*/
            return bool;
        }

        public static boolean isValidPassword ( final String password){

            Pattern pattern;
            Matcher matcher;
            //(?=.*\d)
            // final String PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,}$";
            //final String PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[@$!%*?&])[A-Za-z#@$!%*?&]{6,}$";
            final String PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$!%*?&])[A-Za-z#@$!%*?&0-9]{6,}$";

            pattern = Pattern.compile(PASSWORD_PATTERN);
            matcher = pattern.matcher(password);

            return matcher.matches();

        }
        private void hideKeyboard ()
        {
            InputMethodManager imm = (InputMethodManager)
                    getSystemService(Activity.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(llMain.getWindowToken(), 0);
        }
        private void showRoleDialog () {
            mBottomSheetDialog = new BottomSheetDialog(EditUserActivity.this);
            View sheetView = getLayoutInflater().inflate(R.layout.dialog_role, null);
            mBottomSheetDialog.setContentView(sheetView);
            LinearLayout llAdmin = sheetView.findViewById(R.id.llAdmin);
            llAdmin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    role = "1";
                    tvRole.setText(getString(R.string.admin));
                    mBottomSheetDialog.dismiss();
                }
            });
            LinearLayout llUser = sheetView.findViewById(R.id.llUser);
            llUser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    role = "2";
                    tvRole.setText(getString(R.string.user));
                    mBottomSheetDialog.dismiss();
                }
            });
            mBottomSheetDialog.show();
        }

        private void chosePicture () {
            final CharSequence[] items = {"Take Photo", "Choose Photo"};
            AlertDialog.Builder builder = new AlertDialog.Builder(EditUserActivity.this);
            builder.setTitle("Add Photo");
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {

                    if (items[item].equals("Take Photo")) {

                        takePhotoFromCamera();

                    } else if (items[item].equals("Choose Photo")) {

                        chooseImageFromGallery();
                    }
                }
            });
            builder.show();
        }
        public void takePhotoFromCamera () {
            if (ContextCompat.checkSelfPermission(EditUserActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(EditUserActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(EditUserActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(EditUserActivity.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_PHOTO);
            } else {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    try {
                        Calendar cal = Calendar.getInstance();
                        String imageFileName = cal.getTimeInMillis() + "";

                        File storageDir = new File(Environment.getExternalStorageDirectory(), "." + AppConstant.SUB_FOLDER);
                        if (!storageDir.exists()) {
                            storageDir.mkdir();
                        }

                        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
                        mCurrentPhotoPath = image.getAbsolutePath();
                        Log.d("FILE :::: ", image.getAbsolutePath());
                        // cameraURI = FileProvider.getUriForFile(BoxListDetailActivity.this, AUTHORITY, image);
                        cameraURI = Uri.fromFile(image);
                        Log.d("cameraURI :: ", cameraURI.toString());

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    File file = null;
                    try {
                        file = createImageFile();
                        cameraURI = Uri.fromFile(file);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, cameraURI);
                cameraIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                cameraIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                startActivityForResult(cameraIntent, TAKE_PHOTO);
            }
        }

        private void chooseImageFromGallery () {
            Intent galleryIntent = new Intent(
                    Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(galleryIntent, SELECT_IMAGE);

        }
        private File createImageFile () throws IOException {
            Calendar cal = Calendar.getInstance();

            File storageDir = new File(Environment.getExternalStorageDirectory(), "." + AppConstant.SUB_FOLDER);
            if (!storageDir.exists()) {
                storageDir.mkdir();
            }

            File file = new File(storageDir.getAbsolutePath(),
                    (cal.getTimeInMillis() + ".jpg"));


            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                file.delete();
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return file;
        }
        public void onActivityResult ( int requestCode, int resultCode, Intent data){
            super.onActivityResult(requestCode, resultCode, data);
            switch (requestCode) {
                case TAKE_PHOTO:
                    if (cameraURI != null) {
                        String imageFilePath = "";
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                            File f = new File(mCurrentPhotoPath);
                            Uri contentUri = Uri.fromFile(f);

                            mediaScanIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            mediaScanIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

                            mediaScanIntent.setData(contentUri);
                            sendBroadcast(mediaScanIntent);
                            imageFilePath = f.getAbsolutePath();

                        } else {
                            imageFilePath = cameraURI.getPath();
                        }

                        if (imageFilePath != null && imageFilePath.length() > 0) {
                            try {
                                int mobile_width = 480;
                                BitmapFactory.Options options = new BitmapFactory.Options();
                                options.inJustDecodeBounds = true;
                                int outWidth = options.outWidth;
                                int outHeight = options.outHeight;
                                int ratio = (int) ((((float) outWidth) / mobile_width) + 0.5f);
                                if (ratio == 0) {
                                    ratio = 1;
                                }
                                ExifInterface exif = new ExifInterface(imageFilePath);
                                String orientString = exif
                                        .getAttribute(ExifInterface.TAG_ORIENTATION);
                                int orientation = orientString != null ? Integer
                                        .parseInt(orientString)
                                        : ExifInterface.ORIENTATION_NORMAL;
                                if (orientation == ExifInterface.ORIENTATION_ROTATE_90)
                                    rotationAngle = 90;
                                if (orientation == ExifInterface.ORIENTATION_ROTATE_180)
                                    rotationAngle = 180;
                                if (orientation == ExifInterface.ORIENTATION_ROTATE_270)
                                    rotationAngle = 270;
                                if (orientation == ExifInterface.ORIENTATION_UNDEFINED)
                                    rotationAngle = 0;
                                if (orientation == ExifInterface.ORIENTATION_TRANSVERSE)
                                    rotationAngle = -90;
                                if (orientation == ExifInterface.ORIENTATION_TRANSPOSE)
                                    rotationAngle = 90;
                                if (orientation == ExifInterface.ORIENTATION_FLIP_VERTICAL)
                                    rotationAngle = 180;
                                options.inJustDecodeBounds = false;
                                options.inSampleSize = ratio;


                                myFile = new File(imageFilePath);
                                Bitmap bmp = new ImageHelper().decodeFile(myFile);
                                Matrix matrix = new Matrix();
                                matrix.setRotate(rotationAngle,
                                        (float) bmp.getWidth() / 2,
                                        (float) bmp.getHeight() / 2);


                                bmp = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(),
                                        bmp.getHeight(), matrix, true);

                                FileOutputStream outStream = new FileOutputStream(
                                        myFile);
                                bmp.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
                                outStream.flush();
                                outStream.close();
                                if (!TextUtils.isEmpty(myFile.getAbsolutePath())) {

                                    filePath = getRealPathFromURI(Uri.parse(myFile.getAbsolutePath()));
                                    imgUser.setImageURI(Uri.parse(myFile.getAbsolutePath()));

                                    mCurrentPhotoPathUri = Uri.fromFile(myFile);

                                    // is= activity.getContentResolver().openInputStream(data.getData());
                                    // uploadImage(getBytes(is));

                                    Log.d("full file path :: ", filePath + " ");

                                }
                            } catch (OutOfMemoryError e) {
                                System.out.println("out of bound");
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        revokeUriPermission(cameraURI, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    } else {
                        Toast.makeText(
                                EditUserActivity.this,
                                getResources().getString(R.string.text_unable_to_select_image), Toast.LENGTH_LONG).show();
                    }

                    break;

                case SELECT_IMAGE:

                    if (resultCode == Activity.RESULT_OK) {
                        if (data != null) {
                            try {

                                Bitmap bitmap =
                                        MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                                imgUser.setImageBitmap(bitmap);

                                Uri selectedImageUri = data.getData();
                                mCurrentPhotoPathUri = Uri.fromFile(new File(Objects.requireNonNull(AppUtils.getFilePath(EditUserActivity.this, selectedImageUri))));
                                filePath = getRealPathFromURI(selectedImageUri);
                                myFile = new File(filePath);

                                Log.d("galery file path :: ", myFile.getAbsolutePath());

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    } else if (resultCode == Activity.RESULT_CANCELED) {
                        Toast.makeText(
                                EditUserActivity.this,
                                getResources().getString(R.string.text_unable_to_select_image), Toast.LENGTH_LONG).show();

                    }
                    break;

            }

        }
        private String getRealPathFromURI (Uri contentURI){
            String result;
            Cursor cursor = getContentResolver().query(contentURI, null, null,
                    null, null);

            if (cursor == null) { // Source is Dropbox or other similar local file
                // path
                result = contentURI.getPath();

            } else {
                cursor.moveToFirst();
                try {
                    int idx = cursor
                            .getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    result = cursor.getString(idx);
                } catch (Exception e) {

                    Toast.makeText(EditUserActivity.this, getResources().getString(
                            R.string.text_unable_to_select_image), Toast.LENGTH_SHORT).show();
                    result = "";
                }

                cursor.close();
            }
            return result;
        }

    }