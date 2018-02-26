package blitzboba.blitzboba;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Rodrigo on 10/27/2017.
 */

public class CalendarDataModel implements Parcelable {
    @SerializedName("address")
    public String address;

    @SerializedName("comments")
    public String comments;

    @SerializedName("date")
    public long date;

    @SerializedName("duration")
    public long duration;

    @SerializedName("isOnlineOrderingAvail")
    public Boolean isOnlineOrderingAvail;

    @SerializedName("locationSubtitle")
    public String locationSubtitle;

    @SerializedName("locationTitle")
    public String locationTitle;

    public CalendarDataModel() {
    }

    public CalendarDataModel(String address, String comments, long date, long duration, Boolean isOnlineOrderingAvail, String locationSubtitle, String locationTitle) {
        this.address = address;
        this.comments = comments;
        this.date = date;
        this.duration = duration;
        this.isOnlineOrderingAvail = isOnlineOrderingAvail;
        this.locationSubtitle = locationSubtitle;
        this.locationTitle = locationTitle;
    }

    protected CalendarDataModel(Parcel in) {
        address = in.readString();
        comments = in.readString();
        date = in.readLong();
        duration = in.readLong();
        isOnlineOrderingAvail = in.readInt() != 0;
        locationTitle = in.readString();
        locationSubtitle = in.readString();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public Boolean getOnlineOrderingAvail() {
        return isOnlineOrderingAvail;
    }

    public void setOnlineOrderingAvail(Boolean onlineOrderingAvail) {
        isOnlineOrderingAvail = onlineOrderingAvail;
    }

    public String getLocationSubtitle() {
        return locationSubtitle;
    }

    public void setLocationSubtitle(String locationSubtitle) {
        this.locationSubtitle = locationSubtitle;
    }

    public String getLocationTitle() {
        return locationTitle;
    }

    public void setLocationTitle(String locationTitle) {
        this.locationTitle = locationTitle;
    }

    public String getStartAndEndTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("h:mm a");
        String startTime = sdf.format(new Date(date));
        String endTime = sdf.format(new Date(date + duration));
        return startTime + " - " + endTime;
    }

    public String getDateWithNoYear() {
        String dateString = "";
        if(date > 0) {
            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd");
            dateString = formatter.format(new Date(date));
        }
        return dateString;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(address);
        dest.writeString(comments);
        dest.writeLong(date);
        dest.writeLong(duration);
        dest.writeInt(isOnlineOrderingAvail ? 1 : 0);
        dest.writeString(locationTitle);
        dest.writeString(locationSubtitle);
    }


    public static final Creator<CalendarDataModel> CREATOR = new Creator<CalendarDataModel>() {
        @Override
        public CalendarDataModel createFromParcel(Parcel in) {
            return new CalendarDataModel(in);
        }

        @Override
        public CalendarDataModel[] newArray(int size) {
            return new CalendarDataModel[size];
        }
    };

}
