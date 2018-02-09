package zdalyapp.mayah.global;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by mac on 2/6/18.
 */

public class Utils {
    public static Bitmap GetImageFromAssets(Context context, String fileName)
    {
        AssetManager assetManager = context.getAssets();
        try {
            InputStream is = assetManager.open(fileName);
            Bitmap  bitmap = BitmapFactory.decodeStream(is);
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void makeGraphColor(int size)
    {
        for (int i = 0; i < size; i++)
        {
            Random color = new Random();
            Constants.GraphColorList.add(Color.rgb(color.nextInt(256),color.nextInt(256),color.nextInt(256)));
        }
    }

    public static void drawTextRotate(String strText, float angle, float x, float y, int textSize, Canvas canvas)
    {

        Paint paint = new Paint();
        paint.setColor(Color.GRAY);
        paint.setTextSize(textSize);
        String rotatedtext = strText;

//Draw bounding rect before rotating text:

        Rect rect = new Rect();
        paint.getTextBounds(rotatedtext, 0, rotatedtext.length(), rect);
        canvas.translate(x, y);
        paint.setStyle(Paint.Style.FILL);

    //    canvas.drawText(rotatedtext , 0, 0, paint);
        paint.setStyle(Paint.Style.STROKE);
    //    canvas.drawRect(rect, paint);

        canvas.translate(-x, -y);


        canvas.rotate(angle, x + rect.exactCenterX(),y + rect.exactCenterY());
        paint.setStyle(Paint.Style.FILL);
        canvas.drawText(rotatedtext, x, y, paint);
    }
    public static int dpToPx(int dp, Context context) {
        float density = context.getResources()
                .getDisplayMetrics()
                .density;
        return Math.round((float) dp * density);
    }

    public static int pxToDp(int px) {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }
    public static boolean ValidEmail(String email)
    {
        String regExpn =
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                        +"((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        +"([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                        +"([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(regExpn,Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);

        if(matcher.matches())
            return true;
        else
            return false;
    }
    public static String randomString() {
        Random generator = new Random();
        StringBuilder randomStringBuilder = new StringBuilder();
        int randomLength = generator.nextInt(10);
        char tempChar;
        for (int i = 0; i < randomLength; i++){
            tempChar = (char) (generator.nextInt(96) + 32);
            randomStringBuilder.append(tempChar);
        }
        return randomStringBuilder.toString();
    }

    public static String GetStringFromPreference(String key, Context context)
    {
        SharedPreferences preferences = context.getSharedPreferences(Constants.PACKAGE_NAME, Context.MODE_PRIVATE);
        return preferences.getString(key, "");
    }
    public static void SetStringFromPreference(String key, String value, Context context)
    {
        SharedPreferences preferences = context.getSharedPreferences(Constants.PACKAGE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value).apply();
        editor.commit();
    }

    public static void deleteCache(Context context) {
        try {
            File dir = context.getCacheDir();
            deleteDir(dir);
            context.deleteDatabase("webview.db");
            context.deleteDatabase("webviewCache.db");
        } catch (Exception e) {}
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if(dir!= null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }
}
