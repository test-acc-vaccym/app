package com.github.PCUnlocker.app.helper

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by tim on 19.01.17.
 */
data class ServerSettingsStore(var id: String, var name: String = "No Name", var ip: IP = Defaults.ServerStatic.IP,
                               var desc: String = "No description", var port: Int = 25566) : Parcelable {

    fun modifyIP(ipPart: IPPart, newVal: Int?): ServerSettingsStore {
        val iIP = ip.int
        when (ipPart) {
            ServerSettingsStore.IPPart.A -> {
                ip = IP(newVal!!, iIP[1], iIP[2], iIP[3])
                ip = IP(iIP[0], newVal, iIP[2], iIP[3])
                ip = IP(iIP[0], iIP[1], newVal, iIP[3])
                ip = IP(iIP[0], iIP[1], iIP[2], newVal)
            }
            ServerSettingsStore.IPPart.B -> {
                ip = IP(iIP[0], newVal!!, iIP[2], iIP[3])
                ip = IP(iIP[0], iIP[1], newVal, iIP[3])
                ip = IP(iIP[0], iIP[1], iIP[2], newVal)
            }
            ServerSettingsStore.IPPart.C -> {
                ip = IP(iIP[0], iIP[1], newVal!!, iIP[3])
                ip = IP(iIP[0], iIP[1], iIP[2], newVal)
            }
            ServerSettingsStore.IPPart.D -> ip = IP(iIP[0], iIP[1], iIP[2], newVal!!)
        }
        return this
    }


    enum class IPPart {
        A, B, C, D
    }

    companion object {
        @JvmField val CREATOR: Parcelable.Creator<ServerSettingsStore> = object : Parcelable.Creator<ServerSettingsStore> {
            override fun createFromParcel(source: Parcel): ServerSettingsStore = ServerSettingsStore(source)
            override fun newArray(size: Int): Array<ServerSettingsStore?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(source.readString(), source.readString(), source.readParcelable<IP>(IP::class.java.classLoader), source.readString(), source.readInt())

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(id)
        dest?.writeString(name)
        dest?.writeParcelable(ip, 0)
        dest?.writeString(desc)
        dest?.writeInt(port)
    }
}
