package com.gitlab.PCU.PCU.helper;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by tim on 19.01.17.
 */
public class ServerSettingsStore implements Parcelable {
    private String name = "null";

    private IP ip = Defaults.ServerStatic.INSTANCE.getIP();

    private String desc = "null";

    private Integer port = 25566;

    public ServerSettingsStore(String name, IP ip, String desc) {
        this.name = name;
        this.ip = ip;
        this.desc = desc;
        this.port = 25566;
    }

    public ServerSettingsStore(String name, IP ip, String desc, Integer port) {
        this.name = name;
        this.ip = ip;
        this.desc = desc;
        this.port = port;
    }

    public ServerSettingsStore(Parcel in) {
        name = in.readString();
        ip = in.readParcelable(IP.class.getClassLoader());
        desc = in.readString();
        port = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeParcelable(ip, flags);
        dest.writeString(desc);
        dest.writeInt(port);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<ServerSettingsStore> CREATOR = new Parcelable.Creator<ServerSettingsStore>() {
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

    public ServerSettingsStore modifyIP(IPPart ipPart, Integer newVal) {
        Integer[] iIP = ip.getInt();
        switch (ipPart) {
            case A:
                ip = new IP(newVal, iIP[1], iIP[2], iIP[3]);
            case B:
                ip = new IP(iIP[0], newVal, iIP[2], iIP[3]);
            case C:
                ip = new IP(iIP[0], iIP[1], newVal, iIP[3]);
            case D:
                ip = new IP(iIP[0], iIP[1], iIP[2], newVal);
        }
        return this;
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

    public int getPort() {
        return port;
    }

    public ServerSettingsStore setPort(int port) {
        this.port = port;
        return this;
    }
}
