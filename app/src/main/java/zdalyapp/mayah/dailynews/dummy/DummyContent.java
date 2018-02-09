package zdalyapp.mayah.dailynews.dummy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContent {

    /**
     * An array of sample (dummy) items.
     */

    public static class DailyNewsItem {
        public  String id = "";
        public  String content= "";
        public  String details= "";
        public  String url = "";
        public  String strDate = "";
        public  boolean isSelected;

        public DailyNewsItem(String id, String content, String details, String url) {
            this.id = id;
            this.content = content;
            this.details = details;

            this.url = url;
        }
        public DailyNewsItem(JSONObject jsonObject) {
            try {
                this.id = jsonObject.getString("TITLE");
                this.content = jsonObject.getString("DES");
                this.details = jsonObject.getString("SOURCE");
                this.url = jsonObject.getString("LINK");
                this.strDate = jsonObject.getString("DATE");
                isSelected = false;

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        @Override
        public String toString() {
            return content;
        }
    }
}
