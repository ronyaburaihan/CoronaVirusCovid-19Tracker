package com.techdoctorbd.coronaviruscovid_19tracker.ui.country;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.techdoctorbd.coronaviruscovid_19tracker.R;

import java.util.ArrayList;

public class CovidCountryAdapter extends RecyclerView.Adapter<CovidCountryAdapter.CountryViewHolder> {

    private ArrayList<CovidCountry> covidCountries;
    private Context mContext;

    public CovidCountryAdapter(ArrayList<CovidCountry> covidCountries, Context mContext) {
        this.covidCountries = covidCountries;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public CountryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CountryViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_list_country,parent,false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final CountryViewHolder holder, final int position) {
        holder.tvCountryRank.setText(""+covidCountries.get(position).getRank());
        holder.tvCountryName.setText(covidCountries.get(position).getmCovidCountry());
        holder.tvTotalCases.setText(covidCountries.get(position).getmCases());

        holder.countryItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ViewGroup viewGroup = view.findViewById(android.R.id.content);
                View dialogView = LayoutInflater.from(mContext).inflate(R.layout.dialog_country_layout, viewGroup, false);
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setView(dialogView);
                AlertDialog alertDialog = builder.create();
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                TextView countryName = dialogView.findViewById(R.id.country_name_dialog);
                TextView todayCases = dialogView.findViewById(R.id.today_confirmed_dialog);
                TextView todayDeaths = dialogView.findViewById(R.id.today_deaths_dialog);
                TextView totalCases = dialogView.findViewById(R.id.total_cases_dialog);
                TextView totalDeaths = dialogView.findViewById(R.id.total_deaths_dialog);
                TextView totalRecovered = dialogView.findViewById(R.id.total_recovered_dialog);
                TextView tvActive = dialogView.findViewById(R.id.active_case_dialog);
                TextView tvCritical = dialogView.findViewById(R.id.critical_case_dialog);
                TextView casePerMillion = dialogView.findViewById(R.id.case_per_million_dialog);

                countryName.setText(covidCountries.get(position).getmCovidCountry());
                todayCases.setText(covidCountries.get(position).getmTodayCases());
                todayDeaths.setText(covidCountries.get(position).getmTodayDeaths());
                totalCases.setText(covidCountries.get(position).getmCases());
                totalDeaths.setText(covidCountries.get(position).getmDeaths());
                totalRecovered.setText(covidCountries.get(position).getmRecovered());
                tvActive.setText(covidCountries.get(position).getmActive());
                tvCritical.setText(covidCountries.get(position).getmCritical());
                casePerMillion.setText(covidCountries.get(position).getmCasePerMillion());

                alertDialog.show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return covidCountries.size();

    }

    public class CountryViewHolder extends RecyclerView.ViewHolder{

        TextView tvCountryName,tvTotalCases,tvCountryRank;
        LinearLayout countryItem;

        public CountryViewHolder(@NonNull View itemView) {
            super(itemView);

            tvCountryName = itemView.findViewById(R.id.country_name_item_country);
            tvTotalCases = itemView.findViewById(R.id.total_case_item_country);
            tvCountryRank = itemView.findViewById(R.id.country_rank_item_country);
            countryItem = itemView.findViewById(R.id.item_country_list);
        }
    }
}
