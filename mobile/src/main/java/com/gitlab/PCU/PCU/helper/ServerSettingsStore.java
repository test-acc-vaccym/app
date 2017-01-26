package com.gitlab.PCU.PCU.helper;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by tim on 19.01.17.
 */

@SuppressWarnings("WeakerAccess")
public final class ServerSettingsStore implements Parcelable {

    private String name;
    private IP ip;
    private String desc;

    public ServerSettingsStore(String name, IP ip, String desc) {
        this.name = name;
        this.ip = ip;
        this.desc = desc;
    }

    protected ServerSettingsStore(Parcel in) {
        name = in.readString();
        ip = in.readParcelable(IP.class.getClassLoader());
        desc = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeParcelable(ip, flags);
        dest.writeString(desc);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ServerSettingsStore> CREATOR = new Creator<ServerSettingsStore>() {
        @Override
        public ServerSettingsStore createFromParcel(Parcel in) {
            return new ServerSettingsStore(in);
        }

        @Override
        public ServerSettingsStore[] newArray(int size) {
            return new ServerSettingsStore[size];
        }
    };

    public String getName() {
        return name;
    }

    public ServerSettingsStore setName(String name) {
        this.name = name;
        return this;
    }

    public IP getIp() {
        return ip;
    }

    public ServerSettingsStore setIp(IP ip) {
        this.ip = ip;
        return this;
    }

    public void modifyIP(IPPart ipPart, int newVal) {
        int[] iIP = ip.getInt();
        switch (ipPart) {
            case A:
                ip = new IP(newVal, iIP[1], iIP[2], iIP[3]);
                break;
            case B:
                ip = new IP(iIP[0], newVal, iIP[2], iIP[3]);
                break;
            case C:
                ip = new IP(iIP[0], iIP[1], newVal, iIP[3]);
                break;
            case D:
                ip = new IP(iIP[0], iIP[1], iIP[2], newVal);
                break;
        }
    }

    public enum IPPart {
        A, B, C, D
    }

    public String getDesc() {
        return desc;
    }

    public ServerSettingsStore setDesc(String desc) {
        this.desc = desc;
        return this;
    }
}
