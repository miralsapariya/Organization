package com.onlineeducationsystemorganization;

import android.Manifest;
import android.app.Activity;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.hbb20.CountryCodePicker;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.onlineeducationsystemorganization.interfaces.NetworkListener;
import com.onlineeducationsystemorganization.model.GetProfile;
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

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;

public class EditUserProfileActivity extends BaseActivity implements NetworkListener {

    private CircularImageView imgUser;

    private static final int PERMISSION_PHOTO = 1;
    private static final int TAKE_PHOTO = 2;
    private static final int SELECT_IMAGE = 3;

    private static Uri cameraURI;
    private static String mCurrentPhotoPath;
    private String filePath = "";
    private int rotationAngle;
    File myFile;
    private Uri mCurrentPhotoPathUri;
    private LinearLayout llMain;
    private EditText etName,etEmail,etPhone,etCompanyName;
    private Button btnUpdate;
    private ImageView imgBack;
    private CountryCodePicker ccp;
    private String selectedCountryCode="",selectedCountry="";
    private BottomSheetDialog mBottomSheetDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_profile);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initUI();
    }

    private void initUI()
    {
        imgBack =findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        etName =findViewById(R.id.etName);
        etEmail =findViewById(R.id.etEmail);
        etCompanyName =findViewById(R.id.etCompanyName);
        etPhone =findViewById(R.id.etPhone);
        llMain =findViewById(R.id.llMain);
        imgUser =findViewById(R.id.imgUser);
        btnUpdate =findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AppUtils.isInternetAvailable(EditUserProfileActivity.this)) {
                   if(isValid())
                    editProfile();
                }
            }
        });
        imgUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(EditUserProfileActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                        || ContextCompat.checkSelfPermission(EditUserProfileActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(EditUserProfileActivity.this,new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_PHOTO);
                }else {
                    chosePicture();
                }
            }
        });

        if (AppUtils.isInternetAvailable(EditUserProfileActivity.this)) {
                getProfile();
        }

        ccp=findViewById(R.id.ccp);
        selectedCountryCode =ccp.getSelectedCountryCodeWithPlus();
        selectedCountry =ccp.getSelectedCountryName();

        ccp.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
                selectedCountryCode =ccp.getSelectedCountryCodeWithPlus();
                selectedCountry =ccp.getSelectedCountryName();
            }
        });


    }

    private void editProfile()
    {
        String lang="";

        AppUtils.showDialog(this, getString(R.string.pls_wait));
        ApiInterface apiInterface = RestApi.getConnection(ApiInterface.class, ServerConstents.API_URL1);


        MultipartBody.Part body=null;
        if (myFile != null && !TextUtils.isEmpty(myFile.getAbsolutePath())) {
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), myFile);
            body = MultipartBody.Part.createFormData("profile_picture", myFile.getName(), requestFile);

            Log.d("file :::: ", myFile.getName());
        }
        RequestBody name =
                RequestBody.create(
                        MultipartBody.FORM, etName.getText().toString());

        RequestBody comapanyName =
                RequestBody.create(
                        MultipartBody.FORM, etCompanyName.getText().toString());
        RequestBody email =
                RequestBody.create(
                        MultipartBody.FORM, etEmail.getText().toString());

        RequestBody phone =
                RequestBody.create(
                        MultipartBody.FORM, selectedCountryCode+"-"+etPhone.getText().toString());
        RequestBody countryNAme = RequestBody.create(
                okhttp3.MultipartBody.FORM,selectedCountry);
        if (AppSharedPreference.getInstance().getString(EditUserProfileActivity.this, AppSharedPreference.LANGUAGE_SELECTED) == null ||
                AppSharedPreference.getInstance().getString(EditUserProfileActivity.this, AppSharedPreference.LANGUAGE_SELECTED).equalsIgnoreCase(AppConstant.ENG_LANG)) {
            lang = AppConstant.ENG_LANG;
        }else
        {
            lang= AppConstant.ARABIC_LANG;
        }

        RequestBody userId = RequestBody.create(
                MultipartBody.FORM,AppSharedPreference.getInstance().getString(EditUserProfileActivity.this, AppSharedPreference.USERID));


          RequestBody.create(
                        MultipartBody.FORM, selectedCountryCode+"-"+etPhone.getText().toString());

        if(body != null) {
            Call<GetProfile> call = apiInterface.editProfile(lang,
                    AppSharedPreference.getInstance().getString(EditUserProfileActivity.this, AppSharedPreference.ACCESS_TOKEN),
                    userId,
                    name,comapanyName,email,phone,countryNAme,
                    body
                    );

            ApiCall.getInstance().hitService(EditUserProfileActivity.this, call, this, ServerConstents.EDIT_PROFILE);

        }else
        {
            Call<GetProfile> call = apiInterface.editProfile(lang,
                    AppSharedPreference.getInstance().getString(EditUserProfileActivity.this, AppSharedPreference.ACCESS_TOKEN),
                    userId,
                    name,comapanyName,email,phone,countryNAme
            );

            ApiCall.getInstance().hitService(EditUserProfileActivity.this, call, this, ServerConstents.EDIT_PROFILE);
        }


    }

    private void getProfile()
    {
        String lang ="";
        AppUtils.showDialog(this, getString(R.string.pls_wait));
        ApiInterface apiInterface = RestApi.getConnection(ApiInterface.class, ServerConstents.API_URL1);
        final HashMap params = new HashMap<>();
        params.put("user_id", AppSharedPreference.getInstance().getString(EditUserProfileActivity.this, AppSharedPreference.USERID));
        if (AppSharedPreference.getInstance().getString(EditUserProfileActivity.this, AppSharedPreference.LANGUAGE_SELECTED) == null ||
                AppSharedPreference.getInstance().getString(EditUserProfileActivity.this, AppSharedPreference.LANGUAGE_SELECTED).equalsIgnoreCase(AppConstant.ENG_LANG)) {
            lang = AppConstant.ENG_LANG;
        }else
        {
            lang= AppConstant.ARABIC_LANG;
        }

        Call<GetProfile> call = apiInterface.getProfile(lang,
                AppSharedPreference.getInstance().
                        getString(EditUserProfileActivity.this, AppSharedPreference.ACCESS_TOKEN));

        ApiCall.getInstance().hitService(EditUserProfileActivity.this, call, this, ServerConstents.GET_PROFILE);

    }

    private boolean isValid()
    {
        boolean bool = true;
        if (TextUtils.isEmpty(etName.getText().toString())) {
            bool = false;
            hideKeyboard();
            Toast.makeText(EditUserProfileActivity.this, getString(R.string.toast_name), Toast.LENGTH_SHORT).show();
            // L.showSnackbar(llLogin, getString(R.string.toast_Ic));

        }else if(AppUtils.countWordsUsingSplit(etName.getText().toString()) <= 1)
        {

            bool=false;
            hideKeyboard();
            Toast.makeText(EditUserProfileActivity.this, getString(R.string.toast_full_name), Toast.LENGTH_SHORT).show();

        }else if(TextUtils.isEmpty(etCompanyName.getText().toString()))
        {

            bool=false;
            hideKeyboard();
            Toast.makeText(EditUserProfileActivity.this, getString(R.string.toast_comapany_name), Toast.LENGTH_SHORT).show();

        }else if (TextUtils.isEmpty(etEmail.getText().toString())) {
            bool = false;
            hideKeyboard();
            Toast.makeText(EditUserProfileActivity.this, getString(R.string.toast_email), Toast.LENGTH_SHORT).show();
            // L.showSnackbar(llLogin, getString(R.string.toast_Ic));

        }else if (TextUtils.isEmpty(etPhone.getText().toString())) {
            bool = false;
            hideKeyboard();
            Toast.makeText(EditUserProfileActivity.this, getString(R.string.toast_phone), Toast.LENGTH_SHORT).show();
            // L.showSnackbar(llLogin, getString(R.string.toast_Ic));

        }
        return bool;
    }
    private void hideKeyboard()
    {
        InputMethodManager imm = (InputMethodManager)
                getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(llMain.getWindowToken(), 0);
    }

    @Override
    public void onSuccess(int responseCode, Object response, int requestCode) {
        if(requestCode == ServerConstents.GET_PROFILE) {
            GetProfile data = (GetProfile) response;

            String s=data.getData().get(0).getCountryCode().replaceAll("\\+", "");
            Log.d("______________ ", s);

            ccp.setCountryForPhoneCode(Integer.parseInt(s));

           Picasso.with(this).load(data.getData().get(0).
                    getProfilePicture()).into(imgUser);
            etName.setText(data.getData().get(0).getName());
            etEmail.setText(data.getData().get(0).getEmail());
            etPhone.setText(data.getData().get(0).getPhoneNo());
            etCompanyName.setText(data.getData().get(0).getOrganizationName());
            if(AppSharedPreference.getInstance().getString(EditUserProfileActivity.this, AppSharedPreference.USER_TYPE).equalsIgnoreCase("2")) {
                etCompanyName.setEnabled(false);
            }
        }else
        {
            GetProfile data = (GetProfile) response;
            Toast.makeText(EditUserProfileActivity.this, data.getMessage(), Toast.LENGTH_SHORT).show();
            finish();
        }

    }

    @Override
    public void onError(String response, int requestCode, int errorCode) {
        Toast.makeText(EditUserProfileActivity.this, response, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onFailure() {

    }

    private void chosePicture() {
        mBottomSheetDialog = new BottomSheetDialog(EditUserProfileActivity.this);
        View sheetView = getLayoutInflater().inflate(R.layout.dialog_picture, null);
        mBottomSheetDialog.setContentView(sheetView);
        LinearLayout llTakePic = sheetView.findViewById(R.id.llTakePic);
        llTakePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePhotoFromCamera();
                mBottomSheetDialog.dismiss();
            }
        });
        LinearLayout llChoosePic = sheetView.findViewById(R.id.llChoosePic);
        llChoosePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImageFromGallery();
                mBottomSheetDialog.dismiss();
            }
        });
        mBottomSheetDialog.show();
       /* final CharSequence[] items = {"Take Photo", "Choose Photo"};
        AlertDialog.Builder builder = new AlertDialog.Builder
                (EditUserProfileActivity.this);
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
        final AlertDialog alertDialog = builder.create();
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dlg) {
                try {
                    Log.d("============ ", "RTLLLLLLLL");
                  *//*  TextView messageText = alertDialog.findViewById(android.R.id.text1);
                    messageText.setGravity(Gravity.END);
                    TextView messageText1 = alertDialog.findViewById(android.R.id.text2);
                    messageText1.setGravity(Gravity.END);*//*
                   // if (SharedPreferencesUtils.getInstance(LoginActivity.this).getValue(Constants.KEY_LANGUAGE, "").equalsIgnoreCase(Constants.ARABIC)) {
                        alertDialog.getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL); // set title and message direction to RTL

                    *//*} else {
                        alertDialog.getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR); // set title and message direction to LTR
                    }*//*
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });



        alertDialog.show();*/
    }
    public void takePhotoFromCamera() {
        if (ContextCompat.checkSelfPermission(EditUserProfileActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(EditUserProfileActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(EditUserProfileActivity.this,new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_PHOTO);
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
                    // cameraURI = FileProvider.getUriForFile(BoxListDetailActivity.this, AUTHORITY, image);
                    cameraURI = Uri.fromFile(image);

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

    private void chooseImageFromGallery() {
        Intent galleryIntent = new Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, SELECT_IMAGE);

    }
    private File createImageFile() throws IOException {
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,  resultCode,  data);
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

                                Log.d("full file path :: ", filePath+" ");

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
                            EditUserProfileActivity.this,
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
                            mCurrentPhotoPathUri = Uri.fromFile(new File(Objects.requireNonNull(AppUtils.getFilePath(EditUserProfileActivity.this, selectedImageUri))));
                            filePath =getRealPathFromURI(selectedImageUri);
                            myFile =new File(filePath);

                            Log.d("galery file path :: ", myFile.getAbsolutePath());

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } else if (resultCode == Activity.RESULT_CANCELED) {
                    Toast.makeText(
                            EditUserProfileActivity.this,
                            getResources().getString(R.string.text_unable_to_select_image), Toast.LENGTH_LONG).show();

                }
                break;

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_PHOTO:

                if (grantResults.length > 0) {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        chosePicture();
                    }else if(grantResults[0] == PackageManager.PERMISSION_DENIED)
                    {
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

    private String getRealPathFromURI(Uri contentURI) {
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

                Toast.makeText(EditUserProfileActivity.this, getResources().getString(
                        R.string.text_unable_to_select_image), Toast.LENGTH_SHORT).show();
                result = "";
            }

            cursor.close();
        }
        return result;
    }




}
