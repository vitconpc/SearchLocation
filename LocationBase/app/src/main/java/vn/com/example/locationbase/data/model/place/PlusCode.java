package vn.com.example.locationbase.data.model.place;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PlusCode implements Parcelable {
    @SerializedName("compound_code")
    @Expose
    private String compoundCode;
    @SerializedName("global_code")
    @Expose
    private String globalCode;

    protected PlusCode(Parcel in) {
        compoundCode = in.readString();
        globalCode = in.readString();
    }

    public static final Creator<PlusCode> CREATOR = new Creator<PlusCode>() {
        @Override
        public PlusCode createFromParcel(Parcel in) {
            return new PlusCode(in);
        }

        @Override
        public PlusCode[] newArray(int size) {
            return new PlusCode[size];
        }
    };

    public String getCompoundCode() {
        return compoundCode;
    }

    public void setCompoundCode(String compoundCode) {
        this.compoundCode = compoundCode;
    }

    public String getGlobalCode() {
        return globalCode;
    }

    public void setGlobalCode(String globalCode) {
        this.globalCode = globalCode;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(compoundCode);
        dest.writeString(globalCode);
    }
}
