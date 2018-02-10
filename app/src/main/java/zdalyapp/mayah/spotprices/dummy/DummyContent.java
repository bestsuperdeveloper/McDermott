package zdalyapp.mayah.spotprices.dummy;

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
    public static final List<SpotPriceItem> ITEMS = new ArrayList<SpotPriceItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, SpotPriceItem> ITEM_MAP = new HashMap<String, SpotPriceItem>();

    private static final int COUNT = 25;




    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class SpotPriceItem {
        public  String id = "";
        public  String content = "";
        public  String details1 = "";
        public  String details2 = "";
        public  String details3 = "";
        public  String details4 = "";

        public SpotPriceItem(String id, String content, String details) {
            this.id = id;
            this.content = content;
            this.details1 = details;
        }
        public SpotPriceItem(JSONObject jsonObject) {
            try {
                String type = jsonObject.getString("type");
                if (type.equals("0"))
                {
                    this.id = jsonObject.getString("Name");
                    this.content = "";
                    this.details1 = jsonObject.getString("LastTradePriceOnly");
                    this.details2 = jsonObject.getString("Change");
                    this.details3 = jsonObject.getString("DaysHigh");
                    this.details4 = jsonObject.getString("DaysLow");
                }
                else
                {
                    this.id = jsonObject.getString("Name");
                    this.content = jsonObject.getString("Rate");
                }

            } catch (JSONException e) {


            }

        }
        @Override
        public String toString() {
            return content;
        }
    }
}
