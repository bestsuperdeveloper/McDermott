package zdalyapp.mayah.keytrends;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
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
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import zdalyapp.mayah.McDomatsApp;
import zdalyapp.mayah.R;
import zdalyapp.mayah.global.Constants;
import zdalyapp.mayah.global.Utils;
import zdalyapp.mayah.keytrends.industry.dummy.DummyContent;
import zdalyapp.mayah.keytrends.industry.IndustryFragment;
import zdalyapp.mayah.keytrends.production.ProductionFragment;


public class KeyTrendsFragment extends Fragment{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnTrendsInteractionListener mListener;
    private Dialog loginDialog;
    private List<DummyContent.IndustryItem> data;

    public KeyTrendsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment KeyTrendsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static KeyTrendsFragment newInstance(String param1, String param2) {
        KeyTrendsFragment fragment = new KeyTrendsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    View mainView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        mainView = inflater.inflate(R.layout.fragment_key_trends, container, false);
        ShowView();
        return mainView;
    }
    ViewPager viewPager;
    TabLayout tabLayout;
    private void ShowView() {
        viewPager = (ViewPager) mainView.findViewById(R.id.viewpager);

       tabLayout = (TabLayout) mainView.findViewById(R.id.tabs);

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onTrendInteraction(uri);
        }
    }
    private void GetData() {
        String id = Utils.GetStringFromPreference(Constants.USERID, getActivity());
        String url = Constants.API_URL + Constants.KEY_TRENDS_URL + "?id=" + id;
        showDialog();
        Log.d("URL", url);
        JsonArrayRequest newsRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>()
                {
                    @Override
                    public void onResponse(JSONArray response) {
                        // display response
                        Log.d("Response", response.toString());

                        ParseDataFromJsonArray(response);
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
        McDomatsApp.getInstance().addToRequestQueue(newsRequest, "trensRequest");
    }

    private void ParseDataFromJsonArray(JSONArray response) {
        int length = response.length();
        HashMap<String, Integer> map = new HashMap<>();
        HashMap<String, JSONArray> dataByType = new HashMap<>();
        JSONArray list;
        ArrayList<String> headerTitleList = new ArrayList<>();

        int maxColorCount = 1;
        for (int i = 0; i < length; i++)
        {
            try {
                JSONObject jsonObject = response.getJSONObject(i);
                String type = jsonObject.getString("type");
                JSONArray configArray = jsonObject.getJSONArray("configuration");

                if (maxColorCount < configArray.length())
                    maxColorCount = configArray.length();

                if (type != null && !type.equals("") && !map.containsKey(type))
                {
                    map.put(type, 1);
                    headerTitleList.add(type);
                    Log.d("type", type);
                    dataByType.put(type, new JSONArray());
                }

                if (!dataByType.containsKey(type))
                {
                    list = new JSONArray(); list.put(jsonObject);
                    dataByType.put(type, list);
                }
                else
                {
                    dataByType.get(type).put(jsonObject);
                }

            } catch (JSONException e) {

            }
        }
        if (maxColorCount > Constants.GraphColorList.size())
            Utils.makeGraphColor(maxColorCount - Constants.GraphColorList.size());
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        int size = headerTitleList.size();
        FragmentManager fm = getChildFragmentManager();
        FragmentTransaction fr = fm.beginTransaction();
        for (int i = 0; i < size; i++)
        {
            String type = headerTitleList.get(i);
            Utils.SetStringFromPreference(type, dataByType.get(type).toString(), getActivity());
            adapter.addFragment(ProductionFragment.newInstance(1, type), type);
        }
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

    }

    private void hideDialog() {
        if (loginDialog != null)
            loginDialog.dismiss();
    }

    private void showDialog() {
        if (loginDialog == null) {
            loginDialog = new Dialog(getActivity());
            loginDialog.setContentView(R.layout.activity_login_view);
            ImageView imageView = loginDialog.findViewById(R.id.imageView);
            TextView textView = loginDialog.findViewById(R.id.textView);
            textView.setText("Getting trends...");
        }
        loginDialog.show();
    }
    boolean mAttached;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnTrendsInteractionListener) {
            mListener = (OnTrendsInteractionListener) context;
            mAttached = true;
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
                    + " must implement OnFragmentInteractionListener");
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
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnTrendsInteractionListener {
        // TODO: Update argument type and name
        void onTrendInteraction(Uri uri);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
