package com.my.live.data.entity

import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class Repos(
    @Json(name = "id")
    var id: Int,
    @Json(name = "name")
    var name: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Repos> {
        override fun createFromParcel(parcel: Parcel): Repos {
            return Repos(parcel)
        }

        override fun newArray(size: Int): Array<Repos?> {
            return arrayOfNulls(size)
        }
    }
}