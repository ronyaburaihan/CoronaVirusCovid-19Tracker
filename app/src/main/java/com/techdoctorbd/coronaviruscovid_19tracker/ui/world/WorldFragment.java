package com.techdoctorbd.coronaviruscovid_19tracker.ui.world;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.techdoctorbd.coronaviruscovid_19tracker.Helpers.InternetCheck;
import com.techdoctorbd.coronaviruscovid_19tracker.R;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Objects;

public class WorldFragment extends Fragment {

    private SwipeRefreshLayout refreshLayout;
    private TextView tvTotalConfirm,tvTotalDeath,tvTotalRecovery,connectionStatus,tvSyncTime,tvDeveloperName;
    private String mCases,mDeaths,mRecovered;
    private SharedPreferences sharedPreferences;
    private Long mSyncTime;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_world, container, false);

        refreshLayout = root.findViewById(R.id.swipe_refresh_layout_world);
        tvTotalConfirm = root.findViewById(R.id.total_confirmed);
        tvTotalDeath = root.findViewById(R.id.total_deaths);
        tvTotalRecovery = root.findViewById(R.id.total_recovered);
        connectionStatus = root.findViewById(R.id.internet_connection_status_world);
        tvSyncTime = root.findViewById(R.id.sync_time_world);
        tvDeveloperName = root.findViewById(R.id.developer_name_world);

        refreshLayout.setRefreshing(true);

        sharedPreferences = Objects.requireNonNull(getActivity()).getSharedPreferences("Coronavirus",Context.MODE_PRIVATE);
        mCases = sharedPreferences.getString("cases", "");
        mDeaths = sharedPreferences.getString("deaths", "");
        mRecovered = sharedPreferences.getString("recovered", "");
        mSyncTime = sharedPreferences.getLong("syncTime", 0);

        tvTotalConfirm.setText(mCases);
        tvTotalDeath.setText(mDeaths);
        tvTotalRecovery.setText(mRecovered);

        if (mSyncTime != 0){
            tvSyncTime.setText("Last sync : "+getmSyncTime(mSyncTime));
        }

        getData();

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });

        tvDeveloperName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://profile/100004443240163"));
                    startActivity(intent);
                } catch(Exception e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.facebook.com/ronyaburaihan")));
                }
            }
        });

        return root;
    }

    @SuppressLint("SetTextI18n")
    private void getData() {

        if (InternetCheck.checkConnection(Objects.requireNonNull(getActivity()))){

            RequestQueue queue = Volley.newRequestQueue(getActivity());

            connectionStatus.setVisibility(View.VISIBLE);
            connectionStatus.setText("Getting Latest Data");
            connectionStatus.setBackgroundColor(getResources().getColor(R.color.green));

            String url = "https://corona.lmao.ninja/all";

            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);

                        mCases = jsonObject.getString("cases");
                        mDeaths = jsonObject.getString("deaths");
                        mRecovered = jsonObject.getString("recovered");

                        mSyncTime = System.currentTimeMillis();

                        tvTotalConfirm.setText(mCases);
                        tvTotalDeath.setText(mDeaths);
                        tvTotalRecovery.setText(mRecovered);
                        tvSyncTime.setText("Last sync : "+getmSyncTime(mSyncTime));

                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("cases",mCases);
                        editor.putString("deaths",mDeaths);
                        editor.putString("recovered",mRecovered);
                        editor.putLong("syncTime",mSyncTime);
                        editor.apply();

                        refreshLayout.setRefreshing(false);
                        connectionStatus.setVisibility(View.GONE);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    refreshLayout.setRefreshing(false);
                    Log.d("Error Response : ", error.toString());
                }
            });

            queue.add(stringRequest);

        } else {

            refreshLayout.setRefreshing(false);
            connectionStatus.setVisibility(View.VISIBLE);
            connectionStatus.setText("No Internet Connection");
            connectionStatus.setBackgroundColor(getResources().getColor(R.color.red));
        }
    }

    public static String getmSyncTime(long mSyncTime) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy - hh:mm a", Locale.getDefault());
        return formatter.format(mSyncTime);
    }
}
