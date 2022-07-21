package com.example.examenb1

import android.os.Parcel
import android.os.Parcelable

class Estudiante(
    var id: Int?,
    var nombre: String?,
    var apellido: String?,
    var promedio: String?,
    var edad: Int?,
    var cod: String?,
): Parcelable {
    override fun toString(): String {
        return "${id} - ${nombre} - ${apellido}"
    }

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id!!)
        parcel.writeString(nombre)
        parcel.writeString(apellido)
        parcel.writeString(promedio)
        parcel.writeInt(edad!!)
        parcel.writeString(cod)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Estudiante> {
        override fun createFromParcel(parcel: Parcel): Estudiante {
            return Estudiante(parcel)
        }

        override fun newArray(size: Int): Array<Estudiante?> {
            return arrayOfNulls(size)
        }
    }
}