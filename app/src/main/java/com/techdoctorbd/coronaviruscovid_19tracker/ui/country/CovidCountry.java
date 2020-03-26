package com.techdoctorbd.coronaviruscovid_19tracker.ui.country;

import com.android.volley.toolbox.StringRequest;

public class CovidCountry {
    private String mCovidCountry, mCases, mTodayCases, mDeaths, mTodayDeaths, mRecovered, mActive, mCritical, mCasePerMillion;
    private int rank;

    public CovidCountry(String mCovidCountry, String mCases, String mTodayCases, String mDeaths, String mTodayDeaths, String mRecovered, String mActive, String mCritical, String mCasePerMillion, int rank) {
        this.mCovidCountry = mCovidCountry;
        this.mCases = mCases;
        this.mTodayCases = mTodayCases;
        this.mDeaths = mDeaths;
        this.mTodayDeaths = mTodayDeaths;
        this.mRecovered = mRecovered;
        this.mActive = mActive;
        this.mCritical = mCritical;
        this.mCasePerMillion = mCasePerMillion;
        this.rank = rank;
    }

    public String getmCovidCountry() {
        return mCovidCountry;
    }

    public void setmCovidCountry(String mCovidCountry) {
        this.mCovidCountry = mCovidCountry;
    }

    public String getmCases() {
        return mCases;
    }

    public void setmCases(String mCases) {
        this.mCases = mCases;
    }

    public String getmTodayCases() {
        return mTodayCases;
    }

    public void setmTodayCases(String mTodayCases) {
        this.mTodayCases = mTodayCases;
    }

    public String getmDeaths() {
        return mDeaths;
    }

    public void setmDeaths(String mDeaths) {
        this.mDeaths = mDeaths;
    }

    public String getmTodayDeaths() {
        return mTodayDeaths;
    }

    public void setmTodayDeaths(String mTodayDeaths) {
        this.mTodayDeaths = mTodayDeaths;
    }

    public String getmRecovered() {
        return mRecovered;
    }

    public void setmRecovered(String mRecovered) {
        this.mRecovered = mRecovered;
    }

    public String getmActive() {
        return mActive;
    }

    public void setmActive(String mActive) {
        this.mActive = mActive;
    }

    public String getmCritical() {
        return mCritical;
    }

    public void setmCritical(String mCritical) {
        this.mCritical = mCritical;
    }

    public String getmCasePerMillion() {
        return mCasePerMillion;
    }

    public void setmCasePerMillion(String mCasePerMillion) {
        this.mCasePerMillion = mCasePerMillion;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
}
