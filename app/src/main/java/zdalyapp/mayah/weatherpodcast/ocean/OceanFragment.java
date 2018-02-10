package zdalyapp.mayah.weatherpodcast.ocean;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import zdalyapp.mayah.weatherpodcast.city.DetailWeatherActivity;
import zdalyapp.mayah.R;
import zdalyapp.mayah.weatherpodcast.ocean.dummy.DummyContent;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OceanFragment.OnOceanFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link OceanFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OceanFragment extends Fragment implements OnMapReadyCallback{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnOceanFragmentInteractionListener mListener;
    private JSONArray dataArray;

    public OceanFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OceanFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OceanFragment newInstance(String param1, String param2) {
        OceanFragment fragment = new OceanFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    boolean bMatchState;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            dataArray = new JSONArray();
            try {
                dataArray = new JSONArray(mParam1);
            } catch (JSONException e) {

            }
        }
    }

    View mainView;
    MapView mapView;
    ImageButton imageButton;
    RecyclerView recyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mainView = inflater.inflate(R.layout.fragment_ocean, container, false);
        mapView = (MapView) mainView.findViewById(R.id.mapView);
        recyclerView = (RecyclerView) mainView.findViewById(R.id.listView);
        imageButton = (ImageButton) mainView.findViewById(R.id.imageButton7);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(mainView.getContext()));


        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bMatchState = !bMatchState;
                if (bMatchState == true)
                {
                    mapView.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                }
                else
                {
                    mapView.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }

            }
        });
        bMatchState = false;
        return mainView;
    }
    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
    // TODO: Rename method, update argument and hook method into UI event


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnOceanFragmentInteractionListener) {
            mListener = (OnOceanFragmentInteractionListener) context;
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
    GoogleMap mMap;
    ArrayList<DummyContent.OceanItem> dataList;
    private void ShowMap() {

        try {
            JSONObject dataObj = dataArray.getJSONObject(0);
            double lat = Double.parseDouble(dataObj.getString("lat").replaceAll("[^0-9\\\\.]+",""));
            double lon = Double.parseDouble(dataObj.getString("lon").replaceAll("[^0-9\\\\.]+",""));
            LatLng pos = new LatLng(lat, lon);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(pos));
            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    String subId = marker.getTag().toString();
                    int id  = Integer.valueOf(subId);
                    try {
                        JSONObject jsonObject = dataArray.getJSONObject(id);
                        Intent intent = new Intent(getActivity(), DetailWeatherActivity.class);
                        intent.putExtra("data", jsonObject.toString());
                        startActivity(intent);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Log.d("subid", subId);
                    return false;
                }
            });
            mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {
                    String subId = marker.getTag().toString();
                }
            });
            AddAnnotation();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void AddAnnotation() {
        int length = dataArray.length();
        dataList = new ArrayList<>();
        for (int i = 0; i < length; i++)
        {
            try {

                JSONObject dataObj = dataArray.getJSONObject(i);
                double lat = Double.parseDouble(dataObj.getString("lat").replaceAll("[^0-9\\\\.]+",""));
                double lon = Double.parseDouble(dataObj.getString("lon").replaceAll("[^0-9\\\\.]+",""));
                String title = dataObj.getString("name");
                LatLng pos = new LatLng(lat, lon);
                Marker marker  = mMap.addMarker(new MarkerOptions().position(pos).title(title).icon(BitmapDescriptorFactory.fromResource(R.drawable.red_pin_small)));
                dataList.add(new DummyContent.OceanItem(dataObj));
                Log.d("position", lat + " " + lon);
                String subTitle = String.format("%d", i);
                marker.setTag(subTitle);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        recyclerView.setAdapter(new MyOceanRecyclerViewAdapter(dataList, mListener));

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        ShowMap();
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
    public interface OnOceanFragmentInteractionListener {
        // TODO: Update argument type and name
        void onOceanFragmentInteraction(DummyContent.OceanItem oceanItem);
    }
}
