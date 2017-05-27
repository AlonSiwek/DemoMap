package com.example.alonsiwek.demomap;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Dor on 24-May-17.
 *
 * User Data to display at Recycle view.
 */

public class UserData implements Parcelable {
    String user_name;
    String user_phone;
    String user_id;
    double[] coordinates = new double[2];
    Boolean isRunning;

    public UserData(){
        this.user_name = user_name;
        this.user_phone = user_phone;
        this.user_id = user_id;
        this.isRunning = isRunning;
        this.coordinates = coordinates;
    }

    protected UserData(Parcel in) {
        user_name = in.readString();
        user_phone = in.readString();
        user_id = in.readString();
        coordinates = in.createDoubleArray();
    }

    public static final Creator<UserData> CREATOR = new Creator<UserData>() {
        @Override
        public UserData createFromParcel(Parcel in) {
            return new UserData(in);
        }

        @Override
        public UserData[] newArray(int size) {
            return new UserData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeString(user_name);
        dest.writeString(user_phone);
        dest.writeString(user_id);
        dest.writeDoubleArray(coordinates);
        dest.writeByte((byte) (isRunning ? 1 : 0));     //if myBoolean == true, byte == 1

    }
}
