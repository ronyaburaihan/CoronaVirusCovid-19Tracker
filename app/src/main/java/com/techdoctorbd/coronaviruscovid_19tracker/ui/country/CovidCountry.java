package com.techdoctorbd.coronaviruscovid_19tracker.ui.country;

public class CovidCountry {
    private String mCovidCountry, mTodayCases, mDeaths, mTodayDeaths, mRecovered, mActive, mCritical, mCasePerMillion,mTotalTests,mTestsPerOneMillion;
    private int mCases,rank;


    public CovidCountry(String mCovidCountry, String mTodayCases, String mDeaths, String mTodayDeaths, String mRecovered, String mActive, String mCritical, String mCasePerMillion, String mTotalTests, String mTestsPerOneMillion, int mCases, int rank) {
        this.mCovidCountry = mCovidCountry;
        this.mTodayCases = mTodayCases;
        this.mDeaths = mDeaths;
        this.mTodayDeaths = mTodayDeaths;
        this.mRecovered = mRecovered;
        this.mActive = mActive;
        this.mCritical = mCritical;
        this.mCasePerMillion = mCasePerMillion;
        this.mTotalTests = mTotalTests;
        this.mTestsPerOneMillion = mTestsPerOneMillion;
        this.mCases = mCases;
        this.rank = rank;
    }

    public String getmCovidCountry() {
        return mCovidCountry;
    }

    public void setmCovidCountry(String mCovidCountry) {
        this.mCovidCountry = mCovidCountry;
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

    public String getmTotalTests() {
        return mTotalTests;
    }

    public void setmTotalTests(String mTotalTests) {
        this.mTotalTests = mTotalTests;
    }

    public String getmTestsPerOneMillion() {
        return mTestsPerOneMillion;
    }

    public void setmTestsPerOneMillion(String mTestsPerOneMillion) {
        this.mTestsPerOneMillion = mTestsPerOneMillion;
    }

    public int getmCases() {
        return mCases;
    }

    public void setmCases(int mCases) {
        this.mCases = mCases;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
}
