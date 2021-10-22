package com.example.list_app.entities

import android.os.Parcel
import android.os.Parcelable


class Categoria(nombre: String) :
    Parcelable {
    var nombre: String

    constructor() : this("")

    init {
        this.nombre = nombre!!

    }

    constructor(source: Parcel) : this(
        source.readString()!!

    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(nombre)

    }

    override fun toString(): String {
        return "Categoria(nombre='$nombre')"
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Categoria> = object : Parcelable.Creator<Categoria> {
            override fun createFromParcel(source: Parcel): Categoria = Categoria(source)
            override fun newArray(size: Int): Array<Categoria?> = arrayOfNulls(size)
        }
    }
}