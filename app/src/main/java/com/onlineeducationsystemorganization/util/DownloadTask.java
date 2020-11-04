package com.onlineeducationsystemorganization.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.Gravity;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.view.ContextThemeWrapper;

import com.onlineeducationsystemorganization.R;
import com.onlineeducationsystemorganization.WebPDFActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;

public class DownloadTask {
    private static final String TAG = "Download Task";
    private Context context;

    private String downloadUrl = "", downloadFileName = "";
    private ProgressDialog progressDialog;

    public DownloadTask(Context context, String downloadUrl) {
        this.context = context;

        this.downloadUrl = downloadUrl;
        downloadFileName = downloadUrl.substring(downloadUrl.lastIndexOf('/'), downloadUrl.length());//Create file name by picking download file name from URL
        Log.e(TAG, downloadFileName);

        //Start Downloading Task
        new DownloadingTask().execute();
    }

    private class DownloadingTask extends AsyncTask<Void, Void, Void> {

        File apkStorage = null;
        File outputFile = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage(context.getString(R.string.downloding)+"...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(Void result) {
            try {
                if (outputFile != null) {
                    progressDialog.dismiss();
                    ContextThemeWrapper ctw = new ContextThemeWrapper( context, R.style.MyDialogTheme);
                     AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ctw);
                    alertDialogBuilder.setTitle(context.getString(R.string.document));
                    alertDialogBuilder.setMessage(context.getString(R.string.doc_downlad));
                    alertDialogBuilder.setCancelable(false);
                    alertDialogBuilder.setPositiveButton(context.getString(R.string.ok), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    });

                    alertDialogBuilder.setNegativeButton(context.getString(R.string.open_report),new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                            StrictMode.setVmPolicy(builder.build());
                            File pdfFile = new File(Environment.getExternalStorageDirectory() +"/ULowgic"+  downloadFileName);  // -> filename = maven.pdf

                            Log.d("download :: ", pdfFile.getAbsolutePath());
                            Intent intent =new Intent(context, WebPDFActivity.class);
                            intent.putExtra("file", pdfFile.getAbsolutePath());
                            context.startActivity(intent);

                        }
                    });
                    alertDialogBuilder.show();
                    final AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                                                      @Override
                                                      public void onShow(DialogInterface dlg) {
                                                          if(AppSharedPreference.getInstance().getString(context, AppSharedPreference.LANGUAGE_SELECTED).equals("ar")) {
                                                              TextView messageText1 = alertDialog.findViewById(android.R.id.text2);
                                                              messageText1.setGravity(Gravity.END);
                                                          }
                                                      }
                                                  });
//                    Toast.makeText(context, "Document Downloaded Successfully", Toast.LENGTH_SHORT).show();
                } else {

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                        }
                    }, 3000);

                    Log.e(TAG, "Download Failed");

                }
            } catch (Exception e) {
                e.printStackTrace();

                //Change button text if exception occurs

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                    }
                }, 3000);
                Log.e(TAG, "Download Failed with Exception - " + e.getLocalizedMessage());

            }


            super.onPostExecute(result);
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            try {
                URL url = new URL(downloadUrl);//Create Download URl
                HttpURLConnection c = (HttpURLConnection) url.openConnection();//Open Url Connection
                c.setRequestMethod("GET");//Set Request Method to "GET" since we are grtting data
                c.connect();//connect the URL Connection

                //If Connection response is not OK then show Logs
                if (c.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    Log.e(TAG, "Server returned HTTP " + c.getResponseCode()
                            + " " + c.getResponseMessage());

                }


               /* //Get File if SD card is present
                if (new CheckForSDCard().isSDCardPresent()) {

                    apkStorage = new File(Environment.getExternalStorageDirectory() + "/" + "CodePlayon");
                } else
                    Toast.makeText(context, "Oops!! There is no SD Card.", Toast.LENGTH_SHORT).show();

                //If File is not present create directory
                if (!apkStorage.exists()) {
                    apkStorage.mkdir();
                    Log.e(TAG, "Directory Created.");
                }

                outputFile = new File(apkStorage.getAbsolutePath(), downloadFileName);//Create Output file in Main File

                //Create New File if not present
                if (!outputFile.exists()) {
                    outputFile.createNewFile();
                    Log.e(TAG, "File Created");
                }else {
                    outputFile.delete();
                    try {
                        outputFile.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
*/
                Calendar cal = Calendar.getInstance();

                File storageDir = new File(Environment.getExternalStorageDirectory(),  "ULowgic");
                if (!storageDir.exists()) {
                    storageDir.mkdir();
                }

                 outputFile = new File(storageDir.getAbsolutePath(),
                        downloadFileName);


                if (!outputFile.exists()) {
                    try {
                        outputFile.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    outputFile.delete();
                    try {
                        outputFile.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                FileOutputStream fos = new FileOutputStream(outputFile);//Get OutputStream for NewFile Location

                InputStream is = c.getInputStream();//Get InputStream for connection

                byte[] buffer = new byte[1024];//Set buffer type
                int len1 = 0;//init length
                while ((len1 = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, len1);//Write new file
                }

                //Close all connection after doing task
                fos.close();
                is.close();

            } catch (Exception e) {

                //Read exception if something went wrong
                e.printStackTrace();
                outputFile = null;
                Log.e(TAG, "Download Error Exception " + e.getMessage());
            }

            return null;
        }
    }
}