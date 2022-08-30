package com.example.deber02_examensql

import android.os.Parcel
import android.os.Parcelable

class Facultad(
    var cod: String?,
    var nombre: String?,
    var num_carreras: Int?,
    var fecha_fundacion: String?,
    var biblioteca: String?
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString()
    ) {

    }


    override fun toString(): String {
        return "${cod} - ${nombre}"
    }


    override fun writeToParcel(parcel: Parcel, flags: Int) {
        //Escribir String
        parcel.writeString(cod)
        parcel.writeString(nombre)
        parcel.writeInt(num_carreras!!)
        parcel.writeString(fecha_fundacion)
        parcel.writeString(biblioteca)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Facultad> {
        override fun createFromParcel(parcel: Parcel): Facultad {
            return Facultad(parcel)
        }

        override fun newArray(size: Int): Array<Facultad?> {
            return arrayOfNulls(size)
        }


    }
}