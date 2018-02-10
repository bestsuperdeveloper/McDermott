package zdalyapp.mayah.spotprices;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import zdalyapp.mayah.McDomatsApp;
import zdalyapp.mayah.R;
import zdalyapp.mayah.global.Constants;
import zdalyapp.mayah.global.Utils;
import zdalyapp.mayah.login.LoginActivity;
import zdalyapp.mayah.spotprices.dummy.DummyContent;
import zdalyapp.mayah.spotprices.dummy.DummyContent.SpotPriceItem;

import java.util.ArrayList;
import java.util.List;


public class SpotPriceFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnSpotListFragmentInteractionListener mListener;
    private Dialog loginDialog;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public SpotPriceFragment() {
    }

    private void hideDialog() {
        if (loginDialog != null)
            loginDialog.dismiss();
    }
    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static SpotPriceFragment newInstance(int columnCount) {
        SpotPriceFragment fragment = new SpotPriceFragment();
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
    RecyclerView recyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_spotprice_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            //recyclerView.setAdapter(new MySpotPriceRecyclerViewAdapter(DummyContent.ITEMS, mListener));

        }
        return view;
    }
    private void showDialog() {
        if (loginDialog == null) {
            loginDialog = new Dialog(getActivity());
            loginDialog.setContentView(R.layout.activity_login_view);
            ImageView imageView = loginDialog.findViewById(R.id.imageView);
            TextView textView = loginDialog.findViewById(R.id.textView);
            textView.setText("Getting spot price...");
        }
        loginDialog.show();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSpotListFragmentInteractionListener) {
            mListener = (OnSpotListFragmentInteractionListener) context;
            new CountDownTimer(500, 100) {
                @Override
                public void onTick(long l) {

                }

                @Override
                public void onFinish() {
                    GetData();
                }
            }.start();
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    private void GetData() {
        String id = Utils.GetStringFromPreference(Constants.USERID, getActivity());
        String url = Constants.API_URL + Constants.SPOT_PRICES_URL + "?id=" + id;
        showDialog();
        Log.d("URL", url);
        JsonObjectRequest loginRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        // display response
                        Log.d("Response", response.toString());
                        hideDialog();
                        ParseDataFromJsonArray(response);

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", error.getLocalizedMessage());
                        hideDialog();

                    }
                }
        );
// Add the request to the RequestQueue.
        McDomatsApp.getInstance().addToRequestQueue(loginRequest, "spotRequest");

    }
    JSONArray dataArray;

    private void ParseDataFromJsonArray(JSONObject response) {
        dataArray = new JSONArray();
        try {
            JSONArray commodityArr = response.getJSONArray("commodity");
            JSONArray stockArr = response.getJSONArray("stock");
            JSONArray currencyArr = response.getJSONArray("currency");
            for (int i = 0; i < commodityArr.length(); i++)
            {
                JSONObject jsonObject = commodityArr.getJSONObject(i);
                jsonObject.put("type", "0");
                dataArray.put(jsonObject);
            }
            for (int i = 0; i < stockArr.length(); i++)
            {
                JSONObject jsonObject = stockArr.getJSONObject(i);
                jsonObject.put("type", "0");
                dataArray.put(jsonObject);
            }
            for (int i = 0; i < currencyArr.length(); i++)
            {
                JSONObject jsonObject = currencyArr.getJSONObject(i);
                jsonObject.put("type", "1");
                dataArray.put(jsonObject);
            }

            ArrayList<SpotPriceItem> dataList = new ArrayList<>();
            for (int i = 0; i < dataArray.length(); i++)
            {
                JSONObject jsonObject = dataArray.getJSONObject(i);
                dataList.add(new SpotPriceItem(jsonObject));
            }
            recyclerView.setAdapter(new MySpotPriceRecyclerViewAdapter(dataList, mListener));
        } catch (JSONException e) {
            e.printStackTrace();
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
    public interface OnSpotListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onSpotListFragmentInteraction(SpotPriceItem item);
    }
}
