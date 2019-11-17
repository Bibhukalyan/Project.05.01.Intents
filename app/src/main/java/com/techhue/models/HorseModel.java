package com.techhue.models;

import android.os.Parcel;
import android.os.Parcelable;

public class HorseModel implements Parcelable {

    private int horseImage;
    private String horseName;

    public HorseModel(int horseImage, String horseName) {
        this.horseImage = horseImage;
        this.horseName = horseName;
    }

    protected HorseModel(Parcel in) {
        horseImage = in.readInt();
        horseName = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(horseImage);
        dest.writeString(horseName);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<HorseModel> CREATOR = new Creator<HorseModel>() {
        @Override
        public HorseModel createFromParcel(Parcel in) {
            return new HorseModel(in);
        }

        @Override
        public HorseModel[] newArray(int size) {
            return new HorseModel[size];
        }
    };

    public int getHorseImage() {
        return horseImage;
    }

    public void setHorseImage(int horseImage) {
        this.horseImage = horseImage;
    }

    public String getHorseName() {
        return horseName;
    }

    public void setHorseName(String horseName) {
        this.horseName = horseName;
    }
}
