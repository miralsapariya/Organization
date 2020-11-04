package com.onlineeducationsystemorganization;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.File;

public class WebPDFActivity extends AppCompatActivity {

    String myPdfUrl="";
    private ImageView imgBack;
    private WebView webViewPdfView;
    private PDFView pdfView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_p_d_f);

        Bundle b=getIntent().getExtras();
        myPdfUrl= b.getString("file");
        imgBack =findViewById(R.id.imgBack);


        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Log.d("FILE :: ", myPdfUrl);

        pdfView =findViewById(R.id.pdfView);
        File f =new File(myPdfUrl);
        pdfView.fromFile(f).pages(0, 2, 1, 3, 3, 3) // all pages are displayed by default
                .enableSwipe(true) // allows to block changing pages using swipe
                .swipeHorizontal(false)
                .enableDoubletap(true)
                .defaultPage(0)
                // allows to draw something on the current page, usually visible in the middle of the screen
                .enableAnnotationRendering(false) // render annotations (such as comments, colors or forms)
                .password(null)
                .scrollHandle(null)
                .enableAntialiasing(true) // improve rendering a little bit on low-res screens
                // spacing between pages in dp. To define spacing color, set view background
                .spacing(0)
                .load();
        //loadPDf();
    }

    private void loadPDf()
    {
      //  webViewPdfView=findViewById(R.id.webViewPdfView);
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Data...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        if (Build.VERSION.SDK_INT >= 19) {
            webViewPdfView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }
        webViewPdfView.getSettings().setBuiltInZoomControls(true);
        webViewPdfView.getSettings().setSupportZoom(true);
        webViewPdfView.getSettings().setDisplayZoomControls(false);
        webViewPdfView.getSettings().setJavaScriptEnabled(true);
        webViewPdfView.getSettings().setPluginState(WebSettings.PluginState.ON);
        webViewPdfView.getSettings().setAllowFileAccessFromFileURLs(true);
        webViewPdfView.getSettings().setAllowUniversalAccessFromFileURLs(true);
        webViewPdfView.getSettings().setLoadWithOverviewMode(true);
        webViewPdfView.setVerticalScrollBarEnabled(true);
        webViewPdfView.setHorizontalScrollBarEnabled(false);
        webViewPdfView.getSettings().setJavaScriptEnabled(true);
        webViewPdfView.getSettings().setAllowFileAccess(true);


      //  String url = "https://docs.google.com/gview?embedded=true&url="+myPdfUrl;
      //  /storage/emulated/0/ULowgic/Cef5c02b2ef44b8b63a9d74bd334529e0.pdf
        webViewPdfView.loadUrl("file:///");
        webViewPdfView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                // progressbar.setVisibility(View.GONE);
                progressDialog.dismiss();
            }

        });

    }

}