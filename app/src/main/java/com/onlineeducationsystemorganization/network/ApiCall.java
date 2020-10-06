package com.onlineeducationsystemorganization.network;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;

import com.onlineeducationsystemorganization.interfaces.NetworkListener;
import com.onlineeducationsystemorganization.util.AppSharedPreference;
import com.onlineeducationsystemorganization.util.AppUtils;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * ApiCall.java
 * This singleton class is used to hit service and handle response at single place.
 *
 * @author Appinvetiv
 * @version 1.0
 * @since 1.0
 */
public class ApiCall {
    private static ApiCall apiCall=null;
    private static AppSharedPreference preference;
    private final String LOG_TAG="Network Connection";
    AlertDialog alertDialog;

    /**
     * This method is used to provide the instance of Network Connection Class
     *
     * @return instance of Network Connection Class
     */
    public static ApiCall getInstance() {
        if (apiCall == null) {
            return new ApiCall();
        } else {
            return apiCall;
        }
    }

    /**
     * This class is used to hit the service and handle their responses
     *
     * @param context     - Context of the class
     * @param bodyCall    - retrofit call
     * @param requestCode
     */
    public <T> void hitService(final Context context, Call<T> bodyCall, final NetworkListener networkListener, final int requestCode) {
        bodyCall.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                try {

                    AppUtils.dismissDialog();
                    if (response.isSuccessful()) {
                        T body=response.body();
                        Log.e("RESPONSE", response.code() + "\n" + response.message() + "\n" + response.errorBody() + "\n" + response.body());
                        Object data=(Object) body;
                        /*Object object=data.getData();
                        if (object == null)
                            object=data;*/

                            networkListener.onSuccess(0, body, requestCode);

                    } else if(response.code() == 401)
                    {
                        //token expired
                        AppUtils.dismissDialog();
                        JSONObject jsonObject=new JSONObject(response.errorBody().string());
                        Log.d("msg er :: ", jsonObject.get("message").toString());

                        networkListener.onError(jsonObject.get("message").toString(), requestCode,401);
                    }else if(response.code() == 403)
                    {
                        //otp not verify
                        // remove whish list
                        AppUtils.dismissDialog();
                        JSONObject jsonObject=new JSONObject(response.errorBody().string());
                        networkListener.onError(jsonObject.get("message").toString(), requestCode,403);
//
                    }
                        else {
                        Log.d("in error :: ", " "+response.code());
                        AppUtils.dismissDialog();
                        JSONObject jsonObject=new JSONObject(response.errorBody().string());
//                        if (jsonObject != null && jsonObject.getString("code").equalsIgnoreCase("401")) {
//                            if (jsonObject.get("api_code_result").toString().equalsIgnoreCase("HTTP_UNAUTHORIZED")) {
//                                try {
//                                    if (!getDialogInstance(context, jsonObject.get("message").toString()).isShowing())
//                                        getDialogInstance(context, jsonObject.get("message").toString()).show();
//                                } catch (Exception e) {
//                                    e.printStackTrace();
//                                }
//
//                            }
//                        } else {
//                            JSONObject jsonObject = new JSONObject(response.errorBody().string());

                        //commanDialogError(context);
                        networkListener.onError(jsonObject.get("message").toString(), requestCode,0);
//                        }


                    }
                } catch (JSONException e) {
                    AppUtils.dismissDialog();
                    e.printStackTrace();
                    Log.d("ERROR IN PRSING ", ""+e.getMessage());
                    //commanDialogError(context);
                    networkListener.onFailure();
                } catch (Exception e) {
                    AppUtils.dismissDialog();
                    Log.d("ERROR IN PRSING ", ""+e.getMessage());
                    e.printStackTrace();
                    //commanDialogError(context);
                    networkListener.onFailure();
                }
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                Log.d("ERROR IN PRSING ", ""+t.getMessage());
                //commanDialogError(context);
                networkListener.onFailure();
                AppUtils.dismissDialog();
            }
        });
    }

    private void commanDialogError(Context context)
    {

        alertDialog = new AlertDialog.Builder(context)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Alert")
                .setMessage("Something went wrong.")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //set what would happen when positive button is clickedal
                        alertDialog.dismiss();
                    }
                })

                .show();
    }
}


