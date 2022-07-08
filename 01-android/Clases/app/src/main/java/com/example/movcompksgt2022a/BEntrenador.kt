package com.example.movcompksgt2022a

import android.os.Parcel
import android.os.Parcelable

class BEntrenador(
    var id: Int?,
    var nombre: String?,
    var descripcion: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        //Dice que leer las variables de la clase
        parcel.readInt(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun toString(): String {
        return "${nombre} - ${descripcion}"
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        //Escribir String
        parcel.writeString(nombre)
        parcel.writeString(descripcion)

        if (id != null) {
            parcel.writeInt(id!!)
        }else{
            parcel.writeInt(0)
        }
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BEntrenador> {
        override fun createFromParcel(parcel: Parcel): BEntrenador {
            return BEntrenador(parcel)
        }

        override fun newArray(size: Int): Array<BEntrenador?> {
            return arrayOfNulls(size)
        }
    }
}