package com.onlineeducationsystemorganization.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.ParseException;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.ImageColumns;
import android.provider.MediaStore.Images.Media;
import android.provider.MediaStore.MediaColumns;
import android.provider.MediaStore.Video;
import android.provider.Settings;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.text.style.StyleSpan;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

import com.onlineeducationsystemorganization.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.content.Context.ACTIVITY_SERVICE;


public class AppUtils {

    private static final String TAG = AppUtils.class.getSimpleName();

    static ProgressDialog mProgressDialog;
    static ProgressDialog pgdialog;
    private static Dialog updatedialog;
    public static String image = "image";
    public static String video = "video";
    public static String audio = "audio";
    public static String content = "content";
    public static String file = "file";
    public static  AlertDialog.Builder builder;

    /**
     * /**
     * Shows a long time duration toast message.
     *
     * @param msg Message to be show in the toast.
     * @return Toast object just shown
     **/
    public static Toast showToast(Context ctx, CharSequence msg) {
        return showToast(ctx, msg, Toast.LENGTH_LONG);
    }

    public static void loginAlert(Context context)
    {
        builder = new AlertDialog.Builder(context,R.style.MyDialogTheme);
        builder.setMessage(context.getString(R.string.please_login))
                .setCancelable(false)
                .setPositiveButton(context.getResources().getText(R.string.ok), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

        //Creating dialog box
        AlertDialog alert = builder.create();
        //Setting the title manually
        alert.setCancelable(true);
        alert.setTitle(context.getString(R.string.login_alert));
        alert.show();
    }

    public static void loadImageWithPicasso(String imagePath, ImageView iv, Context context, int width, int height) {
        if (width == 0 && height == 0) {
            Picasso.with(context).load(imagePath)
                    .placeholder(R.mipmap.placeholder).error(R.mipmap.placeholder).into(iv);
        } else {
            if (!imagePath.isEmpty())
                Picasso.with(context).load(imagePath).placeholder(R.mipmap.placeholder)
                        .error(R.mipmap.placeholder).resize(width, height).centerCrop().into(iv);
            else
                Picasso.with(context).load(R.mipmap.placeholder).placeholder(R.mipmap.placeholder)
                        .error(R.mipmap.placeholder).resize(width, height).centerCrop().into(iv);
        }
    }


    /**
     * Shows the message passed in the parameter in the Toast.
     *
     * @param msg      Message to be show in the toast.
     * @param duration Duration in milliseconds for which the toast should be shown
     * @return Toast object just shown
     **/
    public static Toast showToast(Context ctx, CharSequence msg, int duration) {
        Toast toast = Toast.makeText(ctx, msg, Toast.LENGTH_SHORT);
        toast.setDuration(duration);
        toast.show();
        return toast;
    }

    public static boolean validEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }

    public static int countWordsUsingSplit(String input)
    {
        if (input == null || input.isEmpty())
        { return 0; }
        String[] words = input.split(" ");
        return words.length;
    }


    /**
     * Checks if the Internet connection is available.
     *
     * @return Returns true if the Internet connection is available. False otherwise.
     **/
    public static boolean isInternetAvailable(Context ctx) {
        // using received context (typically activity) to get SystemService causes memory link as this holds strong reference to that activity.
        // use application level context instead, which is available until the app dies.
        ConnectivityManager connectivityManager = (ConnectivityManager) ctx.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        // if network is NOT available networkInfo will be null
        // otherwise check if we are connected
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }

        return false;
    }

    /**
     * Checks if the SD Card is mounted on the device.
     **/
    public static boolean isSdCardMounted() {
        String status = Environment.getExternalStorageState();

        if (status != null && status.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        }

        return false;
    }

    private static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    private static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    private static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    @SuppressLint("NewApi")
    public static String getFilePath(Context context, Uri uri) {
        String selection = null;
        String[] selectionArgs = null;
        // Uri is different in versions after KITKAT (Android 4.4), we need to
        if (Build.VERSION.SDK_INT >= 19 && DocumentsContract.isDocumentUri(context, uri)) {
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                return Environment.getExternalStorageDirectory() + "/" + split[1];
            } else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                uri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
            } else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                if (image.equals(type)) {
                    uri = Media.EXTERNAL_CONTENT_URI;
                } else if (video.equals(type)) {
                    uri = Video.Media.EXTERNAL_CONTENT_URI;
                } else if (audio.equals(type)) {
                    uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                selection = "_id=?";
                selectionArgs = new String[]{
                        split[1]
                };
            }
        }
        if (content.equalsIgnoreCase(uri.getScheme())) {
            String[] projection = {
                    Media.DATA
            };
            Cursor cursor = null;
            try {
                cursor = context.getContentResolver()
                        .query(uri, projection, selection, selectionArgs, null);
                int column_index = cursor.getColumnIndexOrThrow(Media.DATA);
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (file.equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }


    /**
     * Shows an alert dialog with the OK button. When the user presses OK button, the dialog
     * dismisses.
     **/
    public static void showAlertDialog(Context context, @StringRes int titleResId, @StringRes int bodyResId) {
        showAlertDialog(context, context.getString(titleResId),
                context.getString(bodyResId), null);
    }

    /**
     * Shows an alert dialog with the OK button. When the user presses OK button, the dialog
     * dismisses.
     **/
    public static void showAlertDialog(Context context, String title, String body) {
        showAlertDialog(context, title, body, null);
    }

    /**
     * Shows an alert dialog with OK button
     **/
    public static void showAlertDialog(Context context, String title, String body, DialogInterface.OnClickListener okListener) {

        if (okListener == null) {
            okListener = new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            };
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setMessage(body).setPositiveButton("OK", okListener);

        if (!TextUtils.isEmpty(title)) {
            builder.setTitle(title);
        }

        builder.show();
    }


    public static Bitmap drawableToBitmap(Drawable drawable) {

        if (drawable == null) {
            throw new NullPointerException("Drawable to convert should NOT be null");
        }

        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        if (drawable.getIntrinsicWidth() <= 0 && drawable.getIntrinsicHeight() <= 0) {
            return null;
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }


    public static InputStream bitmapToInputStream(Bitmap bitmap) throws NullPointerException {

        if (bitmap == null) {
            throw new NullPointerException("Bitmap cannot be null");
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        InputStream inputstream = new ByteArrayInputStream(baos.toByteArray());

        return inputstream;
    }

    /**
     * Shows a progress dialog with a spinning animation in it. This method must preferably called
     * from a UI thread.
     *
     * @param ctx           Activity context
     * @param title         Title of the progress dialog
     * @param body          Body/Message to be shown in the progress dialog
     * @param isCancellable True if the dialog can be cancelled on back button press, false otherwise
     **/
    public static void showProgressDialog(Context ctx, String title, String body, boolean isCancellable) {
        showProgressDialog(ctx, title, body, null, isCancellable);
    }

    /**
     * Shows a progress dialog with a spinning animation in it. This method must preferably called
     * from a UI thread.
     *
     * @param ctx           Activity context
     * @param title         Title of the progress dialog
     * @param body          Body/Message to be shown in the progress dialog
     * @param icon          Icon to show in the progress dialog. It can be null.
     * @param isCancellable True if the dialog can be cancelled on back button press, false otherwise
     **/
    public static void showProgressDialog(Context ctx, String title, String body, Drawable icon, boolean isCancellable) {

        if (ctx instanceof Activity) {
            if (!((Activity) ctx).isFinishing()) {
                mProgressDialog = ProgressDialog.show(ctx, title, body, true);
                mProgressDialog.setIcon(icon);
                mProgressDialog.setCancelable(isCancellable);
            }
        }
    }

    /**
     * this method is used to get the date with month name
     *
     * @param completeDate
     * @return
     */
    public static String getFullSimpleDate(String completeDate) {
        DateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        DateFormat targetFormat = new SimpleDateFormat("dd MMMM, yyyy");
        Date date = null;
        try {
            date = originalFormat.parse(completeDate);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        String formattedDate = targetFormat.format(date);
        return formattedDate;
    }

    /**
     * Check if the {@link ProgressDialog} is visible in the UI.
     **/
    public static boolean isProgressDialogVisible() {
        return (mProgressDialog != null);
    }

    /**
     * Dismiss the progress dialog if it is visible.
     **/
    public static void dismissProgressDialog() {

        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }

        mProgressDialog = null;
    }

    /**
     * Gives the device independent constant which can be used for scaling images, manipulating view
     * sizes and changing dimension and display pixels etc.
     **/
    public static float getDensityMultiplier(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }

    /**
     * This method converts device specific pixels to density independent pixels.
     *
     * @param px      A value in px (pixels) unit. Which we need to convert into db
     * @param context Context to get resources and device specific display metrics
     * @return A int value to represent dp equivalent to px value
     */
    public static int getDip(int px, Context context) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px * scale + 0.5f);
    }

    /**
     * Creates a confirmation dialog with Yes-No Button. By default the buttons just dismiss the
     * dialog.
     *
     * @param ctx
     * @param message     Message to be shown in the dialog.
     * @param yesListener Yes click handler
     * @param noListener
     **/
    public static void showConfirmDialog(Context ctx, String message, DialogInterface.OnClickListener yesListener, DialogInterface.OnClickListener noListener) {
        showConfirmDialog(ctx, message, yesListener, noListener, "Yes", "No");
    }

    /**
     * Creates a confirmation dialog with Yes-No Button. By default the buttons just dismiss the
     * dialog.
     *
     * @param ctx
     * @param message     Message to be shown in the dialog.
     * @param yesListener Yes click handler
     * @param noListener
     * @param yesLabel    Label for yes button
     * @param noLabel     Label for no button
     **/
    public static void showConfirmDialog(Context ctx, String message, DialogInterface.OnClickListener yesListener, DialogInterface.OnClickListener noListener, String yesLabel, String noLabel) {

        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);

        if (yesListener == null) {
            yesListener = new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            };
        }

        if (noListener == null) {
            noListener = new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            };
        }

        builder.setMessage(message).setPositiveButton(yesLabel, yesListener).setNegativeButton(noLabel, noListener).show();
    }

    /**
     * Creates a confirmation dialog that show a pop-up with button labeled as parameters labels.
     *
     * @param ctx                 {@link Activity} {@link Context}
     * @param message             Message to be shown in the dialog.
     * @param dialogClickListener
     * @param positiveBtnLabel    For e.g. "Yes"
     * @param negativeBtnLabel    For e.g. "No"
     **/
    public static void showDialog(Context ctx, String message, String positiveBtnLabel, String negativeBtnLabel, DialogInterface.OnClickListener dialogClickListener) {

        if (dialogClickListener == null) {
            throw new NullPointerException("Action listener cannot be null");
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);

        builder.setMessage(message).setPositiveButton(positiveBtnLabel, dialogClickListener).setNegativeButton(negativeBtnLabel, dialogClickListener).show();
    }

    /**
     * Gets the version name of the application. For e.g. 1.9.3
     **/
    public static String getApplicationVersionNumber(Context context) {

        String versionName = null;

        if (context == null) {
            return versionName;
        }

        try {
            versionName = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }

        return versionName;
    }

    public static String getDeviceId(Context context) {
        String androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        return androidId;
    }

    /**
     * Gets the version code of the application. For e.g. Maverick Meerkat or 2013050301
     **/
    public static int getApplicationVersionCode(Context ctx) {

        int versionCode = 0;

        try {
            versionCode = ctx.getPackageManager().getPackageInfo(ctx.getPackageName(), 0).versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }

        return versionCode;
    }

    /**
     * Gets the version number of the Android OS For e.g. 2.3.4 or 4.1.2
     **/
    public static String getOsVersion() {
        return Build.VERSION.RELEASE;
    }

    /**
     * Checks if the service with the given name is currently running on the device.
     *
     * @param serviceName Fully qualified name of the server. <br/>
     *                    For e.g. nl.changer.myservice.name
     **/
    public static boolean isServiceRunning(Context ctx, String serviceName) {

        if (serviceName == null) {
            throw new NullPointerException("Service name cannot be null");
        }

        // use application level context to avoid unnecessary leaks.
        ActivityManager manager = (ActivityManager) ctx.getApplicationContext().getSystemService(ACTIVITY_SERVICE);
        for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (service.service.getClassName().equals(serviceName)) {
                return true;
            }
        }

        return false;
    }


    /**
     * Shares an application over the social network like Facebook, Twitter etc.
     *
     * @param sharingMsg   Message to be pre-populated when the 3rd party app dialog opens up.
     * @param emailSubject Message that shows up as a subject while sharing through email.
     * @param title        Title of the sharing options prompt. For e.g. "Share via" or "Share using"
     **/
    public static void share(Context ctx, String sharingMsg, String emailSubject, String title) {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);

        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(Intent.EXTRA_TEXT, sharingMsg);
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, emailSubject);

        ctx.startActivity(Intent.createChooser(sharingIntent, title));
    }

    /**
     * Checks the type of data connection that is currently available on the device.
     *
     * @return <code>ConnectivityManager.TYPE_*</code> as a type of internet connection on the
     * device. Returns -1 in case of error or none of
     * <code>ConnectivityManager.TYPE_*</code> is found.
     **/
    public static int getDataConnectionType(Context ctx) {

        // use application level context to avoid unnecessary leaks.
        ConnectivityManager connectivityManager = (ConnectivityManager) ctx.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager != null && connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE) != null) {
            if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnected()) {
                return ConnectivityManager.TYPE_MOBILE;
            } else if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected()) {
                return ConnectivityManager.TYPE_WIFI;
            } else
                return -1;
        } else
            return -1;
    }

    /**
     * Checks if the input parameter is a valid email.
     *
     * @param email
     * @return
     */
    public static boolean isValidEmail(String email) {

        if (email == null) {
            return false;
        }

        final String emailPattern = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Matcher matcher;
        Pattern pattern = Pattern.compile(emailPattern);

        matcher = pattern.matcher(email);

        if (matcher != null) {
            return matcher.matches();
        } else {
            return false;
        }
    }

    @Nullable
    /**
     * Capitalizes each word in the string.
     * @param string
     * @return
     */
    public static String capitalizeString(String string) {

        if (string == null) {
            return null;
        }

        char[] chars = string.toLowerCase().toCharArray();
        boolean found = false;
        for (int i = 0; i < chars.length; i++) {
            if (!found && Character.isLetter(chars[i])) {
                chars[i] = Character.toUpperCase(chars[i]);
                found = true;
            } else if (Character.isWhitespace(chars[i]) || chars[i] == '.' || chars[i] == '\'') { // You
                // can add other
                // chars here
                found = false;
            }
        } // end for

        return String.valueOf(chars);
    }

    /**
     * Checks if the DB with the given name is present on the device.
     *
     * @param packageName
     * @param dbName
     * @return
     */
    public static boolean isDatabasePresent(String packageName, String dbName) {
        SQLiteDatabase sqLiteDatabase = null;
        try {
            sqLiteDatabase = SQLiteDatabase.openDatabase("/data/data/" + packageName + "/databases/" + dbName, null, SQLiteDatabase.OPEN_READONLY);
            sqLiteDatabase.close();
        } catch (SQLiteException e) {
            // database doesn't exist yet.
            e.printStackTrace();
            Log.e(TAG, "The database does not exist." + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "Exception " + e.getMessage());
        }

        return (sqLiteDatabase != null);
    }

    /**
     * Get the file path from the Media Content Uri for video, audio or images.
     *
     * @param mediaContentUri Media content Uri.
     **/
    public static String getPathForMediaUri(Context context, Uri mediaContentUri) {

        Cursor cur = null;
        String path = null;

        try {
            String[] projection = {MediaColumns.DATA};
            cur = context.getContentResolver().query(mediaContentUri, projection, null, null, null);

            if (cur != null && cur.getCount() != 0) {
                cur.moveToFirst();
                path = cur.getString(cur.getColumnIndexOrThrow(MediaColumns.DATA));
            }

            // Log.v( TAG, "#getRealPathFromURI Path: " + path );
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cur != null && !cur.isClosed())
                cur.close();
        }

        return path;
    }

    public static ArrayList<String> toStringArray(JSONArray jsonArr) {

        if (jsonArr == null || jsonArr.length() == 0) {
            return null;
        }

        ArrayList<String> stringArray = new ArrayList<String>();

        for (int i = 0, count = jsonArr.length(); i < count; i++) {
            try {
                String str = jsonArr.getString(i);
                stringArray.add(str);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return stringArray;
    }


    public static JSONArray toJSONArray(ArrayList<String> stringArr) {
        JSONArray jsonArr = new JSONArray();

        for (int i = 0; i < stringArr.size(); i++) {
            String value = stringArr.get(i);
            jsonArr.put(value);
        }

        return jsonArr;
    }

    /**
     * Gets the data storage directory(pictures dir) for the device. If the external storage is not
     * available, this returns the reserved application data storage directory. SD Card storage will
     * be preferred over internal storage.
     *
     * @param dirName if the directory name is specified, it is created inside the DIRECTORY_PICTURES
     *                directory.
     * @return Data storage directory on the device. Maybe be a directory on SD Card or internal
     * storage of the device.
     **/
    public static File getStorageDirectory(Context ctx, String dirName) {

        if (TextUtils.isEmpty(dirName)) {
            dirName = "atemp";
        }

        File f = null;

        String state = Environment.getExternalStorageState();

        if (Environment.MEDIA_MOUNTED.equals(state)) {
            f = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/" + dirName);
        } else {
            // media is removed, unmounted etc
            // Store image in
            // /data/data/<package-name>/cache/atemp/photograph.jpeg
            f = new File(ctx.getCacheDir() + "/" + dirName);
        }

        if (!f.exists()) {
            f.mkdirs();
        }

        return f;
    }

    /**
     * Given a file name, this method creates a {@link File} on best chosen device storage and
     * returns the file object. You can get the file path using {@link File#getAbsolutePath()}
     **/
    public static File getFile(Context ctx, String fileName) {
        File dir = getStorageDirectory(ctx, null);
        File f = new File(dir, fileName);
        return f;
    }

    /**
     * @return Path of the image file that has been written.
     * @deprecated Use {@link(Context, byte[])}
     * Writes the given image to the external storage of the device. If external storage is not
     * available, the image is written to the application private directory
     **/
    public static String writeImage(Context ctx, byte[] imageData) {

        final String FILE_NAME = "photograph.jpeg";
        File dir = null;
        String filePath = null;
        OutputStream imageFileOS;

        dir = getStorageDirectory(ctx, null);
        File f = new File(dir, FILE_NAME);

        try {
            imageFileOS = new FileOutputStream(f);
            imageFileOS.write(imageData);
            imageFileOS.flush();
            imageFileOS.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        filePath = f.getAbsolutePath();

        return filePath;
    }

    /**
     * Inserts an image into {@link Media} content provider of the device.
     *
     * @return The media content Uri to the newly created image, or null if the image failed to be
     * stored for any reason.
     **/
    public static String writeImageToMedia(Context ctx, Bitmap image, String title, String description) {
        // TODO: move to MediaUtils
        if (ctx == null) {
            throw new NullPointerException("Context cannot be null");
        }

        return Media.insertImage(ctx.getContentResolver(), image, title, description);
    }


    /**
     * Returns the URL without the query string
     **/
    public static URL getPathFromUrl(URL url) {

        if (url != null) {
            String urlStr = url.toString();
            String urlWithoutQueryString = urlStr.split("\\?")[0];
            try {
                return new URL(urlWithoutQueryString);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    /**
     * Transforms Calendar to ISO 8601 string.
     **/
    public static String fromCalendar(final Calendar calendar) {
        // TODO: move this method to DateUtils
        Date date = calendar.getTime();
        String formatted = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").format(date);
        return formatted.substring(0, 22) + ":" + formatted.substring(22);
    }

    /**
     * Gets current date and time formatted as ISO 8601 string.
     **/
    public static String now() {
        // TODO: move this method to DateUtils
        return fromCalendar(GregorianCalendar.getInstance());
    }

    /**
     * Transforms ISO 8601 string to Calendar.
     **/
    public static Calendar toCalendar(final String iso8601string) throws ParseException {
        // TODO: move this method to DateUtils
        Calendar calendar = GregorianCalendar.getInstance();
        String s = iso8601string.replace("Z", "+00:00");
        try {
            s = s.substring(0, 22) + s.substring(23);
        } catch (IndexOutOfBoundsException e) {
            // throw new org.apache.http.ParseException();
            e.printStackTrace();
        }

        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").parse(s);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        calendar.setTime(date);
        return calendar;
    }


    /**
     * Set Mock Location for test device. DDMS cannot be used to mock location on an actual device.
     * So this method should be used which forces the GPS Provider to mock the location on an actual
     * device.
     **/
    public static void setMockLocation(Context ctx, double longitude, double latitude) {
        // use application level context to avoid unnecessary leaks.
        LocationManager locationManager = (LocationManager) ctx.getApplicationContext().getSystemService(Context.LOCATION_SERVICE);

        locationManager.addTestProvider(LocationManager.GPS_PROVIDER, "requiresNetwork" == "", "requiresSatellite" == "", "requiresCell" == "", "hasMonetaryCost" == "", "supportsAltitude" == "", "supportsSpeed" == "", "supportsBearing" == "",

                android.location.Criteria.POWER_LOW, android.location.Criteria.ACCURACY_FINE);

        Location newLocation = new Location(LocationManager.GPS_PROVIDER);

        newLocation.setLongitude(longitude);
        newLocation.setLatitude(latitude);
        newLocation.setTime(new Date().getTime());

        newLocation.setAccuracy(500);

        locationManager.setTestProviderEnabled(LocationManager.GPS_PROVIDER, true);

        locationManager.setTestProviderStatus(LocationManager.GPS_PROVIDER, LocationProvider.AVAILABLE, null, System.currentTimeMillis());

        // http://jgrasstechtips.blogspot.it/2012/12/android-incomplete-location-object.html
        makeLocationObjectComplete(newLocation);

        locationManager.setTestProviderLocation(LocationManager.GPS_PROVIDER, newLocation);
    }

    private static void makeLocationObjectComplete(Location newLocation) {
        Method locationJellyBeanFixMethod = null;
        try {
            locationJellyBeanFixMethod = Location.class.getMethod("makeComplete");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        if (locationJellyBeanFixMethod != null) {
            try {
                locationJellyBeanFixMethod.invoke(newLocation);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }





    /**
     * Resizes an image to the given width and height parameters Prefer using
     *
     * @param sourceBitmap Bitmap to be resized
     * @param newWidth     Width of resized bitmap
     * @param newHeight    Height of the resized bitmap
     */
    public static Bitmap resizeImage(Bitmap sourceBitmap, int newWidth, int newHeight, boolean filter) {
        // TODO: move this method to ImageUtils
        if (sourceBitmap == null) {
            throw new NullPointerException("Bitmap to be resized cannot be null");
        }

        Bitmap resized = null;

        if (sourceBitmap.getWidth() < sourceBitmap.getHeight()) {
            // image is portrait
            resized = Bitmap.createScaledBitmap(sourceBitmap, newHeight, newWidth, true);
        } else {
            // image is landscape
            resized = Bitmap.createScaledBitmap(sourceBitmap, newWidth, newHeight, true);
        }

        resized = Bitmap.createScaledBitmap(sourceBitmap, newWidth, newHeight, true);

        return resized;
    }

    /**
     * <br/>
     * <br/>
     *
     * @param compressionFactor Powers of 2 are often faster/easier for the decoder to honor
     */
    public static Bitmap compressImage(Bitmap sourceBitmap, int compressionFactor) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inPreferredConfig = Config.ARGB_8888;
        opts.inSampleSize = compressionFactor;

        if (Build.VERSION.SDK_INT >= 10) {
            opts.inPreferQualityOverSpeed = true;
        }

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        sourceBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        Bitmap image = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length, opts);

        return image;
    }

    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            // Calculate ratios of height and width to requested height and
            // width
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            // Choose the smallest ratio as inSampleSize value, this will
            // guarantee
            // a final image with both dimensions larger than or equal to the
            // requested height and width.
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }

        return inSampleSize;
    }

    /**
     * Checks if the url is valid
     */
    public static boolean isValidURL(String url) {
        URL urlObj;

//        try {
//            urlObj = new URL(url);
//        } catch (MalformedURLException e) {
//            return false;
//        }
//
//        try {
//            urlObj.toURI();
//        } catch (URISyntaxException e) {
//            return false;
//        }

        if (Patterns.WEB_URL.matcher(url).matches())
            return true;
        else
            return false;
    }




    /**
     * Gets the Uri without the fragment. For e.g if the uri is
     * content://com.android.storage/data/images/48829#is_png the part after '#' is called as
     * fragment. This method strips the fragment and returns the url.
     */
    public static String removeUriFragment(String url) {

        if (url == null || url.length() == 0) {
            return null;
        }

        String[] arr = url.split("#");

        if (arr.length == 2) {
            return arr[0];
        } else {
            return url;
        }
    }

    /**
     * Removes the parameters from the query from the uri
     */
    public static String removeQueryParameters(Uri uri) {
        assert (uri.getAuthority() != null);
        assert (uri.getPath() != null);
        Uri.Builder builder = new Uri.Builder();
        builder.scheme(uri.getScheme());
        builder.encodedAuthority(uri.getAuthority());
        builder.encodedPath(uri.getPath());
        return builder.build().toString();
    }

    /**
     * Returns true if the mime type is a standard image mime type
     */
    public static boolean isImage(String mimeType) {
        if (mimeType != null) {
            if (mimeType.startsWith("image/")) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * Returns true if the mime type is a standard audio mime type
     */
    public static boolean isAudio(String mimeType) {
        if (mimeType != null) {
            if (mimeType.startsWith("audio/")) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }





    public static long getMediaSize(Context context, Uri mediaUri) {
        Cursor cur = context.getContentResolver().query(mediaUri, new String[]{Media.SIZE}, null, null, null);
        long size = -1;

        try {
            if (cur != null && cur.getCount() > 0) {
                while (cur.moveToNext()) {
                    size = cur.getLong(cur.getColumnIndex(Media.SIZE));

                    // for unknown reason, the image size for image was found to
                    // be 0
                    // Log.v( TAG, "#getSize byte.size: " + size );

                    if (size == 0)
                        Log.w(TAG, "#getSize The media size was found to be 0. Reason: UNKNOWN");

                } // end while
            } else if (cur.getCount() == 0) {
                Log.e(TAG, "#getMediaSize cur size is 0. File may not exist");
            } else {
                Log.e(TAG, "#getMediaSize cur is null");
            }
        } finally {
            if (cur != null && !cur.isClosed()) {
                cur.close();
            }
        }

        return size;
    }

    /**
     * @deprecated {@link Context , Uri)}
     * Gets media file name.
     **/
    public static String getMediaFileName(Context ctx, Uri mediaUri) {
        String colName = MediaColumns.DISPLAY_NAME;
        Cursor cur = ctx.getContentResolver().query(mediaUri, new String[]{colName}, null, null, null);
        String dispName = null;

        try {
            if (cur != null && cur.getCount() > 0) {
                while (cur.moveToNext()) {
                    dispName = cur.getString(cur.getColumnIndex(colName));

                    // for unknown reason, the image size for image was found to
                    // be 0
                    // Log.v( TAG, "#getMediaFileName byte.size: " + size );

                    if (TextUtils.isEmpty(colName)) {
                        Log.w(TAG, "#getMediaFileName The file name is blank or null. Reason: UNKNOWN");
                    }

                } // end while
            } else if (cur != null && cur.getCount() == 0) {
                Log.e(TAG, "#getMediaFileName File may not exist");
            } else {
                Log.e(TAG, "#getMediaFileName cur is null");
            }
        } finally {
            if (cur != null && !cur.isClosed()) {
                cur.close();
            }
        }

        return dispName;
    }

    @Nullable
    /**
     * @deprecated Use {@link MediaUtils#getMediaType(Uri)}
     * Gets media type from the Uri.
     */
    public static String getMediaType(Uri uri) {
        if (uri == null) {
            return null;
        }

        String uriStr = uri.toString();

        if (uriStr.contains("video")) {
            return "video";
        } else if (uriStr.contains("audio")) {
            return "audio";
        } else if (uriStr.contains("image")) {
            return "image";
        } else {
            return null;
        }
    }



    /**
     * Typefaces the string as bold.
     * If sub-string is null, entire string will be typefaced as bold and returned.
     *
     * @param string
     * @param subString The subString within the string to bold. Pass null to bold entire string.
     * @return {@link android.text.SpannableString}
     */
    public static SpannableStringBuilder toBold(String string, String subString) {
        if (TextUtils.isEmpty(string)) {
            return new SpannableStringBuilder("");
        }

        SpannableStringBuilder spannableBuilder = new SpannableStringBuilder(string);

        StyleSpan bss = new StyleSpan(Typeface.BOLD);
        if (subString != null) {
            int substringNameStart = string.toLowerCase().indexOf(subString);
            if (substringNameStart > -1) {
                spannableBuilder.setSpan(bss, substringNameStart, substringNameStart + subString.length(),
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        } else {
            // set entire text to bold
            spannableBuilder.setSpan(bss, 0, spannableBuilder.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        }
        return spannableBuilder;
    }

    /**
     * Formats given size in bytes to KB, MB, GB or whatever. This will work up to 1000 TB
     */
    public static String formatSize(long size) {

        if (size <= 0) return "0";

        final String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }

    /**
     * Formats given size in bytes to KB, MB, GB or whatever. Preferably use this method for
     * performance efficiency.
     *
     * @param si Controls byte value precision. If true, formatting is done using approx. 1000 Uses
     *           1024 if false.
     **/
    public static String formatSize(long bytes, boolean si) {
        int unit = si ? 1000 : 1024;

        if (bytes < unit) {
            return bytes + " B";
        }

        int exp = (int) (Math.log(bytes) / Math.log(unit));
        String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp - 1) + (si ? "" : "i");
        return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
    }

    /**
     * Creates the uri to a file located on external storage or application internal storage.
     */
    public static Uri createUri(Context ctx) {
        File root = getStorageDirectory(ctx, null);
        root.mkdirs();
        File file = new File(root, Long.toString(new Date().getTime()));
        Uri uri = Uri.fromFile(file);

        return uri;
    }

    /**
     * @param ctx
     * @param savingUri
     * @param durationInSeconds
     * @return Creates an intent to take a video from camera or gallery or any other application that can
     * handle the intent.
     */
    public static Intent createTakeVideoIntent(Activity ctx, Uri savingUri, int durationInSeconds) {

        if (savingUri == null) {
            throw new NullPointerException("Uri cannot be null");
        }

        final List<Intent> cameraIntents = new ArrayList<Intent>();
        final Intent captureIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        final PackageManager packageManager = ctx.getPackageManager();
        final List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
        for (ResolveInfo res : listCam) {
            final String packageName = res.activityInfo.packageName;
            final Intent intent = new Intent(captureIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(packageName);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, savingUri);
            intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, durationInSeconds);
            cameraIntents.add(intent);
        }

        // Filesystem.
        final Intent galleryIntent = new Intent();
        galleryIntent.setType("video/*");
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

        // Chooser of filesystem options.
        final Intent chooserIntent = Intent.createChooser(galleryIntent, "Select Source");

        // Add the camera options.
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, cameraIntents.toArray(new Parcelable[]{}));

        return chooserIntent;
    }

    /**
     * @param savingUri Uri to store a high resolution image at. If the user takes the picture using the
     *                  camera the image will be stored at this uri.
     *                  Creates a ACTION_IMAGE_CAPTURE photo & ACTION_GET_CONTENT intent. This intent will be
     *                  aggregation of intents required to take picture from Gallery and Camera at the minimum. The
     *                  intent will also be directed towards the apps that are capable of sourcing the image data.
     *                  For e.g. Dropbox, Astro file manager.
     **/
    public static Intent createTakePictureIntent(Activity ctx, Uri savingUri) {

        if (savingUri == null) {
            throw new NullPointerException("Uri cannot be null");
        }

        final List<Intent> cameraIntents = new ArrayList<Intent>();
        final Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        final PackageManager packageManager = ctx.getPackageManager();
        final List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
        for (ResolveInfo res : listCam) {
            final String packageName = res.activityInfo.packageName;
            final Intent intent = new Intent(captureIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(packageName);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, savingUri);
            cameraIntents.add(intent);
        }

        // Filesystem.
        final Intent galleryIntent = new Intent();
        galleryIntent.setType("image/*");
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

        // Chooser of filesystem options.
        final Intent chooserIntent = Intent.createChooser(galleryIntent, "Select Source");

        // Add the camera options.
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, cameraIntents.toArray(new Parcelable[]{}));

        return chooserIntent;
    }

    @Nullable
    /**
     * @deprecated Use {@link MediaUtils#createImageUri(Context)}
     * Creates external content:// scheme uri to save the images at. The image saved at this
     * {@link Uri} will be visible via the gallery application on the device.
     */
    public static Uri createImageUri(Context ctx) throws IOException {

        if (ctx == null) {
            throw new NullPointerException("Context cannot be null");
        }

        Uri imageUri = null;

        ContentValues values = new ContentValues();
        values.put(MediaColumns.TITLE, "");
        values.put(ImageColumns.DESCRIPTION, "");
        imageUri = ctx.getContentResolver().insert(Media.EXTERNAL_CONTENT_URI, values);

        return imageUri;
    }

    @Nullable
    /**
     * @deprecated Use {@link MediaUtils#createVideoUri(Context)}
     * Creates external content:// scheme uri to save the videos at.
     */
    public static Uri createVideoUri(Context ctx) throws IOException {

        if (ctx == null) {
            throw new NullPointerException("Context cannot be null");
        }

        Uri imageUri;

        ContentValues values = new ContentValues();
        values.put(MediaColumns.TITLE, "");
        values.put(ImageColumns.DESCRIPTION, "");
        imageUri = ctx.getContentResolver().insert(Video.Media.EXTERNAL_CONTENT_URI, values);

        return imageUri;
    }

    @Nullable
    /**
     *
     * @deprecated Use {#setTextValues} or {#getNullEmptyCheckedValue}
     * Get the correctly appended name from the given name parameters
     *
     * @param firstName
     *            First name
     * @param lastName
     *            Last name
     *
     * @return Returns correctly formatted full name. Returns null if both the values are null.
     **/
    public static String getName(String firstName, String lastName) {
        if (!TextUtils.isEmpty(firstName) && !TextUtils.isEmpty(lastName)) {
            return firstName + " " + lastName;
        } else if (!TextUtils.isEmpty(firstName)) {
            return firstName;
        } else if (!TextUtils.isEmpty(lastName)) {
            return lastName;
        } else {
            return null;
        }
    }

    public static Bitmap roundBitmap(Bitmap bmp, int radius) {
        Bitmap sbmp;
        if (bmp.getWidth() != radius || bmp.getHeight() != radius) {
            sbmp = Bitmap.createScaledBitmap(bmp, radius, radius, false);
        } else {
            sbmp = bmp;
        }

        Bitmap output = Bitmap.createBitmap(sbmp.getWidth(), sbmp.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, sbmp.getWidth(), sbmp.getHeight());

        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(Color.parseColor("#BAB399"));
        canvas.drawCircle(sbmp.getWidth() / 2 + 0.7f, sbmp.getHeight() / 2 + 0.7f, sbmp.getWidth() / 2 + 0.1f, paint);
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(sbmp, rect, rect, paint);

        return output;
    }

    /**
     * Checks if given url is a relative path.
     *
     * @param url
     * @return false if parameter url is null or false
     */
    public static final boolean isRelativeUrl(String url) {

        if (TextUtils.isEmpty(url)) {
            return false;
        }

        Uri uri = Uri.parse(url);

        return uri.getScheme() == null;
    }

    /**
     * Checks if the parameter {@link Uri} is a content uri.
     **/
    public static boolean isContentUri(Uri uri) {
        if (!uri.toString().contains("content://")) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Hides the already popped up keyboard from the screen.
     *
     * @param context
     */
    public static void hideKeyboard(Activity context) {
        try {
            ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow((context.getWindow().getDecorView().getApplicationWindowToken()), 0);
        } catch (Exception e) {
            Log.e(TAG, "Sigh, cant even hide keyboard " + e.getMessage());
        }
    }

    /**
     * Checks if the build version passed as the parameter is
     * lower than the current build version.
     *
     * @param buildVersion One of the values from {@link Build.VERSION_CODES}
     * @return
     */
    public static boolean isBuildBelow(int buildVersion) {
        if (Build.VERSION.SDK_INT < buildVersion) return true;
        else return false;
    }

    /**
     * Sets the two parameter values to the parameter {@link TextView}
     * in null-safe and empty-safe way. Such method can be used when setting firstname-lastname
     * to a textview in the UI.
     *
     * @param textView
     * @param firstValue  String "null" will be treated as null value.
     * @param secondValue String "null" will be treated as null value.
     */
    public static void setTextValues(@NonNull TextView textView, @Nullable String firstValue, @Nullable String secondValue) {
        String nullEmptyCheckedVal = getNullEmptyCheckedValue(firstValue, secondValue, null);
        textView.setText(nullEmptyCheckedVal);
    }

    @Nullable
    /**
     * Returns concatenated values of atleast to or more strings provided to it
     * in a null safe manner.
     * @param firstValue
     * @param secondValue
     * @param delimiter Delimiter to be used to concatnate the parameter strings. If null, space characer will be used.
     * @param moreValues Optional
     * @return
     */
    public static String getNullEmptyCheckedValue(@Nullable String firstValue, @Nullable String secondValue,
                                                  @Nullable String delimiter, String... moreValues) {
        if (TextUtils.isEmpty(delimiter)) {
            delimiter = " ";
        }

        StringBuilder builder = new StringBuilder();
        if (!TextUtils.isEmpty(firstValue) && !firstValue.equalsIgnoreCase("null")
                && secondValue != null && !secondValue.equalsIgnoreCase("null")) {
            builder.append(firstValue);
            builder.append(delimiter);
            builder.append(secondValue);
        } else if (!TextUtils.isEmpty(firstValue) && !firstValue.equalsIgnoreCase("null")) {
            builder.append(firstValue);
        } else if (!TextUtils.isEmpty(secondValue) && !secondValue.equalsIgnoreCase("null")) {
            builder.append(secondValue);
        }

        if (moreValues != null) {
            for (String value : moreValues) {
                if (!TextUtils.isEmpty(value) && !value.equalsIgnoreCase("null")) {
                    builder.append(delimiter);
                    builder.append(value);
                }
            }
        }

        return builder.toString();
    }

    @Nullable
    /**
     * Partially capitalizes the string from paramter start and offset.
     *
     * @param string String to be formatted
     * @param start  Starting position of the substring to be capitalized
     * @param offset Offset of characters to be capitalized
     * @return
     */
    public static String capitalizeString(String string, int start, int offset) {
        if (TextUtils.isEmpty(string)) {
            return null;
        }
        String formattedString = string.substring(start, offset).toUpperCase() + string.substring(offset, string.length());
        return formattedString;
    }

    @Nullable
    /**
     * Generates SHA-512 hash for given binary data.
     * @param stringToHash
     * @return
     */
    public static String getSha512Hash(String stringToHash) {
        if (stringToHash == null) {
            return null;
        } else {
            return getSha512Hash(stringToHash.getBytes());
        }
    }

    @Nullable
    /**
     * Generates SHA-512 hash for given binary data.
     */
    public static String getSha512Hash(byte[] dataToHash) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        if (md != null) {
            md.update(dataToHash);
            byte byteData[] = md.digest();
            String base64 = Base64.encodeToString(byteData, Base64.DEFAULT);

            return base64;
        }
        return null;
    }

    @Nullable
    /**
     * Gets the extension of a file.
     */
    public static String getExtension(File file) {
        String ext = null;
        String s = file.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 && i < s.length() - 1) {
            ext = s.substring(i + 1).toLowerCase();
        }

        return ext;
    }

    /**
     * This function is used to show a progress dialog
     *
     * @param -context of the activity
     */
    public static void showDialog(Context mContext, String strMessage) {
        try {
            if (pgdialog != null)
                if (pgdialog.isShowing())
                    pgdialog.dismiss();
            pgdialog = ProgressDialog.show(mContext, null, null, true);
            pgdialog.setContentView(R.layout.progress_dialog_view);

            pgdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            pgdialog.show();

//            pgdiog = new ProgressDialog(mContext, R.style.MyTheme).show(mContext, "", strMessage, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This function is used to dismiss the dialog
     */
    public static void dismissDialog() {
        try {
            if (pgdialog != null && pgdialog.isShowing())
                pgdialog.dismiss();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    /**
     * Default data
     *
     * @param time
     * @return
     */
    public static String defaultDateFormat(String time) {
        String inputPattern = "yyyy-MM-dd";
        String outputPattern = "dd MMM yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        inputFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
        outputFormat.setTimeZone(TimeZone.getDefault());
        Date date = null;
        String str = null;
        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        if (str.contains(".")) {
            str.replace(".", "");
        }
        return str;
    }

    /***
     * Getting Present day Name
     * @param input
     * @return
     */
    public static String getDayName(String input) {
        SimpleDateFormat inFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date date = null;
        try {
            date = inFormat.parse(input);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat outFormat = new SimpleDateFormat("EEEE", Locale.ENGLISH);
        String goal = outFormat.format(date);
        return goal;
    }

    /***
     * Get present date
     * @return
     */
    public static String getPresentDateYYYY_MM_DD() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        String test = sdf.format(cal.getTime());
        return test;
    }

    public static long convertToMillis(String givenDateString) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long timeInMilliseconds = 0;
        try {
            Date mDate = sdf.parse(givenDateString);
            timeInMilliseconds = mDate.getTime();
            System.out.println("Date in milli :: " + timeInMilliseconds);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        return timeInMilliseconds;
    }

    public static String twoDecimalPlace(String data) {
        return String.format("%.2f", data);
    }

    public static double distance(double lat1, double lon1, double lat2, double lon2, String unit) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        if (unit == "K") {
            dist = dist * 1.609344;
        } else if (unit == "N") {
            dist = dist * 0.8684;
        }

        return (dist);
    }

    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    /*::	This function converts decimal degrees to radians						 :*/
    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    /*::	This function converts radians to decimal degrees						 :*/
    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }

    public static String get12HourTimeFormat(String time) {
        String date12 = null;
        try {
            final SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
            final Date dateObj = sdf.parse(time);
            date12 = new SimpleDateFormat("K:mm").format(dateObj);
            System.out.println(dateObj);
            System.out.println(new SimpleDateFormat("K:mm").format(dateObj));
        } catch (final ParseException e) {
            e.printStackTrace();
            return date12;
        } catch (java.text.ParseException e) {
            e.printStackTrace();
            return date12;
        }
        return date12;
    }

    public static Double removeTrailingDigits(Double data) {
        DecimalFormat df = new DecimalFormat(".#");
        return Double.valueOf(String.format(Locale.ENGLISH, df.format(data)));

    }


//    public static Float removeTrailingDigit(Float data, Context context) {
//        if (AppSharedPreference.getInstance().getString(context, AppSharedPreference.LANGUAGE_SELECTED).equalsIgnoreCase(AppConstant.ENG_LANG)) {
//            DecimalFormat df = new DecimalFormat(".#");
//            return Float.valueOf(String.format(Locale.ENGLISH, df.format(data)));
//        } else {
//            return data;
//        }
//    }

    public static String updateTime(String str) {
        String time1[] = str.split(":");
        int hours = Integer.parseInt(time1[0]);
        int mins = Integer.parseInt(time1[1]);
        String timeSet = "";
        if (hours > 12) {
            hours -= 12;
            timeSet = "PM";
        } else if (hours == 0) {
            hours += 12;
            timeSet = "AM";
        } else if (hours == 12)
            timeSet = "PM";
        else
            timeSet = "AM";

        String minutes = "";
        if (mins < 10)
            minutes = "0" + mins;
        else
            minutes = String.valueOf(mins);
        String time = "";
        if (String.valueOf(hours).length() == 1) {
            time = new StringBuilder().append("0" + hours).append(':')
                    .append(minutes).append(" ").append(timeSet).toString();

        } else if (String.valueOf(minutes).length() == 1) {
            time = new StringBuilder().append(hours).append(':')
                    .append("0" + minutes).append(" ").append(timeSet).toString();

        } else {
            time = new StringBuilder().append(hours).append(':')
                    .append(minutes).append(" ").append(timeSet).toString();
        }


        return String.format(time, Locale.ENGLISH);
    }



    /*
     * method Will round off the number to specific digits
     *
     */
//    public static Double roundOffTheMeters(Double number, int roundOffDigits) {
//        double result = 0;
//        if (number <= 50) {
//            result = 0;
//        } else {
//            DecimalFormat decimalFormat = new DecimalFormat("#.##");
//            Double twoDigitsF = Double.valueOf((decimalFormat.format(number)));
//            int intNumber = (int) (twoDigitsF * 100);
//
//
//            int a = (intNumber / roundOffDigits) * roundOffDigits;
//
//            int b = a + roundOffDigits;
//            result = (intNumber - a > b - intNumber) ? b : a;
//            result = result / 100;
//            return result;
//
//        }
//        Log.e("number", String.valueOf(result));
//        return result;
//    }

    public static TextView changeFontDynamicallyTextView(String fontName, TextView view, Activity mActivity) {
        Typeface custom_font = Typeface.createFromAsset(mActivity.getAssets(), fontName);
        view.setTypeface(custom_font);
        return view;
    }

    public static String roundOffTheMeters(Double number, int roundOffDigits) {
        String finalDistance = "";
        double result = 0;
        if (number <= 50) {
            result = 0;
            finalDistance = String.valueOf(result);
        } else if (number < 1000) {
            result = convertNumber(number, roundOffDigits);
            finalDistance = result + " ms";
        } else if (number >= 1000) {
            result = meterTokm(number, roundOffDigits);
            finalDistance = result + " kms";
        }
        if (finalDistance.equals("1000.0 ms")) {
            finalDistance = "1.0 kms";
        }
//        print.setText(finalDistance);
        return finalDistance;
    }

    private static Double meterTokm(Double number, int roundOffDigits) {

        NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
        DecimalFormat formatter = (DecimalFormat) nf;
        formatter.applyPattern("0.0");
        Double twoDigitsF = Double.valueOf((formatter.format(number / 1000)));
        int intNumber = (int) (twoDigitsF * 10);


        int a = (intNumber / roundOffDigits) * roundOffDigits;

        int b = a + roundOffDigits;
        Double result = (double) ((intNumber - a > b - intNumber) ? b : a);
        result = result / 10;
        return result;
    }

    private static double convertNumber(Double number, int roundOffDigits) {
        DecimalFormat decimalFormat = new DecimalFormat("#.0");
        decimalFormat.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.ENGLISH));
        Double twoDigitsF = Double.valueOf((decimalFormat.format(number)));
        int intNumber = (int) (twoDigitsF * 10);

        int a = (intNumber / roundOffDigits) * roundOffDigits;

        int b = a + roundOffDigits;
        Double result = (double) ((intNumber - a > b - intNumber) ? b : a);
        result = result / 10;
        return result;
    }

    public static String getRandomTransactionNumber(final int sizeOfRandomString) {
        final String ALLOWED_CHARACTERS = "0123456789qwertyuiopasdfghjklzxcvbnm";
        final Random random = new Random();
        final StringBuilder sb = new StringBuilder(sizeOfRandomString);
        for (int i = 0; i < sizeOfRandomString; ++i)
            sb.append(ALLOWED_CHARACTERS.charAt(random.nextInt(ALLOWED_CHARACTERS.length())));
        return sb.toString();
    }

    public static String getNameofDay(String strDate) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            Date date = format.parse(strDate);
//            DateFormat weekDay = new SimpleDateFormat("E");
            DateFormat monthFormate = new SimpleDateFormat("MMMM", Locale.ENGLISH);
            DateFormat dateNumberice = new SimpleDateFormat("dd", Locale.ENGLISH);
            DateFormat yearFormate = new SimpleDateFormat("y", Locale.ENGLISH);
//            String day = weekDay.format(date);
            String month = monthFormate.format(date);
            String date1 = dateNumberice.format(date);
            String year = yearFormate.format(date);
            return month + " " + date1 + "," + year;
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        return "";
    }


    public static boolean isLocationEnabled(Context context) {
        int locationMode = 0;
        String locationProviders;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);

            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
                return false;
            }

            return locationMode != Settings.Secure.LOCATION_MODE_OFF;

        } else {
            locationProviders = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locationProviders);
        }


    }

    //    /*Convert the Date into May 01 this formate*/
    public static String getDateInMonthDay(String strDate) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            Date date = format.parse(strDate);
//            DateFormat weekDay = new SimpleDateFormat("E");
            DateFormat monthFormate = new SimpleDateFormat("MMMM", Locale.ENGLISH);
            DateFormat dateNumberice = new SimpleDateFormat("dd", Locale.ENGLISH);
            DateFormat yearFormate = new SimpleDateFormat("y");
//            String day = weekDay.format(date);
            String month = monthFormate.format(date);
            String date1 = dateNumberice.format(date);
            String year = yearFormate.format(date);
            return month + " " + date1;
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        return "";
    }




    public static String convertDate(String time) {
        time = getDateTimeFromUTC(time);
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss aa", Locale.ENGLISH);
        Date d = null;
        try {
            d = sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        long timeStamp = d.getTime();

        String relavetime = "" + DateUtils.getRelativeTimeSpanString(timeStamp, Calendar.getInstance().getTimeInMillis(), DateUtils.FORMAT_ABBREV_ALL);
        return relavetime;
    }

    public static String getDateTimeFromUTC(String utcdate) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        inputFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date d = convertStringToDate(utcdate, inputFormat);
        DateFormat outputFormat = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss aa", Locale.ENGLISH);
        outputFormat.setTimeZone(TimeZone.getDefault());

        String s = convertDatetoString(d, outputFormat);

        return s;
    }

    public static String getTimeFromUTC(String utcdate) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        inputFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date d = convertStringToDate(utcdate, inputFormat);
        DateFormat outputFormat = new SimpleDateFormat("hh:mm aa", Locale.ENGLISH);
        outputFormat.setTimeZone(TimeZone.getDefault());

        String s = convertDatetoString(d, outputFormat);

        return s;
    }

    public static String convertDatetoString(Date date, DateFormat format) {
        String dateString = "";
        if (date != null) {
            dateString = format.format(date);
        }
        return dateString;
    }

    public static Date convertStringToDate(String dateString, SimpleDateFormat format) {
        Date mdate = null;
        try {
            mdate = (Date) format.parse(dateString);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        return mdate;
    }


    public static void launchMarket(Context context) {
        Uri uri = Uri.parse("market://details?id=" + context.getPackageName());
        Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
        try {
            context.startActivity(myAppLinkToMarket);
        } catch (ActivityNotFoundException e) {
        }
    }

    /**
     * Provide the height to which the sourceImage is to be resized. This method will calculate the
     * resultant height. Use scaleDownBitmap from {@link } wherever possible
     */
    public Bitmap resizeImageByHeight(int height, Bitmap sourceImage) {
        // TODO: move this method to ImageUtils
        int widthO = 0; // original width
        int heightO = 0; // original height
        int widthNew = 0;
        int heightNew = 0;

        widthO = sourceImage.getWidth();
        heightO = sourceImage.getHeight();
        heightNew = height;

        // Maintain the aspect ratio
        // of the original banner image.
        widthNew = (heightNew * widthO) / heightO;

        return Bitmap.createScaledBitmap(sourceImage, widthNew, heightNew, true);
    }
}