package com.techdoctorbd.coronaviruscovid_19tracker.ui.country;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.techdoctorbd.coronaviruscovid_19tracker.Helpers.InternetCheck;
import com.techdoctorbd.coronaviruscovid_19tracker.R;
import org.json.JSONArray;
import org.json.JSONObject;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Objects;

public class CountryFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<CovidCountry> covidCountries;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView connectionStatus;
    private CovidCountryAdapter countryAdapter;
    private SharedPreferences sharedPreferences;
    private Gson gson;
    private SharedPreferences.Editor editor;
    private EditText edSearch;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_country, container, false);

        recyclerView = root.findViewById(R.id.recycler_view_country);
        connectionStatus = root.findViewById(R.id.internet_connection_status_country_list);
        swipeRefreshLayout = root.findViewById(R.id.swipe_refresh_layout_country);
        edSearch = root.findViewById(R.id.search_edit_text_country);

        covidCountries = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        swipeRefreshLayout.setRefreshing(true);

        sharedPreferences = Objects.requireNonNull(getActivity()).getSharedPreferences("Coronavirus",Context.MODE_PRIVATE);
        gson = new Gson();
        String json = sharedPreferences.getString("countryList", null);
        Type type = new TypeToken<ArrayList<CovidCountry>>() {}.getType();
        covidCountries = gson.fromJson(json, type);

        if (covidCountries != null){
            countryAdapter = new CovidCountryAdapter(covidCountries,getActivity());
            recyclerView.setAdapter(countryAdapter);
        }

        getDataFromServer();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getDataFromServer();
            }
        });

        edSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0){
                    searchCountry(charSequence.toString());
                } else {
                    countryAdapter = new CovidCountryAdapter(covidCountries,getActivity());
                    recyclerView.setAdapter(countryAdapter);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        return root;
    }

    private void searchCountry(String str) {
        ArrayList<CovidCountry> myList = new ArrayList<>();
        for (CovidCountry obj : covidCountries) {
            if (obj.getmCovidCountry().toLowerCase().contains(str.toLowerCase())) {
                myList.add(obj);
            }
        }

        CovidCountryAdapter adapterClass = new CovidCountryAdapter(myList,getActivity());
        recyclerView.setAdapter(adapterClass);
    }


    @SuppressLint("SetTextI18n")
    private void getDataFromServer() {

        if (InternetCheck.checkConnection(Objects.requireNonNull(getActivity()))){

            connectionStatus.setVisibility(View.VISIBLE);
            connectionStatus.setText("Getting latest data");
            connectionStatus.setBackgroundColor(getResources().getColor(R.color.green));

            String url = "https://corona.lmao.ninja/countries";

            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    try {
                        covidCountries = new ArrayList<>();

                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++){

                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            covidCountries.add(new CovidCountry(jsonObject.getString("country"),jsonObject.getString("cases"),
                                    jsonObject.getString("todayCases"),jsonObject.getString("deaths"),jsonObject.getString("todayDeaths"),jsonObject.getString("recovered"),
                                    jsonObject.getString("active"),jsonObject.getString("critical"),jsonObject.getString("casesPerOneMillion"),i+1));

                        }

                        editor = sharedPreferences.edit();
                        String json  = gson.toJson(covidCountries);
                        editor.putString("countryList",json);
                        editor.apply();

                        countryAdapter = new CovidCountryAdapter(covidCountries,getActivity());
                        recyclerView.setAdapter(countryAdapter);

                        swipeRefreshLayout.setRefreshing(false);
                        connectionStatus.setVisibility(View.GONE);

                    } catch (Exception e){
                        e.printStackTrace();
                        connectionStatus.setBackgroundColor(getResources().getColor(R.color.red));
                        connectionStatus.setText(e.getMessage());
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    swipeRefreshLayout.setRefreshing(false);
                    connectionStatus.setBackgroundColor(getResources().getColor(R.color.red));
                    connectionStatus.setText("Failed to load data from server");
                    Log.d("Response Error : ",error.toString());
                }
            });

            Volley.newRequestQueue(getActivity()).add(stringRequest);

        } else {
            swipeRefreshLayout.setRefreshing(false);
            connectionStatus.setVisibility(View.VISIBLE);
            connectionStatus.setText("No Internet Connection");
            connectionStatus.setBackgroundColor(getResources().getColor(R.color.red));
        }

    }
}
