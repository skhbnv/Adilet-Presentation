package jmeansjustice.hackday_final;

public class Users {
    private String mName;
    private String mCategory;
    private String mSurname;
    private String mGender;
    private String mAge;
    private String mID;
    private String mPhone;

    Users(){

    }

    Users(String mName, String mSurname, String mGender, String mAge, String mPhone, String mCategory, String mID) {
        this.mName = mName;
        this.mID = mID;
        this.mSurname = mSurname;
        this.mGender = mGender;
        this.mAge = mAge;
        this.mPhone = mPhone;
        this.mCategory= mCategory;
    }

    public String getmAge() {
        return mAge;
    }

    public String getmGender() {
        return mGender;
    }

    public String getmName() {
        return mName;
    }

    public String getmSurname() {
        return mSurname;
    }

    public String getmPhone() {
        return mPhone;
    }
    public String getmCategory() {
        return mCategory;
    }
    public String getmID() {
        return mID;
    }
}