package com.gitlab.PCU.PCU.helper;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by tim on 13.01.17.
 */

@SuppressWarnings("WeakerAccess")
public final class IP implements Parcelable {
    private final int a;
    private final int b;
    private final int c;
    private final int d;

    public IP(int a, int b, int c, int d) {
        if (!(a >= 0 && a <= 255)) {
            a = 0;
        }
        if (!(b >= 0 && b <= 255)) {
            b = 0;
        }
        if (!(c >= 0 && c <= 255)) {
            c = 0;
        }
        if (!(d >= 0 && d <= 255)) {
            d = 0;
        }
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    protected IP(Parcel in) {
        a = in.readInt();
        b = in.readInt();
        c = in.readInt();
        d = in.readInt();
    }

    public static final Creator<IP> CREATOR = new Creator<IP>() {
        @Override
        public IP createFromParcel(Parcel in) {
            return new IP(in);
        }

        @Override
        public IP[] newArray(int size) {
            return new IP[size];
        }
    };

    public int[] getInt() {
        return new int[]{a, b, c, d};
    }

    @Override
    public String toString() {
        return Integer.toString(a) + "." + Integer.toString(b) + "." + Integer.toString(c) + "." + Integer.toString(d);
    }

    /**
     * Describe the kinds of special objects contained in this Parcelable
     * instance's marshaled representation. For example, if the object will
     * include a file descriptor in the output of {@link #writeToParcel(Parcel, int)},
     * the return value of this method must include the
     * {@link #CONTENTS_FILE_DESCRIPTOR} bit.
     *
     * @return a bitmask indicating the set of special object types marshaled
     * by this Parcelable object instance.
     * @see #CONTENTS_FILE_DESCRIPTOR
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Flatten this object in to a Parcel.
     *
     * @param dest  The Parcel in which the object should be written.
     * @param flags Additional flags about how the object should be written.
     *              May be 0 or {@link #PARCELABLE_WRITE_RETURN_VALUE}.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(a);
        dest.writeInt(b);
        dest.writeInt(c);
        dest.writeInt(d);
    }
}
