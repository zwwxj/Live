package com.my.live.data.entity

import android.os.Parcel
import android.os.Parcelable

/**
 * 请求通知
 * @author caishuzhan
 */
class Req() : Parcelable {
    constructor(parcel: Parcel) : this() {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Req> {
        override fun createFromParcel(parcel: Parcel): Req {
            return Req(parcel)
        }

        override fun newArray(size: Int): Array<Req?> {
            return arrayOfNulls(size)
        }
    }
}