package com.onlineeducationsystemorganization;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.onlineeducationsystemorganization.adapter.CourseDetailCourseIncludesAdapter;
import com.onlineeducationsystemorganization.adapter.ExpandedCourseDetail;
import com.onlineeducationsystemorganization.interfaces.NetworkListener;
import com.onlineeducationsystemorganization.model.BaseBean;
import com.onlineeducationsystemorganization.model.CourseDetail;
import com.onlineeducationsystemorganization.network.ApiCall;
import com.onlineeducationsystemorganization.network.ApiInterface;
import com.onlineeducationsystemorganization.network.RestApi;
import com.onlineeducationsystemorganization.network.RestApi1;
import com.onlineeducationsystemorganization.network.ServerConstents;
import com.onlineeducationsystemorganization.util.AppConstant;
import com.onlineeducationsystemorganization.util.AppSharedPreference;
import com.onlineeducationsystemorganization.util.AppUtils;
import com.onlineeducationsystemorganization.widget.NonScrollExpandableListView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;

public class CourseDetailActivity extends BaseActivity implements NetworkListener {

    private static final int PERMISSION_PHOTO = 1;
    private RecyclerView rvCourse;
    private CourseDetailCourseIncludesAdapter courseDetailCourseIncludesAdapter;
    private NonScrollExpandableListView expandableView;
    private LinearLayout llCreatedByInstructor, llCurriculum;
    private ImageView imgWhishList, imgBack;
    private String course_id;
    private ImageView imgCourse, imgUser;
    private TextView tvCourseTitle, tvNewPrice,
            tvOldPrice, tvSubscriber, tvTitle1, tvCourseIncludetitle, tvCurriculum,
            tvStudent, tvCourse, tvViewProfile, tvCreateBy, tvMoreSection, tvDuration;
    private Button buyNow;
    private WebView tvCourseDetail;
    private ImageView imgShare;
    private CourseDetail data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);

        initToolbar();
        initUI();

    }


    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        imgShare = findViewById(R.id.imgShare);
        imgShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(CourseDetailActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                        ContextCompat.checkSelfPermission(CourseDetailActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(CourseDetailActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_PHOTO);
                } else {

                    share();
                }
            }
        });
    }
    private void share() {


        Target target = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {

                String path = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "SomeText", null);
                Log.d("Path", path);
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, data.getData().get(0).getShare_url());
                Uri screenshotUri = Uri.parse(path);
                intent.putExtra(Intent.EXTRA_STREAM, screenshotUri);
                intent.setType("image/*");
                startActivity(Intent.createChooser(intent, data.getData().get(0).getCourseName()));
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };
       // String url = "http://efdreams.com/data_images/dreams/face/face-03.jpg";
        String url=data.getData().get(0).getImage();
        Picasso.with(getApplicationContext()).load(url).into(target);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_PHOTO:

                if (grantResults.length > 0) {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        share();
                    } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {

                    }
                }
                break;
        }
    }

    private void initUI() {
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            course_id = extras.getString("course_id");
        }
        rvCourse = findViewById(R.id.rvCourse);
        imgCourse = findViewById(R.id.imgCourse);
        imgUser = findViewById(R.id.imgUser);
        tvStudent = findViewById(R.id.tvStudent);
        tvDuration = findViewById(R.id.tvDuration);

        tvCourse = findViewById(R.id.tvCourse);
        tvCreateBy = findViewById(R.id.tvCreateBy);
        tvCourseTitle = findViewById(R.id.tvCourseTitle);
        tvCourseDetail = findViewById(R.id.tvCourseDetail);
        tvNewPrice = findViewById(R.id.tvNewPrice);
        tvOldPrice = findViewById(R.id.tvOldPrice);
        tvSubscriber = findViewById(R.id.tvSubscriber);
        tvTitle1 = findViewById(R.id.tvTitle1);
        tvCourseIncludetitle = findViewById(R.id.tvCourseIncludetitle);
        tvCurriculum = findViewById(R.id.tvCurriculum);
        tvViewProfile = findViewById(R.id.tvViewProfile);
        tvMoreSection = findViewById(R.id.tvMoreSection);
        buyNow = findViewById(R.id.buyNow);
        buyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AppSharedPreference.getInstance().getString(CourseDetailActivity.this, AppSharedPreference.USERID) == null) {
                    AppUtils.loginAlert(CourseDetailActivity.this);
                } else {

                    callCart();
                }
            }
        });

        llCreatedByInstructor = findViewById(R.id.llCreatedByInstructor);
        llCurriculum = findViewById(R.id.llCurriculum);
        imgBack = findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        imgWhishList = findViewById(R.id.imgWhishList);
        imgWhishList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (AppUtils.isInternetAvailable(CourseDetailActivity.this)) {

                    if (AppSharedPreference.getInstance().getString(CourseDetailActivity.this, AppSharedPreference.USERID) == null) {
                        Toast.makeText(CourseDetailActivity.this, getString(R.string.toast_login_first), Toast.LENGTH_SHORT).show();
                    } else {
                        if(data.getData().get(0).getIs_purchased() ==1)
                        {
                            Toast.makeText(CourseDetailActivity.this,getResources().getString(R.string.toast_course_alry_whishlist),Toast.LENGTH_SHORT ).show();
                        }else {
                            hintWhishList();
                        }
                    }

                }
            }
        });


        if (AppUtils.isInternetAvailable(CourseDetailActivity.this)) {

            hintCourseDetail();
        }


    }

    private void callCart() {
        if (AppUtils.isInternetAvailable(CourseDetailActivity.this)) {
            String lang = "";
            AppUtils.showDialog(CourseDetailActivity.this, getString(R.string.pls_wait));
            ApiInterface apiInterface = RestApi.getConnection(ApiInterface.class, ServerConstents.API_URL);
            final HashMap params = new HashMap<>();

            params.put("course_id", course_id + "");
            if (AppSharedPreference.getInstance().getString(CourseDetailActivity.this, AppSharedPreference.LANGUAGE_SELECTED) == null ||
                    AppSharedPreference.getInstance().getString(CourseDetailActivity.this, AppSharedPreference.LANGUAGE_SELECTED).equalsIgnoreCase(AppConstant.ENG_LANG)) {
                lang = AppConstant.ENG_LANG;
            } else {
                lang = AppConstant.ARABIC_LANG;
            }

            Call<BaseBean> call = apiInterface.addToCart(lang, AppSharedPreference.getInstance().
                    getString(CourseDetailActivity.this, AppSharedPreference.ACCESS_TOKEN), params);
            ApiCall.getInstance().hitService(CourseDetailActivity.this, call, this, ServerConstents.CART);

        }
    }

    private void hintCourseDetail() {

        String lang = "";
        AppUtils.showDialog(this, getString(R.string.pls_wait));
        ApiInterface apiInterface = RestApi1.getConnection(ApiInterface.class, ServerConstents.API_URL1);
        final HashMap params = new HashMap<>();
        params.put("id", course_id);
        if (AppSharedPreference.getInstance().getString(CourseDetailActivity.this, AppSharedPreference.USERID) == null) {
            params.put("user_id", "");
        } else {
            params.put("user_id", AppSharedPreference.getInstance().getString(CourseDetailActivity.this, AppSharedPreference.USERID));
        }

        if (AppSharedPreference.getInstance().getString(CourseDetailActivity.this, AppSharedPreference.LANGUAGE_SELECTED) == null ||
                AppSharedPreference.getInstance().getString(CourseDetailActivity.this, AppSharedPreference.LANGUAGE_SELECTED).equalsIgnoreCase(AppConstant.ENG_LANG)) {
            lang = AppConstant.ENG_LANG;
        } else {
            lang = AppConstant.ARABIC_LANG;
        }
        Call<CourseDetail> call = apiInterface.getCourseDetail(lang, AppSharedPreference.getInstance().
                getString(CourseDetailActivity.this, AppSharedPreference.ACCESS_TOKEN), params);

        ApiCall.getInstance().hitService(CourseDetailActivity.this, call, this, ServerConstents.DETAIL);

    }

    private void hintWhishList() {
        String lang = "";
        AppUtils.showDialog(this, getString(R.string.pls_wait));
        ApiInterface apiInterface = RestApi.getConnection(ApiInterface.class, ServerConstents.API_URL);
        final HashMap params = new HashMap<>();
        params.put("course_id", course_id);

        if (AppSharedPreference.getInstance().getString(CourseDetailActivity.this, AppSharedPreference.LANGUAGE_SELECTED) == null ||
                AppSharedPreference.getInstance().getString(CourseDetailActivity.this, AppSharedPreference.LANGUAGE_SELECTED).equalsIgnoreCase(AppConstant.ENG_LANG)) {
            lang = AppConstant.ENG_LANG;
        } else {
            lang = AppConstant.ARABIC_LANG;
        }
        Call<BaseBean> call = apiInterface.addWhishList(lang,
                AppSharedPreference.getInstance().
                        getString(CourseDetailActivity.this, AppSharedPreference.ACCESS_TOKEN),

                params);

        ApiCall.getInstance().hitService(CourseDetailActivity.this, call, this, ServerConstents.WHISH_LIST);

    }

    @Override
    public void onSuccess(int responseCode, Object response, int requestCode) {

        if (requestCode == ServerConstents.WHISH_LIST) {
            BaseBean data = (BaseBean) response;
            if (data.getStatus() == ServerConstents.CODE_SUCCESS) {
                imgWhishList.setImageResource(R.drawable.ic_whishlisted);
                Toast.makeText(CourseDetailActivity.this, data.getMessage(), Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == ServerConstents.CART) {

            BaseBean baseBean = (BaseBean) response;
            Toast.makeText(CourseDetailActivity.this, baseBean.getMessage().toString(), Toast.LENGTH_SHORT).show();

        } else {

            data = (CourseDetail) response;

            if (data.getStatus() == ServerConstents.CODE_SUCCESS) {
                AppUtils.loadImageWithPicasso
                        (data.getData().get(0).getImage(), imgCourse, CourseDetailActivity.this, 0, 0);
                tvCourseTitle.setText(data.getData().get(0).getCourseName());
                tvTitle1.setText(data.getData().get(0).getCourseName());

              /*  if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    tvCourseDetail.setText(Html.fromHtml(data.getData().get(0).getDescription(),Html.FROM_HTML_MODE_LEGACY));
                } else {
                    tvCourseDetail.setText(Html.fromHtml(data.getData().get(0).getDescription()));
                }*/

                tvCourseDetail.getSettings().setJavaScriptEnabled(true);
                tvCourseDetail.loadDataWithBaseURL(null, data.getData().get(0).getDescription(), "text/html", "utf-8", null);
                // tvCourseDetail.loadData(data.getData().get(0).getDescription(), "text/html; charset=utf-8", "UTF-8");
                if (data.getData().get(0).getIs_free() == 0) {
                    buyNow.setText(getString(R.string.buy_now));
                } else {
                    buyNow.setText(getString(R.string.enroll_now));
                }

                if(data.getData().get(0).getIs_purchased() == 1)
                {
                    buyNow.setVisibility(View.GONE);
                }else
                {
                    buyNow.setVisibility(View.VISIBLE);
                }
                tvNewPrice.setText(data.getData().get(0).getCoursePrice());
                tvOldPrice.setText(data.getData().get(0).getCourseOldPrice());
                tvOldPrice.setPaintFlags(tvOldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                tvSubscriber.setText(data.getData().get(0).getTotalSubscriber() + "");
                tvCurriculum.setText(data.getData().get(0).getSectionTitle());
                if (data.getData().get(0).getIsWishlist() == 1) {
                    imgWhishList.setImageResource(R.drawable.ic_whishlisted);
                } else {
                    imgWhishList.setImageResource(R.drawable.ic_heart);
                }
                tvCourseIncludetitle.setText(data.getData().get(0).getCourseIncludeTitle());


                courseDetailCourseIncludesAdapter =
                        new CourseDetailCourseIncludesAdapter(CourseDetailActivity.this, data.getData().get(0).getCourseInclude());
                rvCourse.setItemAnimator(new DefaultItemAnimator());
                LinearLayoutManager manager = new LinearLayoutManager(CourseDetailActivity.this);
                rvCourse.setLayoutManager(manager);
                rvCourse.setAdapter(courseDetailCourseIncludesAdapter);

                //cocurriculam

                if (data.getData().get(0).getSectionDetails().size() > 0) {
                    llCurriculum.setVisibility(View.VISIBLE);
                    ArrayList<CourseDetail.SectionDetail> listDataHeader = new ArrayList<CourseDetail.SectionDetail>();
                    HashMap<CourseDetail.SectionDetail, ArrayList<CourseDetail.SectionSlideDetail>> listDataChild = new HashMap<CourseDetail.SectionDetail, ArrayList<CourseDetail.SectionSlideDetail>>();

                    if (data.getData().get(0).getSectionDetails().size() > 3) {
                        tvMoreSection.setVisibility(View.VISIBLE);
                    } else {
                        tvMoreSection.setVisibility(View.GONE);
                    }
                    if (data.getData().get(0).getSectionDetails().size() > 3) {
                        for (int i = 0; i < 3; i++) {
                            listDataHeader.add(data.getData().get(0).getSectionDetails().get(i));
                        }
                    } else {
                        listDataHeader.addAll(data.getData().get(0).getSectionDetails());
                    }


                    expandableView = findViewById(R.id.expandableView);
                    ExpandedCourseDetail expandedCourseDetail = new ExpandedCourseDetail(CourseDetailActivity.this, listDataHeader);
                    expandableView.setAdapter(expandedCourseDetail);
                    expandableView.expandGroup(0);
                    expandedCourseDetail.notifyDataSetChanged();

                    tvMoreSection.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            Intent intent = new Intent(CourseDetailActivity.this, MoreSectionActivity.class);
                            intent.putExtra("listData", data.getData().get(0).getSectionDetails());
                            startActivity(intent);

                        }
                    });

                } else {
                    llCurriculum.setVisibility(View.GONE);
                }
                //
                if (data.getData().get(0).getCreatedBy().equalsIgnoreCase("instructor")) {
                    llCreatedByInstructor.setVisibility(View.VISIBLE);
                    AppUtils.loadImageWithPicasso(data.getData().get(0).getInstructorDetails().getProfileImage(), imgUser, CourseDetailActivity.this, 0, 0);
                    tvCourse.setText(data.getData().get(0).getInstructorDetails().getTotalCourse() + "");
                    tvStudent.setText(data.getData().get(0).getInstructorDetails().getTotalStudents() + "");

                    tvCreateBy.setText(data.getData().get(0).getInstructorDetails().getInstructorName());
                } else {
                    llCreatedByInstructor.setVisibility(View.GONE);

                }
            }
        }
    }

    @Override
    public void onError(String response, int requestCode, int errorCode) {
        if (requestCode == ServerConstents.WHISH_LIST) {
            imgWhishList.setImageResource(R.drawable.ic_heart);
        }
        Toast.makeText(CourseDetailActivity.this, response, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFailure() {

    }
}
