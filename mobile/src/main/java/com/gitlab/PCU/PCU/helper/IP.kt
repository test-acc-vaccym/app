package com.gitlab.PCU.PCU.helper

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by tim on 13.01.17.
 */
data class IP : Parcelable {
    private val a: Int?
    private val b: Int?
    private val c: Int?
    private val d: Int?

    constructor(a: Int?, b: Int?, c: Int?, d: Int?) {
        var a = a
        var b = b
        var c = c
        var d = d
        if (!(a >= 0 && a <= 255)) {
            a = 0
        }
        if (!(b >= 0 && b <= 255)) {
            b = 0
        }
        if (!(c >= 0 && c <= 255)) {
            c = 0
        }
        if (!(d >= 0 && d <= 255)) {
            d = 0
        }
        this.a = a
        this.b = b
        this.c = c
        this.d = d
    }

    protected constructor(`in`: Parcel) {}

    override fun writeToParcel(dest: Parcel, flags: Int) {}

    override fun describeContents(): Int {
        return 0
    }

    val int: Array<Int>
        get() = arrayOf<Int>(a, b, c, d)

    override fun toString(): String {
        return Integer.toString(a!!) + "." + Integer.toString(b!!) + "." + Integer.toString(c!!) + "." + Integer.toString(d!!)
    }

    companion object {

        val CREATOR: Parcelable.Creator<IP> = object : Parcelable.Creator<IP> {
            override fun createFromParcel(`in`: Parcel): IP {
                return IP(`in`)
            }

            override fun newArray(size: Int): Array<IP> {
                return arrayOfNulls(size)
            }
        }
    }
}
