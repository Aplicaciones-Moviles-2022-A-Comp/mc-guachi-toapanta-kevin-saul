package com.example.replicatwitch

import android.os.Parcel
import android.os.Parcelable

data class StreamerData(
    val nombreCanal: String?,
    val titulo: String?,
    val categoria: String?,
    val etiqueta: String?,
    val pathStream: String?,
    val pathLogo: String?
):Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(nombreCanal)
        parcel.writeString(titulo)
        parcel.writeString(categoria)
        parcel.writeString(etiqueta)
        parcel.writeString(pathStream)
        parcel.writeString(pathLogo)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<StreamerData> {
        override fun createFromParcel(parcel: Parcel): StreamerData {
            return StreamerData(parcel)
        }

        override fun newArray(size: Int): Array<StreamerData?> {
            return arrayOfNulls(size)
        }
    }

}