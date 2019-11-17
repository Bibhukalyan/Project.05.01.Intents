package com.techhue.models;

import android.os.Parcel;
import android.os.Parcelable;

public class GunModel implements Parcelable {

    private int gunImage;
    private String gunName;

    public GunModel(int gunImage, String gunName) {
        this.gunImage = gunImage;
        this.gunName = gunName;
    }

    protected GunModel(Parcel in) {
        gunImage = in.readInt();
        gunName = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(gunImage);
        dest.writeString(gunName);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<GunModel> CREATOR = new Creator<GunModel>() {
        @Override
        public GunModel createFromParcel(Parcel in) {
            return new GunModel(in);
        }

        @Override
        public GunModel[] newArray(int size) {
            return new GunModel[size];
        }
    };

    public int getGunImage() {
        return gunImage;
    }

    public void setGunImage(int gunImage) {
        this.gunImage = gunImage;
    }

    public String getGunName() {
        return gunName;
    }

    public void setGunName(String gunName) {
        this.gunName = gunName;
    }
}
