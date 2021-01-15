package com.onlineeducationsystemorganization;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;

import com.onlineeducationsystemorganization.interfaces.NetworkListener;
import com.onlineeducationsystemorganization.model.CheckCourse;
import com.onlineeducationsystemorganization.model.SectionSlideDetail;
import com.onlineeducationsystemorganization.network.ApiCall;
import com.onlineeducationsystemorganization.network.ApiInterface;
import com.onlineeducationsystemorganization.network.RestApi;
import com.onlineeducationsystemorganization.network.ServerConstents;
import com.onlineeducationsystemorganization.util.AppConstant;
import com.onlineeducationsystemorganization.util.AppSharedPreference;
import com.onlineeducationsystemorganization.util.AppUtils;
import com.onlineeducationsystemorganization.widget.NestedWebView;

import java.util.HashMap;

import retrofit2.Call;

public class LessionSlideActivity extends BaseActivity implements NetworkListener, View.OnClickListener {

    SectionSlideDetail data;
    private WebView tvDescription;
    private NestedWebView webView;
    private ProgressBar progressbar;
    private TextView tvSlideId, tvCourseName, tvSectionName, tvSlideName;
    private WebView videoView;
    private ImageView imgSlide, imgNext, imgPrev, imgBackgroundImage, imgBack;
    private String course_id = "", section_id = "", slide_id = "";
    private NestedScrollView nestedScroll;
    //private ScrollView scrollView;
    //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lession_slide);

        initToolBar();
        initUI();
    }


    private void initUI() {
        imgBack = findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        nestedScroll = findViewById(R.id.nestedScroll);
        nestedScroll.scrollTo(0, 0);
        course_id = getIntent().getExtras().getString("course_id");
        section_id = getIntent().getExtras().getString("section_id");
        slide_id = getIntent().getExtras().getString("slide_id");
        //scrollView =findViewById(R.id.scrollView);
        tvDescription = findViewById(R.id.tvDescription);
        videoView = findViewById(R.id.videoView);
        imgSlide = findViewById(R.id.imgSlide);
        imgNext = findViewById(R.id.imgNext);
        imgNext.setOnClickListener(this);
        imgBackgroundImage = findViewById(R.id.imgBackgroundImage);
        tvSlideId = findViewById(R.id.tvSlideId);
        imgPrev = findViewById(R.id.imgPrev);
        imgPrev.setOnClickListener(this);
        tvCourseName = findViewById(R.id.tvCourseName);
        tvSectionName = findViewById(R.id.tvSectionName);
        tvSlideName = findViewById(R.id.tvSlideName);
        webView = findViewById(R.id.webView);

        // playVideo();
        callApi();

    }

    private void callApi() {
        if (AppUtils.isInternetAvailable(LessionSlideActivity.this)) {
            getSectionSlide();
        } else {
            AppUtils.showAlertDialog(LessionSlideActivity.this
                    , getString(R.string.no_internet), getString(R.string.alter_net));
        }
    }

    private void getSectionSlide() {
        String lang = "";
        AppUtils.showDialog(LessionSlideActivity.this, getString(R.string.pls_wait));
        ApiInterface apiInterface = RestApi.getConnection(ApiInterface.class, ServerConstents.API_URL);
        final HashMap params = new HashMap<>();
        params.put("course_id", course_id);
        params.put("section_id", section_id);
        params.put("slide_id", slide_id);
       /* params.put("course_id", "41");
        params.put("section_id", "4");
        params.put("slide_id", "1");*/

        if (AppSharedPreference.getInstance().getString(LessionSlideActivity.this, AppSharedPreference.LANGUAGE_SELECTED) == null ||
                AppSharedPreference.getInstance().getString(LessionSlideActivity.this, AppSharedPreference.LANGUAGE_SELECTED).equalsIgnoreCase(AppConstant.ENG_LANG)) {
            lang = AppConstant.ENG_LANG;
        } else {
            lang = AppConstant.ARABIC_LANG;
        }
        Call<SectionSlideDetail> call = apiInterface.getSectionSlide(lang, AppSharedPreference.getInstance().
                getString(LessionSlideActivity.this, AppSharedPreference.ACCESS_TOKEN), params);
        ApiCall.getInstance().hitService(LessionSlideActivity.this, call, this, ServerConstents.COURSE_LIST);
    }


    @Override
    public void onSuccess(int responseCode, Object response, int requestCode) {
        if (requestCode == ServerConstents.COURSE_LIST) {
            data = (SectionSlideDetail) response;
            if (data.getStatus() == ServerConstents.CODE_SUCCESS) {
                tvCourseName.setText(data.getData().get(0).getCourseTitle());
                tvSectionName.setText(data.getData().get(0).getSectionTitle());
                tvSlideName.setText(data.getData().get(0).getSectionSlideDetails().getSlideName());

                //String value = response.body().getAsJsonObject().get("pair_name").getAsString();
                if (data.getData().get(0).getSectionSlideDetails() == null || data.getData().get(0).getSectionSlideDetails().getSlideDesc() == null) {
                } else {

                    if (!data.getData().get(0).getSectionSlideDetails().getSlideDesc().equals("")) {
                        tvDescription.setVisibility(View.VISIBLE);
                        loadDescription();
                    } else {
                        tvDescription.setVisibility(View.GONE);
                    }
                    if (!data.getData().get(0).getSectionSlideDetails().getSlideImage().equals("")) {
                        imgSlide.setVisibility(View.VISIBLE);
                        loadImage();
                    } else {
                        imgSlide.setVisibility(View.GONE);
                    }
                    if (!data.getData().get(0).getSectionSlideDetails().getSlideBackgroundImage().equals("")) {
                        imgBackgroundImage.setVisibility(View.VISIBLE);
                        loadBackgroundImage();
                    } else {
                        imgBackgroundImage.setVisibility(View.GONE);
                    }
                    if (!data.getData().get(0).getSectionSlideDetails().getVideoUrl().equals("")) {
                        videoView.setVisibility(View.VISIBLE);
                        playVideo(data.getData().get(0).getSectionSlideDetails().getVideoUrl());
                    } else {
                        videoView.setVisibility(View.GONE);
                    }
                    if (!data.getData().get(0).getSectionSlideDetails().getDocumentUrl().equals("")) {
                        webView.setVisibility(View.VISIBLE);
                        // scrollView.setVisibility(View.VISIBLE);
                        loadWebview(data.getData().get(0).getSectionSlideDetails().getDocumentUrl());
                    } else {
                        webView.setVisibility(View.GONE);
                        //scrollView.setVisibility(View.GONE);
                    }
                }
            }
        } else if (requestCode == ServerConstents.CHECK_COURSE) {
            CheckCourse checkCourse = (CheckCourse) response;
            if (checkCourse.getStatus() == ServerConstents.CODE_SUCCESS) {
                if (checkCourse.getData().get(0).getNoOfQue() > 0 && checkCourse.getData().get(0).getQuiz().equalsIgnoreCase("no")) {
                    Intent intent = new Intent(LessionSlideActivity.this, ExamActivity.class);
                    intent.putExtra("course_id", course_id);
                    startActivity(intent);
                } else if (checkCourse.getData().get(0).getCert().equalsIgnoreCase("no")) {

                    Intent intent = new Intent(LessionSlideActivity.this, ReportActivity.class);
                    intent.putExtra("course_id", course_id);
                    startActivity(intent);
                } else if (checkCourse.getData().get(0).getQuiz().equalsIgnoreCase("yes") && checkCourse.getData().get(0).getCert().equalsIgnoreCase("yes")) {
                    Intent intent = new Intent(LessionSlideActivity.this, ThankYouActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.putExtra("course_name", data.getData().get(0).getCourseTitle());
                    startActivity(intent);
                    finish();
                }
            }
        }

    }

    private void loadBackgroundImage() {
        AppUtils.loadImageWithPicasso(data.getData().get(0).getSectionSlideDetails().getSlideBackgroundImage(), imgSlide, LessionSlideActivity.this, 0, 0);
    }

    private void loadImage() {
        AppUtils.loadImageWithPicasso(data.getData().get(0).getSectionSlideDetails().getSlideImage(), imgSlide, LessionSlideActivity.this, 0, 0);
    }

    private void loadDescription() {
        tvDescription.getSettings().setJavaScriptEnabled(true);
        tvDescription.loadDataWithBaseURL(null, data.getData().get(0).getSectionSlideDetails().getSlideDesc(), "text/html", "utf-8", null);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgPrev:
                section_id = data.getData().get(0).getPreviousSection();
                slide_id = data.getData().get(0).getPreviousSlide();

                if (videoView.getVisibility() == View.VISIBLE) {
                    // videoView.onPause();
                   /* videoView.loadData("","","");
                    videoView.clearCache(true);
                    videoView.reload();
                    videoView.clearCache(true);*/
                    clearVideoView();
                }

                if (section_id.length() > 0 && slide_id.length() > 0)
                    callApi();
                break;
            case R.id.imgNext:
                section_id = data.getData().get(0).getNextSection();
                slide_id = data.getData().get(0).getNextSlide();

                if (videoView.getVisibility() == View.VISIBLE) {
                    clearVideoView();
                }

                if (section_id.length() > 0 && slide_id.length() > 0) {
                    callApi();
                } else {
                    if (data.getData().get(0).getIs_completed() == 1) {
                        finish();
                    } else {
                        if (AppUtils.isInternetAvailable(LessionSlideActivity.this)) {
                            checkIfQuizOptionalOrNot();
                        }
                    }

                }

                break;
        }
    }

    private void clearVideoView() {
        videoView = findViewById(R.id.videoView);
        videoView.setVisibility(View.GONE);
        videoView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });
        videoView.setWebChromeClient(new MyChrome());

        WebSettings webSettings = videoView.getSettings();
        webSettings.setAllowFileAccess(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setJavaScriptEnabled(true);

        videoView.loadData("", "text/html", "utf-8");

        videoView.setVisibility(View.GONE);
    }

    private void checkIfQuizOptionalOrNot() {
        String lang = "";
        AppUtils.showDialog(LessionSlideActivity.this, getString(R.string.pls_wait));
        ApiInterface apiInterface = RestApi.getConnection(ApiInterface.class, ServerConstents.API_URL);
        final HashMap params = new HashMap<>();
        params.put("course_id", course_id);

        if (AppSharedPreference.getInstance().getString(LessionSlideActivity.this, AppSharedPreference.LANGUAGE_SELECTED) == null ||
                AppSharedPreference.getInstance().getString(LessionSlideActivity.this, AppSharedPreference.LANGUAGE_SELECTED).equalsIgnoreCase(AppConstant.ENG_LANG)) {
            lang = AppConstant.ENG_LANG;
        } else {
            lang = AppConstant.ARABIC_LANG;
        }
        Call<CheckCourse> call = apiInterface.checkCourse(lang, AppSharedPreference.getInstance().
                getString(LessionSlideActivity.this, AppSharedPreference.ACCESS_TOKEN), params);
        ApiCall.getInstance().hitService(LessionSlideActivity.this, call, this, ServerConstents.CHECK_COURSE);

    }

    @Override
    public void onError(String response, int requestCode, int errorCode) {

    }

    @Override
    public void onFailure() {

    }

    private void playVideo(String embeded_url) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Data...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        videoView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });
        videoView.setWebChromeClient(new MyChrome());

        WebSettings webSettings = videoView.getSettings();
        webSettings.setAllowFileAccess(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setJavaScriptEnabled(true);


      /* webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);*/

        videoView.loadData("<iframe width=\"100%\" height=\"100%\"src=" + embeded_url + " frameborder=\"0\" allow=\"autoplay; fullscreen\" allowfullscreen >\n" +
                "</iframe>", "text/html", "utf-8");

        videoView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                // progressbar.setVisibility(View.GONE);
                progressDialog.dismiss();
            }

        });
    }

    private void initToolBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void loadWebview(final String myPdfUrl) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Data...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        if (Build.VERSION.SDK_INT >= 19) {
            webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAllowFileAccessFromFileURLs(true);
        webView.getSettings().setAllowUniversalAccessFromFileURLs(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.setVerticalScrollBarEnabled(true);
        webView.setHorizontalScrollBarEnabled(false);
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        // myPdfUrl="http://1.22.161.26:9875/online_education_system/public/images/Courses/104/SlideDocument/1605077355.docx";

        String url = "https://docs.google.com/gview?embedded=true&url="+myPdfUrl;

        webView.loadUrl(url);
        Log.d("URL DOC:: ", " "+url);
        webView.setWebViewClient(new WebViewClient() {
            boolean checkhasOnPageStarted = false;
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                Log.d("PAGE START :: ", "CAL PAGE START===== ");
                checkhasOnPageStarted = true;
                super.onPageStarted(view, url, favicon);
            }
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.d("PAGE  :: ", "CAL PAGE FINISHHH===== ");
                if (checkhasOnPageStarted ) {
                    webView.loadUrl("javascript:(function() { document.querySelector('[role=\"toolbar\"]').remove();})()");
                } else {
                    loadWebview(myPdfUrl);
                }
                progressDialog.dismiss();
            }


        });

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        videoView.saveState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        videoView.restoreState(savedInstanceState);
    }

    private class MyChrome extends WebChromeClient {

        protected FrameLayout mFullscreenContainer;
        private View mCustomView;
        private CustomViewCallback mCustomViewCallback;
        private int mOriginalOrientation;
        private int mOriginalSystemUiVisibility;

        MyChrome() {
        }

        public Bitmap getDefaultVideoPoster() {
            if (mCustomView == null) {
                return null;
            }
            return BitmapFactory.decodeResource(getApplicationContext().getResources(), 2130837573);
        }

        public void onHideCustomView() {
            ((FrameLayout) getWindow().getDecorView()).removeView(this.mCustomView);
            this.mCustomView = null;
            getWindow().getDecorView().setSystemUiVisibility(this.mOriginalSystemUiVisibility);
            setRequestedOrientation(this.mOriginalOrientation);
            this.mCustomViewCallback.onCustomViewHidden();
            this.mCustomViewCallback = null;
        }

        public void onShowCustomView(View paramView, CustomViewCallback paramCustomViewCallback) {
            if (this.mCustomView != null) {
                onHideCustomView();
                return;
            }
            this.mCustomView = paramView;
            this.mOriginalSystemUiVisibility = getWindow().getDecorView().getSystemUiVisibility();
            this.mOriginalOrientation = getRequestedOrientation();
            this.mCustomViewCallback = paramCustomViewCallback;
            ((FrameLayout) getWindow().getDecorView()).addView(this.mCustomView, new FrameLayout.LayoutParams(-1, -1));
            getWindow().getDecorView().setSystemUiVisibility(3846 | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }
    }


}
