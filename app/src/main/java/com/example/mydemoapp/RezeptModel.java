package com.example.mydemoapp;

public class RezeptModel {

    //Variablen - zur besseren Unterscheidung mit m für Model
    private Integer mId;
    private String mTitel;
    private String mMenge;
    private String mZutat;
    private String mZubereitung;
    private String mVorZeit;
    private String mKochZeit;
    private String mGesamtZeit;
    private byte[] mImage;


    public RezeptModel(Integer mId, String mTitel, String mMenge, String mZutat, String mZubereitung, String mVorZeit, String mKochZeit, String mGesamtZeit, byte[] mImage) {
        this.mId = mId;
        this.mTitel = mTitel;
        this.mMenge = mMenge;
        this.mZutat = mZutat;
        this.mZubereitung = mZubereitung;
        this.mVorZeit = mVorZeit;
        this.mKochZeit = mKochZeit;
        this.mGesamtZeit = mGesamtZeit;
        this.mImage = mImage;

    }

    public Integer getmId() {
        return mId;
    }

    public void setmId(Integer mId) {
        this.mId = mId;
    }

    public String getmTitel() {
        return mTitel;
    }

    public void setmTitel(String mTitel) {
        this.mTitel = mTitel;
    }

    public String getmMenge() {
        return mMenge;
    }

    public void setmMenge(String mMenge) {
        this.mMenge = mMenge;
    }

    public String getmZutat() {
        return mZutat;
    }

    public void setmZutat(String mZutat) {
        this.mZutat = mZutat;
    }

    public String getmZubereitung() {
        return mZubereitung;
    }

    public void setmZubereitung(String mZubereitung) {
        this.mZubereitung = mZubereitung;
    }

    public String getmVorZeit() {
        return mVorZeit;
    }

    public void setmVorZeit(String mVorZeit) {
        this.mVorZeit = mVorZeit;
    }

    public String getmKochZeit() {
        return mKochZeit;
    }

    public void setmKochZeit(String mKochZeit) {
        this.mKochZeit = mKochZeit;
    }

    public String getmGesamtZeit() {
        return mGesamtZeit;
    }

    public void setmGesamtZeit(String mGesamtZeit) {
        this.mGesamtZeit = mGesamtZeit;
    }

    public byte[] getmImage() {
        return mImage;
    }

    public void setmImage(byte[] mImage) {
        this.mImage = mImage;
    }


}