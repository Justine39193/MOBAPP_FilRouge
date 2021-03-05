package fr.imt_atlantique.initiationandroid;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {

    private String userLast;
    private String userFirst;
    private String userDate;
    private String userPlace;
    private String userDept;
    private String[] userPhones;


    public User(String lastname, String firstname, String birthdate, String birthplace, String dept, String[] phoneNbs ){
        userLast = lastname;
        userFirst = firstname;
        userDate = birthdate;
        userPlace = birthplace;
        userDept = dept;
        userPhones = phoneNbs;
    }

    protected User(Parcel in) {
        userLast = in.readString();
        userFirst = in.readString();
        userDate = in.readString();
        userPlace = in.readString();
        userDept = in.readString();
        userPhones = in.createStringArray();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userLast);
        dest.writeString(userFirst);
        dest.writeString(userDate);
        dest.writeString(userPlace);
        dest.writeString(userDept);
        dest.writeStringArray(userPhones);
    }



    public String getUserLast() {
        return userLast;
    }

    public void setUserLast(String userLast) {
        this.userLast = userLast;
    }

    public String getUserFirst() {
        return userFirst;
    }

    public void setUserFirst(String userFirst) {
        this.userFirst = userFirst;
    }

    public String getUserDate() {
        return userDate;
    }

    public void setUserDate(String userDate) {
        this.userDate = userDate;
    }

    public String getUserPlace() {
        return userPlace;
    }

    public void setUserPlace(String userPlace) {
        this.userPlace = userPlace;
    }

    public String getUserDept() {
        return userDept;
    }

    public void setUserDept(String userDept) {
        this.userDept = userDept;
    }

    public String[] getUserPhones() {
        return userPhones;
    }

    public void setUserPhones(String[] userPhones) {
        this.userPhones = userPhones;
    }
}
