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
import java.util.Collections;

public class CountryFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<CovidCountry> initList,covidCountries;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView connectionStatus;
    private CovidCountryAdapter countryAdapter;
    private SharedPreferences sharedPreferences;
    private Gson gson;
    private SharedPreferences.Editor editor;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_country, container, false);

        recyclerView = root.findViewById(R.id.recycler_view_country);
        connectionStatus = root.findViewById(R.id.internet_connection_status_country_list);
        swipeRefreshLayout = root.findViewById(R.id.swipe_refresh_layout_country);
        EditText edSearch = root.findViewById(R.id.search_edit_text_country);

        covidCountries = new ArrayList<>();
        initList = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        swipeRefreshLayout.setRefreshing(true);

        sharedPreferences = requireActivity().getSharedPreferences("Coronavirus",Context.MODE_PRIVATE);
        gson = new Gson();
        String json = sharedPreferences.getString("countryList", null);
        Type type = new TypeToken<ArrayList<CovidCountry>>() {}.getType();
        covidCountries = gson.fromJson(json, type);

        if (covidCountries != null){
            countryAdapter = new CovidCountryAdapter(covidCountries,getActivity());
            recyclerView.setAdapter(countryAdapter);
        }

        getDataFromServer();

        swipeRefreshLayout.setOnRefreshListener(this::getDataFromServer);

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

        if (InternetCheck.checkConnection(requireActivity())){

            connectionStatus.setVisibility(View.VISIBLE);
            connectionStatus.setText("Getting latest data");
            connectionStatus.setBackgroundColor(getResources().getColor(R.color.green));

            String url = "https://corona.lmao.ninja/v2/countries";

            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, response -> {

                try {
                    covidCountries = new ArrayList<>();
                    initList = new ArrayList<>();

                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++){

                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        initList.add(new CovidCountry(jsonObject.getString("country"),
                                jsonObject.getString("todayCases"),jsonObject.getString("deaths"),jsonObject.getString("todayDeaths"),jsonObject.getString("recovered"),
                                jsonObject.getString("active"),jsonObject.getString("critical"),jsonObject.getString("casesPerOneMillion"),jsonObject.getString("tests"),jsonObject.getString("testsPerOneMillion"),jsonObject.getInt("cases"),i+1));
                    }

                    Collections.sort(initList, (p1, p2) -> p2.getmCases() - p1.getmCases());
                    for (int j = 0; j < initList.size();j++){
                        CovidCountry covidCountry = initList.get(j);
                        covidCountries.add(new CovidCountry(covidCountry.getmCovidCountry(),covidCountry.getmTodayCases(),covidCountry.getmDeaths(),covidCountry.getmTodayDeaths(),
                                covidCountry.getmRecovered(),covidCountry.getmActive(),covidCountry.getmCritical(),covidCountry.getmCasePerMillion(),covidCountry.getmTotalTests(),covidCountry.getmTestsPerOneMillion(),covidCountry.getmCases(),j+1));
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

            }, error -> {
                swipeRefreshLayout.setRefreshing(false);
                connectionStatus.setBackgroundColor(getResources().getColor(R.color.red));
                connectionStatus.setText("Failed to load data from server");
                Log.d("Response Error : ",error.toString());
            });

            Volley.newRequestQueue(requireActivity()).add(stringRequest);

        } else {
            swipeRefreshLayout.setRefreshing(false);
            connectionStatus.setVisibility(View.VISIBLE);
            connectionStatus.setText("No Internet Connection");
            connectionStatus.setBackgroundColor(getResources().getColor(R.color.red));
        }

    }
}
