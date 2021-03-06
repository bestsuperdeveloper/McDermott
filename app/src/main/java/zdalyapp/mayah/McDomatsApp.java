package zdalyapp.mayah;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import zdalyapp.mayah.global.LruBitmapCache;

/**
 * Created by mac on 2/7/18.
 */

public class McDomatsApp extends Application {

    public static final String TAG = McDomatsApp.class
            .getSimpleName();

    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    private static McDomatsApp mInstance;
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }
    public String MainAct;
    public JSONObject dataJsonObj;
    public String getMainAct() {
        return MainAct;
    }

    public void setMainAct(String mainAct) {
        MainAct = mainAct;
    }
    public JSONObject getDataJsonObj() {
        return dataJsonObj;
    }

    public void setDataJsonObj(JSONObject mainAct) {
        dataJsonObj = mainAct;
    }
    public static synchronized McDomatsApp getInstance() {
        return mInstance;
    }
    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public ImageLoader getImageLoader() {
        getRequestQueue();
        if (mImageLoader == null) {
            mImageLoader = new ImageLoader(this.mRequestQueue,
                    new LruBitmapCache());
        }
        return this.mImageLoader;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }


}
