package com.github.PCU.android.helper

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by tim on 13.01.17.
 */
data class IP(val p_a: Int = 0, val p_b: Int = 0, val p_c: Int = 0, val p_d: Int = 0) : Parcelable {
    private var a: Int = p_a

    private var b: Int = p_b

    private var c: Int = p_c

    private var d: Int = p_d

    val int: Array<Int>
        get() = arrayOf<Int>(a, b, c, d)

    companion object {
        @JvmField val CREATOR: Parcelable.Creator<IP> = object : Parcelable.Creator<IP> {
            override fun createFromParcel(source: Parcel): IP = IP(source)
            override fun newArray(size: Int): Array<IP?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(source.readInt(), source.readInt(), source.readInt(), source.readInt())

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeInt(a)
        dest?.writeInt(b)
        dest?.writeInt(c)
        dest?.writeInt(d)
    }
}
