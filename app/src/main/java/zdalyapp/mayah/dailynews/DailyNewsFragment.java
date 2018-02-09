package zdalyapp.mayah.dailynews;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import zdalyapp.mayah.McDomatsApp;
import zdalyapp.mayah.R;
import zdalyapp.mayah.dailynews.dummy.DummyContent;
import zdalyapp.mayah.dailynews.dummy.DummyContent.DailyNewsItem;
import zdalyapp.mayah.global.Constants;
import zdalyapp.mayah.global.Utils;
import zdalyapp.mayah.login.LoginActivity;

import java.util.ArrayList;
import java.util.List;


public class DailyNewsFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListDailyNewsInteractionListener mListener;
    private Dialog loginDialog;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public DailyNewsFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static DailyNewsFragment newInstance(int columnCount) {
        DailyNewsFragment fragment = new DailyNewsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    ArrayList<DailyNewsItem> data;
    RecyclerView recyclerView;
    SearchView searchView;
    View mainView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.fragment_dailynews_list, container, false);
        searchView = (SearchView) mainView.findViewById(R.id.searchView);
        recyclerView = (RecyclerView) mainView.findViewById(R.id.list);
        data = new ArrayList<>();

        // Set the adapter
        if (recyclerView instanceof RecyclerView) {
            Context context = mainView.getContext();
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
        }
        int searchFrameId = searchView.getContext().getResources().getIdentifier("android:id/search_edit_frame", null, null);
        View searchFrame = searchView.findViewById(searchFrameId);
        searchFrame.setBackgroundResource(R.drawable.bg_white_rounded);

        int searchPlateId = searchView.getContext().getResources().getIdentifier("android:id/search_plate", null, null);
        View searchPlate = mainView.findViewById(searchPlateId);
        searchPlate.setBackgroundResource(R.drawable.bg_white_rounded);

        int searchBarId = searchView.getContext().getResources().getIdentifier("android:id/search_bar", null, null);
        View searchBar = mainView.findViewById(searchBarId);
        searchBar.setBackgroundResource(R.drawable.bg_white_rounded);

        int searchCloseButtonId = searchView.getContext().getResources()
                .getIdentifier("android:id/search_close_btn", null, null);
        ImageView closeButton = (ImageView) this.searchView.findViewById(searchCloseButtonId);
// Set on click listener
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Manage this event.

                GetData();
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                GetDataBySearch(query);
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        mainView.findViewById(R.id.imageButton4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int len = data.size();
                ArrayList<DailyNewsItem> tempArray = new ArrayList<>();
                for (int i = 0; i < len; i++)
                {
                    if (data.get(i).isSelected)
                        tempArray.add(data.get(i)) ;
                }
                int size = tempArray.size();
                if (size == 0)
                {
                    ShowAlertDialog();
                }
                else
                {
                    String strHtml = "<!doctype html><html><body><br>";
                    for (int i = 0; i < size; i++) {
                        String titleString = String.format("<div style='font-size: 12px; color: #3d86c7;'><a href='%s'>%s</a></div>", tempArray.get(i).url, tempArray.get(i).id);
                        String sourceString = String.format("<div style='font-weight: bold;'>%s</div>", tempArray.get(i).details);
                        String descString = String.format("<div style='text-align: justify;'>%s</div>", tempArray.get(i).content);
                        String dateString = String.format("<div>%s</div>", tempArray.get(i).strDate);
                        String totalhtml = String.format("<div style='font-size:10px; font-family: sans-serif; color: #000;'>%s%s%s%s</div>",
                                titleString, sourceString, descString, dateString);
                        strHtml += totalhtml;
                    }
                    strHtml += "</body></html>";
                    Log.d("html", strHtml);
                    Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, strHtml);
                    startActivity(Intent.createChooser(shareIntent, "Share via"));

                }
            }

            private void ShowAlertDialog() {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(R.string.app_name);
                builder.setMessage(R.string.search_no_share_selected);
                builder.setPositiveButton(R.string.string_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();
            }
        });

        ((CheckBox) mainView.findViewById(R.id.checkBox)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                int len = data.size();
                for (int i = 0; i < len; i++)
                    data.get(i).isSelected = b;
                recyclerView.setAdapter(new MyDailyNewsRecyclerViewAdapter(data, mListener, true));
            }
        });
//        searchView.setQueryHint("Search new here...");
//        int searchPlateId = searchView.getContext().getResources().getIdentifier("android:id/search_plate", null, null);
//        View searchPlate = searchView.findViewById(searchPlateId);
//        if (searchPlate!=null) {
//            searchPlate.setBackgroundColor(Color.WHITE);
//            int searchTextId = searchPlate.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
//            TextView searchText = (TextView) searchPlate.findViewById(searchTextId);
//            if (searchText!=null) {
//                searchText.setTextColor(Color.BLACK);
//                searchText.setHintTextColor(Color.WHITE);
//            }
//        }
        GetData();
        return mainView;
    }

    private void GetDataBySearch(String search) {
        data = new ArrayList<>();
        String id = Utils.GetStringFromPreference(Constants.USERID, getActivity());
        String url = Constants.API_URL + Constants.DAILY_NEWS_URL + "?id=" + id + "&search=" + search;
        showDialog();

        JsonObjectRequest newsRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        // display response
                        Log.d("Response", response.toString());
                        try {
                            JSONArray jsonArray = response.getJSONArray("result");
                            int length = jsonArray.length();
                            for (int i = 0; i < length; i++)
                            {
                                DailyNewsItem item = new DailyNewsItem(jsonArray.getJSONObject(i));
                                data.add(item);
                            }
                            if (length == 0) {
                                mainView.findViewById(R.id.list).setVisibility(View.GONE);
                                mainView.findViewById(R.id.noresultlayout).setVisibility(View.VISIBLE);
                            }
                            else
                            {
                                mainView.findViewById(R.id.list).setVisibility(View.VISIBLE);
                                mainView.findViewById(R.id.noresultlayout).setVisibility(View.GONE);
                                if (recyclerView != null)
                                    recyclerView.setAdapter(new MyDailyNewsRecyclerViewAdapter(data, mListener, true));
                            }

                        } catch (JSONException e) {
                            mainView.findViewById(R.id.list).setVisibility(View.GONE);
                            mainView.findViewById(R.id.noresultlayout).setVisibility(View.VISIBLE);
                            e.printStackTrace();
                        }
                        mainView.findViewById(R.id.searchBottom).setVisibility(View.VISIBLE);
                        mainView.findViewById(R.id.allBottom).setVisibility(View.GONE);
                        hideDialog();
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        hideDialog();
                    }
                }
        );
        McDomatsApp.getInstance().addToRequestQueue(newsRequest, "newsRequest");
    }

    private void GetData() {
        String id = Utils.GetStringFromPreference(Constants.USERID, getActivity());
        String url = Constants.API_URL + Constants.DAILY_NEWS_URL + "?id=" + id;
        showDialog();
        mainView.findViewById(R.id.list).setVisibility(View.VISIBLE);
        mainView.findViewById(R.id.noresultlayout).setVisibility(View.GONE);
        JsonObjectRequest newsRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        // display response
                        Log.d("Response", response.toString());
                        hideDialog();
                        try {
                            JSONArray jsonArray = response.getJSONArray("result");
                            int length = jsonArray.length();
                            for (int i = 0; i < length; i++)
                            {
                                DailyNewsItem item = new DailyNewsItem(jsonArray.getJSONObject(i));
                                data.add(item);
                            }
                            if (recyclerView != null)
                                recyclerView.setAdapter(new MyDailyNewsRecyclerViewAdapter(data, mListener, false));
                            mainView.findViewById(R.id.searchBottom).setVisibility(View.GONE);
                            mainView.findViewById(R.id.allBottom).setVisibility(View.VISIBLE);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        hideDialog();
                    }
                }
        );
        McDomatsApp.getInstance().addToRequestQueue(newsRequest, "newsRequest");
    }

    private void showDialog() {
        if (loginDialog == null) {
            loginDialog = new Dialog(getActivity());
            loginDialog.setContentView(R.layout.activity_login_view);
            ImageView imageView = loginDialog.findViewById(R.id.imageView);
            TextView textView = loginDialog.findViewById(R.id.textView);
            imageView.setImageBitmap(Utils.GetImageFromAssets(getActivity(), "logo_small.png"));
            textView.setText("Latest News...");
        }
        loginDialog.show();
    }

    private void hideDialog() {
        if (loginDialog != null)
            loginDialog.dismiss();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListDailyNewsInteractionListener) {
            mListener = (OnListDailyNewsInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListDailyNewsInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(DummyContent.DailyNewsItem item);
        void onListShareInteraction(DummyContent.DailyNewsItem item);
    }
}
